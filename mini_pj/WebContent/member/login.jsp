<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>

<c:if test="${not empty login }">
	<%	session.invalidate();%>
	<script>
		location.href = "${cpath }/member/login.jsp";
	</script>
</c:if>

<div class="login member">
	<h2>Login</h2>
	<form action="login-submit.jsp" method="POST">
		<p><input type="text" name="id" placeholder="ID"></p>
		<p><input type="password" name="pw" placeholder="Password"></p>
		<p><input type="submit" value="Login"></p>
	</form>
	<div>
		<a href="${cpath }/member/join.jsp">회원가입</a>
	</div>
</div>


</body>
</html>