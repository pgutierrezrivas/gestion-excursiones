package excursionweb.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import excursionweb.modelo.dao.ExcursionDao;
import excursionweb.modelo.javabean.Excursion;

@Controller
@RequestMapping("/excursion")
public class ExcursionController {
	@Autowired
	private ExcursionDao pdao;

	@GetMapping("/detalle/{idExcursion}")
	public String verDetalle(Model model, @PathVariable int idExcursion) {
		
		Excursion excursion = pdao.findById(idExcursion); //busca la excursion utilizando el id que viene en la url
		
		model.addAttribute("excursion", excursion); //añade la excursion encontrada al modelo para que este disponible en la vista
		
		return "verDetalle"; //devuelve la vista (plantilla thymeleaf 'verDetalle')
	}

	@PostMapping("/modificar/{idExcursion}")
	public String procesarFormEdicion(@PathVariable int idExcursion, Excursion excursion, RedirectAttributes ratt) {

		excursion.setIdExcursion(idExcursion); //establece el idExcursion al objeto 'excursion' 

		if (pdao.updateOne(excursion) == 1) { //llama al metodo 'updateOne' del interface para actualizar la excursion y si la actualización es exitosa (devuelve 1)
			ratt.addFlashAttribute("mensaje", "Excursión modificada correctamente"); //addFlashAttribute dura 2 tareas (el mensaje se mantendrá disponible)
		}
		else { //si la actualizacion falla
			ratt.addFlashAttribute("mensaje", "La excursión no se ha podido modificar");
		}
		return "redirect:/"; //aqui no podemos poner el forward:/ ya que no funciona si sale de un post y va a un get
		//redirect rompe el flujo y por ello manejamos RedirectAttribute en lugar de Model
	}

	@GetMapping("/modificar/{idExcursion}")
	public String procesarModificar(Model model, @PathVariable int idExcursion) { 

		Excursion excursion = pdao.findById(idExcursion); //busca la excursion utilizando el id que viene en la url

		if (excursion == null) { //si la excursion no existe, añade un mensaje al modelo indicandolo
			model.addAttribute("mensaje", "La excursión no existe");
			return "forward:/"; //devuelve 'home'
		}

		model.addAttribute("excursion", excursion); //añade la excursion encontrada al modelo para que este disponible en la vista

		return "formEdicionExcursion"; //devuelve la vista que contiene el formulario de edicion
	}

	@GetMapping("/cancelar/{idExcursion}")
	public String cancelarExcursion(Model model, @PathVariable int idExcursion) {

		Excursion excursion = pdao.findById(idExcursion);

		if (excursion == null) {
			model.addAttribute("mensaje", "La excursión no existe");
			return "forward:/";
		}

		else {
			switch (excursion.getEstado()) { //dependiendo del contenido del estado, muestro un mensaje u otro
				case "CREADO":
					excursion.setEstado("CANCELADO");
					model.addAttribute("mensaje", "Excursión cancelada correctamente");
					break;
				case "CANCELADO":
					model.addAttribute("mensaje", "La excursión ya se encuentra CANCELADA");
					break;
				case "TERMINADO":
					model.addAttribute("mensaje", "Excursión no cancelada por encontrarse TERMINADA");
					break;
			}
		}

		return "forward:/"; 
	}

	@GetMapping("/creados")
	public String listarCreados(Model model) {

		List<Excursion> creados = pdao.findByActivos(); //llama al metodo 'findByActivos' del interface para obtener una lista de excursiones con estado 'creado'

		model.addAttribute("excursion", creados); //añade la lista de excursiones con estado 'creado' al modelo

		return "listarActivos";
	}

	@GetMapping("/destacados")
	public String listarDestacados(Model model) { //misma mecanica que '/creados'

		List<Excursion> destacados = pdao.findByDestacados();

		model.addAttribute("excursion", destacados);

		return "listarDestacados";
	}

	@GetMapping("/terminados")
	public String listarTerminados(Model model) {

		List<Excursion> terminados = new ArrayList<>();

		for (Excursion ele: pdao.findAll()) {

			if (ele.getEstado().equals("TERMINADO")) { //si el estado es 'terminado'
				terminados.add(ele); //lo añadimos a nuestra lista de terminados
			}
		}
		model.addAttribute("excursion", terminados);

		return "listarTerminados";
	}

	@PostMapping("/alta")
	public String procesarFormAlta(Excursion excursion, RedirectAttributes ratt) {

		if (pdao.insertOne(excursion) == 1) { //insertOne devuelve 1 si la excursión se inserta correctamente
			ratt.addFlashAttribute("mensaje", "Alta de excursión realizada correctamente");
		}
		else { //y si falla
			ratt.addFlashAttribute("mensaje", "Alta de excursión no realizada");
		}

		return "redirect:/"; 
	}

	@GetMapping("/alta")
	public String procesarAlta(Model model) {
        return "formAltaExcursion"; //nos lleva al formulario de alta
	}

	@InitBinder //se pone siempre en los metodos donde haya conversion de fechas
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
}
