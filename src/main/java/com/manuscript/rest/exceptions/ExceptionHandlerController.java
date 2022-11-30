package com.manuscript.rest.exceptions;

import ch.qos.logback.classic.Logger;
import com.manuscript.core.exceptions.FailedDownloadException;
import com.manuscript.core.exceptions.FailedUploadException;
import com.manuscript.core.exceptions.NoUserFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
@ResponseBody
@AllArgsConstructor
public class ExceptionHandlerController {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
    @ExceptionHandler(FailedUploadException.class)
    public String handleFailedDependency(HttpServletRequest req, FailedUploadException e) {
        logger.error(e.getMessage());
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
    @ExceptionHandler(FailedDownloadException.class)
    public String handleFailedDependency(HttpServletRequest req, FailedDownloadException e) {
        logger.error(e.getMessage());
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoUserFoundException.class)
    public String handleFailedDependency(HttpServletRequest req, NoUserFoundException e) {
        logger.error(e.getMessage());
        return e.getMessage();
    }


}
