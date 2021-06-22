<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>

<c:if test="${not empty param.count }">
   <c:set var="viewCount" value="${GalleryDAO.viewCount(param.idx) }"/>
   <c:redirect url="/gallery/read.jsp?idx=${param.idx }"/>
</c:if>

<c:set var="galleryDTO" value="${GalleryDAO.selectOne(param.idx) }"/>
<!-- 본문 -->
<div class="readmain">
	<div class="readImg">
		<img src="${cpath }/filename/${galleryDTO.filename }">
	</div>
	
	<div class="readContent">
		<div class="title">
			<div class="userImg"><img src="${cpath }/profilename/${galleryDTO.profilename }"></div>
			<div class="userInfo">
			<h2>${galleryDTO.writer }</h2>
			<h5>View ${galleryDTO.viewCount }</h5>	
			</div>
		</div>
		<div class="content">
			<h4>여행 장소: ${galleryDTO.local }</h4>
			<h4>여행 시기: ${galleryDTO.startdate } ~ ${galleryDTO.enddate }</h4>
			<pre>${galleryDTO.content }</pre>
		</div>
	</div>
</div>

<!-- 글 목록, 수정, 삭제 부분 -->
<div class="listBtn">
	<div>
		<button class="Btn" onclick="location.href='${cpath }/gallery/gallery.jsp';">목록</button>
	</div>
	<div>
		<c:if test="${login.id == galleryDTO.id }">	<!-- 글쓴이 정보와 로그인 정보가 일치하면(id로 비교) -->
			<button class="Btn" onclick="location.href='${cpath }/gallery/modify.jsp?idx=${galleryDTO.idx}';">수정</button>
			<button class="Btn" id="deleteBtn">삭제</button>
		</c:if>
	</div>
</div>

<!-- 댓글 입력 부분  -->
<div>
<c:set var="comment" 
      value="${empty login ? '로그인 사용자만 댓글을 작성할 수 있습니다':'바르고 고운 말을 사용합시다'}" />

   <div class="reply-form">
      <form method="POST">
         <input type="hidden" name="gnum" value="${galleryDTO.idx }">   <!-- hidden으로 글번호, 아이디 전달(회원탈퇴하면 댓글도 없어지게  외래키로 잡음) -->
         <input type="hidden" name="id" value="${login.id }">
         <p><input type="text" name="writer" value="${login.nickname }" readonly required></p>   <!-- 작성자는 닉네임으로 고정  -->
         <textarea name="content" 
                 placeholder="${comment }"
                 ${empty login ? 'disabled' : '' }
                 required></textarea>
         <div><input class="Btn" type="submit" value="댓글 작성"></div>
      </form>
   </div>

	<c:if test="${pageContext.request.method == 'POST'}">
		<jsp:useBean id="reply" class="gallery.ReplyDTO" />
		<jsp:setProperty property="*" name="reply"/>
			<c:set var="row" value="${GalleryDAO.insertReply(reply) }" />
			<c:redirect url="/gallery/read.jsp">
			<c:param name="idx" value="${galleryDTO.idx }"/>
		</c:redirect>
	</c:if>  
 
</div>  

<!-- 댓글 출력 부분  -->   
<c:set var="replyList" value="${GalleryDAO.selectReplyList(galleryDTO.idx ) }" />
<c:forEach var="reply" items="${replyList }">
	<div class="reply-box">
		<div>
			<div class="reWriter"><strong>${reply.writer }</strong></div>
			<div class="reContent"><pre>${reply.content }</pre></div>
			<c:if test="${reply.id == login.id }">   <!-- 로그인 아이디와, 댓글을 쓴 사람의 아이디(히든으로 받음)가 일치하면 보여줌 -->
				<div class="reBtn">
					<button onclick="location.href='${cpath }/gallery/replyModify.jsp?idx=${reply.idx }&gnum=${reply.gnum}';">수정</button>
					<button onclick="location.href='${cpath }/gallery/replyDelete.jsp?idx=${reply.idx }&gnum=${reply.gnum}';">삭제</button>
				</div>
			</c:if>
			<c:if test="${reply.id != login.id or empty login}">
				<div class="emptybox"></div>
			</c:if>
			<div class="replyDate"> ${reply.writeDate }</div>
		</div>
	</div>
</c:forEach>


<!-- 글 삭제 스크립트  -->
<script type="text/javascript">
	const deleteBtn = document.getElementById('deleteBtn');
	if(deleteBtn != null) {
		deleteBtn.onclick = function() {
			const answer = confirm('정말 삭제하시겠습니까?');
			if(answer) {
				location.replace('${cpath}/gallery/delete.jsp?idx=${galleryDTO.idx}');
			}
		};
	}
</script>

</body>
</html>