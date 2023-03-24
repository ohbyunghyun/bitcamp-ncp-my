package bitcamp.myapp;

public class Main {

  public static void main(String[] args) {
    int io = 19;
    String str = String.valueOf(io);
    Object[] arr = new Object[str.length()];


    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == 0) {
        arr[i] = "a";
      } else if (str.charAt(i) == '1') {
        arr[i] = "b";
      } else if (str.charAt(i) == '2') {
        arr[i] = "c";
      } else if (str.charAt(i) == '3') {
        arr[i] = "d";
      } else if (str.charAt(i) == '4') {
        arr[i] = "e";
      } else if (str.charAt(i) == '5') {
        arr[i] = "f";
      } else if (str.charAt(i) == '6') {
        arr[i] = "g";
      } else if (str.charAt(i) == '7') {
        arr[i] = "h";
      } else if (str.charAt(i) == '8') {
        arr[i] = "i";
      } else if (str.charAt(i) == '9') {
        arr[i] = "j";
      }
      System.out.println(arr[i]);
    }

  }

}
