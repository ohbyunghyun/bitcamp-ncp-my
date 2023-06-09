package bitcamp.myapp.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import bitcamp.myapp.vo.Teacher;

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
    try (FileWriter out = new FileWriter(filename)) {

      list.forEach(obj -> {
        try {
          out.write(obj.toCsvString() + "\n");
        } catch (Exception e) {
          System.out.println("데이터 출력 중 오류 발생!");
          e.printStackTrace();
        }
      });

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void load(String filename) {
    if (list.size() > 0) {
      return;
    }

    try (BufferedReader in = new BufferedReader(new FileReader(filename))) {

      String str = null;

      while ((str = in.readLine()) != null) {
        list.add(Teacher.create(str));
      }

      if (list.size() > 0) {
        lastNo = list.get(list.size() - 1).getNo();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}







