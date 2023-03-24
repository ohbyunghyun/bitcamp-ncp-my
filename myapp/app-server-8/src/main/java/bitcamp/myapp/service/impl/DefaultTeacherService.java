package bitcamp.myapp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.dao.TeacherDao;
import bitcamp.myapp.service.TeacherService;
import bitcamp.myapp.vo.Teacher;
import bitcamp.util.TransactionManager;

public class DefaultTeacherService implements TeacherService{
  private TransactionManager txManager;
  private MemberDao memberDao;
  private TeacherDao teacherDao;

  public DefaultTeacherService(TransactionManager txManager, MemberDao memberDao, TeacherDao teacherDao) {
    this.txManager = txManager;
    this.memberDao = memberDao;
    this.teacherDao = teacherDao;
  }

  public void add(Teacher teacher) {
    txManager.startTransaction();
    try {
      memberDao.insert(teacher);
      teacherDao.insert(teacher);
      txManager.commit();

    } catch (Exception e) {
      txManager.rollback();
      throw new RuntimeException(e);
    }
  }

  public List<Teacher> list() {
    return teacherDao.findAll();
  }

  public Teacher get(int no) {
    return teacherDao.findByNo(no);
  }

  public Teacher get(String email, String password) {
    Map<String,Object> paramMap = new HashMap<>();
    paramMap.put("email", email);
    paramMap.put("password", password);

    return teacherDao.findByEmailAndPassword(paramMap);
  }

  public void update(Teacher teacher) {
    txManager.startTransaction();
    try {
      if (memberDao.update(teacher) == 1 &&
          teacherDao.update(teacher) == 1) {
        txManager.commit();

      } else {
        throw new RuntimeException("해당강사가 없음!");
      }
    } catch (Exception e) {
      txManager.rollback();
      throw e;
    }
  }

  public void delete(int no) {
    txManager.startTransaction();
    try {
      if (teacherDao.delete(no) == 1 &&
          memberDao.delete(no) == 1) {
        txManager.commit();
      } else {
        throw new RuntimeException("해당강사가 없음!");
      }
    } catch (Exception e) {
      txManager.rollback();
      throw e;
    }
  }

}