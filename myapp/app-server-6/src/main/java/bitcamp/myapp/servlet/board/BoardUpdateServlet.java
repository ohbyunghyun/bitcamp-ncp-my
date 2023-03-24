package bitcamp.myapp.servlet.board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.BoardFileDao;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.BoardFile;
import bitcamp.myapp.vo.Member;
import bitcamp.util.TransactionManager;

@MultipartConfig(maxFileSize = 1024 * 1024 * 50)
@WebServlet("/board/update")
public class BoardUpdateServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private BoardDao boardDao;
  private BoardFileDao boardFileDao;
  private TransactionManager txManager;

  @Override
  public void init() {
    ServletContext ctx = getServletContext();
    boardDao = (BoardDao) ctx.getAttribute("boardDao");
    boardFileDao = (BoardFileDao) ctx.getAttribute("boardFileDao");
    txManager = (TransactionManager) ctx.getAttribute("txManager");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    txManager.startTransaction();
    try {

      Member loginUser = (Member) request.getSession().getAttribute("loginUser");

      Board board = new Board();
      board.setNo(Integer.parseInt(request.getParameter("no")));
      board.setTitle(request.getParameter("title"));
      board.setContent(request.getParameter("content"));

      Board old = boardDao.findByNo(board.getNo());

      if (old.getWriter().getNo() != loginUser.getNo()) {
        response.sendRedirect("../auth/fail");
        return;
      } if (boardDao.update(board) == 0) {
        request.setAttribute("error", "data");
      }

      Collection<Part> parts = request.getParts();
      List<BoardFile> boardFiles = new ArrayList<>();

      for (Part part : parts) {
        if (!part.getName().equals("files")) {
          continue;
        }

        String filename = UUID.randomUUID().toString();

        String realPath = this.getServletContext().getRealPath("/board/upload/" + filename);
        part.write(realPath);

        BoardFile boardFile = new BoardFile();
        boardFile.setOriginalFilename(part.getName());
        boardFile.setFilepath(filename);
        boardFile.setMimeType(part.getContentType());
        boardFile.setBoardNo(board.getNo());
        // boardFileDao.insert(boardFile);

        boardFiles.add(boardFile);
      }

      if (boardFiles.size() > 0) {
        boardFileDao.insertList(boardFiles);
      }
      txManager.commit();

    } catch (Exception e) {
      txManager.rollback();
      e.printStackTrace();
      request.setAttribute("error", "data");
    }

    request.getRequestDispatcher("/board/update.jsp").forward(request, response);
  }

}




