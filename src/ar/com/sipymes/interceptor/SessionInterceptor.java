package ar.com.sipymes.interceptor;

import java.util.Map;

import ar.com.sipymes.entidades.Usuario;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class SessionInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 6517364523687692665L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> session = ActionContext.getContext().getSession();
		Usuario usuario = (Usuario) session.get("usuario");
		
		if (usuario == null && !"login".equals(ActionContext.getContext().getName())) {
			return "loginRedirect";
		}
		
		// TODO: Revisar el interceptor
		return invocation.invoke();
	}
}
