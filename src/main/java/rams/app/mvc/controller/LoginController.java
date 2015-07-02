package rams.app.mvc.controller;

import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import rams.app.domain.dao.UserRepository;
import rams.app.domain.model.User;
import rams.app.domain.model.VerificationToken;
import rams.app.domain.service.IUserService;
import rams.app.domain.service.UserDto;
import rams.app.domain.model.PasswordResetToken;
import rams.app.registration.OnRegistrationCompleteEvent;
import rams.app.validation.EmailExistsException;
import rams.app.web.error.InvalidOldPasswordException;
import rams.app.web.error.UserAlreadyExistException;
import rams.app.web.error.UserNotFoundException;
import rams.app.web.util.GenericResponse;

@Controller
public class LoginController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private IUserService userService;

	@Autowired
	private MessageSource messages;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private Environment env;

//	@RequestMapping("/error")
//	  public String error(HttpServletRequest request, Model model) {
//	    model.addAttribute("errorCode", request.getAttribute("javax.servlet.error.status_code"));
//	    Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
//	    String errorMessage = null;
//	    if (throwable != null) {
//	      errorMessage = throwable.getMessage();
//	    }
//	    model.addAttribute("errorMessage", errorMessage);
//	    return "error";
//	  }
	
	@RequestMapping("/admin")
	public String admin(Model model,final Locale locale) {
		return "/admin/admin";
	}
	
	@RequestMapping("/login")
	public String login(Model model,final Locale locale) {
		return "/admin/loginScreen";
	}

	// Login form with error
	@RequestMapping("/login-error")
	public String loginError(Model model,final Locale locale) {
		model.addAttribute("loginError", true);
		return "/admin/loginScreen";
	}
	

	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public String home(Model model,final Locale locale) {

		return "/admin/home";

	}

	@RequestMapping(value = "/signup")
	public String signupForm(Model model,final Locale locale) {
		model.addAttribute("user", new UserDto());
		model.addAttribute("alerts", "All fields are required.");

		return "/user/signup";

	}
	
	@RequestMapping(value = "/badUser")
	public String badUser(Model model,final Locale locale) {

		return "/user/badUser";

	}
	
	
	@RequestMapping(value = {"/forgotPassword"}, method=RequestMethod.GET)
	public String forgotPassword(Model model,final Locale locale) {

		return "/user/forgotPassword";

	}
	
	@RequestMapping(value = {"/changePassword"}, method=RequestMethod.GET)
	public String changePassword(Model model,final Locale locale) {

		return "/user/resetPassword";

	}
	

	@RequestMapping(value = {"/successRegister"}, method=RequestMethod.GET)
	public String successRegister(Model model ,final Locale locale) {

		return "/user/successRegister";

	}


    @RequestMapping(value = "/user/registration", method = RequestMethod.POST)
    @ResponseBody
    public GenericResponse registerUserAccount(@Valid final UserDto accountDto, final HttpServletRequest request) {
        //LOGGER.debug("Registering user account with information: {}", accountDto);

        final User registered = createUserAccount(accountDto);
        if (registered == null) {
            throw new UserAlreadyExistException();
        }
        final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));

        return new GenericResponse("success");
    }

    @RequestMapping(value = "/regitrationConfirm", method = RequestMethod.GET)
    public String confirmRegistration(final Locale locale, final Model model, @RequestParam("token") final String token) {
        final VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            final String message = messages.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            return "redirect:/badUser?lang=" + locale.getLanguage();
        }

        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            model.addAttribute("message", messages.getMessage("auth.message.expired", null, locale));
            model.addAttribute("expired", true);
            model.addAttribute("token", token);
            return "redirect:/badUser?lang=" + locale.getLanguage();
        }

        user.setEnabled(true);
        userService.saveRegisteredUser(user);
        model.addAttribute("message", messages.getMessage("message.accountVerified", null, locale));
        return "redirect:/successRegister?lang=" + locale.getLanguage();
    }
    
    

    // user activation - verification

    @RequestMapping(value = "/user/resendRegistrationToken", method = RequestMethod.GET)
    @ResponseBody
    public GenericResponse resendRegistrationToken(final HttpServletRequest request, @RequestParam("token") final String existingToken) {
        final VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
        final User user = userService.getUser(newToken.getToken());
        final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        final SimpleMailMessage email = constructResendVerificationTokenEmail(appUrl, request.getLocale(), newToken, user);
        mailSender.send(email);

        return new GenericResponse(messages.getMessage("message.resendToken", null, request.getLocale()));
    }

    // Reset password

    @RequestMapping(value = "/user/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public GenericResponse resetPassword(final HttpServletRequest request, @RequestParam("email") final String userEmail) {
        final User user = userService.findUserByEmail(userEmail);
        if (user == null) {
            throw new UserNotFoundException();
        }

        final String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);
        final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        final SimpleMailMessage email = constructResetTokenEmail(appUrl, request.getLocale(), token, user);
        mailSender.send(email);
        return new GenericResponse(messages.getMessage("message.resetPasswordEmail", null, request.getLocale()));
    }

    @RequestMapping(value = "/user/changePassword", method = RequestMethod.GET)
    public String showChangePasswordPage(final Locale locale, final Model model, @RequestParam("id") final long id, @RequestParam("token") final String token) {
        final PasswordResetToken passToken = userService.getPasswordResetToken(token);
        final User user = passToken.getUser();
        if (passToken == null || user.getUserId() != id) {
            final String message = messages.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            return "redirect:/login?lang=" + locale.getLanguage();
        }

        final Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            model.addAttribute("message", messages.getMessage("auth.message.expired", null, locale));
            return "redirect:/login?lang=" + locale.getLanguage();
        }

        final Authentication auth = new UsernamePasswordAuthenticationToken(user, null, userDetailsService.loadUserByUsername(user.getEmail()).getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        return "redirect:/updatePassword?lang=" + locale.getLanguage();
    }
    @RequestMapping(value="/updatePassword")
    public String updatePassword(Model model,final Locale locale){
    	
    	return "/user/updatePassword";
    }
    
    @RequestMapping(value = "/user/savePassword", method = RequestMethod.POST)
   // @PreAuthorize("hasRole('READ_PRIVILEGE')")
    @ResponseBody
    public GenericResponse savePassword(final Locale locale, @RequestParam("password") final String password) {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.changeUserPassword(user, password);
        return new GenericResponse(messages.getMessage("message.resetPasswordSuc", null, locale));
    }

    // change user password

    @RequestMapping(value = "/user/updatePassword", method = RequestMethod.POST)
    //@PreAuthorize("hasRole('READ_PRIVILEGE')")
    @ResponseBody
    public GenericResponse changeUserPassword(final Locale locale, @RequestParam("password") final String password, @RequestParam("oldpassword") final String oldPassword) {
        final User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!userService.checkIfValidOldPassword(user, oldPassword)) {
            throw new InvalidOldPasswordException();
        }
        userService.changeUserPassword(user, password);
        return new GenericResponse(messages.getMessage("message.updatePasswordSuc", null, locale));
    }
   


    // NON-API

    private final SimpleMailMessage constructResendVerificationTokenEmail(final String contextPath, final Locale locale, final VerificationToken newToken, final User user) {
        final String confirmationUrl = contextPath + "/regitrationConfirm?token=" + newToken.getToken();
        final String message = messages.getMessage("message.resendToken", null, locale);
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject("Resend Registration Token");
        email.setText(message + " \r\n" + confirmationUrl);
        email.setTo(user.getEmail());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

    private final SimpleMailMessage constructResetTokenEmail(final String contextPath, final Locale locale, final String token, final User user) {
        final String url = contextPath + "/user/changePassword?id=" + user.getUserId() + "&token=" + token;
        final String message = messages.getMessage("message.resetPassword", null, locale);
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Reset Password");
        email.setText(message + " \r\n" + url);
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

    private User createUserAccount(final UserDto accountDto) {
        User registered = null;
        try {
            registered = userService.registerNewUserAccount(accountDto);
        } catch (final EmailExistsException e) {
            return null;
        }
        return registered;
    }
}
