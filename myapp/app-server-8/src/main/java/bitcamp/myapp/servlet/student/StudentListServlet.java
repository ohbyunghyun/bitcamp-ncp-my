package bitcamp.myapp.servlet.student;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.service.StudentService;
import bitcamp.myapp.vo.Student;

@WebServlet("/student/list")
public class StudentListServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private StudentService studentService;

  @Override
  public void init() {
    studentService = (StudentService) getServletContext().getAttribute("studentService");
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    List<Student> students = studentService.list(request.getParameter("keyword"));
    request.setAttribute("students", students);
    request.setAttribute("view", "/student/list.jsp");
  }

}
