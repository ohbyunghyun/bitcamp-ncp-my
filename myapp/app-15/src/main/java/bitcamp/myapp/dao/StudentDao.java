package bitcamp.myapp.dao;

import java.sql.Date;
import bitcamp.myapp.vo.Student;
import bitcamp.util.LinkedList;

public class StudentDao {
  LinkedList list = new LinkedList();

  int lastNo;


  public Student findByNo(int no) {
    Student s = new Student();
    s.setNo(no);

    int index = list.indexOf(s);
    if (index == -1) {
      return null;
    }

    return (Student) list.get(list.indexOf(s));
  }

  public void insert(Student student) {
    student.setNo(++lastNo);
    student.setCreatedDate(new Date(System.currentTimeMillis()).toString());
    list.add(student);
  }


  public Student[] findAll() {
    Student[] students = new Student[list.size()];
    Object[]  arr = list.toArray();
    for (int i = 0; i < students.length; i++) {
      students[i] = (Student) arr[i];
    }
    return students;
  }

  public void update(Student s) {
    list.set(list.indexOf(s), s);
  }

  public boolean delete(Student s) {
    return list.remove(s);
  }
}







