package com.example.AdrianCarrasco.component;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component("methodLogger")
public class MethodLogger {

	private static final Log LOG = LogFactory.getLog(MethodLogger.class);

	public void info(String requestType, String method, String template, String controller, String object, String actionType, Object data) {
		LOG.info("METHOD ("+requestType+"): "+method+" ("+template+" view) FROM "+controller+" ---- "+object+" GOING TO BE "+actionType+": "+data.toString());
//		LOG.info("METHOD (GET): editParticipations (EDIT_VIEW) ---- PARTICIPATION GOING TO BE EDITED: " + participacionService.findById(id));
	}
	
	public void redirect(String target, String source) {
		LOG.info("REDIRECTING TO "+target+" FROM "+source);
	}
	
	public void regularMessage(String message) {
		LOG.info(message);
	}
	
	public void validationError() {
		LOG.info("ERROR IN VALIDATION FIELDS");
	}
	
	public void success(String object, String actionType, Object data) {
		LOG.info(object + " " + actionType + " SUCCESSFULLY: " + data.toString());
	}
	
	public void unsuccessful(String actionType, String object, Object data) {
		LOG.info("UNABLE TO " + actionType + " " + object + ": " + data.toString());
	}
}
