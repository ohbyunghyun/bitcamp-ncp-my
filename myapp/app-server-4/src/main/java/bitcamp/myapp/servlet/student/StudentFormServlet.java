package bitcamp.myapp.servlet.student;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import bitcamp.myapp.dao.StudentDao;
import bitcamp.util.BitcampSqlSessionFactory;
import bitcamp.util.DaoGenerator;

@WebServlet("/student/form")
public class StudentFormServlet extends HttpServlet{
  private static final long serialVersionUID = 1L;

  private StudentDao studentDao;

  public StudentFormServlet() {
    try {
      InputStream mybatisConfigInputStream = Resources.getResourceAsStream(
          "bitcamp/myapp/config/mybatis-config.xml");
      BitcampSqlSessionFactory sqlSessionFactory = new BitcampSqlSessionFactory(
          new SqlSessionFactoryBuilder().build(mybatisConfigInputStream));
      studentDao = new DaoGenerator(sqlSessionFactory).getObject(StudentDao.class);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html; charset=utf-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>비트캠프 - NCP1기</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>학생등록</h1>");
    out.println("<form action='insert' method='post'>");
    out.println("<table border='1'>");

    out.println("<tr>");
    out.println("<th>이름</th>");
    out.println("<td><input type='text' name='name'></td>");
    out.println("</tr>");

    out.println("<tr>");
    out.println("<th>이메일</th>");
    out.println("<td><input type='email' name='email'></td>");
    out.println("</tr>");

    out.println("<tr>");
    out.println("<th>암호</th>");
    out.println("<td><input type='password' name='password'></td>");
    out.println("</tr>");

    out.println("<tr>");
    out.println("<th>전화</th>");
    out.println("<td><input type='tel' name='tel'></td>");
    out.println("</tr>");

    out.println("<tr>");
    out.println("<th>우편번호</th>");
    out.println("<td><input type='text' name='postNo'></td>");
    out.println("</tr>");

    out.println("<tr>");
    out.println("<th>기본주소</th>");
    out.println("<td><input type='text' name='basicAddress'></td>");
    out.println("</tr>");

    out.println("<tr>");
    out.println("<th>상세주소</th>");
    out.println("<td><input type='text' name='detailAddress'></td>");
    out.println("</tr>");

    out.println("<tr>");
    out.println("<th>재직</th>");
    out.println("<td><input type='checkbox' name='working' value='true' id='work'></td>");
    out.println("</tr>");

    out.println("<input type='hidden' name='working' value='false' id='hidden'/>");

    out.println("<tr>");
    out.println("<th>성별</th>");
    out.println("<td>");
    out.println("<input type='radio' name='gender' value='M'>남자");
    out.println("<input type='radio' name='gender' value='W' checked='checked'>여자");
    out.println("</td>");
    out.println("</tr>");

    out.println("<tr>");
    out.println("<th>전공</th>");
    out.println("<td>");
    out.println("<select name='level'>");
    out.println("<option value='1'>비전공</option>");
    out.println("<option value='2'>준전공</option>");
    out.println("<option value='3'>전공</option>");
    out.println("</select>    ");
    out.println("</td>");
    out.println("</tr>");
    out.println("</table>");




    out.println("<div>");
    out.println("<button>등록</button>");
    out.println("<button id='btn-cancel' type='button'>취소</button>");
    out.println("</div>");

    out.println("</form>");
    out.println("<script>");
    out.println("document.querySelector('#btn-cancel').onclick = function() {");
    out.println("location.href = 'list'");
    out.println("}");

    out.println("var work = document.querySelector('#work');");
    out.println("var hidden = documnet.querySelector('#hidden');");



    out.println("</script>");
    out.println("</body>");
    out.println("</html>");
  }
}