<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>

<c:if test = "${not empty param.idx }">
	<c:set var="row" value="${GalleryDAO.delete(param.idx) }" />
	<c:redirect url="/gallery/gallery.jsp"/>
</c:if>

<c:if test="${empty param.idx }">
	<script>
		swal({
				title: '잘못된 접근입니다.',
				type: 'error',
				button: '확인'
			}, function() {
				location.href = '${cpath}';
			})
	</script>
</c:if>

</body>
</html>