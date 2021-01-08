package features;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface iCommunicatesHandling {
	void fillGapAgainAction(String jsp, int whichMsg, 
			HttpServletRequest req, HttpServletResponse resp) 
					throws ServletException, IOException;
}        