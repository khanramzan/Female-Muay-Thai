package rams.app.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import rams.app.domain.dao.CountryRepository;
import rams.app.domain.model.Country;


@Controller
public class CountryController {

	@Autowired
	CountryRepository cntryRepo;

	@RequestMapping("/fmtkb/showCountryPage")
	public String showCountriesPage(
			@PageableDefault(size = 15) Pageable pageable, Model model) {

		
		model.addAttribute("pages", cntryRepo.count()/pageable.getPageSize());
		
		
		Page<Country> countryPage = cntryRepo.findAll(pageable);

		model.addAttribute("countries", countryPage);

		//model.addAttribute("page", pageable);

		return "/country/countries";
	}

	@RequestMapping("/admin/addCountry/")
	public String addNewCountry(Model model) {
		Country country = new Country();
		String addBtn = "Save";
		model.addAttribute("country", country);
		model.addAttribute("addBtn", addBtn);

		return "/country/addCountry";
	}

	@RequestMapping("/admin/editCountry/")
	public String editCountry(@RequestParam String countryId, Model model) {

		String addBtn = "Edit";

		Country country = cntryRepo.findOne(countryId);

		model.addAttribute("country", country);
		model.addAttribute("addBtn", addBtn);

		return "/country/addCountry";
	}

	@RequestMapping("/admin/saveCountry/")
	public String savecountry(Country country, Model model) {

		Country countryInt = cntryRepo.findOne(country.getCountryId());
		if(country.getCountryId()=="" || country.getCountryId()==null ){
			return "redirect:/fmtkb/showCountryPage";
		}else
		if (countryInt != null) {
			countryInt.setCountry(country.getCountry());
			countryInt.setCountryId(country.getCountryId());
			cntryRepo.save(countryInt);
		} else {
			// countryInt = new Country();
			// countryInt.setCountry(country.getCountry());
			// countryInt.setCountryId(country.getCountryId());
			cntryRepo.save(country);
		}

		return "redirect:/fmtkb/showCountryPage";
	}

	@RequestMapping(value = "/admin/country/deleteCountry/", method = RequestMethod.POST)
	public String deleteFighter(@RequestParam String countryId, Model model) {

		Country country = cntryRepo.findOne(countryId);
		
		country.setFighters(null);
		
		country.setFights(null);
		
		country.setPromoters(null);
		
		cntryRepo.delete(country);

		return "redirect:/fmtkb/showCountryPage";

	}

}
