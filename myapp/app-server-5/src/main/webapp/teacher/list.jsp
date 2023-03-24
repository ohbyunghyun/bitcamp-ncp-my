<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%! 
private static String getDegreeText(int degree) {
  switch (degree) {
    case 1: return "고졸";
    case 2: return "전문학사";
    case 3: return "학사";
    case 4: return "석사";
    case 5: return "박사";
    default: return "기타";
  }
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<title>비트캠프 - NCP 1기</title>
</head>
<body>
<h1>강사(JSP + MVC2)</h1>

<div><a href='form'>새 강사</a></div>

<table border='1'>
<tr>
  <th>번호</th> <th>이름</th> <th>전화</th> <th>학위</th> <th>전공</th> <th>시강료</th>
</tr>
<c:forEach items = "${teachers}" var="teacher">
  <tr>
      <td>${teacher.no}</td> 
      <td><a href='view?no=${teacher.no}'>${teacher.name}</a></td> 
      <td>${teacher.tel}</td> 
      <c:choose>
      <c:when test="${teacher.degree == 1}"><td> 고졸 </td></c:when>
      <c:when test="${teacher.degree == 2}"><td> 전문학사 </td></c:when>
      <c:when test="${teacher.degree == 3}"><td> 학사 </td></c:when>
      <c:when test="${teacher.degree == 4}"><td> 박사 </td></c:when>
      <c:when test="${teacher.degree == 5}"><td> 기타 </td></c:when>
      </c:choose>
      <td>${teacher.major}</td> 
      <td>${teacher.wage}</td>
  </tr>
</c:forEach>
</table>

</body>
</html>


