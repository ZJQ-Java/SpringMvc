package com.qiu.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class TestFilterOrder implements Filter {


    @Override

    public void init(FilterConfig filterConfig) throws ServletException {

    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        System.out.println("----   ----testFilter order-  --------");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("---------------testFilter after ------------------------");

    }

    private String getParams(Map<String, String[]> parameterMap) {
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