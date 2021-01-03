package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
public class ExceptionController {
    private Log logger = LogFactory.getLog(ExceptionController.class);

    @ExceptionHandler(MultipartException.class)
    public String handleError1(MultipartException e) {
        logger.error(e.getStackTrace());
        return "redirect:/result?error";
    }


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleError2(MaxUploadSizeExceededException e) {
        logger.error(e.getStackTrace());
        return "redirect:/result?error_large_file";
    }
}
