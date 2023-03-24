package bitcamp.myapp;

public class Main {

  public static void main(String[] args) {
    String my_string = "hiroo";
    String letter = "o";
    char c = letter.charAt(0);
    char[] arr = new char[my_string.length()];
    String str = "";


    for (int i = 0; i < my_string.length(); i++) {
      arr[i] += my_string.charAt(i);
    }
    for (int i = 0; i < my_string.length(); i++) {
      if (arr[i] == c) {
        System.out.println(arr[i]);
      }
    }

    for (int i = 0; i < arr.length; i++) {
      str += arr[i];
    }
    System.out.println(str);
  }
}
