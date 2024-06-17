<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<link href="${pageContext.request.contextPath}/assets/css/mysite.css"
	rel="stylesheet" type="text/css">
<div id="navigation">
	<ul>
		<c:choose>
			<c:when test='${param.menu == "guestbook" }'>
				<li><a href="${pageContext.request.contextPath}"><spring:message code='navigation.li.main'/></a></li>
				<li class='selected'><a href="${pageContext.request.contextPath}/guestbook"><spring:message code='navigation.li.guestbook'/></a></li>
				<li><a href="${pageContext.request.contextPath}/board?p=1"><spring:message code='navigation.li.board'/></a></li>
			</c:when>
			<c:when test='${param.menu == "board" }'>
				<li><a href="${pageContext.request.contextPath}"><spring:message code='navigation.li.main'/></a></li>
				<li><a href="${pageContext.request.contextPath}/guestbook"><spring:message code='navigation.li.guestbook'/></a></li>
				<li class='selected'><a href="${pageContext.request.contextPath}/board?p=1"><spring:message code='navigation.li.board'/></a></li>
			</c:when>
			<c:otherwise>
				<li class='selected'><a href="${pageContext.request.contextPath}"><spring:message code='navigation.li.main'/></a></li>
				<li><a href="${pageContext.request.contextPath}/guestbook"><spring:message code='navigation.li.guestbook'/></a></li>
				<li><a href="${pageContext.request.contextPath}/board?p=1"><spring:message code='navigation.li.board'/></a></li>
			</c:otherwise>
		</c:choose>
	</ul>
</div>