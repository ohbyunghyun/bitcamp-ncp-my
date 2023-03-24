package bitcamp.myapp.servlet.student;

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
import bitcamp.myapp.dao.StudentDao;
import bitcamp.myapp.vo.Student;
import bitcamp.util.BitcampSqlSessionFactory;
import bitcamp.util.DaoGenerator;

@WebServlet("/student/search")
public class StudentSearchServlet extends HttpServlet{
  private static final long serialVersionUID = 1L;

  private StudentDao studentDao;

  public StudentSearchServlet() {
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
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html; charset=utf-8");
    PrintWriter out = response.getWriter();

    String keyword = request.getParameter("search");

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>비트캠프 - NCP1기</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>학생 목록</h1>");

    out.println("<div><a href='form'>새 글 </a></div>");

    out.println("<table border='1'>");
    out.println("<tr>");
    out.println("<th>번호</th><th>이름</th><th>이메일</th><th>전화</th><th>재직</th><th>전공</th>");
    out.println("</tr>");

    List<Student> students = this.studentDao.findByKeyword(keyword);

    for (Student t : students) {
      String work = t.isWorking() == true ? "재직" : "백수";
      String level = t.getLevel() == 3 ? "전공" : t.getLevel() == 2 ? "준전공" : "비전공";
      out.println("<tr>");
      out.printf(""
          + "<td>%d</td>"
          + "<td><a href='view?no=%d'>%s</a></td>"
          + "<td>%s</td>"
          + "<td>%s</td>"
          + "<td>%s</td>"
          + "<td>%s</td>\n",
          t.getNo(),
          t.getNo(), t.getName(),
          t.getEmail(),
          t.getTel(),
          work,
          level);
      out.println("</tr>");
    }

    out.println("</table>");

    out.println("<form action='search' method='post' name='search'>");
    out.println("<table border='1'>");
    out.println("<tr>");
    out.println("<th>검색</th>");
    out.println("<td><input type='text' name='search'></td>");
    out.println("</tr>");

    out.println("</table>");

    out.println("<script>");
    out.println("function press(f) {");
    out.println("if(f.keyCode == 13) {");
    out.println("search.submit();");
    out.println("}");
    out.println("}");
    out.println("</script>");

    out.println("</body>");
    out.println("</html>");
  }
}
