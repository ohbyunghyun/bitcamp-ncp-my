package bitcamp.myapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {

  public static void main(String[] args) {
    new ClientApp().execute("192.168.0.17", 8888);

  } // main()

  void execute(String ip, int port) {
    try (Scanner keyboard = new Scanner(System.in);
        Socket socket = new Socket(ip, port);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

      System.out.print(in.readUTF());

      while (true) {
        String input = prompt("> ", keyboard);
        out.writeUTF(input);

        String response = in.readUTF();
        if (response.equalsIgnoreCase("quit")) {
          break;
        }
        System.out.print(response);
      }
      System.out.println("안녕~");
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  String prompt(String title, Scanner keyboard) throws Exception {
    System.out.printf("%s", title);
    return keyboard.nextLine();
  }
} // class App









