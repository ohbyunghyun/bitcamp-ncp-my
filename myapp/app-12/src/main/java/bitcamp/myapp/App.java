package bitcamp.myapp;

import bitcamp.myapp.handler.BoardHandler;
import bitcamp.myapp.handler.StudentHandler;
import bitcamp.myapp.handler.TeacherHandler;
import bitcamp.util.Prompt;

public class App {

  public static void main(String[] args) {
    goMainMenu();
    System.out.println("안녕히 가세요!");

    // 프로그램이 사용한 자원 해제하기
    Prompt.close();
  } // main()

  private static void goMainMenu() {
    // 학생 목록을 저장할 메모리를 준비한다.
    StudentHandler studentHandler = new StudentHandler("학생");

    TeacherHandler teacherHandler = new TeacherHandler("강사");
    // 게시글 목록을 저장할 메모리를 준비한다.
    BoardHandler boardHandler = new BoardHandler("게시판");


    loop: while (true) {
      System.out.println("1. 일반학생관리");
      System.out.println("2. 강사");
      System.out.println("3. 게시판");
      System.out.println("9. 종료");
      int menuNo;

      try {
        menuNo = Prompt.inputInt("메뉴> ");
      } catch (Throwable e) {
        System.out.println("메뉴 번호가 옳지 않습니다!");
        continue;
      }

      try {
        switch (menuNo) {
          case 1: studentHandler.service(); break;
          case 2: teacherHandler.service(); break;
          case 3: boardHandler.service(); break;
          case 9: break loop;
          default:
            System.out.println("잘못된 메뉴 번호 입니다.");
        }
      } catch (Throwable e) {
        System.out.println("에러에러!!");
      }
    }
  }

} // class App









