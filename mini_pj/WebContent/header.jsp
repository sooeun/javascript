<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ page import="member.MemberDAO"%>
<%@page import="gallery.GalleryDAO"%>

<c:set var="MemberDAO" value="<%= MemberDAO.getInstance() %>" />    
<c:set var="GalleryDAO" value="<%= GalleryDAO.getInstance() %>" />    
<c:set var="cpath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FootLOG</title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>
<link rel="stylesheet"href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" />
<link type="text/css" href="<%=request.getContextPath() %>/css/style3.css" rel="stylesheet">
</head>
<body>
<header class="menu">
	<c:set var="mainPath" value="${empty login ? '' : '/gallery/gallery.jsp' }" />
	<h1 class="logo"><a href="${cpath }${mainPath }">FootLOG</a></h1>
	<nav class="menu">
		<ul>
			<c:if test="${not empty login }">
				<li><a href="${cpath }/gallery/myhome.jsp">MY Home</a></li>
				<li><a href="${cpath }/gallery/write.jsp">Write</a></li>
			</c:if>
			<li><a href="${cpath }/gallery/gallery.jsp">Gallery</a></li>
			<li><a href="${cpath }/member/login.jsp">${empty login ? 'Login' : 'Logout'}</a></li>
		</ul>
	</nav>
</header>

</body>
</html>