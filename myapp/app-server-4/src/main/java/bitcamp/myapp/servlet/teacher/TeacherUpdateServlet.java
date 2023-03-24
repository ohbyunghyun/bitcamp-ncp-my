package bitcamp.myapp.servlet.teacher;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.dao.TeacherDao;
import bitcamp.myapp.vo.Teacher;
import bitcamp.util.BitcampSqlSessionFactory;
import bitcamp.util.DaoGenerator;
import bitcamp.util.TransactionManager;

@WebServlet("/teacher/update")
public class TeacherUpdateServlet extends HttpServlet{
  private static final long serialVersionUID = 1L;

  private MemberDao memberDao;
  private TeacherDao teacherDao;
  private TransactionManager txManeger;

  public TeacherUpdateServlet() {
    try {
      InputStream mybatisConfigInputStream = Resources.getResourceAsStream(
          "bitcamp/myapp/config/mybatis-config.xml");
      BitcampSqlSessionFactory sqlSessionFactory = new BitcampSqlSessionFactory(
          new SqlSessionFactoryBuilder().build(mybatisConfigInputStream));

      txManeger = new TransactionManager(sqlSessionFactory);
      memberDao = new DaoGenerator(sqlSessionFactory).getObject(MemberDao.class);
      teacherDao = new DaoGenerator(sqlSessionFactory).getObject(TeacherDao.class);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");

    int teacherNo = Integer.parseInt(request.getParameter("no"));

    Teacher teacher = new Teacher();
    teacher.setNo(Integer.parseInt(request.getParameter("no")));
    teacher.setName(request.getParameter("name"));
    teacher.setEmail(request.getParameter("email"));
    teacher.setPassword(request.getParameter("password"));
    teacher.setTel(request.getParameter("tel"));
    teacher.setDegree(Integer.parseInt(request.getParameter("degree")));
    teacher.setSchool(request.getParameter("school"));
    teacher.setMajor(request.getParameter("major"));
    teacher.setWage(Integer.parseInt(request.getParameter("wage")));

    String old = memberDao.findByNo(teacherNo).getPassword();

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
    out.println("<h1>강사관리</h1>");

    System.out.println(old);

    MessageDigest md = null;
    try {
      md = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e1) {
      e1.printStackTrace();
    }
    md.update(request.getParameter("password").getBytes());
    String passwordSHA = String.format("%064x", new BigInteger(1, md.digest()));

    if (!old.equals(passwordSHA)) {
      out.println("<p>암호틀림</p>");
      return;
    }

    txManeger.startTransaction();
    try {
      memberDao.update(teacher);
      teacherDao.update(teacher);
      txManeger.commit();

    } catch (Exception e) {
      e.printStackTrace();
      txManeger.rollback();

    }
    out.println("<p>변경 했습니다.</p>");

    out.println("</body>");
    out.println("</html>");
  }
}
