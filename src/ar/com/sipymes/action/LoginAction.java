package ar.com.sipymes.action;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import ar.com.sipymes.entidades.Usuario;
import ar.com.sipymes.entidades.util.HibernateUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {

	private static final long serialVersionUID = -2407332220228611918L;

	// Sesion de aplicacion
	private Map<String, Object> session = ActionContext.getContext().getSession();

	private String username;
	private String password;
	private String rol;
	private String idUsuario;

	/**
	 * Validar usuarios.
	 */
	public String authenticate() {
		// si ya estaba logueado devuelvo el success
		Usuario usuarioSession = (Usuario) session.get("usuario");
		if (usuarioSession != null) {
			return SUCCESS;
		}

		//Creo una nueva session de BBDD 
		Session sessionBBDD = HibernateUtil.getSession();
		// Criterio de busqueda
		Criteria usuarioCriteria = sessionBBDD.createCriteria(Usuario.class);
		// Filtro de nombre
		SimpleExpression usuarioRestrictions = Restrictions.eq("usuario", this.username);
		usuarioCriteria.add(usuarioRestrictions);
		// Busco al usuario
		@SuppressWarnings("unchecked")
		List<Usuario> usuarios = usuarioCriteria.list();
		// Valido q exista el usuario
		for (Usuario usuario : usuarios) {
			if (this.password.equals(usuario.getPassword())) {
				if (usuario.getActivo().booleanValue()) {
						// pongo al usuario en session
						session.put("usuario", usuario);

						return SUCCESS;
				}
			}
		}

		// No coincide use/pass o usuario inactivo
		addActionError(getText("error.login"));
		return ERROR;
	}
 
	/**
	 * Cargo el rol para porder armar el menu segun el usuario.
	 */
	public String cargarMenu() {
		// Sesion de aplicacion
		Map<String, Object> session = ActionContext.getContext().getSession();
		// Recupero el usuario de sesion
		Usuario usuarioSession = (Usuario) session.get("usuario");

		// Cargo el rol del usuario para la pantalla
		this.rol = usuarioSession.getRol();
		// Cargo el id del usuario
		this.idUsuario = usuarioSession.getUsuario();

		return SUCCESS;
	}

	/**
	 * Ejecuto el logout de la aplicación.
	 */
	public String logout() {
		// Limpio la session
		session.clear();

		return SUCCESS;
	}
	
	public String getUsername() {
		return username;
	}
 
	public void setUsername(String username) {
		this.username = username;
	}
 
	public String getPassword() {
		return password;
	}
 
	public void setPassword(String password) {
		this.password = password;
	}

	public String getRol() {
		return rol;
	}

	public String getIdUsuario() {
		return idUsuario;
	}
}