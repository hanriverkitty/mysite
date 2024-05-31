<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link href="${pageContext.request.contextPath}/assets/css/mysite.css"
	rel="stylesheet" type="text/css">
<div id="navigation">
	<ul>
		<c:choose>
			<c:when test='${param.menu == "guestbook" }'>
				<li><a href="${pageContext.request.contextPath}">신찬규</a></li>
				<li class='selected'><a href="${pageContext.request.contextPath}/guestbook">방명록</a></li>
				<li><a href="${pageContext.request.contextPath}/board?p=1">게시판</a></li>
			</c:when>
			<c:when test='${param.menu == "board" }'>
				<li><a href="${pageContext.request.contextPath}">신찬규</a></li>
				<li><a href="${pageContext.request.contextPath}/guestbook">방명록</a></li>
				<li class='selected'><a href="${pageContext.request.contextPath}/board?p=1">게시판</a></li>
			</c:when>
			<c:otherwise>
				<li class='selected'><a href="${pageContext.request.contextPath}">신찬규</a></li>
				<li><a href="${pageContext.request.contextPath}/guestbook">방명록</a></li>
				<li><a href="${pageContext.request.contextPath}/board?p=1">게시판</a></li>
			</c:otherwise>
		</c:choose>
	</ul>
</div>