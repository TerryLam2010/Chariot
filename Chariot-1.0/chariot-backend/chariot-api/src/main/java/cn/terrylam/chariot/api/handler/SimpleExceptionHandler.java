package cn.terrylam.chariot.api.handler;

import cn.terrylam.framework.enums.ResponseCodeEnum;
import cn.terrylam.framework.exception.AccessForbiddenException;
import cn.terrylam.framework.exception.EmpytCollectionException;
import cn.terrylam.framework.exception.FieldValidateException;
import cn.terrylam.framework.exception.ObjectNotFoundException;
import cn.terrylam.framework.util.ResponseResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@Log4j2
@ControllerAdvice(annotations = {RestController.class})
public class SimpleExceptionHandler {


    // 通用异常的处理，返回500
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult<?> handleException(HttpServletRequest request, Exception e) {

        log.error(e.getMessage(), e);

        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            return ResponseResult.build(ResponseCodeEnum.SC_NOT_FOUND, e.getMessage());
        } else {
            return ResponseResult.build(ResponseCodeEnum.ERROR, e.getMessage());
        }
    }

    // 参数不合法
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {ConstraintViolationException.class, FieldValidateException.class})
    @ResponseBody
    public ResponseResult<?> constraintViolationExceptionHandler(HttpServletRequest request, Exception e)
            throws Exception {

        log.error(e.getMessage(), e);

        if (e instanceof FieldValidateException) {
            return ResponseResult.build(ResponseCodeEnum.BAD_REQUEST, ((FieldValidateException) e).getFieldMsgs());
        }
        return ResponseResult.build(ResponseCodeEnum.BAD_REQUEST, e.getMessage());
    }

    // 空结果集
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {ObjectNotFoundException.class, EmptyResultDataAccessException.class,
            EmpytCollectionException.class})
    @ResponseBody
    public ResponseResult<?> notFoundExceptionHandler(HttpServletRequest request, Exception e) throws Exception {

        log.error(e.getMessage(), e);

        return ResponseResult.build(ResponseCodeEnum.SC_NOT_FOUND, e.getMessage());
    }

    // 禁止访问
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = {AccessForbiddenException.class})
    @ResponseBody
    public ResponseResult<?> forbiddenExceptionHandler(HttpServletRequest request, Exception e) throws Exception {

        log.error(e.getMessage(), e);

        return ResponseResult.build(ResponseCodeEnum.BAD_REQUEST, e.getMessage());
    }

}

