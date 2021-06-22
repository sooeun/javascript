<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>

<div class="join member">
	<h2>정보 수정</h2>
	<form action="modify-submit.jsp" method="POST" enctype="multipart/form-data">
		<input type="hidden" name="id" value="${login.id }">
		<h5>비밀번호</h5>
		<p><input type="password" name="pw" placeholder="Password"></p>
		<h5>닉네임</h5>
		<p><input type="text" name="nickname" value="${login.nickname }" required></p>
		<h5>자기소개</h5>
		<p><input type="text" name="intro" value="${login.intro }"></p>
		<h5>프로필이미지</h5>
		<p><input type="file" name="profilename"></p>
		<p><sub>현재 파일 : ${login.profilename == 'default.jpg' ? '없음' : login.profilename }</sub></p>
		<p><input type="submit" value="정보 수정"></p>
	</form>
</div>

</body>
</html>