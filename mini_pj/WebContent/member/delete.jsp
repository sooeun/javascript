<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>

<c:if test="${pageContext.request.method == 'GET' }">
	<div class="join member">
		<h2>회원 탈퇴</h2>
		<form method="POST">
		<input type="hidden" name="id" value="${login.id }">
	      <h5>비밀번호 재확인</h5>
	      <p><input type="password" name="pw" required></p>
	      <p><input type="submit" value="회원 탈퇴"></p>   
		</form>
	</div>
</c:if>


<c:if test="${pageContext.request.method=='POST' }">
	<jsp:useBean id="dto" class="member.MemberDTO"/>
	<jsp:setProperty property="*" name="dto"/>
	<c:if test="${login.pw == param.pw}">
		<c:set var="row" value="${MemberDAO.delete(dto)}"/>
		<% session.invalidate(); %>
		<script>
			const row = ${row};
			alert(row == 1 ? '탈퇴 완료' : '탈퇴 실패');
			location.href = '${cpath}/member/join.jsp';
		</script>
	</c:if>
	<c:if test="${login.pw != param.pw}">
		<script>
		swal({
			title: '탈퇴 실패',
			text: '다시 시도해주세요.',
			type: 'error',
			button: '확인'
		}, function() {
			location.href = '${cpath}/member/delete.jsp';
		})
		</script>
	</c:if>
</c:if>


</body>
</html>