<%@page import="com.syx.taobao.service.PropertiesService"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.syx.taobao.ueditor.ActionEnter"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%

    request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");
	
	String rootPath = application.getRealPath( "/" );
	//String url = new ActionEnter( request, rootPath ).exec();
	ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
	PropertiesService propertyService = (PropertiesService)ac.getBean("propertiesServiceImpl");
	String url = new ActionEnter( request, rootPath,propertyService ).exec();
	out.write( url );
%>