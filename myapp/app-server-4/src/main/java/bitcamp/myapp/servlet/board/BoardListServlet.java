package bitcamp.myapp.servlet.board;

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
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import bitcamp.util.BitcampSqlSessionFactory;
import bitcamp.util.DaoGenerator;

@WebServlet("/board/list")
public class BoardListServlet extends HttpServlet{
  private static final long serialVersionUID = 1L;

  private BoardDao boardDao;

  public BoardListServlet() {
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
    out.println("<h1>게시판 목록</h1>");

    out.println("<div><a href='form'>새 글 </a></div>");

    out.println("<table border='1'>");
    out.println("<tr>");
    out.println("<th>번호</th><th>제목</th><th>작성일</th><th>조회수</th>");
    out.println("</tr>");

    List<Board> boards = this.boardDao.findAll();
    for (Board b : boards) {
      out.println("<tr>");
      out.printf("<td>%d</td><td><a href='view?no=%d'>%s</a></td><td>%s</td><td>%d</td>\n",
          b.getNo(), b.getNo(), b.getTitle(), b.getCreatedDate(), b.getViewCount());
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
    out.println("</form>");

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
