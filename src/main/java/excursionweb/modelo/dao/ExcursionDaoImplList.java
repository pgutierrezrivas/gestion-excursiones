package excursionweb.modelo.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

import excursionweb.modelo.javabean.Excursion;

@Repository
public class ExcursionDaoImplList implements ExcursionDao{
	private List<Excursion> lista;

	public ExcursionDaoImplList() {
		lista = new ArrayList<>();
		cargarDatos(); //aqui cargo los datos que he creado
		checkAllOptions(); //aqui uso el metodo para comprobar las opciones de toda la lista cada vez que cargo los datos
	}

	private void cargarDatos() { //simulacion de mi "bbdd"
		lista.add(new Excursion(1001, "Viaje a Orense", "Madrid", "Orense", getDateByString("01-12-2024"), 4, "estado_incorrecto", "S", 26, 10, 250, "orense_viaje.jpg", new Date())); //aqui pruebo metodo checkEstado por introducir un estado no permitido 
		lista.add(new Excursion(1002, "Viaje a Oporto", "Lugo", "Oporto", getDateByString("06-12-2024"), 7, "CREADO", "S" , 30, 12, 500, "oporto_viaje.jpg", new Date()));
		lista.add(new Excursion(1003, "Viaje a Oviedo", "Soria", "Oviedo", getDateByString("14-09-2024"), 5, "CREADO", "N" , 20, 10, 340, "oviedo_viaje.jpg", new Date())); //aqui pruebo metodo checkEstado por fechaExcursion < today
		lista.add(new Excursion(1004, "Viaje a París", "Barcelona", "París", getDateByString("25-10-2024"), 10, "CANCELADO", "destacado_incorrecto" , 42, 20, 1950, "paris_viaje.jpg", new Date())); //aqui pruebo metodo checkDestacado 
	}

	//metodo para verificar el estado de una excursion
	private void checkEstado(Excursion excursion) {
		Date today = new Date();

		//compruebo que el estado introducido sea uno de los permitidos
		if (!excursion.getEstado().equals("CREADO") &&
				!excursion.getEstado().equals("TERMINADO") &&
		        !excursion.getEstado().equals("CANCELADO")) {
		        //y si no, lo establezco por defecto como 'creado'
		        excursion.setEstado("CREADO"); //(y si ya lo quieren con otro estado lo modifican)
		    }

		//y tambien compruebo que si la fecha de excursion es anterior a la fecha de hoy y ademas su valor='creado' (si es terminado o cancelado no se toca), lo establezco como 'terminado'
		if (excursion.getFechaExcursion().compareTo(today) < 0 && excursion.getEstado().equals("CREADO")) {
			excursion.setEstado("TERMINADO");
		}
	}

	//metodo para verificar el destacado de una excursion
	private void checkDestacado(Excursion excursion) {
		//compruebo que el destacado introducido sea uno de los dos permitidos
		if (!excursion.getDestacado().equals("S") && !excursion.getDestacado().equals("N")) {
			//y si no, lo establezco por defecto como 'N'
			excursion.setDestacado("N");
		}
	}

	//aqui uso los dos metodos de comprobación (estado y destacado) para comprobar toda la lista
	private void checkAllOptions() {
		for (Excursion excursion : lista) {
			checkEstado(excursion);
			checkDestacado(excursion);
		}
	}

	//metodo para que la fechaExcursion sea una fecha determinada
	private Date getDateByString(String dateString) {
		//sacado de: stackoverflow.com/questions/25751917/java-unparseable-date-difference-in-formats
		Date date = new Date(); //por defecto la fecha de hoy
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");

		try {
			date = format.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	//metodos implementados del interface
	@Override
	public Excursion findById(int idExcursion) {
		for (Excursion ele: lista) {
			if (ele.getIdExcursion() == idExcursion)
				return ele; //devolvemos el elemento de la lista cuyo id coincida con el que le hemos pasado
		}
		return null; //si no encuentra coincidencia, no devuelve nada
	}

	@Override
	public int insertOne(Excursion excursion) {
		if  (lista.contains(excursion)) //si la lista ya contiene la excursion
			return 0; //no le doy de alta (devuelvo 0)
		checkEstado(excursion);//comprueba el estado de la excursion que se quiere dar de alta
		excursion.setFechaAlta(new Date()); //al dar de alta la excursion, establecemos la 'fechaAlta' con la fecha de hoy
		return lista.add(excursion) ? 1 : 0; //lo añadimos a la lista (true (operacion realizada) devuelve 1 y false (operacion falla) devuelve 0)
	}

	@Override
	public int updateOne(Excursion excursion) {
		int pos = lista.indexOf(excursion); //indeof devuelve la posicion de la excursion en la lista
		
		if (pos == -1) //si no se encuentra
			return 0; //no la modifica (devuelvo 0)

		checkEstado(excursion); //comprueba el estado de la excursion que se quiere modificar

		Excursion excursionOld = lista.get(pos); //obtiene la excursion antigua en la posición encontrada en la lista
		excursionOld.update(excursion); //llamo al metodo propio de la clase excursion para actualizar los campos de la excursión antigua con los datos nuevos (evita perder 'fechaAlta' u otros datos sensibles que no quiero que se modifiquen pero que tampoco quiero que se pierdan)

		return (lista.set(pos, excursionOld) != null) ? 1 : 0;	//reemplaza la excursión antigua actualizada en la lista
	}

	@Override
	public List<Excursion> findAll() {
		return lista; //devuelve toda la lista (todas las excursiones)
	}

	@Override
	public List<Excursion> findByActivos() {
		List<Excursion> activos = new ArrayList<>(); //creo una lista vacia llamada activos

		for (Excursion ele: lista) { //recorro la lista de excursiones registradas
			if (ele.getEstado().equals("CREADO")) {  //si el estado de alguna excursion es igual a 'creado'
				activos.add(ele); //lo añado a la lista vacia de activos
			}
		}
		return activos; //devuelvo la lista de activos tras recorrer
	}

	@Override
	public List<Excursion> findByCancelados() { //misma mecanica que activos
		List<Excursion> cancelados = new ArrayList<>();
		
		for (Excursion ele: lista) {
			if (ele.getEstado().equals("CANCELADO")) {
				cancelados.add(ele);
			}
		}
		return cancelados;
	}

	@Override
	public List<Excursion> findByDestacados() { //misma mecanica que activos
		List<Excursion> destacados = new ArrayList<>();
		
		for (Excursion ele: lista) {
			if (ele.getDestacado().equals("S")) {
				destacados.add(ele);
			}
		}
		return destacados;
	}

}
