import javax.print.DocFlavor;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CreatingDB {
    public static void main(String[] args) {
        System.out.println("doc".hashCode());
        Statement stmt = null;
        System.out.println("This will DELETE all data, do you want to continue? (y/n) ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.equals("y") || input.equals("Y")) {
            try {
                JDBC.connect();
                stmt = JDBC.connection.createStatement();
                // Drop Tables
                String drop1 = "DROP TABLE IF EXISTS admins ";
                stmt.executeUpdate(drop1);
                String drop7 = "DROP TABLE IF EXISTS company";
                stmt.executeUpdate(drop7);
                String drop2 = "DROP TABLE IF EXISTS employees";
                stmt.executeUpdate(drop2);
                String drop3 = "DROP TABLE IF EXISTS financialind ";
                stmt.executeUpdate(drop3);
                String drop4 = "DROP TABLE IF EXISTS finreports";
                stmt.executeUpdate(drop4);
                String drop5 = "DROP TABLE IF EXISTS info ";
                stmt.executeUpdate(drop5);
                String drop8 = "DROP TABLE IF EXISTS productsandservice";
                stmt.executeUpdate(drop8);
                String drop6 = "DROP TABLE IF EXISTS users";
                stmt.executeUpdate(drop6);
                System.out.println("Data deleted");
                // Create Tables
                String usersTable = "CREATE TABLE users " +
                        "(id INTEGER NOT NULL AUTO_INCREMENT, " +
                        " login VARCHAR(20) NOT NULL, " +
                        " password INTEGER NOT NULL, " +
                        " PRIMARY KEY (id));";
                stmt.executeUpdate(usersTable);
                String adminsTable = "CREATE TABLE admins" +
                        "(id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                        " user_id INTEGER NOT NULL, " +
                        " rightsToRedactUsers BOOLEAN DEFAULT FALSE, " +
                        " rightsToDeleteUsers BOOLEAN DEFAULT FALSE, " +
                        " rightsToRedactAdmins BOOLEAN DEFAULT FALSE, " +
                        " rightsToDeleteAdmins BOOLEAN DEFAULT FALSE, " +
                        " FOREIGN KEY (user_id) REFERENCES users(id))";
                stmt.executeUpdate(adminsTable);
                String CompanyTable="CREATE TABLE company"+
                        "(idcompany INTEGER PRIMARY KEY AUTO_INCREMENT,"+
                        "name VARCHAR(100) NOT NULL UNIQUE)";
                stmt.executeUpdate(CompanyTable);
                String employeessTable = "CREATE TABLE employees " +
                        "(idemployees INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                        "idcompany INTEGER NOT NULL,"+
                        "fio VARCHAR(255) NOT NULL,"+
                        "post VARCHAR(30), " +
                        "salary DOUBLE NOT NULL, " +
                        "hiredate DATE NOT NULL, " +
                        " FOREIGN KEY (idcompany) REFERENCES company(idcompany))";
                stmt.executeUpdate(employeessTable);
                String FinIndTable = "CREATE TABLE financialind " +
                        "(idfinancialind INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                        "idcompany INTEGER NOT NULL," +
                        "year YEAR(4) NOT NULL, " +
                        "infoExpenses DOUBLE NOT NULL, " +
                        "infoIncomes DOUBLE NOT NULL," +
                        " FOREIGN KEY (idcompany) REFERENCES company(idcompany))";
                stmt.executeUpdate(FinIndTable);
                String finreportstable = "CREATE TABLE finreports " +
                        "(idfinreports INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                        "idcompany INTEGER NOT NULL, " +
                        "year YEAR(4) NOT NULL, " +
                        "reporttype VARCHAR(45) NOT NULL, " +
                        "reportdate VARCHAR(45), " +
                        " FOREIGN KEY (idcompany) REFERENCES company(idcompany))";
                stmt.executeUpdate(finreportstable);
                String infoTable = "CREATE TABLE info " +
                        "(idinfo INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                        "idcompany INTEGER NOT NULL, " +
                        "address VARCHAR(45) NOT NULL, " +
                        "phone VARCHAR(45) NOT NULL, " +
                        "email VARCHAR(45), " +
                        "activity VARCHAR(45) NOT NULL, " +
                        " FOREIGN KEY (idcompany) REFERENCES company(idcompany))";
                stmt.executeUpdate(infoTable);
                String pnsTable = "CREATE TABLE productsandservice " +
                        "(idproductsandservice INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                        "idcompany INTEGER NOT NULL, " +
                        "name VARCHAR(45) NOT NULL, " +
                        "description VARCHAR(45), " +
                        "price DOUBLE  NOT NULL, " +
                        "soldcount INTEGER NOT NULL, " +
                        " FOREIGN KEY (idcompany) REFERENCES company(idcompany))";
                stmt.executeUpdate(pnsTable);
                String userCreation = "INSERT INTO users ( id, login, password)" +
                        "VALUES (1, 'admin', " + "admin".hashCode() + ")";
                stmt.executeUpdate(userCreation);
                String adminCreation = "INSERT INTO admins (user_id, rightsToRedactUsers," +
                        "rightsToDeleteUsers," +
                        "rightsToRedactAdmins, rightsToDeleteAdmins)" +
                        "VALUES (1, true, true, true, true)";
                stmt.executeUpdate(adminCreation);
            } catch (SQLException se) {
                // Handle errors for JDBC
                se.printStackTrace();
            } finally {
                // Finally block, used to close resources
                if (stmt != null) {
                    JDBC.close();
                }
            }
        }
    }
}
