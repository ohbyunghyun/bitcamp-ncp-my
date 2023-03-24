package bitcamp.myapp.servlet.teacher;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import bitcamp.myapp.dao.TeacherDao;
import bitcamp.myapp.vo.Teacher;
import bitcamp.util.BitcampSqlSessionFactory;
import bitcamp.util.DaoGenerator;

@WebServlet("/teacher/list")
public class TeacherListServlet extends HttpServlet{
  private static final long serialVersionUID = 1L;

  private TeacherDao teacherDao;

  public TeacherListServlet() {
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
    out.println("<h1>강사 목록</h1>");

    out.println("<div><a href='form'>새 글 </a></div>");

    out.println("<table border='1'>");
    out.println("<tr>");
    out.println("<th>번호</th><th>이름</th><th>전화</th><th>디그리</th><th>전공</th><th>웨이지</th>");
    out.println("</tr>");

    List<Teacher> teachers = this.teacherDao.findAll();
    for (Teacher t : teachers) {
      out.println("<tr>");
      out.printf(""
          + "<td>%d</td>"
          + "<td><a href='view?no=%d'>%s</a></td>"
          + "<td>%s</td>"
          + "<td>%d</td>"
          + "<td>%s</td>"
          + "<td>%d</td>\n",
          t.getNo(), t.getNo(), t.getName(), t.getTel(), t.getDegree(), t.getMajor(), t.getWage());
      out.println("</tr>");
    }

    out.println("</table>");
    out.println("</body>");
    out.println("</html>");
  }
}
