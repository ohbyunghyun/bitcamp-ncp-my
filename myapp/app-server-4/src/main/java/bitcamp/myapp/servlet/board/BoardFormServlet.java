package bitcamp.myapp.servlet.board;

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
import bitcamp.myapp.dao.BoardDao;
import bitcamp.util.BitcampSqlSessionFactory;
import bitcamp.util.DaoGenerator;

@WebServlet("/board/form")
public class BoardFormServlet extends HttpServlet{
  private static final long serialVersionUID = 1L;

  private BoardDao boardDao;

  public BoardFormServlet() {
    try {
      InputStream mybatisConfigInputStream = Resources.getResourceAsStream(
          "bitcamp/myapp/config/mybatis-config.xml");
      BitcampSqlSessionFactory sqlSessionFactory = new BitcampSqlSessionFactory(
          new SqlSessionFactoryBuilder().build(mybatisConfigInputStream));
      boardDao = new DaoGenerator(sqlSessionFactory).getObject(BoardDao.class);

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
    out.println("<h1>게시판</h1>");
    out.println("<form action='insert' method='post'>");
    out.println("<table border='1'>");

    out.println("<tr>");
    out.println("<th>제목</th>");
    out.println("<td><input type='text' name='title'></td>");
    out.println("</tr>");

    out.println("<tr>");
    out.println("<th>내용</th>");
    out.println("<td><textarea rows='10' cols='60' name='content'></textarea></td>");
    out.println("</tr>");

    out.println("<tr>");
    out.println("<th>암호</th>");
    out.println("<td><input type='password' name='password'></td>");
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