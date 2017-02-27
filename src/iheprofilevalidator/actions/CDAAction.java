package iheprofilevalidator.actions;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.schematron.ant.SchematronReport;
import com.schematron.ant.SchematronResult;
import com.schematron.ant.ValidatorFactory;

import iheprofilevalidator.bean.ResultBean;
import iheprofilevalidator.tools.SchematronErrorListener;

public class CDAAction implements Action {
	private ValidatorFactory schematronFactory;
	SchemaFactory schemaFactory;
	Schema schema;
	MultipartRequest multipartRequest;
	Enumeration<?> files;
	
	public CDAAction(MultipartRequest mreq, Enumeration<?> files){
		// Schema: xsd file
		schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		// Schematron: sch file
		schematronFactory = new ValidatorFactory("xslt2", "svrl" );
	    schematronFactory.setDebugMode(true);
	    schematronFactory.setErrorListener(new SchematronErrorListener());
	    
		this.multipartRequest = mreq;
		this.files = files;
	}

	@Override
	public String processRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		saveFile(request, response);
		return "/validate/validate.index.jsp";
	}
	
	private void saveFile(HttpServletRequest request, HttpServletResponse response) throws Throwable{
		ServletContext context;
		// file upload related
		String fileFolder = "/uploads"; // file folder
		String realFileFolder = ""; // absolute path of web application
		// absolute path for schema(xsd)
		String schemaFolder="/schemas/CDASchemas/cda/Schemas";
		String schemaFileName = "CDA.xsd";
		String realSchemaFolder="";
		// absolute path for schematron(sch)
		String schematronFolder="/schematrons/ccd";
		String schematronFileName="ccd.sch";
		String realSchematronFolder="";
		// result files
		String fileName;
		ArrayList<ResultBean> uploadedFiles = null;
		
		request.setCharacterEncoding("utf-8");
		
		context = request.getSession().getServletContext();
		realFileFolder = context.getRealPath(fileFolder); 
		realSchemaFolder = context.getRealPath(schemaFolder);
		realSchematronFolder = context.getRealPath(schematronFolder);
		String schemaPath = realSchemaFolder + "/" + schemaFileName;
		String schematronPath = realSchematronFolder + "/" + schematronFileName;
		
		Source source = new StreamSource(schematronPath);
		com.schematron.ant.Validator schematronValidator = schematronFactory.newValidator(source);
		
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
		       fileName = multipartRequest.getFilesystemName(name);
		       
		       ResultBean bean = new ResultBean();
		       bean.setFileName(multipartRequest.getOriginalFileName(name));
		       uploadedFiles.add(bean);
		       
		       StreamSource xsd =new StreamSource(schemaPath);
		       StreamSource xml = new StreamSource(realFileFolder + "/" + fileName);
		       schema = schemaFactory.newSchema(xsd);
		       Validator schemaValidator = schema.newValidator();
		       schemaValidator.validate(xml);
		       
		       SchematronResult schematronResult = schematronValidator.validate(xml, "", "", "", "", "utf-8");
		       SchematronReport report = new SchematronReport();
		       report.add(schematronResult);
		       File resultFile = new File(realFileFolder, fileName+"_result.xml");
		       report.saveAs(resultFile );
		       bean.setResultFile("uploads/"+ resultFile.getName());
		       bean.addErrorMessage(schematronResult.getSVRLAsString());
			}
		}catch(Exception e){
		     e.printStackTrace();
		     uploadedFiles.get(uploadedFiles.size()-1).addErrorMessage(e.getMessage());
		}finally{
			request.setAttribute("result", uploadedFiles);
		}
		
	}

}
