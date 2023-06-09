package bitcamp.myapp.dao;

public abstract class ObjectDao {

  private Node head;
  private Node tail;
  private int size;

  public void insert(Object value) {
    Node node = new Node(value);
    if (this.tail == null) {
      this.head = this.tail = node;
      return;
    }
    this.tail.next = node;
    this.tail = node;
    this.size++;
  }

  public Object[] findAll() {

    Object[] values = new Object[this.size];
    int index = 0;
    Node cursor = this.head;
    while (cursor !=null) {
      values[index++] = cursor.value;
      cursor = cursor.next;
    }
    return values;
  }

  public void update(Object object) {
    this.objects[this.indexOf(object)] = object;
  }

  public void delete(Object object) {
    for (int i = this.indexOf(object) + 1; i < this.count; i++) {
      this.objects[i - 1] = this.objects[i];
    }
    this.objects[--this.count] = null; // 레퍼런스 카운트를 줄인다.
  }

  // 객체의 위치를 찾는 것은
  // 객체의 타입에 따라 다를 수 있기 때문에
  // 이 클래스에서 정의하지 말고,
  // 서브 클래스에서 정의할 수 있도록
  // 그 구현의 책임을 위임해야 한다.
  protected abstract int indexOf(Object b);

  public int size() {
    return this.count;
  }

  public Object get(int i) {
    if (i < 0 || i >= this.size) {
      throw new DaoException("인덱스가 무효합니다");
    }
    return objects[i];
  }
}






