<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>

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

<c:if test = "${not empty param.idx }">
	<c:set var="galleryDTO" value="${GalleryDAO.selectOne(param.idx) }"/>
	<div class="write">
		<form method="POST" enctype="multipart/form-data">
			<c:set var="idx" value="${param.idx }" />
			<input type="hidden" name="idx" value="${galleryDTO.idx }">
			<h5>작성자</h5>
			<p><input type="text" name="writer" readonly value="${galleryDTO.writer }"></p>
			<h5>여행 장소</h5>
			<p><input type="text" name="local" value="${galleryDTO.local }" required></p>
			<h5>출발일</h5>
			<p><input type="date" name="startdate" value="${galleryDTO.startdate }" required></p>
			<h5>리턴일</h5>
			<p><input type="date" name="enddate" value="${galleryDTO.enddate }"  required></p>
			<h5>본문</h5>
			<p><input type="text" name="content" value="${galleryDTO.content }" required></p>
			<h5>사진</h5>
			<p><input type="file" name="filename"></p>
			<p><sub>현재 파일 : ${galleryDTO.filename }</sub></p>
			<p><input type="submit" value="글수정"></p>
		</form>
	</div>
</c:if>

<c:if test="${pageContext.request.method=='POST' }">
	<jsp:useBean id="FileUtil" class="fileutil.FileUtil" />
	<c:set var="galleryDTO" value="${FileUtil.getGalleryDTO(pageContext.request) }" />
	<jsp:setProperty property="idx" name="galleryDTO" value="${param.idx }"/>
	<c:set var="row" value="${GalleryDAO.modify(galleryDTO) }" />
	
	<c:if test = "${row == 1 }">
	   <script>
	   swal({
	      title: '글수정 성공',
	      type: 'success',
	      button: '확인'
	   }, function() {
	      location.href = '${cpath}/gallery/read.jsp?idx=${galleryDTO.idx}';
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
	      location.href = '${cpath}/gallery/read.jsp?idx=${galleryDTO.idx}';
	   })
  	 </script>
	</c:if>
	
</c:if>

</body>
</html>