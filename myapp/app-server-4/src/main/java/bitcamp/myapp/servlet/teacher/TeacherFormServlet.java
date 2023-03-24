package bitcamp.myapp.servlet.teacher;

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
import bitcamp.myapp.dao.TeacherDao;
import bitcamp.util.BitcampSqlSessionFactory;
import bitcamp.util.DaoGenerator;

@WebServlet("/teacher/form")
public class TeacherFormServlet extends HttpServlet{
  private static final long serialVersionUID = 1L;

  private TeacherDao teacherDao;

  public TeacherFormServlet() {
    try {
      InputStream mybatisConfigInputStream = Resources.getResourceAsStream(
          "bitcamp/myapp/config/mybatis-config.xml");
      BitcampSqlSessionFactory sqlSessionFactory = new BitcampSqlSessionFactory(
          new SqlSessionFactoryBuilder().build(mybatisConfigInputStream));
      teacherDao = new DaoGenerator(sqlSessionFactory).getObject(TeacherDao.class);

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
    out.println("<h1>강사등록</h1>");
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
    out.println("<th>디그리</th>");
    out.println("<td><input type='text' name='degree'></td>");
    out.println("</tr>");

    out.println("<tr>");
    out.println("<th>학교</th>");
    out.println("<td><input type='text' name='school'></td>");
    out.println("</tr>");

    out.println("<tr>");
    out.println("<th>메이저</th>");
    out.println("<td><input type='text' name='major'></td>");
    out.println("</tr>");

    out.println("<tr>");
    out.println("<th>웨이지</th>");
    out.println("<td><input type='text' name='wage'></td>");
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
    out.println("</script>");
    out.println("</body>");
    out.println("</html>");
  }
}