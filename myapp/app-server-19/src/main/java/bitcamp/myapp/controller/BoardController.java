package bitcamp.myapp.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.BoardFile;
import bitcamp.myapp.vo.Member;
import bitcamp.util.ErrorCode;
import bitcamp.util.RestResult;
import bitcamp.util.RestStatus;

@RestController
@RequestMapping("/board")
public class BoardController {

  Logger log = LogManager.getLogger(getClass());

  {
    log.trace("BoardController 생성됨!");
  }

  // ServletContext 는 요청 핸들러의 파라미터로 주입 받을 수 없다.
  // 객체의 필드로만 주입 받을 수 있다.
  @Autowired private ServletContext servletContext;
  @Autowired private BoardService boardService;

  @PostMapping("insert")
  public Object insert(
      Board board,
      List<MultipartFile> files,
      HttpSession session) throws Exception{

    Member loginUser = (Member) session.getAttribute("loginUser");

    Member writer = new Member();
    writer.setNo(loginUser.getNo());
    board.setWriter(writer);

    List<BoardFile> boardFiles = new ArrayList<>();
    for (MultipartFile file : files) {
      if (file.isEmpty()) {
        continue;
      }

      String filename = UUID.randomUUID().toString();
      file.transferTo(new File(servletContext.getRealPath("/board/upload/" + filename)));

      BoardFile boardFile = new BoardFile();
      boardFile.setOriginalFilename(file.getOriginalFilename());
      boardFile.setFilepath(filename);
      boardFile.setMimeType(file.getContentType());
      boardFiles.add(boardFile);
    }
    board.setAttachedFiles(boardFiles);

    boardService.add(board);

    return new RestResult().setStatus(RestStatus.SUCCESS);
  }

  @GetMapping("list")
  public Object list(String keyword) {
    log.debug("BoardController.list() 호출됨!");
    List<Board> boards = boardService.list(keyword);

    // MappingJackson2HttpMessageConverter 가 jackson 라이브러리를 이용해
    // 자바 객체를 JSON 문자열로 변환하여 클라이언트로 보낸다.
    // 이 컨버터를 사용하면 굳이 UTF-8 변환을 설정할 필요가 없다.
    // 즉 produces = "application/json;charset=UTF-8" 를 설정하지 않아도 된다.
    return new RestResult()
        .setStatus(RestStatus.SUCCESS)
        .setData(boardService.list(keyword));
  }

  @GetMapping("view")
  public Object view(int no) {
    Board board = boardService.get(no);
    if (board != null) {
      return new RestResult()
          .setStatus(RestStatus.SUCCESS)
          .setData(boardService.get(no));
    } else {
      return new RestResult()
          .setStatus(RestStatus.FAILURE)
          .setErrorCode(ErrorCode.rest.NO_DATA);
    }

  }

  @PostMapping("update")
  public Object update(
      Board board,
      List<MultipartFile> files,
      HttpSession session) throws Exception {

    Member loginUser = (Member) session.getAttribute("loginUser");

    Board old = boardService.get(board.getNo());
    if (old.getWriter().getNo() != loginUser.getNo()) {
      return new RestResult()
          .setStatus(RestStatus.FAILURE)
          .setErrorCode(ErrorCode.rest.UNAUTHORIZED)
          .setData("권한이 없습니다.");
    }

    List<BoardFile> boardFiles = new ArrayList<>();
    for (MultipartFile file : files) {
      if (file.isEmpty()) {
        continue;
      }

      String filename = UUID.randomUUID().toString();
      file.transferTo(new File(servletContext.getRealPath("/board/upload/" + filename)));

      BoardFile boardFile = new BoardFile();
      boardFile.setOriginalFilename(file.getOriginalFilename());
      boardFile.setFilepath(filename);
      boardFile.setMimeType(file.getContentType());
      boardFile.setBoardNo(board.getNo());
      boardFiles.add(boardFile);
    }
    board.setAttachedFiles(boardFiles);

    boardService.update(board);

    return new RestResult()
        .setStatus(RestStatus.SUCCESS);
  }

  @PostMapping("delete")
  public Object delete(int no, HttpSession session) {
    Member loginUser = (Member) session.getAttribute("loginUser");

    Board old = boardService.get(no);
    if (old.getWriter().getNo() != loginUser.getNo()) {
      return new RestResult()
          .setStatus(RestStatus.FAILURE)
          .setErrorCode(ErrorCode.rest.UNAUTHORIZED)
          .setData("권한이 없습니다.");
    }
    boardService.delete(no);

    return new RestResult()
        .setStatus(RestStatus.SUCCESS);
  }

  @PostMapping("filedelete")
  public Object filedelete(int boardNo, int fileNo, HttpSession session) {
    Member loginUser = (Member) session.getAttribute("loginUser");
    Board old = boardService.get(boardNo);
    if (old.getWriter().getNo() != loginUser.getNo()) {
      return new RestResult()
          .setStatus(RestStatus.FAILURE)
          .setErrorCode(ErrorCode.rest.UNAUTHORIZED)
          .setData("권한이 없습니다.");
    } else {
      boardService.deleteFile(fileNo);
      return new RestResult()
          .setStatus(RestStatus.SUCCESS);
    }
  }
}








