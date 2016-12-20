package iheprofilevalidator.actions;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import iheprofilevalidator.bean.ResultBean;
import iheprofilevalidator.tools.SchematronValidator;

public class ValidateAction implements Action{

	@Override
	public String processRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		String type = request.getParameter("type");
		if(type == null) type="phmrcda";
		
		saveFile(request, response);
		return "/phmrcda/phmrcda.index.jsp";
	}
	
	private void saveFile(HttpServletRequest request, HttpServletResponse response) throws Throwable{
		String resultMessage = "Valid document";
		String result="valid";
		request.setCharacterEncoding("utf-8");
		String filename ="";
		String realFolder = ""; // absolute path of web application
		String saveFolder = "/uploads/phmr_cda"; // file folder
		String encType = "utf-8";
		int maxSize = 5*1024*1024;  //Max file size 5Mb
		ArrayList<ResultBean> uploadedFiles = null;
		
		MultipartRequest fileRequest = null;
		
		ServletContext context = request.getSession().getServletContext();
		realFolder = context.getRealPath(saveFolder);  
		
		try{
			fileRequest = new MultipartRequest(request,realFolder,maxSize,
					            encType,new DefaultFileRenamePolicy());
			   
			// every parameter includes <input type="file">
			Enumeration<?> files = fileRequest.getFileNames();
			//파일 정보가 있다면
			if(files.hasMoreElements())
				uploadedFiles = new ArrayList<>();
		     while(files.hasMoreElements()){
		       String name = (String)files.nextElement();
		       filename = fileRequest.getFilesystemName(name);
		       
		       ResultBean bean = new ResultBean();
		       bean.setFileName(fileRequest.getOriginalFileName(name));
		       bean.setResult("valid");
		       bean.setResultMessage("Valid Document.");
		       uploadedFiles.add(bean);
		     }
		     request.setAttribute("uploaded", uploadedFiles);
		     
		  }catch(Exception e){
		     e.printStackTrace();
		     result="upload-error";
		     resultMessage = "Too big file.";
		  } finally {
			 request.setAttribute("result", uploadedFiles);
			 SchematronValidator validator = SchematronValidator.getInstance();
			 String schema = "schema.sch";
			 String xml = "input.xml";
			 validator.validate(realFolder,schema, xml, realFolder);
		  }
	}

}
