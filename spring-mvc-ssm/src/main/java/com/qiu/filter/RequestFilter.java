package com.qiu.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestFilter implements Filter {
    private static      Log               log;
    private static      Log               accessLog;
    public static final String            RESPONSE             = "RESPONSE";

    public static void initLog() {
        log = LogFactory.getLog(RequestFilter.class);
        accessLog = LogFactory.getLog("AccessLog");

    }

    private static List<String> ALLOW_DOMAIN_SUFFIX = new ArrayList<>();

    static {
        ALLOW_DOMAIN_SUFFIX.add("127.0.0.1");
        ALLOW_DOMAIN_SUFFIX.add("localhost");
    }

    public static String getHost(String link) {
        URL url;
        String host = "";
        try {
            url = new URL(link);
            host = url.getHost();
        } catch (MalformedURLException e) {
        }
        return host;
    }

    private static String getAllowDomain(HttpServletRequest request) throws Exception {
        String origin = request.getHeader("origin");
        if (StringUtils.isEmpty(origin)) {
            origin = request.getHeader("referer");
        }
        if (StringUtils.isEmpty(origin)) {
            origin = Strings.EMPTY;
        }
        String host = getHost(origin);
        if (StringUtils.isEmpty(host)) {
            host = origin;
        }

        for (String domainSuffix : ALLOW_DOMAIN_SUFFIX) {
            if (host.endsWith(domainSuffix)) {
                return origin;
            }
        }
        if (StringUtils.isEmpty(origin)) {
            return origin;
        }
        throw new Exception("请求：[" + request.getRequestURL().toString()
                + "]域名host:[" + host + "] origin:[" + origin + "]不在白名单中");
    }

    @Override

    public void init(FilterConfig filterConfig) throws ServletException {

    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        request.removeAttribute(RESPONSE);

        long startTime = LocalTime.now().getNano() / 1000000;
        boolean terminated = false;
        String params = getParams(request.getParameterMap());
        try {
            try {
                getAllowDomain(request);
            } catch (Exception e) {
               /* if (!"online".equals(ConfigHelper.getEnvironmentString()) &&
                        !StringUtils.isEmpty(ConfigHelper.getEnvironmentString())) {
                    throw e;
                } else {
                    log1.error("currentEnv:" + ConfigHelper.getEnvironmentString() + " getAllowDomain error: " +
                            e.toString());
                }*/
            }
            if (!StringUtils.isEmpty(request.getHeader("origin"))) {
                response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
            } else if (!StringUtils.isEmpty(request.getHeader("referer"))) {
                response.setHeader("Access-Control-Allow-Origin", request.getHeader("referer"));
            } else {
                response.setHeader("Access-Control-Allow-Origin", "localhost");
            }
            response.setHeader("Access-Control-Allow-Headers",
                    "host,origin,referer,user-agent,cookie,x-requested-with,content-type");
            response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS");

            // 非GET 、 POST 直接返回。
            if (!request.getMethod().equals("GET") && !request.getMethod().equals("POST")) {
                if (!request.getMethod().equals("OPTIONS")) {
                    log.error("请求不是GET POST OPTIONS" + request.getRequestURL().toString());
                }
                return;
            }
        } catch (Exception e) {
//            render(request, response, AjaxResult.CODE_SERVER_ERROR, "", null,
//                    e.toString());
            log.error("DeCryptRequest Exception:", e);
            terminated = true;
        }
        Exception ex = null;
        try {
            if (!terminated) {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } catch (Exception e) {
            log.error("filterChain.doFilter", e);
            ex = e;
        } finally {
            long endTime = LocalTime.now().getNano() / 1000000;
            String responseString = (String) servletRequest.getAttribute(RequestFilter.RESPONSE);
            if (responseString != null && responseString.length() > 500) {
                responseString = responseString.substring(0, 500);
            }
            if (responseString == null && ex != null) {
                responseString = ex.toString();
            }
            response.setContentType("application/json; charset=utf-8");
            String[] access = new String[]{
                    //ms
                    String.valueOf(endTime - startTime),
                    //url
                    request.getRequestURL().toString(),
                    //queryString
                    request.getQueryString(),
                    //params
                    params,
                    //RESPONSE
                    responseString,
                    request.getHeader("referer"),
                    //USER-agent
                    request.getHeader("user-agent"),

            };
            accessLog.info(String.join("\t", access));
        }

    }

    private String getParams(Map<String,String[]> parameterMap){
        //todo stringbuffer stringbuilder
        StringBuffer params = new StringBuffer();
        if (parameterMap != null && !parameterMap.isEmpty()) {
            params.append("\tPARAMS={");
            String[] values = null;
            for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                values = entry.getValue();
                params.append('\"').append(entry.getKey()).append("\":");
                if (values == null || values.length == 0) {
                    params.append("\"\"");
                } else if (values.length == 1) {
                    params.append('\"').append(values[0]).append('\"');
                } else {
                    params.append('[');
                    for (int i = 0; i < values.length; i++) {
                        if (i != 0) {
                            params.append(",");
                        }
                        params.append('\"').append(values[i]).append('\"');
                    }
                    params.append(']');
                }
            }
            params.append('}');
        }
        return params.toString();
    }

    @Override
    public void destroy() {

    }
}