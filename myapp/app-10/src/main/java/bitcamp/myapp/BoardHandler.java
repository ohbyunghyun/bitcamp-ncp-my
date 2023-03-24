package bitcamp.myapp;

import java.sql.Date;

public class BoardHandler {

  static final int SIZE = 100;
  int count = 0;
  Board[] boards = new Board[SIZE];
  String title;

  BoardHandler(String title) {
    this.title = title;
  }

  void inputBoard() {
    Board b = new Board();
    b.boardNo = Prompt.inputInt("번호? ");
    b.boardTitle = Prompt.inputString("제목? ");
    b.content = Prompt.inputString("내용? ");
    b.password = Prompt.inputString("암호? ");
    b.createdDate = new Date(System.currentTimeMillis()).toString();
    boards[count++] = b;
  }

  void viewBoardlist() {
    System.out.println("번호\t 제목\t 작성일\t\t 조회수\t");

    for (int i = 0; i < count; i++) {
      System.out.print(boards[i].boardNo + "\t");
      System.out.print(boards[i].boardTitle + "\t");
      System.out.print(boards[i].createdDate + "\t");
      System.out.print(boards[i].view + "\n");
    }
  }

  void viewBoard() {
    int boardNo = Prompt.inputInt("게시글번호? ");

    for (int i = 0; i < count; i++) {
      if (boards[i].boardNo == boardNo) {
        System.out.printf("제목 : %s\n", boards[i].boardTitle);
        System.out.printf("내용 : %s\n", boards[i].content);
        System.out.printf("작성일 : %s\n", boards[i].createdDate);
        System.out.printf("조회수 : %s\n", ++boards[i].view);
      }
    }
  }

  void modifyBoard() {
    int boardNo = Prompt.inputInt("게시글번호? ");

    for (int i = 0; i < count; i++) {
      if (boards[i].boardNo == boardNo) {
        String boardPassword = Prompt.inputString("암호? ");
        if (boards[i].password.equals(boardPassword)) {
          Board b = new Board();
          b.boardTitle = Prompt.inputString("제목? ");
          b.content = Prompt.inputString("내용? ");

          int boardNoTemp = boards[i].boardNo;
          String passwordTemp = boards[i].password;
          int viewTemp = boards[i].view;
          String createdDateTemp = boards[i].createdDate;

          boards[i] = b;
          boards[i].boardNo = boardNoTemp;
          boards[i].password = passwordTemp;
          boards[i].view = viewTemp;
          boards[i].createdDate = createdDateTemp;

        } else {
          System.out.println("틀린 암호임");
        }
      } else {
        System.out.println("없는 게시글 번호임");
      }
    }
  }

  void deleteBoard() {

    int boardNo = Prompt.inputInt("게시글번호? ");

    for (int i = 0; i < count; i++) {
      if (boards[i].boardNo == boardNo) {
        String boardPassword = Prompt.inputString("암호? ");
        if (boards[i].password.equals(boardPassword)) {
          for (int j = this.indexOf(boardNo) + 1; j < this.count; j++) {
            this.boards[j - 1] = this.boards[j];
          }
          this.boards[--this.count] = null; // 레퍼런스 카운트를 줄인다.
          System.out.println("삭제했습니다.");
          return;
        } else {
          System.out.println("틀린 암호임");
          return;
        }
      }
    }
    System.out.println("없는 게시글 번호임");
  }

  int indexOf(int b) {
    for (int i = 0; i < this.count; i++) {
      if (this.boards[i].boardNo == b) {
        return i;
      }
    }
    return -1;
  }

  void service() {
    while (true) {
      System.out.printf("[%s]\n", this.title);
      System.out.println("1. 등록");
      System.out.println("2. 목록");
      System.out.println("3. 조회");
      System.out.println("4. 변경");
      System.out.println("5. 삭제");
      System.out.println("0. 이전");
      int menuNo = Prompt.inputInt(String.format("%s> ", this.title));

      switch (menuNo) {
        case 0: return;
        case 1: this.inputBoard(); break;
        case 2: this.viewBoardlist(); break;
        case 3: this.viewBoard(); break;
        case 4: this.modifyBoard(); break;
        case 5: this.deleteBoard(); break;
        default:
          System.out.println("잘못된 메뉴 번호 입니다.");
      }
    }
  }
}
