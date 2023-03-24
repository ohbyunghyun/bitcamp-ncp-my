package bitcamp.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class CalcServer{
  public static void main(String[] args) throws Exception {
    System.out.println("서버실행중...");
    ServerSocket serverSocket = new ServerSocket(8888);
    Socket socket = serverSocket.accept();
    System.out.println("클라이언트 연결");

    DataInputStream in = new DataInputStream(socket.getInputStream());
    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

    while (true) {
      String str = in.readUTF();
      System.out.println(str);
      if (str.equals("quit")) {
        break;
      }
      out.writeUTF(String.valueOf(calculateSentence(str)));
    }
    out.close();
    in.close();
    socket.close();
    serverSocket.close();
    System.out.println("서버종료");

    //String testInput = "3*3-3 / 3 + 5 * 2 * -3";
    //System.out.println(calculateSentence("3+-3"));
  }

  static boolean isOperator(char operator) {
    char[] operators = new char[] {'+','-','*','/'};
    for (char c : operators) {
      if (c == operator) {
        return true;
      }
    }
    return false;
  }

  static String calculateSentence(String expression) {
    String sentence = expression.replaceAll(" ", ""); // 공백 제거
    int startIndex = 0;

    System.out.println(sentence);

    double tmp = 0.0;
    ArrayList<Double> addValues = new ArrayList<>();
    int nowSavedIndex = 0;

    boolean multypliyFlag = false;
    boolean divideFlag = false;
    boolean minuseFlag = false;
    try {
      for (int i = 0; i < sentence.length(); i++) { // 공통코드좀 묶어주세요
        if (isOperator(sentence.charAt(i))) {
          switch (sentence.charAt(i)) {
            case '+':
              tmp = Double.parseDouble(sentence.substring(startIndex, i));
              System.out.println(tmp);
              if (minuseFlag) {
                tmp = -tmp;
                minuseFlag = false;
              }
              if (multypliyFlag) {
                addValues.set(nowSavedIndex, addValues.get(nowSavedIndex) * tmp);
                multypliyFlag = false;
              } else if (divideFlag) {
                addValues.set(nowSavedIndex, addValues.get(nowSavedIndex) / tmp);
                divideFlag = false;
              } else {
                addValues.add(tmp);
              }
              nowSavedIndex++;

              break;
            case '-':
              if (i == 0 || isOperator(sentence.charAt(i - 1))) {
                minuseFlag = true;
                break;
              }
              tmp = Double.parseDouble(sentence.substring(startIndex, i));
              if (minuseFlag) {
                tmp = -tmp;
                minuseFlag = false;
              }
              if (multypliyFlag) {
                addValues.set(nowSavedIndex, addValues.get(nowSavedIndex) * tmp);
                multypliyFlag = false;
              } else if (divideFlag) {
                addValues.set(nowSavedIndex, addValues.get(nowSavedIndex) / tmp);
                divideFlag = false;
              } else {
                addValues.add(-tmp);
              }
              nowSavedIndex++;
              break;
            case '*':
              tmp = Double.parseDouble(sentence.substring(startIndex, i));
              if (minuseFlag) {
                tmp = -tmp;
                minuseFlag = false;
              }
              if (multypliyFlag) {
                addValues.set(nowSavedIndex, addValues.get(nowSavedIndex) * tmp);
                multypliyFlag = false;
              } else if (divideFlag) {
                addValues.set(nowSavedIndex, addValues.get(nowSavedIndex) / tmp);
                divideFlag = false;
              } else {
                addValues.add(tmp);
              }
              multypliyFlag = true;
              break;

            case '/':
              tmp = Double.parseDouble(sentence.substring(startIndex, i));
              if (minuseFlag) {
                tmp = -tmp;
                minuseFlag = false;
              }
              if (multypliyFlag) {
                addValues.set(nowSavedIndex, addValues.get(nowSavedIndex) * tmp);
                multypliyFlag = false;
              } else if (divideFlag) {
                addValues.set(nowSavedIndex, addValues.get(nowSavedIndex) / tmp);
                divideFlag = false;
              } else {
                addValues.add(tmp);
              }
              divideFlag = true;
              break;
            default:
          }
          startIndex = i + 1 ;
        }
      }
      tmp = Double.valueOf(sentence.substring(startIndex, sentence.length()));
      if (minuseFlag) {
        tmp = -tmp;
        minuseFlag = false;
      }
      if (multypliyFlag) {
        addValues.set(nowSavedIndex, addValues.get(nowSavedIndex) * tmp);
        multypliyFlag = false;
      } else if (divideFlag) {
        addValues.set(nowSavedIndex, addValues.get(nowSavedIndex) / tmp);
        divideFlag = false;
      } else {
        addValues.add(tmp);
      }
    } catch (Exception e) {
      return "계산 실패";
    }
    double result = 0;
    for  (double value : addValues) {
      System.out.println(value);
      result += value;
    }
    return String.valueOf(result);
  }
}
