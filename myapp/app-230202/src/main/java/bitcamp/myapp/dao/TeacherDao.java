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

      list.forEach(t -> {
        try {
          out.write(String.format("%d,%s,%s,%s,%s,%d,%s,%s,%d\n",
              t.getNo(),
              t.getName(),
              t.getTel(),
              t.getCreatedDate(),
              t.getEmail(),
              t.getDegree(),
              t.getSchool(),
              t.getMajor(),
              t.getWage()));
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
        Teacher t = new Teacher();
        t.setNo(Integer.parseInt(values[0]));
        t.setName(values[1]);
        t.setTel(values[2]);
        t.setCreatedDate(values[3]);
        t.setEmail(values[4]);
        t.setDegree(Integer.parseInt(values[5]));
        t.setSchool(values[6]);
        t.setMajor(values[7]);
        t.setWage(Integer.parseInt(values[8]));

        list.add(t);
      }

      lastNo = list.get(list.size() - 1).getNo();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}







