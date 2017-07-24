package com.bhg.pipeServer.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bhg.pipeServer.exception.PipeException;

/**
 * Default controller that exists to return a proper REST response for unmapped
 * requests.
 */
@RestController
public class DefaultController {
	@RequestMapping("/**")
	public void unmappedRequest(HttpServletRequest request) {
		String uri = request.getRequestURI();
		throw new PipeException("There is no resource for path " + uri);
	}
}
