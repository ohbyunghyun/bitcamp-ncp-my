package bitcamp.util;

import java.util.Objects;

public class LinkedListTest {

  static class Member {
    int no;
    String name;
    String tel;

    public Member(int no, String name, String tel) {
      this.no = no;
      this.name = name;
      this.tel = tel;
    }

    @Override
    public String toString() {
      return "Member [no=" + no + ", name=" + name + ", tel=" + tel + "]";
    }

    @Override
    public int hashCode() {
      return Objects.hash(no);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      Member other = (Member) obj;
      return no == other.no;
    }


  }
  public static void main(String[] args) {
    LinkedList list = new LinkedList();

    list.add(new Member(1, "aaa", "1111"));
    list.add(new Member(2, "bbb", "2222"));
    list.add(new Member(3, "ccc", "3333"));
    list.add(new Member(4, "ddd", "4444"));

    print(list);
    //
    //    System.out.println(list.remove(new Member(3, null, null)));
    //    print(list);
    //    System.out.println(list.remove(new Member(4, null, null)));
    //    print(list);
    //    System.out.println(list.remove(new Member(1, null, null)));
    //    print(list);
    //    System.out.println(list.remove(new Member(2, null, null)));
    //    print(list);
    //
    //    list.add(new Member(5, "eee", "5555"));
    //    print(list);

    list.set(2, new Member(3, "ccccx", "6666x"));
    list.set(0, new Member(1, "hhhx", "6666x"));
    list.set(3, new Member(4, "xxxxh", "666xx6"));
    print(list);

  }

  static void print(LinkedList l) {
    System.out.println("+++++++++++++++++++++++++++++++++++++++");

    for (Object obj : l.toArray()) {
      System.out.println(obj);
    }
  }

}
