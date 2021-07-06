package com.bdf.controller.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.bdf.entity.mix.ErrorResponse;

public class BaseController {

	protected HttpServletRequest request;
	protected HttpServletResponse response;

	@ModelAttribute
	public void addAttributes(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleSQLException(HttpServletRequest request, Exception ex) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.CONFLICT);
	}
	
//	@Bean(name = "multipartResolver")
//	public CommonsMultipartResolver multipartResolver() {
//		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//		multipartResolver.setMaxUploadSize(-1);
//		return multipartResolver;
//	}
}
