<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title}</title>
<link rel="stylesheet" type="text/css" href="/res/css/common.css">
<c:if test="${css != null}">
	<c:forEach items="${css}" var="item">
		<link rel="stylesheet" type="text/css" href="/res/css/${item}.css">
	</c:forEach>
</c:if>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
</head>
<body>
	<div id="container">
		<header>
			<div id="headerLeft">
				<c:if test="${loginUser != null}">
					<div class="containerPImg">
						<c:choose>
							<c:when test="${loginUser.profile_img != null}">
								<img class="pImg"
									src="/res/img/user/${loginUser.i_user}/${loginUser.profile_img}">
							</c:when>
							<c:otherwise>
								<img class="pImg" src="/res/img/default_profile.png">
							</c:otherwise>
						</c:choose>
					</div>
					<div class="mL10">${loginUser.nm}님환영합니다.</div>
				</c:if>
				<c:if test="${loginUser != null}">
					<div class="mL10" id="logout">
						<a href="/user/logout">로그아웃</a>
					</div>
				</c:if>
				<c:if test="${loginUser == null}">
					<div class="mL10" id="logout">
						<a href="/user/login">로그인</a>
					</div>
				</c:if>
			</div>
			<div id="headerRight">
				<a href="/rest/map">지도</a>
				<c:if test="${loginUser != null}">
					<a class="mL10" href="/rest/reg">등록</a>
				</c:if>
				<c:if test="${loginUser == null}">
					<a class="mL10" href="#" onclick="alert('로그인이 필요합니다.')">등록</a>
				</c:if>
				<a class="mL10" href="/user/restFavorite">찜</a>
			</div>
		</header>
		<section>
			<jsp:include page="/WEB-INF/views/${view}.jsp"></jsp:include>
		</section>
		<footer> 회사 정보 </footer>
	</div>
</body>
</html>