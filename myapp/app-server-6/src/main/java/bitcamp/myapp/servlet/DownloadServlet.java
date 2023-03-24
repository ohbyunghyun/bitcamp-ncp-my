package bitcamp.myapp.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.dao.BoardFileDao;
import bitcamp.myapp.vo.BoardFile;

// 이 클래스를 서블릿 컨테이너에 등록한다.
// 클라이언트에서 /hello URL로 요청을 했을 때 이 클래스를 실행한다.
@WebServlet("/download/boardfile")
public class DownloadServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private BoardFileDao boardFileDao;

  @Override
  public void init() {
    ServletContext ctx = getServletContext();
    boardFileDao = (BoardFileDao) ctx.getAttribute("boardFileDao");
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    try {
      int fileNo = Integer.parseInt(request.getParameter("fileNo"));

      BoardFile boardFile = boardFileDao.findByNo(fileNo);
      if (boardFile == null) {
        throw new RuntimeException("파일 정보 없음!");
      }

      File downloadFile = new File(this.getServletContext().getRealPath(
          "/board/upload/" + boardFile.getFilepath()));
      if (!downloadFile.exists()) {
        throw new RuntimeException("파일이 존재하지 않음!");
      }

      response.setContentType(boardFile.getMimeType());

      response.setHeader("Content-Disposition",
          String.format("attachment; filename=\"%s\"", boardFile.getOriginalFilename()));

      try (

          BufferedInputStream fileIn = new BufferedInputStream(new FileInputStream(downloadFile));
          BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());) {

        int b;
        while ((b = fileIn.read()) != -1) {
          out.write(b);
        }
        out.flush();
      }
    } catch (Exception e) {
      request.getRequestDispatcher("/downloadfail.jsp").forward(request, response);
    }

  }
}






