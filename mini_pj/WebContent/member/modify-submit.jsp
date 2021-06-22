<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>

<jsp:useBean id="FileUtil" class="fileutil.FileUtil" />
<c:set var="dto" value="${FileUtil.getMemberDTO(pageContext.request) }" />
<c:set var="row" value="${MemberDAO.modify(dto) }" />

<c:if test = "${row == 1 }">
	<script>
	swal({
		title: '수정 성공',
		text: '다시 로그인 해주세요!',
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
		title: '수정 실패',
		text: '다시 시도해주세요.',
		type: 'error',
		button: '확인'
	}, function() {
		location.href = '${cpath}/member/modify.jsp';
	})
	</script>
</c:if>

</body>
</html>