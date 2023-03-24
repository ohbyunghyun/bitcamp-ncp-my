package bitcamp.myapp.servlet.board;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.BoardFileDao;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.BoardFile;
import bitcamp.myapp.vo.Member;
import bitcamp.util.TransactionManager;

@WebServlet("/board/insert")
public class BoardInsertServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private TransactionManager txManager;
  private BoardDao boardDao;
  private BoardFileDao boardFileDao;

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

    DiskFileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload upload = new ServletFileUpload(factory);

    try {
      List<FileItem> items = upload.parseRequest(request);
      Map<String, String> paramMap = new HashMap<>();
      List<FileItem> files = new ArrayList<>();

      for (FileItem item : items) {
        if (item.isFormField()) {
          paramMap.put(item.getFieldName(), item.getString("UTF-8"));

        } else {
          files.add(item);
        }
      }

      Board board = new Board();
      board.setTitle(paramMap.get("title"));
      board.setContent(paramMap.get("content"));

      Member loginUser = (Member) request.getSession().getAttribute("loginUser");
      Member writer = new Member();
      writer.setNo(loginUser.getNo());
      board.setWriter(writer);

      txManager.startTransaction();
      boardDao.insert(board);

      List<BoardFile> boardFiles = new ArrayList<>();
      for (FileItem file : files) {
        if (file.getSize() == 0) {
          continue;
        }

        String filepath = UUID.randomUUID().toString();

        String realPath = this.getServletContext().getRealPath("/board/upload/" + filepath);
        file.write(new File(realPath));

        BoardFile boardFile = new BoardFile();
        boardFile.setOriginalFilename(file.getName());
        boardFile.setFilepath(filepath);
        boardFile.setBoardNo(board.getNo());
        boardFile.setMimeType(file.getContentType());
        // boardFileDao.insert(boardFile);

        boardFiles.add(boardFile);
      }

      if (boardFiles.size() > 0) {
        boardFileDao.insertList(boardFiles);
      }

      txManager.commit();

    } catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("error", "data");
      txManager.rollback();

    }

    request.getRequestDispatcher("/board/insert.jsp").forward(request, response);
  }
}

