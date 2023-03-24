package bitcamp.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class CalcClient {
  public static void main(String[] args) throws Exception{

    Scanner keyScan = new Scanner(System.in);

    System.out.println("클라이언트 실행");
    Socket socket = new Socket("127.0.0.1", 8888);
    System.out.println("서버에 연결됨...");

    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
    DataInputStream in = new DataInputStream(socket.getInputStream());

    while (true) {
      System.out.print("입력>");
      String message = keyScan.nextLine();
      out.writeUTF(message);

      if (message.equals("quit")) {
        break;
      }
      System.out.println(in.readUTF()); // 결과 받기
    }

    in.close();
    out.close();
    socket.close();

    keyScan.close();
    System.out.println("클라이언트 종료");

    //String testInput = "3*3-3 / 3 + 5 * 2 * -3";
  }
}

