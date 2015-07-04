package rams.app.mvc.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import rams.app.domain.dao.CountryRepository;
import rams.app.domain.dao.FightRepository;
import rams.app.domain.dao.FightResultRepository;
//import rams.app.domain.MediaTable;
import rams.app.domain.dao.FighterRepository;
import rams.app.domain.dao.MediaTableRepository;
import rams.app.domain.model.Country;
import rams.app.domain.model.Fight;
import rams.app.domain.model.FightResult;
import rams.app.domain.model.FightType;
import rams.app.domain.model.Fighter;
import rams.app.domain.model.MediaTable;
import rams.app.domain.service.MediaTableService;
import rams.app.mvc.controller.modal.CountryModel;
import rams.app.mvc.controller.modal.MediaTableModel;

@Controller
public class FighterController {

	@Autowired
	FighterRepository fighterRepository;

	@Autowired
	MediaTableRepository mtRepo;

	@Autowired
	CountryRepository cRepo;

	@Autowired
	FightResultRepository frRepo;

	@Autowired
	FightRepository ftRepo;

	@RequestMapping("/fmtkb/greeting")
	public String greetings(Model model, Locale locale) {

		return "index";

	}

	@RequestMapping(value = "/admin/customerFile", method = RequestMethod.POST)
	public String handleFileUpload(@RequestParam("myFile") MultipartFile file) {
		if (!file.isEmpty()) {
			MediaTable mt = new MediaTable();

			String name = file.getName();
			try {
				byte[] bytes = file.getBytes();
				// BufferedOutputStream stream = new BufferedOutputStream(
				// new FileOutputStream(new File("RamazanFolder/"+name +
				// "-uploaded.jpg")));
				// stream.write(bytes);
				// stream.close();
				mt.setMediaOnServer(bytes);
				// ms.save(mt);
				mtRepo.save(mt);

				return "uploadSuccess";
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}

		} else {
			return "The selected file was empty and could not be uploaded.";
		}
	}

	@RequestMapping("/admin/addFighter")
	public String addFighter(Model model, Locale locale) {
		String addBtn = "Save";
		
		Fighter fighter = new Fighter();
		

		model.addAttribute("addBtn", addBtn);

		
		model.addAttribute("fighter", fighter);

		return "fighter/addFighter";

	}

	@RequestMapping(value="/admin/countries.php", method = RequestMethod.GET)
	@ResponseBody
	public List<CountryModel> getCountries(Model model,
			@RequestParam String term) {
		List<Country> countries = cRepo.findCountriesByCountryContaining(term);

		List<CountryModel> cmList = new ArrayList<CountryModel>();

		CountryModel cm = null;

		for (Country c : countries) {
			cm = new CountryModel();
			cm.setValue(c.getCountryId());
			cm.setLabel(c.getCountry());
			cmList.add(cm);
		}

		return cmList;
	}

	@RequestMapping("/admin/editFighter")
	public String editFighter(@RequestParam Long fighterId, Model model,@PageableDefault(size = 15) Pageable pageable) {

		String addBtn = "Edit";

		Page<MediaTable> mTable;// = new ArrayList<MediaTable>();

		Fighter fighter = fighterRepository.findOne(fighterId);

		mTable = mtRepo.findAll(pageable);

		MediaTableModel mtm1 = null;

		List<MediaTableModel> mtModel1 = new ArrayList<MediaTableModel>();

		for (MediaTable c : mTable) {

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

		List<Country> countries = cRepo.findAll();

		List<MediaTable> mtList = fighter.getMediaTables();
		if (mtList != null) {
			List<MediaTableModel> mtModel = new ArrayList<MediaTableModel>();

			MediaTableModel mtm = null;

			for (MediaTable c : mtList) {
				mtm = new MediaTableModel();
				mtm.setMediaTableId(c.getMediaTableId());

				mtm.setClassic(c.getClassic());

				mtm.setMediaComments(c.getMediaComments());
				mtm.setMediaDate(c.getMediaDate());
				mtm.setMediaOnServer(c.getMediaOnServer());
				mtm.setStrImg(new sun.misc.BASE64Encoder().encode(c
						.getMediaOnServer()));
				mtModel.add(mtm);
			}

			model.addAttribute("mtModel", mtModel);
		}
		
		model.addAttribute("addBtn", addBtn);
		
		model.addAttribute("fighter", fighter);
		
		return "/fighter/addFighter";
	}

	@RequestMapping("/admin/saveFighter")
	public String saveFighter(Fighter fighter, Model model) {

		if (fighter.getFighterId() != null) {
			Fighter fighterInt = fighterRepository.findOne(fighter
					.getFighterId());

			if (fighterInt != null) {
				fighterInt.setFighterName(fighter.getFighterName());
				fighterInt.setFighterClub(fighter.getFighterClub());
				fighterInt.setCountryBean(fighter.getCountryBean());
				fighterInt.setFighterComments(fighter.getFighterComments());
				fighterRepository.save(fighterInt);
			}
		} else

			fighterRepository.save(fighter);

		return "redirect:/admin/fighter/showFighter/";
	}

	@RequestMapping(value = "/admin/fighter/deleteFighter/", method = RequestMethod.POST)
	public String deleteFighter(@RequestParam Long fighterId, Model model) {

		Fighter fighter = fighterRepository.findOne(fighterId);

		fighter.setCountryBean(null);
		List<Fight> fights1 = fighter.getFights1();
		List<MediaTable> mtable = null;
		List<MediaTable> mt1 = fighter.getMediaTables();

		for (MediaTable m : mt1) {
			m.setFighters(null);
		}

		FightResult fr = null;

		for (Fight f1 : fights1) {
			f1.setCountryBean(null);
			f1.setFighter1Bean(null);
			f1.setFighter2Bean(null);

			fr = f1.getFightResult();

			// fr1.setFight(null);
			frRepo.delete(fr);

			// f1.setFightResults(null);
			f1.setFightTypeBean(null);
			f1.setPromotionBean(null);
			f1.setSanctionerBean(null);
			f1.setWeightCategoryBean(null);
			mtable = f1.getMediaTables();

			for (MediaTable m : mtable) {
				m.setFights(null);
			}
			ftRepo.delete(f1);

		}
		List<Fight> fights2 = fighter.getFights2();

		for (Fight f1 : fights2) {
			f1.setCountryBean(null);
			f1.setFighter1Bean(null);
			f1.setFighter2Bean(null);

			fr = f1.getFightResult();

			frRepo.delete(fr);

			// f1.setFightResults(null);
			f1.setFightTypeBean(null);
			f1.setPromotionBean(null);
			f1.setSanctionerBean(null);
			f1.setWeightCategoryBean(null);
			mtable = f1.getMediaTables();

			for (MediaTable m : mtable) {
				m.setFights(null);
			}
			ftRepo.delete(f1);

		}

		fighterRepository.delete(fighter);

		return "redirect:/admin/fighter/showFighter/";
	}
	
	
	@RequestMapping(value = "/admin/fighter/{fighterId}/addImage/{mediaTableId}", method = RequestMethod.POST)
	public String addImage(Model model,
			@PathVariable("fighterId") Long fighterId,
			@PathVariable("mediaTableId") Long mediaTableId) {

		MediaTable mt = mtRepo.findOne(mediaTableId);
		Fighter fighter = fighterRepository.findOne(fighterId);
		List<Fighter> fighters = mt.getFighters();
		fighters.add(fighter);
		mt.setFighters(fighters);
		try{
	        //Try to insert your entity by calling persist method 
			mtRepo.save(mt);
	    }
	    catch(EntityExistsException e){
	        
	    	throw new EntityExistsException("This image is already assigned to this category please select other");
	    	
	    }
		catch (DataIntegrityViolationException e) {
			
			throw new EntityExistsException("This image is already assigned to this category please select other");
	    	
		}
		

		// model.addAttribute("fighter", fighter);

		return "/fighter/imageThumbs";

	}
	
	
	@RequestMapping(value = "/admin/fighter/showFighter/", method = RequestMethod.GET)
	public String showAllFightTypes(@PageableDefault(size = 15) Pageable pageable, Model model) {
		
		model.addAttribute("pages", fighterRepository.count()/pageable.getPageSize());
		
		Page<Fighter> fighterPage = fighterRepository.findAll(pageable);
		
		model.addAttribute("fighters", fighterPage);
		
		return "fighter/showAllFighers";
	}

}
