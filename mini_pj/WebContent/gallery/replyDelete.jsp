<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>

<c:if test = "${not empty param.idx }">
	<c:set var="row" value="${GalleryDAO.deleteReply(param.idx) }" />
	<c:redirect url="/gallery/read.jsp">
      	<c:param name="idx" value="${param.gnum }"/>
     </c:redirect>
</c:if>

<c:if test="${empty param.idx }">
	<script>
		swal({
				title: '잘못된 접근입니다.',
				type: 'error',
				button: '확인'
			}, function() {
				location.href = '${cpath}/gallery/read.jsp?idx=${param.gnum}';
			})
	</script>
</c:if>
</body>
</html>