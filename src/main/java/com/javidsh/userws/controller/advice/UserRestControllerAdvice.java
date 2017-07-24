package com.javidsh.userws.controller.advice;

import com.javidsh.userws.exception.InvalidParamsException;
import com.javidsh.userws.exception.NoUserFoundException;
import com.javidsh.userws.exception.UserNotFoundException;
import com.javidsh.userws.exception.DuplicateUsernameException;
import com.javidsh.userws.util.CustomMessage;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 *
 * UserRestControllerAdvice.java 
 * Purpose: Controller adviser for catching exceptions thrown inside the UserRestController class.
 *
 * @author javid
 * @version 1.0
 * @since 2017-07-23
 */
@ControllerAdvice(annotations = {RestController.class})
public class UserRestControllerAdvice {

    public static final Logger logger = Logger.getLogger(UserRestControllerAdvice.class);

    @ExceptionHandler({MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CustomMessage handleBindingErrors(Exception ex) {
        List<String> details = new LinkedList<>();
        String errorMsg = "Validation failed.";

        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException notValidArgEx = (MethodArgumentNotValidException) ex;
            BindingResult bindingResult = notValidArgEx.getBindingResult();
            if (bindingResult.hasErrors()) {
                StringBuilder sb = new StringBuilder();
                for (FieldError fieldError : bindingResult.getFieldErrors()) {
                    sb.append("Field-name: ").append(fieldError.getField()).append("; ");
                    sb.append("Rejected-value: ").append(fieldError.getRejectedValue()).append("; ");
                    sb.append("Message: ").append(fieldError.getDefaultMessage());

                    details.add(sb.toString());
                    sb.setLength(0);
                }

                logger.warn(errorMsg);
                logger.warn(details);
                return new CustomMessage(HttpStatus.BAD_REQUEST.name(), errorMsg, details);
            }
        }

        //ex instanceof MethodArgumentTypeMismatchException
        errorMsg = "Bad Request.";
        details.add(ex.getLocalizedMessage());

        logger.warn(errorMsg);
        logger.warn(details);
        return new CustomMessage(HttpStatus.BAD_REQUEST.name(), errorMsg, details);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CustomMessage handleHttpMessageErrors(HttpMessageNotReadableException ex) {
        String errorMsg = "Some field(s) have a an invalid data type.";
        String detailedMsg = ex.getLocalizedMessage();
        List<String> details = new LinkedList();
        details.add(detailedMsg);

        logger.warn(errorMsg);
        logger.warn(detailedMsg);
        return new CustomMessage(HttpStatus.BAD_REQUEST.name(), errorMsg, details);
    }

    @ExceptionHandler({InvalidParamsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CustomMessage handleInvalidParamsException(InvalidParamsException ex) {
        String errorMsg = "Invalid page and/or size parameteres. They must be positive.";

        logger.warn(errorMsg);
        return new CustomMessage(HttpStatus.BAD_REQUEST.name(), errorMsg);
    }

    @ExceptionHandler({NoUserFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public CustomMessage handleInvalidParamsException(NoUserFoundException ex) {
        String errorMsg = "No user found.";

        logger.warn(errorMsg);
        return new CustomMessage(HttpStatus.NOT_FOUND.name(), errorMsg);
    }

    @ExceptionHandler({UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public CustomMessage handleUserNotFoundException(UserNotFoundException ex) {
        StringBuilder sb = new StringBuilder();
        sb.append(ex.getMessage())
                .append(" User by id=")
                .append(ex.getUserId())
                .append(" not found.");
        String errorMsg = sb.toString();

        logger.warn(errorMsg);
        return new CustomMessage(HttpStatus.NOT_FOUND.name(), errorMsg);
    }

    @ExceptionHandler({DuplicateUsernameException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public CustomMessage handleUsernameExistsException(DuplicateUsernameException ex) {
        StringBuilder sb = new StringBuilder();
        sb.append(ex.getMessage())
                .append(" User by userName=")
                .append(ex.getUserName())
                .append(" does already exist on another user.");
        String errorMsg = sb.toString();

        logger.warn(errorMsg);
        return new CustomMessage(HttpStatus.CONFLICT.name(), errorMsg);
    }

    @ExceptionHandler({ObjectOptimisticLockingFailureException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public CustomMessage handleObjectOptimisticLockingFailureException(ObjectOptimisticLockingFailureException ex) {
        String errorMsg = "User was updated or deleted by another transaction.";

        logger.error(errorMsg);
        return new CustomMessage(HttpStatus.CONFLICT.name(), errorMsg);
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public CustomMessage handleAnyException(Exception ex) {
        String errorMsg = "Internal Server Error.";
        String detailedMsg = ex.getLocalizedMessage();
        List<String> details = new LinkedList();
        details.add(detailedMsg);

        logger.error(errorMsg);
        logger.error(detailedMsg);
        return new CustomMessage(HttpStatus.INTERNAL_SERVER_ERROR.name(), errorMsg, details);
    }

}
