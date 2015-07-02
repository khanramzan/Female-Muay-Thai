package rams.app.security;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import rams.app.domain.dao.UserRepository;
import rams.app.domain.model.Role;
import rams.app.domain.model.User;

/**
 * @author Rob Winch
 *
 */
@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException("Could not find user " + username);
        }
        return new UserRepositoryUserDetails(user);
    }

    private final static class UserRepositoryUserDetails extends User implements UserDetails {

    	User account;
        private UserRepositoryUserDetails(User user) {
            super(user);
            account=user;
        }
        
        
        
        
        

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            
        	Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
            for (Role r :account.getRoles()) {
                authorities.add(new GrantedAuthorityImpl(r.getRole()));
            }
            return authorities;
        	
        }
        
       
       

        @Override
        public String getUsername() {
            return getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return account.isEnabled();
        }

        private static final long serialVersionUID = 5639683223516504866L;
    }
}

