import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;
import static java.lang.System.exit;

public class DbConnect {
    private static Connection dbConnect = null;

    static  //ensures not loaded into memory too early
    {
        String url = "jdbc:mysql://";
        String dbName ="";
        String user = "";
        String password = "";
        try {
            Scanner sc = new Scanner(new File("config.txt"));
            if (sc.hasNextLine()) url = url + sc.nextLine()+"/";
            if (sc.hasNextLine()) dbName =  sc.nextLine();
            if (sc.hasNextLine()) user   =  sc.nextLine();
            if (sc.hasNextLine()) password   =  sc.nextLine();
            sc.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("An error occurred while reading config.txt");
            exit(-1);
        }
        try {Class.forName("com.mysql.cj.jdbc.Driver");
            dbConnect = DriverManager.getConnection(url+dbName, user, password);
            System.out.println("MySQL Db Connection is successful");}
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();}
    }

    public static Connection getDbConnect(){     //this allows other classes to get the connection as the singleton is private
        return dbConnect;
    } // used in other classes to get a connection


}
