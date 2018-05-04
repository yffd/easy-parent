package com.yffd.easy.common.core.util;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description  函数工具类：资源路径.
 * @Date		 2017年9月12日 下午2:04:27 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class UrlUtils {
    
    /**
     * 
     * 私有构造方法,将该工具类设为单例模式.
     */
    private UrlUtils() {
        
    }
    
    /**
     * 
     * buildFullRequestUrl:构建完整的URL. <br/>
     * @Date    2017年8月7日 下午2:52:55 <br/>
     * @author  zhangST
     * @param request
     * @return
     */
    public static String buildFullRequestUrl(HttpServletRequest request) {
        return buildFullRequestUrl(request.getScheme(), request.getServerName(), request.getServerPort(),
                request.getRequestURI(), request.getQueryString());
    }

    /**
     * 
     * buildFullRequestUrl:构建完整的URL. <br/>
     * @Date    2017年8月7日 下午2:53:07 <br/>
     * @author  zhangST
     * @param scheme
     * @param serverName
     * @param serverPort
     * @param requestURI
     * @param queryString
     * @return
     */
    public static String buildFullRequestUrl(String scheme, String serverName,
            int serverPort, String requestURI, String queryString) {

        scheme = scheme.toLowerCase();

        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);

        // Only add port if not default
        if ("http".equals(scheme)) {
            if (serverPort != 80) {
                url.append(":").append(serverPort);
            }
        } else if ("https".equals(scheme)) {
            if (serverPort != 443) {
                url.append(":").append(serverPort);
            }
        }

        // Use the requestURI as it is encoded (RFC 3986) and hence suitable for
        // redirects.
        url.append(requestURI);

        if (queryString != null) {
            url.append("?").append(queryString);
        }

        return url.toString();
    }

    /**
     * 
     * buildRequestUrl:构建一个URL. <br/>
     * @Date    2017年8月7日 下午5:31:14 <br/>
     * @author  zhangST
     * @param request
     * @return
     */
    public static String buildRequestUrl(HttpServletRequest request) {
        return buildRequestUrl(request.getServletPath(), request.getRequestURI(), request.getContextPath(),
                request.getPathInfo(), request.getQueryString());
    }

    /**
     * 
     * buildRequestUrl:构建一个URL. <br/>
     * @Date    2017年8月7日 下午5:27:52 <br/>
     * @author  zhangST
     * @param servletPath
     * @param requestURI
     * @param contextPath
     * @param pathInfo
     * @param queryString
     * @return
     */
    private static String buildRequestUrl(String servletPath, String requestURI,
            String contextPath, String pathInfo, String queryString) {

        StringBuilder url = new StringBuilder();

        if (servletPath != null) {
            url.append(servletPath);
            if (pathInfo != null) {
                url.append(pathInfo);
            }
        } else {
            url.append(requestURI.substring(contextPath.length()));
        }

        if (queryString != null) {
            url.append("?").append(queryString);
        }

        return url.toString();
    }

    /**
     * 
     * isValidRedirectUrl:校验是否为绝对路径或是否以斜杠（“/”）开头. <br/>
     * @Date    2017年8月7日 下午5:28:37 <br/>
     * @author  zhangST
     * @param url
     * @return
     */
    public static boolean isValidRedirectUrl(String url) {
        return url != null && (url.startsWith("/") || isAbsoluteUrl(url));
    }

    /**
     * 
     * isAbsoluteUrl:校验是否为绝对路径. <br/>
     * @Date    2017年8月7日 下午5:30:07 <br/>
     * @author  zhangST
     * @param url
     * @return
     */
    public static boolean isAbsoluteUrl(String url) {
        if(url == null) {
            return false;
        }
        final Pattern ABSOLUTE_URL = Pattern.compile("\\A[a-z0-9.+-]+://.*",
                Pattern.CASE_INSENSITIVE);

        return ABSOLUTE_URL.matcher(url).matches();
    }
}

