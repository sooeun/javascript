<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>

<c:if test="${empty param.sort }">
   <c:if test="${empty param.word }">
      <c:set var="map" value="${GalleryDAO.galleryList(param.page) }" />
      <c:set var="galleryList" value="${map.galleryList }" />
      <c:set var="paging" value="${map.paging }" />
   </c:if>
   <c:if test="${not empty param.word }">
      <c:set var="map" value="${GalleryDAO.galleryList(param.page, param.word) }" />
      <c:set var="galleryList" value="${map.galleryList }" />
      <c:set var="paging" value="${map.paging }" />   
   </c:if>
</c:if>

<c:if test="${not empty param.sort }">
   <c:set var="map" value="${GalleryDAO.sortList(param.page, param.sort) }" />
   <c:set var="galleryList" value="${map.galleryList }" />
   <c:set var="paging" value="${map.paging }" />
</c:if>

<div class="gallery-Header">
   <div class="sorting">
      <ul>
         <li>|</li>
         <li><a href="${cpath }/gallery/gallery.jsp">NEW</a></li>
         <li><a href="${cpath }/gallery/gallery.jsp?page=1&sort=1">DATE</a></li>
         <li><a href="${cpath }/gallery/gallery.jsp?page=1&sort=2">VIEW</a></li>
      </ul>
   </div>
   <div class="search">
      <form>
         <input type="search" name="word" value="${param.word }">
         <input class="Btn" type="submit" value="검색">
      </form>
   </div>
</div>

<div class="galleryList">
   <c:forEach var="galleryDTO" items="${galleryList }">
      <div class="galleryListOne" onclick="location.href='${cpath }/gallery/read.jsp?idx=${galleryDTO.idx}&count=1';">
      
         <div class="thumbnail">
            <img src="${cpath }/filename/${galleryDTO.filename }">
         </div>
         <div class="summary">
            <div>
               <div class="local"><img src="${cpath }/uploadfile/pin.jpg">${galleryDTO.local }</div>
               <div class="nick">${galleryDTO.writer }</div>
            </div>
            <div class="viewLike">VIEW ${galleryDTO.viewCount }<br>${galleryDTO.startdate}</div>
         </div>
      </div>
   </c:forEach>
</div>


<div class="paging-number">
   <c:if test="${paging.prev }">
      <a href="${cpath }/gallery/gallery.jsp?page=${paging.begin-1}&word=${param.word}&sort=${param.sort}">
         ◀
      </a>
   </c:if>

   <c:forEach var="i" begin="${paging.begin }" end="${paging.end }">
      <a href="${cpath }/gallery/gallery.jsp?page=${i}&word=${param.word }&sort=${param.sort}">
         ${i == param.page ? '<strong>' : '' }[${i }]${i == param.page? '</strong>' : '' }   
      </a>
   </c:forEach>
   
   <c:if test="${paging.next }">
      <a href="${cpath }/gallery/gallery.jsp?page=${paging.end+1}&word=${param.word}&sort=${param.sort}">
         ▶
      </a>
   </c:if>
</div>


   
</body>
</html>