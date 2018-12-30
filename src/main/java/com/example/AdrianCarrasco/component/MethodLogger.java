package com.example.AdrianCarrasco.component;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component("methodLogger")
public class MethodLogger {

	private static final Log LOG = LogFactory.getLog(MethodLogger.class);

	public void info(String requestType, String method, String controller, String objectType, String actionType, String data) {
		LOG.info("METHOD ("+requestType+"): "+method+" FROM "+controller+" ---- "+objectType+" GOING TO BE "+actionType+": "+data);
//		LOG.info("METHOD (GET): editParticipations (EDIT_VIEW) ---- PARTICIPATION GOING TO BE EDITED: " + participacionService.findById(id));
	}
	
	public void redirect(String target, String source) {
		LOG.info("REDIRECTING TO "+target+" FROM "+source);
	}
	
	public void regularMessage(String message) {
		LOG.info(message);
	}
}
