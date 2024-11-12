package excursionweb.modelo.dao;

import java.util.List;
import excursionweb.modelo.javabean.Excursion;

public interface ExcursionDao {
	//crud
	Excursion findById(int idExcursion);
	int insertOne(Excursion excursion);
	int updateOne (Excursion excursion);
	//busquedas
	List<Excursion> findAll();
	List<Excursion> findByActivos();
	List<Excursion> findByCancelados();
	List<Excursion> findByDestacados();

}
