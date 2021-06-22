<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>



<c:if test = "${empty login }">
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

<c:if test = "${not empty login }">

	
<c:set var="count" value="${GalleryDAO.myHomeCount(login.id) }" />
	<div class="profile">
		<div class="userImg"><img src="${cpath }/profilename/${login.profilename }"></div>
		<div class="userInfo">
			<h2>${login.nickname }</h2>
			<h5>게시글 수 ${count }</h5>
		</div>
		<div class="intro"><pre>${login.intro }</pre></div>
		<div class="usermenu">
			<ul>
				<li><a href="${cpath }/member/modify.jsp">정보 수정</a></li>
				<li><a href="${cpath }/member/delete.jsp">회원 탈퇴</a></li>
			</ul>
		</div>
	</div>
	
<c:set var="map" value="${GalleryDAO.mygalleryList(param.page, login.id) }" />
<c:set var="galleryList" value="${map.galleryList }" />
<c:set var="paging" value="${map.paging }" />

<div class="galleryList">
	<c:forEach var="galleryDTO" items="${galleryList }">
		<div class="galleryListOne">
			<div class="thumbnail">
				<a href="${cpath }/gallery/read.jsp?idx=${galleryDTO.idx}&count=1"><img src="${cpath }/filename/${galleryDTO.filename }"></a>
			</div>
			<div class="summary">
				<div>
					<div class="local"><img src="${cpath }/uploadfile/pin.jpg">${galleryDTO.local }</div>
					<div class="nick">${galleryDTO.writer }</div>
				</div>
				<div class="viewLike">VIEW ${galleryDTO.viewCount }</div>
			</div>
		</div>
	</c:forEach>
</div>
	
	
<div class="paging-number">
	<c:if test="${paging.prev }">
		<a href="${cpath }/gallery/myhome.jsp?page=${paging.begin-1}">
			◀
		</a>
	</c:if>

	<c:forEach var="i" begin="${paging.begin }" end="${paging.end }">
		<a href="${cpath }/gallery/myhome.jsp?page=${i}" ${i == param.page ? 'Style="color:#56c2f5"' : '' }>
			${i == param.page ? '<strong>' : '' }${i }${i == param.page? '</strong>' : '' }	
		</a>
	</c:forEach>
	
	<c:if test="${paging.next }">
		<a href="${cpath }/gallery/myhome.jsp?page=${paging.end+1}">
			▶
		</a>
	</c:if>
</div>


</c:if>
</body>
</html>