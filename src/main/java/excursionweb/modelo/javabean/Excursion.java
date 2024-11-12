package excursionweb.modelo.javabean;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Excursion implements Serializable {

	private static final long serialVersionUID = 1L;

	private int idExcursion;
	private String descripcion;
	private String origen;
	private String destino;
	private Date fechaExcursion;
	private int duracion;
	private String estado;
	private String destacado;
	private int aforoMaximo;
	private int minimoAsistentes;
	private double precioUnitario;
	private String imagen;
	private Date fechaAlta;

	//constructor sin nada (obligatorio)
	public Excursion() {
		super();
	}

	//constructor con todo
	public Excursion(int idExcursion, String descripcion, String origen, String destino, Date fechaExcursion,
			int duracion, String estado, String destacado, int aforoMaximo, int minimoAsistentes, double precioUnitario,
			String imagen, Date fechaAlta) {
		super();
		this.idExcursion = idExcursion;
		this.descripcion = descripcion;
		this.origen = origen;
		this.destino = destino;
		this.fechaExcursion = fechaExcursion;
		this.duracion = duracion;
		this.estado = estado;
		this.destacado = destacado;
		this.aforoMaximo = aforoMaximo;
		this.minimoAsistentes = minimoAsistentes;
		this.precioUnitario = precioUnitario;
		this.imagen = imagen;
		this.fechaAlta = fechaAlta;
	}

	//getter & setter
	public int getIdExcursion() {
		return idExcursion;
	}

	public void setIdExcursion(int idExcursion) {
		this.idExcursion = idExcursion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public Date getFechaExcursion() {
		return fechaExcursion;
	}

	public void setFechaExcursion(Date fechaExcursion) {
		this.fechaExcursion = fechaExcursion;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDestacado() {
		return destacado;
	}

	public void setDestacado(String destacado) {
		this.destacado = destacado;
	}

	public int getAforoMaximo() {
		return aforoMaximo;
	}

	public void setAforoMaximo(int aforoMaximo) {
		this.aforoMaximo = aforoMaximo;
	}

	public int getMinimoAsistentes() {
		return minimoAsistentes;
	}

	public void setMinimoAsistentes(int minimoAsistentes) {
		this.minimoAsistentes = minimoAsistentes;
	}

	public double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = new Date();
	}

	//equals & hashCode
	@Override
	public int hashCode() {
		return Objects.hash(idExcursion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Excursion))
			return false;
		Excursion other = (Excursion) obj;
		return idExcursion == other.idExcursion;
	}
	
	//toString
	@Override
	public String toString() {
		return "Excursion [idExcursion=" + idExcursion + ", descripcion=" + descripcion + ", origen=" + origen
				+ ", destino=" + destino + ", fechaExcursion=" + fechaExcursion + ", duracion=" + duracion + ", estado="
				+ estado + ", destacado=" + destacado + ", aforoMaximo=" + aforoMaximo + ", minimoAsistentes="
				+ minimoAsistentes + ", precioUnitario=" + precioUnitario + ", imagen=" + imagen + ", fechaAlta="
				+ fechaAlta + "]";
	}
	
	//metodo propio para actualizar solo los datos permitidos (idExcursion y fechaAlta no se tocan (no se incluyen))
	public void update(Excursion excursion) {
		descripcion = excursion.getDescripcion();
		origen = excursion.getOrigen();
		destino = excursion.getDestino();
		fechaExcursion = excursion.getFechaExcursion();
		duracion = excursion.getDuracion();
		estado = excursion.getEstado();
		destacado = excursion.getDestacado();
		aforoMaximo = excursion.getAforoMaximo();
		minimoAsistentes = excursion.getMinimoAsistentes();
		precioUnitario = excursion.getPrecioUnitario();
		imagen = excursion.getImagen();
	}
}
