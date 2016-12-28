package iheprofilevalidator.actions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.schematron.ant.SchematronReport;
import com.schematron.ant.SchematronResult;
import com.schematron.ant.Validator;
//import com.schematron.ant.Validator;
//import com.schematron.ant.ValidatorFactory;
import com.schematron.ant.ValidatorFactory;

import iheprofilevalidator.bean.ResultBean;
import iheprofilevalidator.tools.SchematronErrorListener;

public class PHMRCDAAction implements Action{
	private ValidatorFactory factory;
	//private Validator validator;
	
	public PHMRCDAAction(){
		System.setProperty("javax.xml.transform.TransformerFactory",
		          "net.sf.saxon.TransformerFactoryImpl");
		factory = new ValidatorFactory("xslt2", "svrl" );
	    factory.setDebugMode(true);
	    factory.setErrorListener(new SchematronErrorListener());
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
		
		String schema = realSchemaFolder + "/" + schemaFile;
		Source source = new StreamSource(schema);
		Validator validator = factory.newValidator(source);
		
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

		       // Validation
		       StreamSource xml =new StreamSource(realFileFolder + "/" + filename);
		       SchematronResult schematronResult = validator.validate(xml, "", "", "", "", "utf-8");
		       SchematronReport report = new SchematronReport();
		       report.add(schematronResult);
		       File resultFile = new File(realFileFolder, filename+"_result.xml");
		       report.saveAs(resultFile );
		       
		       ResultBean bean = new ResultBean();
		       bean.setFileName(fileRequest.getOriginalFileName(name));
	    	   bean.setResultFile("uploads/phmr_cda/"+ resultFile.getName());
	    	   // Find Error Messages from result. and add to bean
	    	   bean.addErrorMessage(schematronResult.getFailedMessage());
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
