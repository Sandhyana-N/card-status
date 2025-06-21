package com.demo.service.util;

import org.slf4j.Logger;


public final class LogUtil {


    public static void error(Logger logger, String method, String errorCode, String errorMessage){

        StringBuilder sb = new StringBuilder();
        sb.append("method=\"").append(method).append("\",");
        sb.append("errorCode=\"").append(errorCode).append("\",");
        sb.append("errorMessage=\"").append(errorMessage).append("\",");

        logger.error(sb.toString());

    }


}
