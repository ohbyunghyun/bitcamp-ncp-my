package bitcamp.myapp.servlet.student;

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
import bitcamp.myapp.dao.StudentDao;
import bitcamp.myapp.vo.Student;
import bitcamp.util.BitcampSqlSessionFactory;
import bitcamp.util.DaoGenerator;
import bitcamp.util.TransactionManager;

@WebServlet("/student/update")
public class StudentUpdateServlet extends HttpServlet{
  private static final long serialVersionUID = 1L;

  private MemberDao memberDao;
  private StudentDao studentDao;
  private TransactionManager txManeger;

  public StudentUpdateServlet() {
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
    request.setCharacterEncoding("UTF-8");

    int studentNo = Integer.parseInt(request.getParameter("no"));

    Student student = new Student();
    student.setNo(Integer.parseInt(request.getParameter("no")));
    student.setName(request.getParameter("name"));
    student.setEmail(request.getParameter("email"));
    student.setPassword(request.getParameter("password"));
    student.setTel(request.getParameter("tel"));
    student.setPostNo(request.getParameter("postNo"));
    student.setBasicAddress(request.getParameter("basicAddress"));
    student.setDetailAddress(request.getParameter("detailAddress"));
    student.setWorking((request.getParameter("working").equals("true")) ? true : false);
    student.setGender(request.getParameter("gender").charAt(0));
    student.setLevel(Byte.parseByte(request.getParameter("level")));

    String old = memberDao.findByNo(studentNo).getPassword();

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
    out.println("<h1>학생관리</h1>");

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
      memberDao.update(student);
      studentDao.update(student);
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
