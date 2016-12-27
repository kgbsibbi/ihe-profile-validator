package iheprofilevalidator.actions;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
//import com.schematron.ant.Validator;
//import com.schematron.ant.ValidatorFactory;

import iheprofilevalidator.bean.ResultBean;

public class PHMRCDAAction implements Action{
	//private ValidatorFactory factory;
	//private Validator validator;
	
	public PHMRCDAAction(){
		System.setProperty("javax.xml.transform.TransformerFactory",
		          "net.sf.saxon.TransformerFactoryImpl");
	}

	@Override
	public String processRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		saveFile(request, response);
		return "/validate/validate.index.jsp";
	}
	
	private void saveFile(HttpServletRequest request, HttpServletResponse response) throws Throwable{
		String result="valid";
		String filename ="";
		String fileFolder = "/uploads/phmr_cda"; // file folder
		String schematronFolder="/schematrons";
		String realFileFolder = ""; // absolute path of web application
		String realSchemaFolder="";
		String schemaFile = "PHMR.sch";
		String encType = "utf-8";
		int maxSize = 5*1024*1024;  //Max file size 5Mb
		ArrayList<ResultBean> uploadedFiles = null;
		MultipartRequest fileRequest = null;
		
		request.setCharacterEncoding("utf-8");
		
		
		ServletContext context = request.getSession().getServletContext();
		realFileFolder = context.getRealPath(fileFolder);  
		realSchemaFolder = context.getRealPath(schematronFolder);
		
		try{
			fileRequest = new MultipartRequest(request,realFileFolder,maxSize,
					            encType,new DefaultFileRenamePolicy());
			   
			// every parameter includes <input type="file">
			Enumeration<?> files = fileRequest.getFileNames();
			//파일 정보가 있다면
			if(files.hasMoreElements()){
				uploadedFiles = new ArrayList<>();
			}
			while(files.hasMoreElements()){
		       String name = (String)files.nextElement();
		       filename = fileRequest.getFilesystemName(name);
		       // 여기서 validate 할 것.
		       // xslt2인지 아닌지 확인을 한 다음 validator를 생성해야 함....
		       
		       ResultBean bean = new ResultBean();
		       bean.setFileName(fileRequest.getOriginalFileName(name));
		       //임시 코드
		       result = "valid";
		       if(result.equals("500")){
		    	   bean.setResult("invalid");
		    	   bean.setResultMessage("INVALID Document");
		       }
		       else{
		    	   bean.setResult("valid");
		    	   bean.setResultMessage(result);
		       }
		       bean.setResultMessage(result);
		       uploadedFiles.add(bean);
		     }
		     request.setAttribute("uploaded", uploadedFiles);
		     
		  }catch(Exception e){
		     e.printStackTrace();
		     result="upload-error: Too big file.";
		  } finally {
			 request.setAttribute("result", uploadedFiles);
		  }
	}

}
