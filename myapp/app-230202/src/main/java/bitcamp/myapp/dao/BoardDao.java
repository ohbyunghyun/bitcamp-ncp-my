package bitcamp.myapp.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import bitcamp.myapp.vo.Board;

public class BoardDao {

  List<Board> list;
  int lastNo;

  public BoardDao(List<Board> list) {
    this.list = list;
  }

  public void insert(Board board) {
    board.setNo(++lastNo);
    board.setCreatedDate(new Date(System.currentTimeMillis()).toString());
    list.add(board);
  }

  public Board[] findAll() {
    Board[] boards = new Board[list.size()];
    Iterator<Board> i = list.iterator();
    int index = 0;
    while (i.hasNext()) {
      boards[index++] = i.next();
    }
    return boards;
  }

  public Board findByNo(int no) {
    Board b = new Board();
    b.setNo(no);

    int index = list.indexOf(b);
    if (index == -1) {
      return null;
    }

    return list.get(index);
  }

  public void update(Board b) {
    int index = list.indexOf(b);
    list.set(index, b);
  }

  public boolean delete(Board b) {
    return list.remove(b);
  }

  public void save(String filename) {


    try (FileWriter out = new FileWriter(filename)) {

      list.forEach(b -> {
        try {
          out.write(String.format("%d,%s,%s,%s,%d,%s\n",
              b.getNo(),
              b.getTitle(),
              b.getContent(),
              b.getPassword(),
              b.getViewCount(),
              b.getCreatedDate()));
        } catch (Exception e) {
          System.out.println("데이터 출력 중 오류 발생!");
          e.printStackTrace();
        }
      });

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @SuppressWarnings("unchecked")
  public void load(String filename) {
    if (list.size() > 0) { // 중복 로딩 방지!
      return;
    }

    try (BufferedReader in = new BufferedReader(new FileReader(filename))) {

      while(true) {
        String str = in.readLine();
        if (str == null) {
          break;
        }
        String[] values = str.split(",");

        Board b = new Board();
        b.setNo(Integer.parseInt(values[0]));
        b.setTitle(values[1]);
        b.setContent(values[2]);
        b.setPassword(values[3]);
        b.setViewCount(Integer.parseInt(values[4]));
        b.setCreatedDate(values[5]);

        list.add(b);
      }

      lastNo = list.get(list.size() - 1).getNo();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}























