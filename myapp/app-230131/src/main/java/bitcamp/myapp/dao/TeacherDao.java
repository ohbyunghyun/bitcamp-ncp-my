package bitcamp.myapp.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import bitcamp.myapp.vo.Teacher;
import bitcamp.util.BinaryDecoder;
import bitcamp.util.BinaryEncoder;

public class TeacherDao {

  List<Teacher> list;

  int lastNo;

  public TeacherDao(List<Teacher> list) {
    this.list = list;
  }

  public void insert(Teacher t) {
    t.setNo(++lastNo);
    t.setCreatedDate(new Date(System.currentTimeMillis()).toString());
    list.add(t);
  }

  public Teacher[] findAll() {
    Teacher[] teachers = new Teacher[list.size()];
    Iterator<Teacher> i = list.iterator();
    int index = 0;
    while (i.hasNext()) {
      teachers[index++] = i.next();
    }
    return teachers;
  }

  public Teacher findByNo(int no) {
    Teacher t = new Teacher();
    t.setNo(no);

    int index = list.indexOf(t);
    if (index == -1) {
      return null;
    }

    return list.get(index);
  }

  public void update(Teacher t) {
    int index = list.indexOf(t);
    list.set(index, t);
  }

  public boolean delete(Teacher t) {
    return list.remove(t);
  }

  public void save(String filename) {
    try {
      // 1) 바이너리 데이터(바이트배열)를 출력할 도구를 준비한다.

      FileOutputStream out = new FileOutputStream(filename);

      // 2) 멤버 수를 출력 : 4byte

      out.write(BinaryEncoder.write(list.size()));

      // 3) 멤버 출력
      // 목록에서 Board 객체를 꺼내 바이트 배열로 만든 다음 출력한다.
      for (Teacher t : list) {
        out.write(BinaryEncoder.write(t.getNo()));
        out.write(BinaryEncoder.write(t.getName()));
        out.write(BinaryEncoder.write(t.getTel()));
        out.write(BinaryEncoder.write(t.getCreatedDate()));
        out.write(BinaryEncoder.write(t.getEmail()));
        out.write(BinaryEncoder.write(t.getDegree()));
        out.write(BinaryEncoder.write(t.getSchool()));
        out.write(BinaryEncoder.write(t.getMajor()));
        out.write(BinaryEncoder.write(t.getWage()));
      }
    } catch (Exception e){
      e.printStackTrace();
    }
  }

  public void load(String filename) {
    if (list.size() > 0) {
      return;
    }
    try {

      // 1) 바이너리 데이터를 읽을 도구 준비

      FileInputStream in = new FileInputStream(filename);

      // 2) 저장된 게시글 개수를 읽는다 : 4byte

      int size = BinaryDecoder.readInt(in);


      // 3) 게시글 개수 만큼 반복해서 데이터를 읽어 Board 객체에 저장한다.
      for (int i = 0; i < size; i++) {
        // 바이너리 데이터를 저장한 순서대로 읽어서 Board 객체에 담는다.
        Teacher t = new Teacher();
        t.setNo(BinaryDecoder.readInt(in));
        t.setName(BinaryDecoder.readString(in));
        t.setTel(BinaryDecoder.readString(in));
        t.setCreatedDate(BinaryDecoder.readString(in));
        t.setEmail(BinaryDecoder.readString(in));
        t.setDegree(BinaryDecoder.readInt(in));
        t.setSchool(BinaryDecoder.readString(in));
        t.setMajor(BinaryDecoder.readString(in));
        t.setWage(BinaryDecoder.readInt(in));

        // 5) Board 객체를 목록에 추가한다.
        list.add(t);
      }

      if (list.size() > 0) {
        lastNo = list.get(list.size()-1).getNo();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}







