package rams.app.mvc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import rams.app.domain.dao.CountryRepository;
import rams.app.domain.dao.FightRepository;
import rams.app.domain.dao.FightResultRepository;
import rams.app.domain.dao.FightTypeRepository;
import rams.app.domain.dao.FighterRepository;
import rams.app.domain.dao.MediaTableRepository;
import rams.app.domain.dao.PromoterRepository;
import rams.app.domain.dao.PromotionRepository;
import rams.app.domain.dao.SanctionerRepository;
import rams.app.domain.dao.WeightCategoryRepository;
import rams.app.domain.model.Country;
import rams.app.domain.model.Fight;
import rams.app.domain.model.FightType;
import rams.app.domain.model.Fighter;
import rams.app.domain.model.MediaTable;
import rams.app.domain.model.Promoter;
import rams.app.domain.model.Promotion;
import rams.app.domain.model.Sanctioner;
import rams.app.domain.model.WeightCategory;
import rams.app.mvc.controller.modal.MediaTableModel;



@Controller
public class FightController {

	@Autowired
	private FightRepository fightRepo;
	
	@Autowired
	private CountryRepository cRepo;
	
	@Autowired
	private MediaTableRepository mtRepo;
	
	@Autowired
	private FighterRepository fighterRepo;
	
	@Autowired
	private FightResultRepository fResRepo;
	
	@Autowired
	private FightTypeRepository fTypeRepo;
	
	@Autowired
	private PromoterRepository promoterRepo;
	
	@Autowired
	private PromotionRepository promotionRepo;
	
	@Autowired
	private SanctionerRepository sRepo;
	
	@Autowired
	private WeightCategoryRepository wRepo;
	
	
	

	@RequestMapping("/fmtkb/upcomingFights")
	public String upcomingFights(ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.HOUR_OF_DAY, 0); // anything 0 - 23
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date d1 = c.getTime();

		List<Fight> fightsAfterToday = fightRepo.findByFightDateIsAfter(d1);
		model.addAttribute("fights", fightsAfterToday);

		return "/fight/upcomingFight";

	}

	@RequestMapping("/fmtkb/fightResults")
	public String fightResults(ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.HOUR_OF_DAY, 0); // anything 0 - 23
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date d1 = c.getTime();

		List<Fight> fightsBeforeToday = new ArrayList<Fight>();
		fightsBeforeToday = fightRepo
				.findByFightDateIsBeforeOrderByFightDateDesc(d1);
		model.addAttribute("fights", fightsBeforeToday);

		return "/fight/upcomingFight";

	}
	
	@RequestMapping("/admin/saveFight")
	public String saveFight(Model model, Fight fight){
		Fight fightInt = fightRepo.findOne(fight.getFightId());

		if (fightInt != null) {
			fightInt.setCountryBean(fight.getCountryBean());
			fightInt.setFightComments(fight.getFightComments());
			fightInt.setFightDate(fight.getFightDate());
			fightInt.setFighter1Bean(fight.getFighter1Bean());
			fightInt.setFighter2Bean(fight.getFighter2Bean());
			fightInt.setFightId(fight.getFightId());
			fightInt.setFightResult(fight.getFightResult());
			fightInt.setFightTypeBean(fight.getFightTypeBean());
			fightInt.setMediaTables(fight.getMediaTables());
			fightInt.setPromotionBean(fight.getPromotionBean());
			fightInt.setSanctionerBean(fight.getSanctionerBean());
			fightInt.setWeightCategoryBean(fight.getWeightCategoryBean());
			
			
			fightRepo.save(fightInt);
		} else
			fightRepo.save(fight);

		return "redirect:/uploaded";
	}
	
	@RequestMapping("/admin/addFight")
	public String addNewFighter(Model model) {

		String addBtn = "Save";
		
		Fight fight = new Fight();
		List<Country> countries = cRepo.findAll();
		
		List<Fighter> fighters = fighterRepo.findAll();
		
		List<FightType> fightTypes = fTypeRepo.findAll();
		
		List<MediaTable> fightMedia = mtRepo.findAll();
		
		List<Promoter> promoters = promoterRepo.findAll();
		
		List<Promotion> promotions = promotionRepo.findAll();
		
		List<Sanctioner> sanctioners = sRepo.findAll();
		
		List<WeightCategory> weightCategories = wRepo.findAll();
		
		model.addAttribute("addBtn", addBtn);

		model.addAttribute("countries", countries);
		
		//fighter.setCountryBean(countryBean);
		
		MediaTableModel mtm1 = null;
		
		List<MediaTableModel> mtModel1 = new ArrayList<MediaTableModel>();

		for (MediaTable c : fightMedia) {

			mtm1 = new MediaTableModel();
			mtm1.setMediaTableId(c.getMediaTableId());

			mtm1.setClassic(c.getClassic());

			mtm1.setMediaComments(c.getMediaComments());
			mtm1.setMediaDate(c.getMediaDate());
			mtm1.setMediaOnServer(c.getMediaOnServer());
			mtm1.setStrImg(new sun.misc.BASE64Encoder().encode(c
					.getMediaOnServer()));
			mtModel1.add(mtm1);
		}

		model.addAttribute("mtModel1", mtModel1);

		model.addAttribute("fight", fight);
		
		model.addAttribute("fighters", fighters);
		model.addAttribute("fightTypes",fightTypes);
		//model.addAttribute("fightMedia",fightMedia);
		model.addAttribute("promoters", promoters);
		model.addAttribute("promotions",promotions);
		model.addAttribute("sanctioners",sanctioners);
		model.addAttribute("weightCategories",weightCategories);
		
		
		return "/fight/addFight";
	}
	

}
