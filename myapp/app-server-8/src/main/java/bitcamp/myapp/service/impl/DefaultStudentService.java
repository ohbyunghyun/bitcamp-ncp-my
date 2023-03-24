package bitcamp.myapp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.dao.StudentDao;
import bitcamp.myapp.service.StudentService;
import bitcamp.myapp.vo.Student;
import bitcamp.util.TransactionManager;

public class DefaultStudentService implements StudentService {
  private TransactionManager txManager;
  private MemberDao memberDao;
  private StudentDao studentDao;

  public DefaultStudentService(TransactionManager txManager, MemberDao memberDao, StudentDao studentDao) {
    this.txManager = txManager;
    this.memberDao = memberDao;
    this.studentDao = studentDao;
  }

  public void add(Student student) {
    txManager.startTransaction();
    try {
      memberDao.insert(student);
      studentDao.insert(student);
      txManager.commit();

    } catch (Exception e) {
      txManager.rollback();
      throw new RuntimeException(e);
    }
  }

  public List<Student> list(String keyword) {
    return studentDao.findAll(keyword);
  }

  public Student get(int no) {
    return studentDao.findByNo(no);
  }

  public Student get(String email, String password) {
    Map<String,Object> paramMap = new HashMap<>();
    paramMap.put("email", email);
    paramMap.put("password", password);

    return studentDao.findByEmailAndPassword(paramMap);
  }

  public void update(Student student) {
    txManager.startTransaction();
    try {
      if (memberDao.update(student) == 1 &&
          studentDao.update(student) == 1) {
        txManager.commit();
      } else {
        throw new RuntimeException("해당학생 없음!");
      }
    } catch (Exception e) {
      txManager.rollback();
      throw e;
    }
  }

  public void delete(int no) {
    txManager.startTransaction();
    try {
      if (studentDao.delete(no) == 1 &&
          memberDao.delete(no) == 1) {
        txManager.commit();
      } else {
        throw new RuntimeException("해당학생 없음!");
      }
    } catch (Exception e) {
      txManager.rollback();
      throw e;
    }
  }


}
