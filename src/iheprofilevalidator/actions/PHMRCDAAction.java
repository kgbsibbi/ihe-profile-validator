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
	private MultipartRequest multipartRequest;
	private Enumeration<?> files;
	//private Validator validator;
	
	public PHMRCDAAction(MultipartRequest mreq, Enumeration<?> files){
		System.setProperty("javax.xml.transform.TransformerFactory",
		          "net.sf.saxon.TransformerFactoryImpl");
		factory = new ValidatorFactory("xslt2", "svrl" );
	    factory.setDebugMode(true);
	    factory.setErrorListener(new SchematronErrorListener());
	    this.multipartRequest = mreq;
	    this.files = files;
	}

	@Override
	public String processRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		saveFile(request, response);
		return "/validate/validate.index.jsp";
	}
	
	private void saveFile(HttpServletRequest request, HttpServletResponse response) throws Throwable{
		String result="valid";
		// uploaded file
		String filename ="";
		String fileFolder = "/uploads"; // file folder
		String realFileFolder = ""; // absolute path of web application
		// schematron file
		String schematronFolder="/schematrons/phmr";
		String schemaFile = "PHMR.sch";
		String realSchemaFolder="";
		ArrayList<ResultBean> uploadedFiles = null;
		
		request.setCharacterEncoding("utf-8");
		
		ServletContext context = request.getSession().getServletContext();
		realFileFolder = context.getRealPath(fileFolder);  
		realSchemaFolder = context.getRealPath(schematronFolder);
		
		String schema = realSchemaFolder + "/" + schemaFile;
		Source source = new StreamSource(schema);
		Validator validator = factory.newValidator(source);
		
		try{
			if(files == null){
				return;
			}
			//파일 정보가 있다면
			if(files.hasMoreElements()){
				uploadedFiles = new ArrayList<>();
			}
			while(files.hasMoreElements()){
		       String name = (String)files.nextElement();
		       filename = multipartRequest.getFilesystemName(name);

		       // Validation
		       StreamSource xml =new StreamSource(realFileFolder + "/" + filename);
		       SchematronResult schematronResult = validator.validate(xml, "", "", "", "", "utf-8");
		       SchematronReport report = new SchematronReport();
		       report.add(schematronResult);
		       File resultFile = new File(realFileFolder, filename+"_result.xml");
		       report.saveAs(resultFile );
		       
		       ResultBean bean = new ResultBean();
		       bean.setFileName(multipartRequest.getOriginalFileName(name));
	    	   bean.setResultFile("uploads/"+ resultFile.getName());
	    	   // Find Error Messages from result. and add to bean
	    	   bean.addErrorMessage(schematronResult.getSVRLAsString());
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
