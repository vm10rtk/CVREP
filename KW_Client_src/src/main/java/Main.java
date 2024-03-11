import GUILate.LoginWindow;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("server connecting....");
            Socket clientSocket = new Socket("127.0.0.1", 2525);
            System.out.println("connection established....");
            ObjectOutputStream coos = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream cois = new ObjectInputStream(clientSocket.getInputStream());
//Scanner scanner = new Scanner(System.in);
            int choice = 1;
            LoginWindow loginWindow = new LoginWindow("Login", coos, cois, clientSocket);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}