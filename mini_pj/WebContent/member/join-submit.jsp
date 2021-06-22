<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>

<jsp:useBean id="FileUtil" class="fileutil.FileUtil" />
<c:set var="dto" value="${FileUtil.getMemberDTO(pageContext.request) }" />
<c:set var="row" value="${MemberDAO.join(dto) }" />

<c:if test = "${row != 0 }">
	<script>
	swal({
		title: '회원가입 성공',
		text: '반가워요, 풋로거!',
		type: 'success',
		button: '확인'
	}, function() {
		location.href = '${cpath}/member/login.jsp';
	})
	</script>
</c:if>

<c:if test="${row == 0 }">
	<script>
	swal({
		title: '회원가입 실패',
		text: '다시 시도해주세요.',
		type: 'error',
		button: '확인'
	}, function() {
		location.href = '${cpath}/member/join.jsp';
	})
	</script>
</c:if>

</body>
</html>