<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<title>비트캠프 - NCP 1기</title>
</head>
<body>
<h1>학생(JSP + MVC2)</h1>
<c:if test="${empty student}">
  <p>해당 번호의 학생이 없습니다.</p>
<div>
  <button id='btn-list' type='button'>목록</button>
</div>
</c:if> 

<c:if test="${not empty student}">
  <form id='student-form' action='update' method='post'>
  <table border='1'>

  <tr>
    <th>번호</th>
    <td><input type='text' name='no' value='${student.no}' readonly></td>
  </tr>

  <tr>
    <th>이름</th>
    <td><input type='text' name='name' value='${student.name}'></td>
  </tr>

  <tr>
    <th>이메일</th>
    <td><input type='email' name='email' value='${student.email}'></td>
  </tr>

  <tr>
    <th>암호</th>
    <td><input type='password' name='password'></td>
  </tr>

  <tr>
    <th>전화</th>
    <td><input type='tel' name='tel' value='${student.tel}'></td>
  </tr>

  <tr>
    <th>우편번호</th>
    <td><input type='text' name='postNo' value='${student.postNo}'></td>
  </tr>

  <tr>
    <th>기본주소</th>
    <td><input type='text' name='basicAddress' value='${student.basicAddress}'></td>
  </tr>

  <tr>
    <th>상세주소</th>
    <td><input type='tel' name='detailAddress' value='${student.detailAddress}'></td>
  </tr>

  <tr>
    <th>재직여부</th>
    <td><input type='checkbox' name='working' ${student.working ? "checked" : ""}'> 재직중</td>
  </tr>

  <tr>
    <th>성별</th>
    <td><input type='radio' name='gender' value='M' ${student.gender() == 'M' ? "checked" : ""}'>남
    <input type='radio' name='gender' value='W' ${student.gender() == 'W' ? "checked" : ""}'> 여</td>

  </tr>

  <tr>
    <th>전공</th>
    <td><select name='level'>"
    <option value='0' ${student.level() == 0 ? "selected" : ""}'>비전공자</option>
    <option value='1' ${student.level() == 1 ? "selected" : ""}'>준전공자</option>
    <option value='2' ${student.level() == 2 ? "selected" : ""}'>전공자</option>
    </select></td>
  </tr>

  <tr>
    <th>등록일</th>
    <td>${student.createdDate}</td>
  </tr>

  </table>
</c:if>
<div>
  <button id='btn-list' type='button'>목록</button>
  <button>변경</button>
  <button id='btn-delete' type='button'>삭제</button>
</div>

</form>

<script>
document.querySelector('#btn-list').onclick = function() {
  location.href = 'list';
}

document.querySelector('#btn-delete').onclick = function() {
  var form = document.querySelector('#student-form');
  form.action = 'delete';
  form.submit();
}
</script>

</body>
</html>


