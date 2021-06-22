<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>

<c:set var="reply" value="${GalleryDAO.selectReply(param.idx) }"/>
<c:set var="gnum" value="${param.gnum}"/>

<!-- 댓글 수정-기존 댓글 보여주고 수정 -->
<div>
   <div class="reply-form">
      <form method="POST">
         <input type="hidden" name="gnum" value="${gnum }"> <!-- 댓글이 있는 글 번호 전달 -->
         <input type="hidden" name="idx" value="${reply.idx }"> <!-- 댓글 인덱스 전달 -->
         <p><input type="text" name="writer" value="${reply.writer }" readonly required></p> 
         <textarea name="content" 
                 placeholder="${reply.content }"
                 ${empty login ? 'disabled' : '' }
                 required></textarea>
         <input class="Btn" type="submit" value="댓글 수정">
      </form>
   </div>
</div>


<c:if test="${pageContext.request.method == 'POST'}">
      <jsp:useBean id="reply" class="gallery.ReplyDTO" />
      <jsp:setProperty property="*" name="reply"/>
      <c:set var="row" value="${GalleryDAO.modifyReply(reply) }" />
      
      <c:if test = "${row == 1 }">
		<script>
		swal({
			title: '댓글 수정 성공',
			type: 'success',
			button: '확인'
		}, function() {
			location.href = '${cpath}/gallery/read.jsp?idx=${param.gnum}';
		})
		</script>
	</c:if>

	<c:if test="${row == 0 }">
		<script>
		swal({
			title: '댓글 수정 실패',
			text: '다시 시도해주세요.',
			type: 'error',
			button: '확인'
		}, function() {
			location.href = '${cpath}/gallery/read.jsp?idx=${param.gnum}';
		})
		</script>
	</c:if>
	
</c:if>	
      


</body>
</html>