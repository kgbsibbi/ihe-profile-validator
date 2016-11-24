package iheprofilevalidator.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
	public String processRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable;
}
