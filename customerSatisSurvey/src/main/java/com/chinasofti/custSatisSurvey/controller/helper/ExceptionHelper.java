package com.chinasofti.custSatisSurvey.controller.helper;

import org.slf4j.Logger;

import com.chinasofti.custSatisSurvey.Exception.TipException;
import com.chinasofti.custSatisSurvey.dto.RestResponseBo;

 
public class ExceptionHelper {
	/**
	     * 统一处理异常
	     *
	     * @param logger
	     * @param msg
	     * @param e
	     * @return
	     */
		
	public static RestResponseBo handlerException(Logger logger, String msg, Exception e) {
        if (e instanceof TipException) {
            msg = msg + "," +  e.getMessage();
        } else {
            logger.error(msg, e);
        }
        return RestResponseBo.fail(msg);
    }

}


 
