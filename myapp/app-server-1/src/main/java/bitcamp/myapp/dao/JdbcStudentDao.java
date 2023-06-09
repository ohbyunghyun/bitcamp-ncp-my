package bitcamp.myapp.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import bitcamp.myapp.vo.Student;

public class JdbcStudentDao implements StudentDao{

  Connection con;

  public JdbcStudentDao(Connection con) {
    this.con = con;
  }

  @Override
  public void insert(Student s) {
    try (Statement stmt = con.createStatement()) {

      String sql = String.format("insert into app_student(name, tel, pst_no, bas_addr, det_addr, work, gender, level) values('%s', '%s', '%s', '%s', '%s', %b, '%c', %d)",
          s.getName(), s.getTel(), s.getPostNo(), s.getBasicAddress(), s.getDetailAddress(), s.isWorking(), s.getGender(), s.getLevel());
      stmt.executeUpdate(sql);

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public Student[] findAll() {
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(
            "select student_id, name, tel, created_date, pst_no, bas_addr, det_addr, work, gender, level from app_student order by student_id desc");) {

      ArrayList<Student> list = new ArrayList<>();
      while (rs.next()) {
        Student s = new Student();
        s.setNo(rs.getInt("student_id"));
        s.setName(rs.getString("name"));
        s.setTel(rs.getString("tel"));
        s.setCreatedDate(rs.getString("created_date"));
        s.setPostNo(rs.getString("pst_no"));
        s.setBasicAddress(rs.getString("bas_addr"));
        s.setDetailAddress(rs.getString("det_addr"));
        s.setWorking(rs.getBoolean("work"));
        s.setGender(rs.getString("gender").charAt(0));
        s.setLevel(rs.getByte("level"));

        list.add(s);
      }

      Student[] students = new Student[list.size()];
      list.toArray(students);

      return students;

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public Student findByNo(int no) {

    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(
            "select student_id, name, tel, created_date, pst_no, bas_addr, det_addr, work, gender, level from app_student where student_id=" + no)) {

      while (rs.next()) {
        Student s = new Student();
        s.setNo(rs.getInt("student_id"));
        s.setName(rs.getString("name"));
        s.setTel(rs.getString("tel"));
        s.setCreatedDate(rs.getString("created_date"));
        s.setPostNo(rs.getString("pst_no"));
        s.setBasicAddress(rs.getString("bas_addr"));
        s.setDetailAddress(rs.getString("det_addr"));
        s.setWorking(rs.getBoolean("work"));
        s.setGender(rs.getString("gender").charAt(0));
        s.setLevel(rs.getByte("level"));

        return s;
      }

      return null;

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public Student[] findByKeyword(String keyword) {
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(
            "select student_id, name, tel, created_date, pst_no, bas_addr, det_addr, work, gender, level"
                + " from app_student"
                + " where name like('%" + keyword + "%') "
                + " or tel like('%" + keyword + "%') "
                + " order by student_id desc");) {

      ArrayList<Student> list = new ArrayList<>();
      while (rs.next()) {
        Student s = new Student();
        s.setNo(rs.getInt("student_id"));
        s.setName(rs.getString("name"));
        s.setTel(rs.getString("tel"));
        s.setCreatedDate(rs.getString("created_date"));
        s.setPostNo(rs.getString("pst_no"));
        s.setBasicAddress(rs.getString("bas_addr"));
        s.setDetailAddress(rs.getString("det_addr"));
        s.setWorking(rs.getBoolean("work"));
        s.setGender(rs.getString("gender").charAt(0));
        s.setLevel(rs.getByte("level"));

        list.add(s);
      }

      Student[] students = new Student[list.size()];
      list.toArray(students);

      return students;

    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  @Override
  public void update(Student s) {

    try (Statement stmt = con.createStatement()) {

      String sql = String.format("update app_student set name='%s', tel='%s', pst_no='%s', bas_addr='%s', det_addr='%s', work=%b, gender='%c', level=%d where student_id=%d",
          s.getName(), s.getTel(), s.getPostNo(), s.getBasicAddress(), s.getDetailAddress(), s.isWorking(), s.getGender(), s.getLevel(), s.getNo());
      stmt.executeUpdate(sql);

    } catch (Exception e) {
      throw new DaoException(e);
    }

  }

  @Override
  public boolean delete(Student s) {

    try (Statement stmt = con.createStatement()) {

      String sql = String.format("delete from app_student where student_id=%d", s.getNo());

      return stmt.executeUpdate(sql) == 1;

    } catch (Exception e) {
      throw new DaoException(e);
    }

  }

}






















