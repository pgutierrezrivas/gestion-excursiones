package excursionweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import excursionweb.modelo.dao.ExcursionDao;

@Controller
public class HomeController {
	@Autowired //busca clase que implemente este interface, crea el objeto y lo mete en la variable pdao
	private ExcursionDao pdao;
	
	@GetMapping("/")
	public String excursionHome(Model model) {

		model.addAttribute("excursion", pdao.findAll());
		
		return "home";
	}
}
