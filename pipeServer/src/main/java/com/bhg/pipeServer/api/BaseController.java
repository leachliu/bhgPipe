package com.bhg.pipeServer.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bhg.pipeServer.exception.PipeException;

public class BaseController {
	/** 基于@ExceptionHandler异常处理 */
	@ExceptionHandler
	public String exp(HttpServletRequest request, Exception ex) {

		request.setAttribute("ex", ex);

		// 根据不同错误转向不同页面
		if (ex instanceof PipeException) {
			return "error-business";
		} else {
			return "error";
		}
	}
}
