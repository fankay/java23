package com.kaishengit.crm.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 当访问的数据不存在时，抛出该异常会引起HTTP的404错误
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "资源不存在")
public class NotFoundException extends RuntimeException {
}
