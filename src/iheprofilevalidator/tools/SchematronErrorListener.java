package iheprofilevalidator.tools;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.TransformerException;

public class SchematronErrorListener implements ErrorListener {

	@Override
	public void error(TransformerException arg0) throws TransformerException {
		System.out.println("ERROR!!");
		System.out.println(arg0.getMessage());
	}

	@Override
	public void fatalError(TransformerException arg0) throws TransformerException {
		System.out.println(arg0.getMessage());
	}

	@Override
	public void warning(TransformerException arg0) throws TransformerException {
		System.out.println(arg0.getMessage());
	}

}
