package rams.app.mvc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import rams.app.domain.dao.PromoterRepository;
import rams.app.domain.model.Fighter;
import rams.app.domain.model.Promoter;
import rams.app.mvc.controller.modal.PromoterModel;

@Controller
public class PromoterController {

	@Autowired
	PromoterRepository promoterRepo;
	
	@RequestMapping(value = "/admin/promoters.php", method = RequestMethod.GET)
	@ResponseBody
	public List<PromoterModel> getPromoters(Model model,
			@RequestParam String term) {
		
		List<Promoter> promoters = promoterRepo.findByPromoterNameContaining(term);
		
		List<PromoterModel> pmList = new ArrayList<PromoterModel>();
		
		PromoterModel pm = null;
		
		for(Promoter p : promoters){
			pm = new PromoterModel();
			pm.setValue(p.getPromoterId());
			pm.setLabel(p.getPromoterName());
			pmList.add(pm);
		}
		
		return pmList;
	}
	
	
	@RequestMapping(value = "/admin/addPromoter", method = RequestMethod.GET)
	public String addFightTypes(Model model) {
		
		model.addAttribute("addBtn", "Edit");
		Promoter promoter = new Promoter();
		model.addAttribute("promoter", promoter);
		
		return "promoter/addPromoter";
	}
	
	@RequestMapping(value = "/admin/savePromoter/", method = RequestMethod.POST)
	public String savePromoter(@ModelAttribute Promoter promoter, Model model){
		String myStr = "asdfasd";
		
		if(promoter.getPromoterId()!=null){
			Promoter promoterInt = promoterRepo.findOne(promoter.getPromoterId());

			if (promoterInt != null) {
				promoterInt.setPromoterName(promoter.getPromoterName());
				promoterInt.setCountryBean(promoter.getCountryBean());
				promoterInt.setPromoterComments(promoter.getPromoterComments());
				promoterRepo.save(promoterInt);
			}
		} else

			promoterRepo.save(promoter);

		
		return "uploaded";
	}
	
	
}
