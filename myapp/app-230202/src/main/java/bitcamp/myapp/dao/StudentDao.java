package bitcamp.myapp.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import bitcamp.myapp.vo.Student;

public class StudentDao {

  List<Student> list;

  int lastNo;

  public StudentDao(List<Student> list) {
    this.list = list;
  }

  public void insert(Student s) {
    s.setNo(++lastNo);
    s.setCreatedDate(new Date(System.currentTimeMillis()).toString());
    list.add(s);
  }

  public Student[] findAll() {
    Student[] students = new Student[list.size()];
    Iterator<Student> i = list.iterator();
    int index = 0;
    while (i.hasNext()) {
      students[index++] = i.next();
    }
    return students;
  }

  public Student findByNo(int no) {
    Student s = new Student();
    s.setNo(no);

    int index = list.indexOf(s);
    if (index == -1) {
      return null;
    }
    return list.get(index);
  }

  public void update(Student s) {
    int index = list.indexOf(s);
    list.set(index, s);
  }

  public boolean delete(Student s) {
    return list.remove(s);
  }

  public void save(String filename) {


    try (FileWriter out = new FileWriter(filename)) {

      list.forEach(s -> {
        try {
          out.write(String.format("%d,%s,%s,%s,%s,%s,%s,%b,%s,%d\n",
              s.getNo(),
              s.getName(),
              s.getTel(),
              s.getCreatedDate(),
              s.getPostNo(),
              s.getBasicAddress(),
              s.getDetailAddress(),
              s.isWorking(),
              s.getGender(),
              s.getLevel()));
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
        Student s = new Student();
        s.setNo(Integer.parseInt(values[0]));
        s.setName(values[1]);
        s.setTel(values[2]);
        s.setCreatedDate(values[3]);
        s.setPostNo(values[4]);
        s.setBasicAddress(values[5]);
        s.setDetailAddress(values[6]);
        s.setWorking(Boolean.parseBoolean(values[7]));
        s.setGender(values[8].charAt(0));
        s.setLevel(Byte.parseByte(values[9]));

        list.add(s);
      }

      lastNo = list.get(list.size() - 1).getNo();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}







