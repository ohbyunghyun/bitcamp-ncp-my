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
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.dao.TeacherDao;
import bitcamp.myapp.vo.Teacher;
import bitcamp.util.BitcampSqlSessionFactory;
import bitcamp.util.DaoGenerator;
import bitcamp.util.TransactionManager;

@WebServlet("/teacher/insert")
public class TeacherInsertServlet extends HttpServlet{
  private static final long serialVersionUID = 1L;

  private TeacherDao teacherDao;
  private MemberDao memberDao;
  private TransactionManager txManeger;

  public TeacherInsertServlet() {
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


    Teacher teacher = new Teacher();
    teacher.setName(request.getParameter("name"));
    teacher.setEmail(request.getParameter("email"));
    teacher.setPassword(request.getParameter("password"));
    teacher.setTel(request.getParameter("tel"));
    teacher.setDegree(Integer.parseInt(request.getParameter("degree")));
    teacher.setSchool(request.getParameter("school"));
    teacher.setMajor(request.getParameter("major"));
    teacher.setWage(Integer.parseInt(request.getParameter("wage")));

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
    out.println("<h1>강사등록</h1>");

    txManeger.startTransaction();
    try {
      memberDao.insert(teacher);
      teacherDao.insert(teacher);
      txManeger.commit();

    } catch (Exception e){
      e.printStackTrace();
      txManeger.rollback();

    }

    out.println("<p>입력 했습니다.</p>");

    out.println("</body>");
    out.println("</html>");
  }

}
