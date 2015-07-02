package rams.app.mvc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import rams.app.domain.dao.FightTypeRepository;
import rams.app.domain.model.Country;
import rams.app.domain.model.Fight;
import rams.app.domain.model.FightType;
import rams.app.domain.model.Fighter;
import rams.app.mvc.controller.modal.CountryModel;
import rams.app.mvc.controller.modal.FightTypeModel;

@Controller
public class FightTypeController {

	@Autowired
	FightTypeRepository fightTypeRepo;

	@RequestMapping(value = "/admin/fightTypes.php", method = RequestMethod.GET)
	@ResponseBody
	public List<FightTypeModel> getCountries(Model model,
			@RequestParam String term) {
		List<FightType> fightTypes = fightTypeRepo
				.findFightTypeByFightTypeContaining(term);

		List<FightTypeModel> ftmList = new ArrayList<FightTypeModel>();

		FightTypeModel ftm = null;

		for (FightType c : fightTypes) {
			ftm = new FightTypeModel();
			ftm.setValue(c.getFightTypeId());
			ftm.setLabel(c.getFightType());
			ftmList.add(ftm);

		}

		return ftmList;
	}

	@RequestMapping(value = "/admin/addFightType", method = RequestMethod.GET)
	public String addFightTypes(Model model) {
		//FightType fightType = new FightType();

		model.addAttribute("addBtn", "Save");

		model.addAttribute("fightType1", new FightType());

		return "fightType/addFightType";
	}

	@RequestMapping(value = "/admin/saveFightType/", method = RequestMethod.POST)
	public String saveFightType( @ModelAttribute("fightType1") FightType fightType, Model model) {
		
		if (fightType.getFightTypeId() != null) {
			FightType fightTypeInt = fightTypeRepo.findOne(fightType
					.getFightTypeId());

			if (fightTypeInt != null) {
				fightTypeInt.setFightType(fightType.getFightType());
				fightTypeInt.setFightTypeComments(fightType.getFightTypeComments());
				fightTypeInt.setFightTypecDescription(fightType.getFightTypecDescription());
				fightTypeRepo.save(fightTypeInt);
			}
		} else

			fightTypeRepo.save(fightType);

		return "uploaded";
	}
	
	
	
	@RequestMapping("/admin/editFightType/")
	public String editFightType(@RequestParam Long fightTypeId, Model model) {

		model.addAttribute("fightType1",fightTypeRepo.findOne(fightTypeId));
		
		model.addAttribute("addBtn", "Edit");
	
		return "fightType/addFightType";
	}

	
	@RequestMapping(value = "/admin/fightType/deleteFightType/", method = RequestMethod.POST)
	public String deleteFightType(@RequestParam Long fightTypeId, Model model) {

		FightType fightType = fightTypeRepo.findOne(fightTypeId);
		List<Fight> fights1 = fightType.getFights();
		
		for(Fight f:fights1){
			f.setFightTypeBean(null);
			
		}
		
		fightTypeRepo.delete(fightType);
		
		return "uploaded";
		
	}
	
	@RequestMapping(value = "/admin/fightType/showFightTypes/", method = RequestMethod.GET)
	public String showAllFightTypes(@PageableDefault(size = 15) Pageable pageable, Model model) {
		
		model.addAttribute("pages", fightTypeRepo.count()/pageable.getPageSize());
		
		Page<FightType> fightTypePage = fightTypeRepo.findAll(pageable);
		
		model.addAttribute("fightTypes", fightTypePage);
		
		return "fightType/showAllFightTypes";
	}

}
