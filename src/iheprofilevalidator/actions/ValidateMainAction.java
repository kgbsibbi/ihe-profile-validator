package iheprofilevalidator.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ValidateMainAction implements Action{

	@Override
	public String processRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		return "/validate/validate.index.jsp";
	}

}
