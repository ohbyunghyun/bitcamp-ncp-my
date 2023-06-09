package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Student;

public interface StudentDao {
  void insert(Student s);
  Student[] findAll();
  Student findByNo(int no);
  Student[] findByKeyword(String keyword);
  void update(Student s);
  boolean delete(Student s);
}







