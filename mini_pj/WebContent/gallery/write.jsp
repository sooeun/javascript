<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>

<c:if test="${empty login }">
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

<c:if test="${not empty login }">
	<div class="write">
		<form action="write-submit.jsp" method="POST" enctype="multipart/form-data">
			<input type="hidden" name="id" readonly value="${login.id }">
			<input type="hidden" name="profilename" readonly value="${login.profilename }">
			<table>
				<tr>
					<th>작성자</th>
					<td><input type="text" name="writer" readonly value="${login.nickname }"></td>
				</tr>
				<tr>
					<th>여행 장소</th>
					<td><input type="text" name="local"></td>
				</tr>
				<tr>
					<th>여행 시기</th>
					<td><input type="date" name="startdate" required> ~ <input type="date" name="enddate" required></td>
				</tr>
				<tr>
					<th>사진</th>
					<td><input type="file" name="filename"></td>
				</tr>
				<tr>
					<td colspan="2"><textarea name="content" required></textarea></td>
				</tr>
			</table>
			<div>
				<input class="Btn" type="submit" value="글쓰기">
			</div>
		</form>
	</div>
</c:if>

</body>
</html>