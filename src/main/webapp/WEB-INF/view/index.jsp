<%@ page language="java" contentType="text/html; charset=UTF-8" 	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%
request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 목록</title>
<style>
	table {
		border-collapse: collapse;
		width: 80%;
		margin: 0 auto;
		margin-top: 30px;
	}

	th, td {
		border: 1px solid #ddd;
		padding: 8px;
		text-align: center;
	}

	tr:nth-child(even) {
		background-color: #f2f2f2;
	}

	tr:hover {
		background-color: #ddd;
	}

	.btn-write {
		text-align: right;
		margin-top: 10px;
		margin-right: 190px;
	}

	.btn-write a {
		display: inline-block;
		padding: 10px 20px;
		border-radius: 5px;
		background-color: #333;
		color: #fff;
		text-decoration: none;
	}

	.empty-msg {
		text-align: center;
		font-size: 14px;
		color: #333;
		margin-top: 50px;
	}
		img {
			border: 0px;
		}
		   .pagination img {
        width: 16px;
        height: 16px;
    }
</style>
<link rel="stylesheet" href="/MyBanking/css/table.css" />
</head>
<body>
	
	<section>
		<table>
			<tr>
				<th>글번호</th>
				<th>작성자</th>
				<th>제목</th>
				<th>작성일</th>
				<th>조회수</th>
			</tr>
					<c:forEach var="board" items="${boardList}">
						<tr>
							<td>${board.boardNo}</td>
							<td>${board.userId}</td>
							<td>
								<a href="${contextPath}/getBoard.do?boardNo=${board.boardNo}">${board.title}</a>
							<c:if test="${board.commentCount != 0}">
									(댓글${board.commentCount})
								</c:if>
							</td>
							
							<td><fmt:formatDate value="${board.boardTime}" pattern="yyyy-MM-dd" /></td>
							<td>${board.viewCnt}</td>
						</tr>
					</c:forEach>
		</table>
	
	<!-- 페이징 -->
	
		<div class="pagination" style="text-align: center;">
			<!-- 첫 번째 페이지로 이동하는 링크 -->
			<a href="boardList.do?pageNo=1"><img src="btn_first.gif" alt="첫 번째 페이지"></a>
			<a href="${contextPath}/boardList.do?pageNo=${pageNo - 1}"><img src="btn_pre.gif" alt="이전 페이지"></a>
			

		   <c:forEach begin="0" end="${lastPage}" step="1" varStatus="status">
        <c:choose>
            <c:when test="${status.index + 1 eq pageNo}">
                <strong>${status.index + 1}</strong>
            </c:when>
            <c:otherwise>
                <c:if test="${status.index + 1 <= lastPage}">
                    <a href="boardList.do?pageNo=${status.index + 1}">${status.index + 1}</a>
                </c:if>
            </c:otherwise>
        </c:choose>
    </c:forEach>

			
			
			<a href="${contextPath}/boardList.do?pageNo=${pageNo + 1}"><img src="btn_next.gif" alt="다음 페이지"></a>
			<!-- 마지막 페이지로 이동하는 링크 -->
			<a href="boardList.do?pageNo=${lastPage}"><img src="btn_last.gif" alt="마지막 페이지"></a>
			
		</div>
		<div style="text-align: center; margin-top: 20px;">
		    <form action="${contextPath}/boardList.do" method="get">
		        <input type="hidden" name="searchType" value="id">
		        <input type="text" name="searchKeyword" placeholder="작성한 아이디 입력">
		        <button type="submit">검색</button>
		    </form>
		</div>
	
		<div class="btn-write">
			<a href="${contextPath}/jsp/board/board.jsp">글쓰기</a>
		</div>
	</section>
</body>
</html>