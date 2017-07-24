package com.bhg.pipeServer.action.base;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.struts2.json.annotations.JSON;

import com.bhg.pipeServer.config.ErrorCode;
import com.opensymphony.xwork2.ActionSupport;

public abstract class CommonActionBase extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int error;

	protected final void setError(int error) {
		this.error = error;
	}

	/*----------------------------------接口标准返回-----------------------------*/

	public final int getReturn_code() {
		return getError();
	}

	public final String getReturn_msg() {
		return getErrorMessage();
	}

	protected final int getError() {
		return error;
	}

	private final String getErrorMessage() {
		return ErrorCode.getErrorMessage(error);
	}

	/*----------------------------------隐藏来自基类的属性-----------------------------*/

	@JSON(serialize = false)
	public Collection<String> getActionErrors() {
		return super.getActionErrors();
	}

	@SuppressWarnings("deprecation")
	@JSON(serialize = false)
	public Collection<String> getErrorMessages() {
		return super.getErrorMessages();
	}

	@JSON(serialize = false)
	public Collection<String> getActionMessages() {
		return super.getActionMessages();
	}

	@SuppressWarnings("deprecation")
	@JSON(serialize = false)
	public Map<String, List<String>> getErrors() {
		return super.getErrors();
	}

	@JSON(serialize = false)
	public Map<String, List<String>> getFieldErrors() {
		return super.getFieldErrors();
	}

	@JSON(serialize = false)
	public Locale getLocale() {
		return super.getLocale();
	}

	@JSON(serialize = false)
	public ResourceBundle getTexts() {
		return super.getTexts();
	}
}
