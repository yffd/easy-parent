package com.yffd.easy.common.core.log;

import org.slf4j.Logger;

public class EasyCommonLogFormat {
    private static final String LOG_PREFIX = "[common]";

    public static void info(Logger logger, String msg) {
        if (logger.isInfoEnabled())
            logger.info(LOG_PREFIX + msg);
    }

    public static void warn(Logger logger, String msg) {
        if (logger.isWarnEnabled())
            logger.warn(LOG_PREFIX + msg);
    }

    public static void warn(Logger logger, String msg, Throwable t) {
        if (logger.isWarnEnabled())
            logger.warn(LOG_PREFIX + msg, t);
    }

    public static void error(Logger logger, String msg) {
        if (logger.isErrorEnabled())
            logger.error(LOG_PREFIX + msg);
    }
    
    public static void error(Logger logger, String msg, Throwable t) {
        if (logger.isErrorEnabled())
            logger.error(LOG_PREFIX + msg, t);
    }
}