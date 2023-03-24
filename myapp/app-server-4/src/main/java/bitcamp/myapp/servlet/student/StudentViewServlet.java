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
import bitcamp.myapp.vo.Student;
import bitcamp.util.BitcampSqlSessionFactory;
import bitcamp.util.DaoGenerator;

@WebServlet("/student/view")
public class StudentViewServlet extends HttpServlet{
  private static final long serialVersionUID = 1L;

  private StudentDao studentDao;

  public StudentViewServlet() {
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

    int studentNo = Integer.parseInt(request.getParameter("no"));

    response.setContentType("text/html; charset=utf-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>비트캠프 - NCP1기</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>상세 보기</h1>");

    Student s = this.studentDao.findByNo(studentNo);

    if (s == null) {
      out.println("<p>해당 번호의 학생이 없습니다.</p>");

    } else {

      out.println("<form id='student-form' action='update' method='post'>");

      out.println("<table border='1'>");

      out.println("<tr>");
      out.println("<th>번호</th>\n");
      out.printf("<td><input type='text' value='%d' name='no' readonly></td>\n", s.getNo());
      out.println("</tr>");

      out.println("<tr>");
      out.println("<th>이름</th>");
      out.printf("<td><input type='text' value='%s' name='name'></td>", s.getName());
      out.println("</tr>");

      out.println("<tr>");
      out.println("<th>이메일</th>");
      out.printf("<td><input type='text' value='%s' name='email'></td>", s.getEmail());
      out.println("</tr>");

      out.println("<tr>");
      out.println("<th>전화</th>");
      out.printf("<td><input type='text' value='%s'name='tel'></td>", s.getTel());
      out.println("</tr>");

      out.println("<tr>");
      out.println("<th>우편번호</th>");
      out.printf("<td><input type='text' value='%s'name='postNo'></td>", s.getPostNo());
      out.println("</tr>");

      out.println("<tr>");
      out.println("<th>기본주소</th>");
      out.printf("<td><input type='text' value='%s' name='basicAddress'></td>", s.getBasicAddress());
      out.println("</tr>");

      out.println("<tr>");
      out.println("<th>상세주소</th>");
      out.printf("<td><input type='text' value='%s' name='detailAddress'></td>", s.getDetailAddress());
      out.println("</tr>");

      out.println("<tr>");
      out.println("<th>재직</th>");
      out.printf("<td><input type='checkbox' value='true' name='working' id='work'></td>");
      out.println("</tr>");

      out.println("<input type='hidden' name='working' value='false' id='hidden'/>");

      out.println("<tr>");
      out.println("<th>성별</th>");
      out.println("<td>");
      out.println("<input type='radio' name='gender' value='M' id='man'>남자");
      out.println("<input type='radio' name='gender' value='W' id='woman'>여자");
      out.println("</td>");
      out.println("</tr>");

      out.println("<tr>");
      out.println("<th>전공</th>");
      out.println("<td>");
      out.println("<select name='level'>");
      out.println("<option value='1' id='one'>비전공</option>");
      out.println("<option value='2' id='two'>준전공</option>");
      out.println("<option value='3' id='three'>전공</option>");
      out.println("</select>");
      out.println("</td>");
      out.println("</tr>");

      out.println("<tr>");
      out.println("<th>가입일</th>");
      out.printf("<td><input type='text' value='%s' readonly></td>", s.getCreatedDate());
      out.println("</tr>");

      out.println("<tr>");
      out.println("<th>암호</th>");
      out.println("<td><input type='password' name='password'></td>");
      out.println("</tr>");

      out.println("</table>");

    }

    out.println("<div>");
    out.println("<button id='btn-list' type='button'>목록</button>");
    out.println("<button>변경</button>");
    out.println("<button id='btn-delete' type='button'>삭제</button>");
    out.println("</div>");

    out.println("</form>");

    out.println("<script>");
    out.println("document.querySelector('#btn-list').onclick = function() {");
    out.println("location.href = 'list'");
    out.println("}");
    out.println("document.querySelector('#btn-delete').onclick = function() {");
    out.println(" var form = document.querySelector('#student-form');");
    out.println(" form.action = 'delete';");
    out.println(" form.submit();");
    out.println("}");

    out.println("var work = document.querySelector('#work');");
    out.printf("if ('%s' == 'true') {", s.isWorking());
    out.println("work.checked = true;");
    out.println("} else {");
    out.println("work.checked = false;");
    out.println("}");

    out.printf("if ('%s' == 'M') {", s.getGender());
    out.println("document.querySelector('#man').checked = true");
    out.println("} else {");
    out.println("document.querySelector('#woman').checked = true");
    out.println("}");

    out.printf("if ('%s' == '1') {", s.getLevel());
    out.println("document.querySelector('#one').selected = true;");
    out.printf("} else if ('%s' == '2'){", s.getLevel());
    out.println("document.querySelector('#two').selected = true;");
    out.println("} else {");
    out.println("document.querySelector('#three').selected = true;");
    out.println("}");

    out.println("</script>");

    out.println("</body>");
    out.println("</html>");
  }
}
