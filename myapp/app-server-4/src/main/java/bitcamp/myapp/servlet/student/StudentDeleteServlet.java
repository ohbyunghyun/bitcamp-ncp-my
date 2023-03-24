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
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.dao.StudentDao;
import bitcamp.util.BitcampSqlSessionFactory;
import bitcamp.util.DaoGenerator;
import bitcamp.util.TransactionManager;

@WebServlet("/student/delete")
public class StudentDeleteServlet extends HttpServlet{
  private static final long serialVersionUID = 1L;

  private StudentDao studentDao;
  private MemberDao memberDao;
  private TransactionManager txManeger;

  public StudentDeleteServlet() {
    try {
      InputStream mybatisConfigInputStream = Resources.getResourceAsStream(
          "bitcamp/myapp/config/mybatis-config.xml");
      BitcampSqlSessionFactory sqlSessionFactory = new BitcampSqlSessionFactory(
          new SqlSessionFactoryBuilder().build(mybatisConfigInputStream));

      txManeger = new TransactionManager(sqlSessionFactory);
      memberDao = new DaoGenerator(sqlSessionFactory).getObject(MemberDao.class);
      studentDao = new DaoGenerator(sqlSessionFactory).getObject(StudentDao.class);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    int studentNo = Integer.parseInt(request.getParameter("no"));

    response.setContentType("text/html; charset=utf-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<meta http-equiv='Refresh' content='1;url=list'>");
    out.println("<title>비트캠프 - NCP1기</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>학생 관리</h1>");

    txManeger.startTransaction();

    try {
      studentDao.delete(studentNo);
      memberDao.delete(studentNo);
      txManeger.commit();

    } catch (Exception e) {
      e.printStackTrace();
      txManeger.rollback();

    }
    out.println("<p>삭제 했습니다.</p>");

    out.println("</body>");
    out.println("</html>");
  }
}
