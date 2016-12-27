package iheprofilevalidator.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ValidateAction implements Action{

	@Override
	public String processRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		String type = request.getParameter("type");
		Action action = null;
		//일단은 PHMR CDA만 처리
		if(type == null) type="phmrcda";
		
		if(type.equals("phmrcda")){
			action = new PHMRCDAAction();
		}
		
		if(action == null){
			return "/validate/validate.index.jsp";
		}
		
		return action.processRequest(request, response);
	}
	


}
