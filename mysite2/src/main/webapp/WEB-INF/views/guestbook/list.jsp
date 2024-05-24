<%@page import="java.util.List"%>
<%@page import="com.poscodx.mysite.vo.GuestbookVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	List<GuestbookVo> list = (List<GuestbookVo>) request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="<%=request.getContextPath()%>/assets/css/guestbook.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<form action="<%=request.getContextPath()%>/guestbook" method="post">
					<input type="hidden" name="a" value="insert">
					<table>
						<tr>
							<td>이름</td>
							<td><input type="text" name="name"></td>
							<td>비밀번호</td>
							<td><input type="password" name="pass"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="content" id="content"></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>
				</form>
				<ul>
					<%
						for (int i = list.size(); i > 0; i--) {
					%>
					<li>
						<table>
							<tr>
								<td>[<%=i%>]
								</td>
								<td><%=list.get(i - 1).getName()%></td>
								<td><%=list.get(i - 1).getDate()%></td>
								<td><a href="<%=request.getContextPath() %>/guestbook?a=deleteform&no=<%=list.get(i-1).getNo() %>">삭제</a></td>
							</tr>
							<tr>
								<td colspan=4><%=list.get(i - 1).getContents().replace("\n", "<br>")%></td>
								</td>
							</tr>
						</table> <br> 
					</li>
					<%
 						}
					%>
				</ul>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp" />
		<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>