package bitcamp.myapp.dao;

import java.sql.Date;
import bitcamp.myapp.vo.Teacher;
import bitcamp.util.LinkedList;

public class TeacherDao {

  int lastNo;

  LinkedList list = new LinkedList();

  public Teacher findByNo(int no) {
    Teacher t = new Teacher();
    t.setNo(no);
    int index = list.indexOf(t);
    if (index == -1) {
      return null;
    }


    return (Teacher) list.get(list.indexOf(t));
  }

  public void insert(Teacher t) {
    t.setNo(++lastNo);
    t.setCreatedDate(new Date(System.currentTimeMillis()).toString());

    list.add(t);
  }

  public Teacher[] findAll() {
    Teacher[] t = new Teacher[list.size()];
    Object[] arr = list.toArray();
    for (int i = 0; i < t.length; i++) {
      t[i] = (Teacher) arr[i];
    }
    return t;
  }

  public void update(Teacher t) {
    list.set(list.indexOf(t), t);
  }

  public boolean delete(Teacher t) {
    return list.remove(t);
  }

}







