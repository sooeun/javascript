<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<div class="wrap">
	<div class="mainImg">
		<img src="${cpath }/uploadfile/main.jpg">
	</div>
	<div class="mainContent ">
		<div>
			<h1>FootLOG</h1>
			<h3>SubTitle</h3>
			<pre>Lorem ipsum dolor sit amet,
ut labore et dolore magna aliqua.
Nisl tincidunt eget nullam non.
magna eget est lorem ipsum dolor sit. 
Volutpat odio facilisissit amet massa.</pre>
			<c:set var="mainpath" value="${empty login ? '/member/join.jsp' : '/gallery/gallery.jsp' }"/>
			<button class="join Btn" onclick="location.href='${cpath }${mainpath}';">JOIN</button>
		</div>
	</div>
</div>

</body>
</html>