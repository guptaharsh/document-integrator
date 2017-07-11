package com.servo.xssfilter;

import com.servo.error.InvalidStatusException;
import com.servo.service.SRVSessionService;
import com.servo.util.SRVUtil;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.NamingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringEscapeUtils;

public class WebFilter
  implements Filter
{
  SRVSessionService sessionSer;
  private FilterConfig filterConfig;
  
  public WebFilter()
  {
    this.filterConfig = null;
  }
  
  public void init(FilterConfig filterConfig)
    throws ServletException
  {
    this.filterConfig = filterConfig;
  }
  
  public void destroy()
  {
    this.filterConfig = null;
  }
  
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException
  {
    long sessionID = 0L;
    Integer userID = Integer.valueOf(0);
    boolean isSessionIdPresent = false;
    boolean isUserIdPresent = false;
    String redirectPage = "  ";
    final HttpServletRequest req = (HttpServletRequest)request;
    HttpServletResponse res = (HttpServletResponse)response;
    res.setCharacterEncoding("UTF-8");
    res.setDateHeader("Expires", 0L);
    String dbName = request.getParameter("dbName");
    if (dbName != null) {
      request.setAttribute("dbName", request.getServletContext().getInitParameter("Cabinet"));
    }
    System.out.println("Configured Origin of Request : " + request.getServletContext().getInitParameter("RequestOrigin"));
    System.out.println("Request Origin :" + req.getHeader("Origin"));
    if ((req.getHeader("Origin") != null) && 
      (!req.getHeader("Origin").equalsIgnoreCase(request.getServletContext().getInitParameter("RequestOrigin"))) && (req.getHeader("Origin").equalsIgnoreCase("null")))
    {
      StringBuffer errorMessage = new StringBuffer();
      errorMessage.append("Request not from same origin");
      request.setAttribute("message", errorMessage.toString());
      request.setAttribute("ERROR_TYPE", "1");
      RequestDispatcher dispatcher = request.getRequestDispatcher("/FilterErrorMsg.jsp");
      dispatcher.forward(request, response);
      errorMessage = null;
      return;
    }
    isSessionIdPresent = false;
    isUserIdPresent = false;
    System.out.println("Servlet Name : " + req.getRequestURI());
//    if ((req.getRequestURI().matches(".*(css|jpg|png|gif|js)")) || (req.getRequestURI().toLowerCase().contains("srvupdatemailtemplateinfo")) || (req.getRequestURI().contains("DMS-FILE-VIEWER")))
//    {
//      System.out.println("request.getServletContext().getInitParameter(\"AllowDomain\")------->" + request.getServletContext().getInitParameter("AllowDomain"));
//      System.out.println("request.getServletContext().getInitParameter(\"RequestOrigin\")------>" + request.getServletContext().getInitParameter("RequestOrigin"));
//      if (!request.getServletContext().getInitParameter("AllowDomain").equalsIgnoreCase(request.getServletContext().getInitParameter("RequestOrigin"))) {
//        res.addHeader("X-FRAME-OPTIONS", "ALLOW-FROM " + request.getServletContext().getInitParameter("AllowDomain"));
//      }
//      chain.doFilter(request, response);
//      return;
//    }
    FilterVar filterVar = validateParameters(request, req.getRequestURI().toLowerCase());
    isSessionIdPresent = filterVar.isIsSessionIdPresent();
    isUserIdPresent = filterVar.isIsUserIdPresent();
    sessionID = filterVar.getSessionId();
    userID = Integer.valueOf(filterVar.getUserID());
    redirectPage = filterVar.getRedirectPage();
    if (!filterVar.isValidateParameter())
    {
      request.setAttribute("message", filterVar.getErrorMessage());
      request.setAttribute("ERROR_TYPE", "1");
      RequestDispatcher dispatcher = request.getRequestDispatcher("/FilterErrorMsg.jsp");
      dispatcher.forward(request, response);
      StringBuffer errorMessage = null;
      return;
    }
//    if ((isSessionIdPresent) && (isUserIdPresent))
//    {
//      Object[] status = new Object[3];
//      try
//      {
//        EJBContext con = new EJBContext();
//        SRVSessionService sessionSer = (SRVSessionService)con.lookup("SRVSessionServiceImpl", request.getServletContext().getInitParameter("ApplicationServer-MODULE"), request.getServletContext().getInitParameter("ApplicationServer-IP"), request.getServletContext().getInitParameter("ApplicationServer-PORT"));
//        status = sessionSer.validateUserSession(Long.valueOf(sessionID), userID);
//      }
//      catch (NamingException ex)
//      {
//        SRVUtil.printErr(SRVUtil.getExcpStackTrace(ex));
//      }
//      catch (InvalidStatusException ex)
//      {
//        Logger.getLogger(WebFilter.class.getName()).log(Level.SEVERE, null, ex);
//      }
//      System.out.println("status    ---------->  " + status[0]);
//      System.out.println("isSupervisor   --------->    " + status[1]);
//      System.out.println("entitlement   --------->    " + status[2]);
//      if ((req.getRequestURI().toLowerCase().contains("srvwacvalidateentitlement")) && (status[2].toString().charAt(6) != '1'))
//      {
//        response.getOutputStream().write("error".getBytes());
//        return;
//      }
//      if ((req.getRequestURI().toLowerCase().contains("srvwacvalidateentitlement")) && (status[2].toString().charAt(6) == '1'))
//      {
//        response.getOutputStream().write("success".getBytes());
//        return;
//      }
//      if ((req.getRequestURI().toLowerCase().contains("home")) && (redirectPage.toLowerCase().contains("srvwachome")) && (status[2].toString().charAt(6) != '1'))
//      {
//        System.out.println("Only Supervisor can work");
//        StringBuffer errorMessage = new StringBuffer();
//        errorMessage.append("Only Supervisor can work");
//        request.setAttribute("message", errorMessage.toString());
//        request.setAttribute("ERROR_TYPE", "1");
//        System.out.println("message----->");
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/FilterErrorMsg.jsp");
//        dispatcher.forward(request, response);
//        errorMessage = null;
//        return;
//      }
//      if (!checkIfUserCanWork(request, response, req.getRequestURI().toLowerCase(), status[1].toString(), userID))
//      {
//        System.out.println("Only Supervisor can work");
//        StringBuffer errorMessage = new StringBuffer();
//        errorMessage.append("Only Supervisor can work");
//        request.setAttribute("message", errorMessage.toString());
//        request.setAttribute("ERROR_TYPE", "1");
//        System.out.println("message----->");
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/FilterErrorMsg.jsp");
//        dispatcher.forward(request, response);
//        errorMessage = null;
//        return;
//      }
//      if (Integer.parseInt(status[0].toString()) == 0)
//      {
//        System.out.println("filter--------> validation failed");
//        StringBuffer errorMessage = new StringBuffer();
//        errorMessage.append("Request parameters tempered");
//        request.setAttribute("message", errorMessage.toString());
//        request.setAttribute("ERROR_TYPE", "1");
//        res.sendError(1504, "User Session Invalid");
//        
//        errorMessage = null;
//        return;
//      }
//      System.out.println("filter--------> validation Success...");
//    }
    System.out.println("Servlet Name : " + req.getRequestURI() + " ----------end do filter");
    System.out.println("Servlet Name : " + req.getRequestURI() + " ----------end do filter");
//    if (!request.getServletContext().getInitParameter("AllowDomain").equalsIgnoreCase(request.getServletContext().getInitParameter("RequestOrigin"))) {
//      res.addHeader("X-FRAME-OPTIONS", "ALLOW-FROM " + request.getServletContext().getInitParameter("AllowDomain"));
//    }
    if (!req.getRequestURI().toLowerCase().contains("srvopenactivityform"))
    {
      ServletRequest modifiedRequest = new HttpServletRequestWrapper(req)
      {
        public String getParameter(String param)
        {
          String value = req.getParameter(param);
          if ((param.equalsIgnoreCase("searchQuery")) || (param.toLowerCase().contains("complex")))
          {
            System.out.println("[webFilter] getParameter(String param)");
            SRVUtil.printOut("[webFilter] getParameter(String param)");
            return value;
          }
          if ((req.getRequestURI().toLowerCase().contains("srvupdatesearchvariablelist")) && (param.equalsIgnoreCase("object"))) {
            return value;
          }
          if (((value != null) && (value.contains("{"))) || ((value != null) && (value.contains("["))))
          {
            value = value.replaceAll("%26", "&amp;");
            value = value.replaceAll("\\\\\"", "&quot;");
            if ((!param.equalsIgnoreCase("transDataStr1")) && (!param.equalsIgnoreCase("transDataStr2")) && (!param.equalsIgnoreCase("transValueStr")) && (!param.equalsIgnoreCase("searchQuery"))) {
              value = value.replaceAll("'", "&#39;");
            }
            value = value.replaceAll("%27", "&#39;");
            value = value.replaceAll("%22", "&quot;");
            value = value.replaceAll("\\\r|\\\n", "_");
            value = value.replaceAll("%0d", "_");
            value = value.replaceAll("%0a", "_");
            value = value.replaceAll("%0D", "_");
            value = value.replaceAll("%0A", "_");
            System.out.println("param : " + param + "    value: " + value);
            return value;
          }
          value = StringEscapeUtils.escapeHtml(req.getParameter(param));
          if (value != null)
          {
            value = value.replaceAll("%22", "&quot;");
            if ((!param.equalsIgnoreCase("transDataStr1")) && (!param.equalsIgnoreCase("transDataStr2")) && (!param.equalsIgnoreCase("transValueStr")) && (!param.equalsIgnoreCase("searchQuery")) && (!param.equalsIgnoreCase("appendQuery"))) {
              value = value.replaceAll("'", "&#39;");
            }
            value = value.replaceAll("%27", "&#39;");
            value = value.replaceAll("\\\r|\\\n", "_");
            value = value.replaceAll("%0d", "_");
            value = value.replaceAll("%0a", "_");
            value = value.replaceAll("%0D", "_");
            value = value.replaceAll("%0A", "_");
            value = value.replaceAll("--", " ");
          }
          System.out.println("param1 : " + param + "    value: " + value);
          return value;
        }
      };
      chain.doFilter(modifiedRequest, response);
    }
    else
    {
      chain.doFilter(request, response);
    }
  }
  
  private FilterVar validateParameters(ServletRequest request, String servletName)
  {
    Enumeration<String> params = request.getParameterNames();
    
    FilterVar filterObjValidate = new FilterVar();
    while (params.hasMoreElements())
    {
      String paramName = (String)params.nextElement();
      FilterVar filterObj = searchReservedChars(request.getParameter(paramName), paramName, servletName);
      if (filterObj.isFilterFlag())
      {
        filterObj.setValidateParameter(false);
        
        return filterObj;
      }
      if (filterObj.isIsSessionIdPresent())
      {
        filterObjValidate.setIsSessionIdPresent(true);
        filterObjValidate.setSessionId(filterObj.getSessionId());
      }
      if (filterObj.isIsUserIdPresent())
      {
        filterObjValidate.setIsUserIdPresent(true);
        filterObjValidate.setUserID(filterObj.getUserID());
      }
    }
    return filterObjValidate;
  }
  
  private FilterVar searchReservedChars(String value, String paramName, String servletName)
  {
    FilterVar filterVar = new FilterVar();
    
    value = value.toLowerCase();
    System.out.println("paramName------------->" + paramName + "      value---------->" + value);
    if ((servletName.toLowerCase().contains("srvclosureactionpinstlist")) && (paramName.equalsIgnoreCase("searchQuery")))
    {
      System.out.println("[webFilter] for Reopen and closure issue.");
      SRVUtil.printOut("[webFilter] for Reopen and closure issue.");
      
      filterVar.setFilterFlag(false);
      return filterVar;
    }
    if ((servletName.toLowerCase().contains("srvprocessinstancelist")) && (paramName.equalsIgnoreCase("searchQuery")))
    {
      if ((value.contains(" or ")) || (value.contains(" select ")) || (value.contains(",select ")) || (value.contains(" insert ")) || (value.contains(" delete ")) || (value.contains(" update ")))
      {
        StringBuffer errorMessage = new StringBuffer();
        errorMessage.append("Suspicious input [ " + paramName + " contains value may lead to sql injection]. Please go to the previous screen to correct this problem.");
        System.out.println("errorMessage ::: " + errorMessage);
        SRVUtil.printOut("errorMessage ::: " + errorMessage);
        
        filterVar.setFilterFlag(true);
        filterVar.setErrorMessage(errorMessage);
        return filterVar;
      }
      filterVar.setFilterFlag(false);
      return filterVar;
    }
    if (paramName.toLowerCase().contains("complex"))
    {
      filterVar.setFilterFlag(false);
      return filterVar;
    }
    if ((servletName.toLowerCase().contains("srvmasterdata")) && (paramName.equalsIgnoreCase("conditionValues")))
    {
      if ((value.contains(" or ")) || (value.contains(" select ")) || (value.contains(",select ")) || (value.contains(" insert ")) || (value.contains(" delete ")) || (value.contains(" update ")))
      {
        StringBuffer errorMessage = new StringBuffer();
        errorMessage.append("Suspicious input [ " + paramName + " contains value may lead to sql injection]. Please go to the previous screen to correct this problem.");
        System.out.println("errorMessage ::: " + errorMessage);
        SRVUtil.printOut("errorMessage ::: " + errorMessage);
        
        filterVar.setFilterFlag(true);
        filterVar.setErrorMessage(errorMessage);
        return filterVar;
      }
      filterVar.setFilterFlag(false);
      return filterVar;
    }
    if (paramName.equalsIgnoreCase("sessionid"))
    {
      filterVar.setIsSessionIdPresent(true);
      filterVar.setSessionId(Long.parseLong(value));
    }
    if (paramName.equalsIgnoreCase("userid"))
    {
      filterVar.setIsUserIdPresent(true);
      filterVar.setUserID(Integer.parseInt(value));
    }
    if ((servletName.toLowerCase().contains("home")) && (paramName.equalsIgnoreCase("page")))
    {
      System.out.println("home redirect request found ... redirectPage : " + value);
      

      filterVar.setRedirectPage(value);
    }
    if (((servletName.contains("srvsaveprocessinstanceex")) && (paramName.equalsIgnoreCase("transData"))) || ((servletName.contains("srvcreatepinstanceex")) && (paramName.equalsIgnoreCase("transData"))))
    {
      filterVar.setFilterFlag(false);
      return filterVar;
    }
    if ((servletName.contains("srvupdatesearchvariablelist")) && (paramName.equalsIgnoreCase("object")))
    {
      filterVar.setFilterFlag(false);
      return filterVar;
    }
    if (((servletName.contains("srvclosureactionpinstlist")) && (paramName.equalsIgnoreCase("searchQuery"))) || ((servletName.contains("srvsearchprocessinstance")) && (paramName.equalsIgnoreCase("searchQuery"))) || (servletName.contains("srvsaveprocessinstance")) || (servletName.contains("srvcreatepinstance")))
    {
      if ((value.contains(" or ")) || (value.contains(" select ")) || (value.contains(",select ")) || (value.contains(" insert ")) || (value.contains(" delete ")) || (value.contains(" update ")))
      {
        StringBuffer errorMessage = new StringBuffer();
        errorMessage.append("Suspicious input [ " + paramName + " contains value may lead to sql injection]. Please go to the previous screen to correct this problem.");
        System.out.println("errorMessage ::: " + errorMessage);
        SRVUtil.printOut("errorMessage ::: " + errorMessage);
        
        filterVar.setFilterFlag(true);
        filterVar.setErrorMessage(errorMessage);
        return filterVar;
      }
    }
    else if ((value.contains(" and ")) || (value.contains(" or ")) || (value.contains(" select ")) || (value.contains(",select ")) || (value.contains(" insert ")) || (value.contains(" delete ")) || (value.contains(" update "))) {
      if ((servletName.contains("srvgetfilter")) && (paramName.equalsIgnoreCase("appendQuery")))
      {
        System.out.println("Filter Skip");
        SRVUtil.printOut("Filter Skip");
      }
      else
      {
        StringBuffer errorMessage = new StringBuffer();
        errorMessage.append("Suspicious input [ " + paramName + " contains value may lead to sql injection]. Please go to the previous screen to correct this problem.");
        System.out.println("errorMessage------->" + errorMessage);
        SRVUtil.printOut("errorMessage------->" + errorMessage);
        
        filterVar.setFilterFlag(true);
        filterVar.setErrorMessage(errorMessage);
        return filterVar;
      }
    }
    if ((!paramName.equalsIgnoreCase("storedStatements")) && (!paramName.equalsIgnoreCase("transDataStr1")) && (!paramName.equalsIgnoreCase("transDataStr2")) && (!paramName.equalsIgnoreCase("transValueStr")) && (!paramName.equalsIgnoreCase("searchQuery")))
    {
      Pattern xsspattern = Pattern.compile("[\\w]*((%27)|('))\\s*((%6F)|o|(%4F))((%72)|r|(%52))|[\\w]*((%27)|('))\\s*((%61)|a|(%41))((%6E)|n|(%4E))((%64)|d|(%44))|(((%3E)|>|(%3C)|<))|(((%3E)|>|(%3C)|<)+.*[://.=/(/);'\"&#-]+.*)|(.*[://.=/(/);'\"&#-]+.*((%3E)|>|(%3C)|<)+)|(((%3C)|<)((%69)|i|(%49))((%6D)|m|(%4D))((%67)|g|(%47))[^\\n]+((%3E)|>))");
      Matcher match = xsspattern.matcher(value);
      if (match.find())
      {
        StringBuffer errorMessage = new StringBuffer();
        String charstr = value.substring(match.start(), match.end());
        charstr = charstr.replaceAll(">", "&gt;");
        charstr = charstr.replaceAll("<", "&lt;");
        errorMessage.append("Suspicious input [ ").append(charstr).append(" ]. Please go to the previous screen to correct this problem.");
        
        filterVar.setFilterFlag(true);
        filterVar.setErrorMessage(errorMessage);
        return filterVar;
      }
    }
    else
    {
      Pattern xsspattern = Pattern.compile("(((%3E)|>|(%3C)|<))|(((%3E)|>|(%3C)|<)+.*[://.=/(/);'\"&#-]+.*)|(.*[://.=/(/);'\"&#-]+.*((%3E)|>|(%3C)|<)+)|(((%3C)|<)((%69)|i|(%49))((%6D)|m|(%4D))((%67)|g|(%47))[^\\n]+((%3E)|>))");
      Matcher match = xsspattern.matcher(value);
      if (match.find())
      {
        StringBuffer errorMessage = new StringBuffer();
        String charstr = value.substring(match.start(), match.end());
        charstr = charstr.replaceAll(">", "&gt;");
        charstr = charstr.replaceAll("<", "&lt;");
        errorMessage.append("Suspicious input [ ").append(charstr).append(" ]. Please go to the previous screen to correct this problem.");
        
        filterVar.setFilterFlag(true);
        filterVar.setErrorMessage(errorMessage);
        return filterVar;
      }
    }
    filterVar.setFilterFlag(false);
    return filterVar;
  }
  
  private boolean checkIfUserCanWork(ServletRequest request, ServletResponse response, String servletName, String isSupervisor, Integer userId)
    throws IOException, ServletException
  {
    if ((servletName.contains("wac/")) && (!isSupervisor.equalsIgnoreCase("1")))
    {
      System.out.println("reach 1-------->" + servletName);
      return false;
    }
    System.out.println("reach 2---->" + servletName);
    if ((servletName.contains("srvupdateusergrouplist")) || (servletName.contains("srvupdateprocessusers")) || (servletName.contains("createdocdefcontroller")) || (servletName.contains("deletedocdefcontroller")) || (servletName.contains("updatedocdefcontroller")) || (servletName.contains("srvdeployprocess")) || (servletName.contains("srvupdatemappedtablelist")) || (servletName.contains("srvupdatemailtemplateinfo")) || (servletName.contains("srvregisterextensionmethods")) || (servletName.contains("srvclosureaction")) || (servletName.contains("srvupdatesearchvariablelist")) || (servletName.contains("srvassigndocdefcontroller")) || (servletName.contains("srvgeneralupdate")) || (servletName.contains("srvupdatesearchvariablelist")) || (servletName.contains("srvupdateruleorder")) || (servletName.contains("srvupdateruleexecution")) || (servletName.contains("srvupdateactivityusers")) || (servletName.contains("srvprocessdocdef")) || (servletName.contains("srvprocessrights")) || (servletName.contains("srvprocessupdate")) || (servletName.contains("srvvariablemapping")) || (servletName.contains("srvworklist")) || (servletName.contains("srvgetassigndocDefcontroller")) || (servletName.contains("srvactivityupdate")) || (servletName.contains("srvactivityworklist")) || (servletName.contains("srvrule")) || (servletName.contains("srvactivityrights")) || (servletName.contains("actf_admin")))
    {
      if (isSupervisor.equalsIgnoreCase("1")) {
        return true;
      }
      return false;
    }
    if ((servletName.contains("srvupdateusergrouplist")) || (servletName.contains("srvupdateaccessrights")))
    {
      if (userId.intValue() == 1) {
        return true;
      }
      return false;
    }
    return true;
  }
  
  public class FilterVar
  {
    private long sessionId = 0L;
    private int userID = 0;
    private boolean filterFlag = false;
    private StringBuffer errorMessage = new StringBuffer("");
    private boolean isSessionIdPresent = false;
    private boolean isUserIdPresent = false;
    private boolean validateParameter = true;
    private String redirectPage = "  ";
    
    public FilterVar() {}
    
    public long getSessionId()
    {
      return this.sessionId;
    }
    
    public void setSessionId(long sessionId)
    {
      this.sessionId = sessionId;
    }
    
    public int getUserID()
    {
      return this.userID;
    }
    
    public void setUserID(int userID)
    {
      this.userID = userID;
    }
    
    public boolean isFilterFlag()
    {
      return this.filterFlag;
    }
    
    public void setFilterFlag(boolean filterFlag)
    {
      this.filterFlag = filterFlag;
    }
    
    public StringBuffer getErrorMessage()
    {
      return this.errorMessage;
    }
    
    public void setErrorMessage(StringBuffer errorMessage)
    {
      this.errorMessage = errorMessage;
    }
    
    public boolean isIsSessionIdPresent()
    {
      return this.isSessionIdPresent;
    }
    
    public void setIsSessionIdPresent(boolean isSessionIdPresent)
    {
      this.isSessionIdPresent = isSessionIdPresent;
    }
    
    public boolean isIsUserIdPresent()
    {
      return this.isUserIdPresent;
    }
    
    public void setIsUserIdPresent(boolean isUserIdPresent)
    {
      this.isUserIdPresent = isUserIdPresent;
    }
    
    public String getRedirectPage()
    {
      return this.redirectPage;
    }
    
    public void setRedirectPage(String redirectPage)
    {
      this.redirectPage = redirectPage;
    }
    
    public boolean isValidateParameter()
    {
      return this.validateParameter;
    }
    
    public void setValidateParameter(boolean validateParameter)
    {
      this.validateParameter = validateParameter;
    }
  }
}
