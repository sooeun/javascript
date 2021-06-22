<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>


<div class="join member">
	<h2>Join</h2>
	<form action="join-submit.jsp" method="POST" enctype="multipart/form-data">
		<h5>아이디</h5>
		<p><input type="text" name="id" required></p>
		<h5>비밀번호</h5>
		<p><input type="password" name="pw" required></p>
		<h5>닉네임</h5>
		<p><input type="text" name="nickname" required></p>
		<h5>자기소개</h5>
		<p><input type="text" name="intro"></p>
		<h5>프로필이미지</h5>
		<p><input type="file" name="profilename"></p>
		<p><input type="submit" value="Join"></p>
	</form>
</div>


</body>
</html>