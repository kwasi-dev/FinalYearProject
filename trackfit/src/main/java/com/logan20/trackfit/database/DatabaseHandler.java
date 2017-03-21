package com.logan20.trackfit.database;

import java.security.spec.ECField;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by kwasi on 3/21/2017.
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

    public static void init() throws ClassNotFoundException, SQLException {
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

    public static boolean createUser(final String fName, String lName, final String gender, final String email, final String pass, final String weight, final String height, String exCategory, int day, int month, int year) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String sql = "INSERT INTO USER (emailAdd,password,firstName,gender,age,height,startWeight,startBMI,currWeight,currBMI,exPrefID,datecreated,points,rank) VALUES " +
                            "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement pStmt = conn.prepareStatement(sql);
                    pStmt.setString(1,email);
                    pStmt.setString(2,pass);
                    pStmt.setString(3,fName);
                    pStmt.setString(4, String.valueOf(gender.charAt(0)));
                    pStmt.setInt(5,25);// TODO: 3/21/2017
                    pStmt.setFloat(6,Float.valueOf(height));
                    pStmt.setFloat(7,Float.valueOf(weight));
                    pStmt.setFloat(8,100);// TODO: 3/21/2017
                    pStmt.setFloat(9,Float.valueOf(weight));
                    pStmt.setFloat(10,100);// TODO: 3/21/2017
                    pStmt.setFloat(11,2);// TODO: 3/21/2017
                    pStmt.setDate(12, new java.sql.Date(new Date().getTime()));// TODO: 3/21/2017
                    pStmt.setInt(13,100);//// TODO: 3/21/2017
                    pStmt.setInt(14,100);//// TODO: 3/21/2017
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
}
