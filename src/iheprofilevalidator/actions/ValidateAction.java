package iheprofilevalidator.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ValidateAction implements Action{

	@Override
	public String processRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		String type = request.getParameter("type");
		String result = "Valid document";
		
		if(type == null) type="phmrcda";
		
		request.setAttribute("result", result);
		return "/phmrcda/phmrcda.index.jsp";
	}

}
