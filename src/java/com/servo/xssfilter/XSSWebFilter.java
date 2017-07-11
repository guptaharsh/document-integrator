package com.servo.xssfilter;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class XSSWebFilter implements Filter {

    private FilterConfig filterConfig;
    long sessionID = 0L;
    Integer userID = Integer.valueOf(0);
    boolean isSessionIdPresent = false;
    boolean isUserIdPresent = false;
    String redirectPage = "  ";
    private static Pattern[] patterns = {
        Pattern.compile("<script>(.*?)</script>", 2),
        Pattern.compile("src[\r\n]*=[\r\n]*\\'(.*?)\\'", 42),
        Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", 42),
        Pattern.compile("</script>", 2),
        Pattern.compile("<script(.*?)>", 42),
        Pattern.compile("eval\\((.*?)\\)", 42),
        Pattern.compile("expression\\((.*?)\\)", 42),
        Pattern.compile("javascript:", 2),
        Pattern.compile("vbscript:", 2),
        Pattern.compile("onload(.*?)=", 42),
        Pattern.compile("onclick(.*?)=", 42),
        Pattern.compile("confirm(.*?)=", 42),
        Pattern.compile("prompt(.*?)=", 42),
        Pattern.compile("onmouseover(.*?)=", 42),
        Pattern.compile("onmouselower(.*?)=", 42),
        Pattern.compile("marquee(.*?)=", 42)
    };

    public XSSWebFilter() {
        this.filterConfig = null;
    }

    public void init(FilterConfig filterConfig)
            throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        res.setCharacterEncoding("UTF-8");
        res.setDateHeader("Expires", 0L);
        FilterVar filterVar = validateParameters(request, req.getRequestURI().toLowerCase());
        this.isSessionIdPresent = filterVar.isIsSessionIdPresent();
        this.isUserIdPresent = filterVar.isIsUserIdPresent();
        this.sessionID = filterVar.getSessionId();
        this.userID = Integer.valueOf(filterVar.getUserID());
        this.redirectPage = filterVar.getRedirectPage();
        if (!filterVar.isValidateParameter()) {
            System.out.println("Not valid!!!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("FilterErrorMsg.jsp");
            request.setAttribute("message", filterVar.getErrorMessage());
            request.setAttribute("ERROR_TYPE", "1");
            dispatcher.forward(request, response);
        }
        System.out.println("Calling: " + req.getRequestURI() + " ----------end do filter");
        chain.doFilter(request, response);
    }

    public void destroy() {
        this.filterConfig = null;
    }

    private FilterVar validateParameters(ServletRequest request, String servletName) {
        Enumeration<String> params = request.getParameterNames();
        FilterVar filterObjValidate = new FilterVar();
        while (params.hasMoreElements()) {
            String paramName = (String) params.nextElement();
            FilterVar filterObj = searchReservedChars(request.getParameter(paramName), paramName, servletName);
            System.out.println("filterObj.isFilterFlag() :::" + filterObj.isFilterFlag());
            if (filterObj.isFilterFlag()) {
                filterObj.setValidateParameter(false);
                return filterObj;
            }
            if (filterObj.isIsSessionIdPresent()) {
                filterObjValidate.setIsSessionIdPresent(true);
                filterObjValidate.setSessionId(filterObj.getSessionId());
            }
            if (filterObj.isIsUserIdPresent()) {
                filterObjValidate.setIsUserIdPresent(true);
                filterObjValidate.setUserID(filterObj.getUserID());
            }
        }
        return filterObjValidate;
    }

    private FilterVar searchReservedChars(String value, String paramName, String servletName) {
        FilterVar filterVar = new FilterVar();
        System.out.println("servletName------------->" + servletName);
        System.out.println("paramName------------->" + paramName + "      value---------->" + value);
        if (servletName.toLowerCase().contains("srvgetdb")) {
            Pattern xsspattern = Pattern.compile("[\\w]*((%27)|('))\\s*((%6F)|o|(%4F))((%72)|r|(%52))|[\\w]*((%27)|('))\\s*((%61)|a|(%41))((%6E)|n|(%4E))((%64)|d|(%44))|(((%3E)|>|(%3C)|<))|(((%3E)|>|(%3C)|<)+.*[://.=/(/);'\"&#-]+.*)|(.*[://.=/(/);'\"&#-]+.*((%3E)|>|(%3C)|<)+)|(((%3C)|<)((%69)|i|(%49))((%6D)|m|(%4D))((%67)|g|(%47))[^\\n]+((%3E)|>))");
            Matcher match = xsspattern.matcher(value);
            if (match.find()) {
                StringBuffer errorMessage = new StringBuffer();
                String charstr = value.substring(match.start(), match.end());
                charstr = charstr.replaceAll(">", "&gt;");
                charstr = charstr.replaceAll("<", "&lt;");
                errorMessage.append("Suspicious input [ ").append(charstr).append(" ]. Please go to the previous screen to correct this problem.");
                filterVar.setFilterFlag(true);
                filterVar.setErrorMessage(errorMessage);
                return filterVar;
            }
        } else {
            for (Pattern scriptPattern : patterns) {
                Matcher match = scriptPattern.matcher(value);
                if (match.find()) {
                    StringBuffer errorMessage = new StringBuffer();
                    String charstr = value.substring(match.start(), match.end());
                    charstr = charstr.replaceAll(">", "&gt;");
                    charstr = charstr.replaceAll("<", "&lt;");
                    errorMessage.append("Suspicious input [ ").append(charstr).append(" ]. Please go to the previous screen to correct this problem.");
                    System.out.println("errorMessage:::::" + errorMessage);
                    filterVar.setFilterFlag(true);
                    filterVar.setErrorMessage(errorMessage);
                    return filterVar;
                }
            }
        }
        return filterVar;
    }

    public class FilterVar {

        private long sessionId = 0L;
        private int userID = 0;
        private boolean filterFlag = false;
        private StringBuffer errorMessage = new StringBuffer("");
        private boolean isSessionIdPresent = false;
        private boolean isUserIdPresent = false;
        private boolean validateParameter = true;
        private String redirectPage = "  ";

        public FilterVar() {
        }

        public long getSessionId() {
            return this.sessionId;
        }

        public void setSessionId(long sessionId) {
            this.sessionId = sessionId;
        }

        public int getUserID() {
            return this.userID;
        }

        public void setUserID(int userID) {
            this.userID = userID;
        }

        public boolean isFilterFlag() {
            return this.filterFlag;
        }

        public void setFilterFlag(boolean filterFlag) {
            this.filterFlag = filterFlag;
        }

        public StringBuffer getErrorMessage() {
            return this.errorMessage;
        }

        public void setErrorMessage(StringBuffer errorMessage) {
            this.errorMessage = errorMessage;
        }

        public boolean isIsSessionIdPresent() {
            return this.isSessionIdPresent;
        }

        public void setIsSessionIdPresent(boolean isSessionIdPresent) {
            this.isSessionIdPresent = isSessionIdPresent;
        }

        public boolean isIsUserIdPresent() {
            return this.isUserIdPresent;
        }

        public void setIsUserIdPresent(boolean isUserIdPresent) {
            this.isUserIdPresent = isUserIdPresent;
        }

        public String getRedirectPage() {
            return this.redirectPage;
        }

        public void setRedirectPage(String redirectPage) {
            this.redirectPage = redirectPage;
        }

        public boolean isValidateParameter() {
            return this.validateParameter;
        }

        public void setValidateParameter(boolean validateParameter) {
            this.validateParameter = validateParameter;
        }
    }
}
