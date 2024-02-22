package org.nickz.spring.http.controller.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;


@RestControllerAdvice(basePackages = "com.nickz.spring.http.rest")
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {


}
