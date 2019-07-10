package com.santex.challenge.footballdata.error;

import com.santex.challenge.footballdata.bean.Wrapper;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * Created by federicoberon on 25/06/2019.
 */
@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomGlobalExceptionHandler.class);

    @ExceptionHandler({ ResourceAccessException.class, HibernateException.class })
    public ResponseEntity<Wrapper> customHandleNotFound(Exception ex, WebRequest request) {
        LOGGER.error("Handled Error by footbal-data application. ", ex);
        return new ResponseEntity<>(new Wrapper("Server Error. ", HttpStatus.GATEWAY_TIMEOUT), HttpStatus.GATEWAY_TIMEOUT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Wrapper> customHandleException(Exception ex, WebRequest request) {
        LOGGER.error("Exception by footbal-data application. ", ex);

        return new ResponseEntity<>(new Wrapper("Footbal-data could not Handle the error. ", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }
}
