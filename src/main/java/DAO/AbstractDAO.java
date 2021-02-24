package DAO;

import java.sql.*;

public class AbstractDAO {
    private static final String controller = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost/merkabbdd";
    private static final String user = "root";
    private static final String password = "1234";

    static Connection cnt = null;
    static Statement st = null;

    static ResultSet rs = null;

    public static void conectar(){
        try {
            cnt= DriverManager.getConnection(url, user, password);
            st = cnt.createStatement();
        } catch (SQLException ex) {
            System.out.println("No se ha podido conectar con la BBDD, llama al servicio técnico");
        }
    }
}
