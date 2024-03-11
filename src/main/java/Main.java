import models.*;
import services.*;

import javax.print.Doc;
import java.io.*;
import java.net.*;

public class Main {
    public static int connections = 0;
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket clientAccepted = null;
        System.out.println("Сервер запущен.");
        try {
            serverSocket = new ServerSocket(2525);
            NewThread t;
            do
            {
                clientAccepted = serverSocket.accept();
                t = new NewThread(clientAccepted, connections);
                if (clientAccepted != null){
                    Main.connections = t.getConnections();
                    t.start();
                }
            }
            while (t.isAlive());
        } catch (Exception e) {}
        finally {
            try {
                serverSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
/*
        User user = new User("login", "123456");
        userService.saveUser(user);


        User userA = new User("admin", "admin");
        Admin admin = new Admin(true, true, true, true);
        admin.setUser(userA);
        userService.saveUser(userA);
        adminService.saveAdmin(admin);

        User userD = new User("doctor", "doc");
        Doctor doctor = new Doctor("Famil", "Imya", "Otchs", "Vrach", 1, "doctoremail");
        doctor.setUser(userD);
        userService.saveUser(userD);
        doctorService.saveDoctor(doctor);

        User userP = new User("patient", "pat");
        Patient patient = new Patient("f", "i", "o", "ulica", "m", "patemail", 1234567);
        patient.setUser(userP);
        userService.saveUser(userP);
        patientService.savePatient(patient);

        */

        /*
        ServerSocket serverSocket = null;
        Socket clientAccepted = null;
        System.out.println("server starting....");
        try {
            serverSocket = new ServerSocket(2525);
            NewThread t;
            do
            {
                clientAccepted = serverSocket.accept();
                t = new NewThread(clientAccepted, connections);
                if (clientAccepted != null){
                    Main.connections = t.getConnections();
                    t.start();
                }
            }
            while (t.isAlive());
        } catch (Exception e) {}
        finally {
            try {
                serverSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } */
    }


}
