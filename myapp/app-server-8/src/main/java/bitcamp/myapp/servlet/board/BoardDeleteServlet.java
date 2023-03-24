package bitcamp.myapp.servlet.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;

// 게시글 삭제는 게시글 수정 폼을 그대로 사용하기 때문에
// 요청 데이터가 multipart/form-data 형식으로 넘어온다.
@MultipartConfig(maxFileSize = 1024 * 1024 * 50)
@WebServlet("/board/delete")
public class BoardDeleteServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private BoardService boardService;

  @Override
  public void init() {
    boardService = (BoardService) getServletContext().getAttribute("boardService");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    try {
      // 로그인 사용자의 정보를 가져온다.
      Member loginUser = (Member) request.getSession().getAttribute("loginUser");

      int boardNo = Integer.parseInt(request.getParameter("no"));

      Board old = boardService.get(boardNo);

      if (old.getWriter().getNo() != loginUser.getNo()) {
        request.setAttribute("view", "redirect../auth/fail");
        return;
      }
      boardService.delete(boardNo);

    }  catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("error", "data");
    }

    request.setAttribute("view", "/board/delete.jsp");

  }
}
