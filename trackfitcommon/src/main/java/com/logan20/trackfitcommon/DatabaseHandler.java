package com.logan20.trackfitcommon;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 * Created by kwasi on 3/21/2017.
 * This class is used to perform all database transactions such as logging in of the user
 * and querying the database's tables
 */

public class DatabaseHandler {
    private static final String DB_NAME = "trackfit";
    private static final String DB_URL = "jdbc:mysql://192.168.43.123/";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    private static Connection conn;
    private static Statement stmt;
    private final static String TABLE_NAME = "USER";

    public static void init(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                conn = null;
                stmt = null;
                try {
                    connect();
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static boolean createUser(final String fName, final String lName, final String gender, final String email, final String pass, final String weight, final String height, String exCategory, int day, int month, int year) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String sql = "INSERT INTO USER (emailAdd,password,firstName,lastName,gender,age,height,startWeight,startBMI,currWeight,currBMI,exPrefID,datecreated,points,rank) VALUES " +
                            "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement pStmt = conn.prepareStatement(sql);
                    pStmt.setString(1,email);
                    pStmt.setString(2,pass);
                    pStmt.setString(3,fName);
                    pStmt.setString(4,lName);
                    pStmt.setString(5, String.valueOf(gender.charAt(0)));
                    pStmt.setInt(6,25);// TODO: 3/21/2017
                    pStmt.setFloat(7,Float.valueOf(height));
                    pStmt.setFloat(8,Float.valueOf(weight));
                    pStmt.setFloat(9,100);// TODO: 3/21/2017
                    pStmt.setFloat(10,Float.valueOf(weight));
                    pStmt.setFloat(11,100);// TODO: 3/21/2017
                    pStmt.setFloat(12,2);// TODO: 3/21/2017
                    pStmt.setDate(13, new java.sql.Date(new Date().getTime()));// TODO: 3/21/2017
                    pStmt.setInt(14,100);//// TODO: 3/21/2017
                    pStmt.setInt(15,100);//// TODO: 3/21/2017
                    pStmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        return true;
    }
    private static void connect() throws SQLException, ClassNotFoundException {
        Class.forName(DRIVER);
        conn = DriverManager.getConnection(DB_URL + DB_NAME, USERNAME, PASSWORD);
        stmt = conn.createStatement();
    }

    public static int login(String username, String password) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT USERID FROM USER WHERE emailAdd ='" + username + "' AND PASSWORD = '"+password+"'");
            while (rs.next()) {
                return rs.getInt("USERID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    // TODO: 3/23/2017 add last name to database, get first name and last name from user
    public static String getNameFromID(int userid) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT FIRSTNAME,LASTNAME FROM USER WHERE USERID ='" + userid + "'");
            while (rs.next()) {
                return rs.getString("FIRSTNAME")+" "+rs.getString("LASTNAME");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getEmailFromID(int userid) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT EMAILADD FROM USER WHERE USERID ='" + userid + "'");
            while (rs.next()) {
                return rs.getString("EMAILADD");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return " ";
    }
}
