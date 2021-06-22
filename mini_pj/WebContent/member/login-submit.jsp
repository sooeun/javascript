<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>

<jsp:useBean id="inputData" class="member.MemberDTO"/>
<jsp:setProperty property="*" name="inputData"/>
<c:set var="login" value="${MemberDAO.login(inputData)}" scope="session"/>

<c:if test = "${not empty login }">
	<script>
		location.href = '${cpath}/gallery/gallery.jsp';
	</script>
</c:if>

<c:if test="${empty login }">
	<script>
		swal({
			title: '로그인 실패',
			text: '다시 로그인해 주세요.',
			type: 'error',
			button: '확인'
		}, function() {
			location.href = '${cpath}/member/login.jsp';
		})
	</script>
</c:if>

</body>
</html>