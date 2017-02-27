package iheprofilevalidator.actions;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class ValidateAction implements Action{

	@Override
	public String processRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		Action action = null;
		String fileFolder = "/uploads";
		ServletContext context = request.getSession().getServletContext();
		String realFileFolder = context.getRealPath(fileFolder);  
		
		try{
			MultipartRequest multipartRequest = new MultipartRequest(request,realFileFolder,5*1024*1024,
		            "utf-8",new DefaultFileRenamePolicy());
			Enumeration<?> files = multipartRequest.getFileNames();
			
			String type = multipartRequest.getParameter("type");
			
			if(type == null) type="none";
			
			if(type.equals("phmrcda")){
				action = new PHMRCDAAction(multipartRequest, files);
			} else if(type.equals("cdaccd")){
				action = new CDAAction(multipartRequest, files);
			}
			
			if(action == null){
				return "/validate/validate.index.jsp";
		}
		} catch(Exception e){
			return "/validate/validate.index.jsp";
		}
		return action.processRequest(request, response);
	}
	


}
