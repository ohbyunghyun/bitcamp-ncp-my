<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비트캠프 - NCP 1기</title>
</head>
<body>
<h1>로그인</h1>
<form action="login" method="post">
<table border="1">

<c:if test="${error == 'loginfail'}">
<p style="color:red;">이메일 또는 암호가 맞지 않습니다!</p>
</c:if>

<tr>
  <th>회원유형</th>
  <td>
  <input type="radio" name="usertype" value="student" checked>학생<br>
  <input type="radio" name="usertype" value="teacher">강사
  </td>
</tr>
<tr>
  <th>이메일</th>
  <td><input type="email" name="email" value="${cookie.email.value}"></td>
</tr>
<tr>
  <th>암호</th>
  <td><input type="password" name="password"></td>
</tr>
</table>
<div>
<c:if test="${cookie.email != null}">
  <input type="checkbox" name="saveEmail" checked> 이메일 저장<br>
  <button>로그인</button>
</c:if>
<c:if test="${cookie.email == null}">
  <input type="checkbox" name="saveEmail"> 이메일 저장<br>
  <button>로그인</button>
</c:if>
</div>
</form>
</body>
</html>