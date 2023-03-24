package bitcamp.myapp;

public class Main {

  public static void main(String[] args) {
    int[] emergency = {30, 10, 23, 6, 100};
    int[] answer = new int[emergency.length];
    int no = emergency.length;

    for (int i = 0; i < emergency.length; i++) {
      answer[i] = emergency[i];
    }

    java.util.Arrays.sort(answer);

    for (int i = 0; i < answer.length; i++) {
      for (int j = answer.length-1; j >= 0; j--) {
        if (answer[i] == emergency[j]) {
          emergency[j] = no--;
          break;
        }
      }
    }

    for (int i : emergency) {
      System.out.println(i);
    }
  }

}
