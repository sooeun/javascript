<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>

<jsp:useBean id="FileUtil" class="fileutil.FileUtil" />
<c:set var="dto" value="${FileUtil.getGalleryDTO(pageContext.request) }" />
<c:set var="row" value="${GalleryDAO.write(dto) }" />

<c:if test = "${row == 1 }">
	<c:redirect url="/gallery/gallery.jsp"/>
</c:if>

<c:if test="${row == 0 }">
	<script>
	swal({
		title: '글쓰기 실패',
		text: '다시 시도해주세요.',
		type: 'error',
		button: '확인'
	}, function() {
		location.href = '${cpath}/gallery/write.jsp';
	})
	</script>
</c:if>

</body>
</html>