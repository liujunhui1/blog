package cn.junhui.blog_test.util;


import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * 军辉
 * 2019-02-04 14:08
 * 用于处理 ConstraintViolation 异常的处理器
 */
public class ConstraintViolationExceptionHandler {

    /*
    获取批量异常信息
     */
    public static String getMessage(ConstraintViolationException e) {
        List<String> msgList = new ArrayList<>();
        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
            msgList.add(constraintViolation.getMessage());
        }
        String message = StringUtils.join(msgList.toArray(), ";");
        return message;
    }
}
