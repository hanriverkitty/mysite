<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath}/board" method="post">
					<input type="text" id="kwd" name="kwd" value="${kwd }">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>  
					<c:set var="count" value="${fn:length(list) }" />
					<c:forEach items='${list }' var="vo" varStatus="status">				
					<tr>
						<td>${total-(p-1)*5-status.index }</td>
						<td style="text-align:left; padding-left: ${20*vo.depth}px">
							<c:if test='${vo.depth>0 }'>
							<img src='${pageContext.request.contextPath}/assets/images/reply.png' />
							</c:if>
							<a href="${pageContext.request.contextPath}/board?a=view&no=${vo.no}">${vo.title }</a>
						</td>
						<td>${vo.userName }</td>
						<td>${vo.hit }</td>
						<td>${vo.regDate }</td>
						<td>
							<c:if test='${! empty authUser && authUser.no == vo.userNo}'>
								<a href="${pageContext.request.contextPath}/board?a=delete&no=${vo.no}&p=${p}&kwd=${kwd}" class="del">삭제</a>
							</c:if>
						</td>
					</tr>
					</c:forEach>
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<li>
							<c:choose>
								<c:when test="${startNo-1 > 1}">
									<a href="${pageContext.request.contextPath}/board?p=${startNo-1}&kwd=${kwd}">◀</a>
								</c:when>
								<c:otherwise>
									◀
								</c:otherwise>
							</c:choose>		
						</li>
						
						<c:forEach begin="${startNo }" end="${endNo }" var="i" step="1">
							<c:choose>
								<c:when test="${p==i }">
									<li class="selected">
								</c:when>
								<c:otherwise>
									<li>
								</c:otherwise>
							</c:choose>
							
								<c:choose>
									<c:when test="${i<=block }">
										<a href="${pageContext.request.contextPath}/board?p=${i}&kwd=${kwd}">${i }</a>
									</c:when>
									
									<c:otherwise>
										${i }
									</c:otherwise>
								</c:choose>
								
									</li>
						</c:forEach>
						
						<li>
							<c:choose>
								<c:when test="${endNo < block}">
									<a href="${pageContext.request.contextPath}/board?p=${endNo+1}&kwd=${kwd}">▶</a>
								</c:when>
								<c:otherwise>
									▶
								</c:otherwise>
							</c:choose>		
						</li>
						
					</ul>
				</div>					
				<!-- pager 추가 -->
				
				<div class="bottom">
					<c:if test="${login }">
					<a href="${pageContext.request.contextPath}/board?a=addform" id="new-book">글쓰기</a>
					</c:if>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>