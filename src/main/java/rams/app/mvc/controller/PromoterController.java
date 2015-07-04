package rams.app.mvc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import rams.app.domain.dao.MediaTableRepository;
import rams.app.domain.dao.PromoterRepository;
import rams.app.domain.dao.PromotionRepository;
import rams.app.domain.model.Country;
import rams.app.domain.model.Fighter;
import rams.app.domain.model.MediaTable;
import rams.app.domain.model.Promoter;
import rams.app.domain.model.Promotion;
import rams.app.mvc.controller.modal.MediaTableModel;
import rams.app.mvc.controller.modal.PromoterModel;

@Controller
public class PromoterController {

	@Autowired
	PromoterRepository promoterRepo;

	@Autowired
	MediaTableRepository mtRepo;

	@Autowired
	PromotionRepository promotionRepo;

	@RequestMapping(value = "/admin/promoters.php", method = RequestMethod.GET)
	@ResponseBody
	public List<PromoterModel> getPromoters(Model model,
			@RequestParam String term) {

		List<Promoter> promoters = promoterRepo
				.findByPromoterNameContaining(term);

		List<PromoterModel> pmList = new ArrayList<PromoterModel>();

		PromoterModel pm = null;

		for (Promoter p : promoters) {
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
	public String savePromoter(@ModelAttribute Promoter promoter, Model model) {

		if (promoter.getPromoterId() != null) {
			Promoter promoterInt = promoterRepo.findOne(promoter
					.getPromoterId());

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

	@RequestMapping("/admin/editPromoter")
	public String editPromoter(@RequestParam Long promoterId, Model model,
			@PageableDefault(size = 15) Pageable pageable) {

		String addBtn = "Edit";

		Page<MediaTable> mTable;// = new ArrayList<MediaTable>();

		Promoter promoter = promoterRepo.findOne(promoterId);

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

		List<MediaTable> mtList = promoter.getMediaTables();
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

		model.addAttribute("promoter", promoter);

		return "promoter/addPromoter";

	}

	@RequestMapping(value = "/admin/promoter/{promoterId}/addImage/{mediaTableId}", method = RequestMethod.POST)
	public String addImage(Model model,
			@PathVariable("promoterId") Long promoterId,
			@PathVariable("mediaTableId") Long mediaTableId) {

		MediaTable mt = mtRepo.findOne(mediaTableId);
		Promoter promoter = promoterRepo.findOne(promoterId);
		List<Promoter> promoters = mt.getPromoters();
		promoters.add(promoter);
		mt.setPromoters(promoters);
		try {
			// Try to insert your entity by calling persist method
			mtRepo.save(mt);
		} catch (EntityExistsException e) {

			throw new EntityExistsException(
					"This image is already assigned to this category please select other");

		} catch (DataIntegrityViolationException e) {

			throw new EntityExistsException(
					"This image is already assigned to this category please select other");

		}

		// model.addAttribute("fighter", fighter);

		return "/fighter/imageThumbs";

	}

	@RequestMapping(value = "/admin/promoter/deletePromoter/", method = RequestMethod.POST)
	public String deletePromoter(@RequestParam Long promoterId, Model model) {

		Promoter promoter = promoterRepo.findOne(promoterId);

		promoter.setCountryBean(null);

		// List<MediaTable> mtable = promoter.getMediaTables();

		List<Promotion> promotions = promoter.getPromotions();

		for (Promotion p : promotions) {
			promotionRepo.delete(p);
		}

		promoter.setMediaTables(null);

		promoter.setPromotions(null);

		promoterRepo.delete(promoter);

		return "redirect:/admin/promoter/showPromoters/";
	}

	@RequestMapping(value = "/admin/promoter/showPromoters/", method = RequestMethod.GET)
	public String showPromoter(Model model,
			@PageableDefault(size = 15) Pageable pageable) {
		
		model.addAttribute("pages",promoterRepo.count()/pageable.getPageSize());
		

		Page<Promoter> promoterPage = promoterRepo.findAll(pageable);
		
		model.addAttribute("promoters", promoterPage);
		
		
		
		return "/promoter/showPromoters";
	}

}
