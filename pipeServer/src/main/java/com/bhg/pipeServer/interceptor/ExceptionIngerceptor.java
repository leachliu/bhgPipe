package com.bhg.pipeServer.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.bhg.pipeServer.exception.PipeException;

public class ExceptionIngerceptor implements HandlerExceptionResolver {

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		System.out.println("************ExceptionIngerceptor resolveException executed**********");

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ex", ex);

		// 根据不同错误转向不同页面
		if (ex instanceof PipeException) {
			return new ModelAndView("error-business", model);
		} else {
			return new ModelAndView("error", model);
		}
	}
}