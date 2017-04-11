package com.logan20.trackfitcommon;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

    public static boolean createUser(final String fName, final String lName, final String gender, final String email, final String pass, final String weight, final String height, final String exCategory, final int age, final float bmi) {
        try {
            String sql = "INSERT INTO USER (emailAdd,password,firstName,lastName,gender,age,height,startWeight,startBMI,currWeight,currBMI,exPrefID,datecreated,points,rank) VALUES " +
                    "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1,email);
            pStmt.setString(2,md5(pass));
            pStmt.setString(3,fName);
            pStmt.setString(4,lName);
            pStmt.setString(5, String.valueOf(gender.charAt(0)));
            pStmt.setInt(6,age);
            pStmt.setFloat(7,Float.valueOf(height));
            pStmt.setFloat(8,Float.valueOf(weight));
            pStmt.setFloat(9,bmi);
            pStmt.setFloat(10,Float.valueOf(weight));
            pStmt.setFloat(11,bmi);
            pStmt.setInt(12, getExerciseIdFromName(exCategory));
            pStmt.setDate(13, new java.sql.Date(new Date().getTime()));
            pStmt.setInt(14,100);
            pStmt.setInt(15,1);
            pStmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }
    private static void connect() throws SQLException, ClassNotFoundException {
        Class.forName(DRIVER);
        conn = DriverManager.getConnection(DB_URL + DB_NAME, USERNAME, PASSWORD);
        stmt = conn.createStatement();
    }

    public static int login(String username, String password) {
        password = md5(password);
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

     /*
        Shanea Lewis 03/04/2017
        retrieve exercise preference
    */

    public static int getExercisePrefFromID(int userid){
        try{
            ResultSet rs = stmt.executeQuery("SELECT EXPREFID FROM USER WHERE USERID='" + userid + "'");
            while(rs.next()){
                return rs.getInt("EXPREFID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public static String md5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            return Base64.encodeToString(md.digest(),Base64.DEFAULT);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getExercise(int exPrefId){
        try{
            ResultSet rs = stmt.executeQuery("SELECT EXERCISE FROM EXERCISES WHERE TYPEID ='"+exPrefId+"'");
            while(rs.next()){
                return rs.getString("EXERCISE");
                }
            }catch(SQLException e){
                e.printStackTrace();
        }
        return " ";
    }

    //NOT SURE IF OUTPUT IS TIME
    public static String getDuration(String exercise){
        try{
            ResultSet rs = stmt.executeQuery("SELECT DURATION FROM EXERCISES WHERE EXERCISE ='"+exercise+"'");
            while(rs.next()){
                return rs.getString("DURATION");
            }
        }catch(SQLException e){
                e.printStackTrace();
            }
        return " ";
    }

    public static int getHeartRateZone(String exercise){
        try{
            ResultSet rs = stmt.executeQuery("SELECT HRZONE FROM EXERCISES WHERE EXERCISE ='"+exercise+"'");
            while(rs.next()){
                return rs.getInt("HRZONE");
            }
        }catch(SQLException e){
                e.printStackTrace();
            }
        return -1;
    }

    public static String getEquipment(String exercise){
        try{
            ResultSet rs = stmt.executeQuery("SELECT EQUIPMENT FROM EXERCISES WHERE EXERCISE ='"+exercise+"'");
            while(rs.next()){
                return rs.getString("EQUIPMENT");
            }
        }catch(SQLException e){
                e.printStackTrace();
            }
        return " ";
    }

    public static Float getStartingWeight(int id) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT STARTWEIGHT FROM USER WHERE USERID ='" + id + "'");
            while (rs.next()) {
                return rs.getFloat("STARTWEIGHT");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0f;
    }

    public static Float getCurrentWeight(int id) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT CURRWEIGHT FROM USER WHERE USERID ='" + id + "'");
            while (rs.next()) {
                return rs.getFloat("CURRWEIGHT");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0f;
    }

    public static int getRank(int id) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT rank FROM USER WHERE USERID ='" + id + "'");
            while (rs.next()) {
                return rs.getInt("rank");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static String getExercisePrefAsText(int id) {
        int prefid = getExercisePrefFromID(id);
        try {
            ResultSet rs = stmt.executeQuery("SELECT TYPE FROM EXTYPE WHERE TYPEID ='" + prefid + "'");
            while (rs.next()) {
                return rs.getString("TYPE");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static JSONArray getUserExercises(int id) {
        JSONArray arr = new JSONArray();
        int prefid = getExercisePrefFromID(id);
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM EXERCISES JOIN HEARTRATEZONES WHERE EXTYPE ='" + prefid + "'AND HEARTRATEZONES.HRID = EXERCISES.HRZONE");
             while (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put("id",rs.getInt("exID"));
                obj.put("name",rs.getString("EXERCISE"));
                obj.put("duration",rs.getTime("DURATION").toString());
                obj.put("zoneMin",String.valueOf(rs.getInt("LOWERLIMIT")));
                obj.put("zoneMax",String.valueOf(rs.getInt("UPPERLIMIT")));
                obj.put("level",String.valueOf(rs.getInt("LEVEL")));
                obj.put("equipment",rs.getString("EQUIPMENT"));

                arr.put(obj);
            }
            return arr;
        } catch (SQLException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean updateUser(final String fName, final String lName, final String gender, final String email, final String pass, final String weight, final String height, String exCategory, final int id, final String oldPass,final float bmi) {
        if (!md5(oldPass).equals(getPasswordFromID(id))){
            return false;
        }
        String sql = "UPDATE USER SET emailAdd=?,password=?,firstName=?,lastName=?,gender=?,height=?,currWeight=?,currBMI=?,exPrefID=? WHERE USERID = '"+id+"'";

        try {
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1,email);
            pStmt.setString(2,md5(pass));
            pStmt.setString(3,fName);
            pStmt.setString(4,lName);
            pStmt.setString(5, String.valueOf(gender.charAt(0)));
            pStmt.setFloat(6, Float.parseFloat(height));
            pStmt.setFloat(7, Float.parseFloat(weight));
            pStmt.setFloat(8, bmi);
            pStmt.setFloat(9,getExerciseIdFromName(exCategory));
            pStmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static int getExerciseIdFromName(String exCategory) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT TYPEID FROM EXTYPE WHERE TYPE ='" + exCategory + "'");
            while (rs.next()) {
                return rs.getInt("TYPEID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static String getFNameFromID(int id) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT FIRSTNAME,LASTNAME FROM USER WHERE USERID ='" + id + "'");
            while (rs.next()) {
                return rs.getString("FIRSTNAME");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getLNameFromID(int id) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT FIRSTNAME,LASTNAME FROM USER WHERE USERID ='" + id + "'");
            while (rs.next()) {
                return rs.getString("LASTNAME");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getGenderFromID(int id) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT GENDER FROM USER WHERE USERID ='" + id + "'");
            while (rs.next()) {
                return rs.getString("GENDER");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static Float getHeightFromID(int id) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT HEIGHT FROM USER WHERE USERID ='" + id + "'");
            while (rs.next()) {
                return rs.getFloat("HEIGHT");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0f;
    }

    public static String getPasswordFromID(int id) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT PASSWORD FROM USER WHERE USERID ='" + id + "'");
            while (rs.next()) {
                return rs.getString("PASSWORD");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static ArrayList<String> getNotifByType(int i) {
        ArrayList<String>arr = new ArrayList<>();
        try {
            ResultSet rs = stmt.executeQuery("SELECT MESSAGE FROM NOTIFICATIONS WHERE ID ='" + i + "'");
            while (rs.next()) {
                arr.add(rs.getString("MESSAGE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static void saveExHistory(int userid, int exerciseId, float percentOptimal, int points) {
        String sql = "INSERT INTO USERHISTORY (userid,exid,percentOptimal,points) VALUES " +"(?,?,?,?)";
        try{
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1,userid);
            pStmt.setInt(2,exerciseId);
            pStmt.setFloat(3,percentOptimal);
            pStmt.setInt(3,points);
            pStmt.executeUpdate();
        }catch (Exception e){

        }

    }


    public static JSONArray getHistoryById(int id) {
        JSONArray arr = new JSONArray();
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM userhistory JOIN EXERCISES WHERE userid ='" + id + "'AND userhistory.exid = EXERCISES.exid");
            while (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put("name",rs.getString("EXERCISE"));
                obj.put("percent",rs.getFloat("PERCENTOPTIMAL"));
                obj.put("points",rs.getInt("POINTS"));

                arr.put(obj);
            }
            return arr;
        } catch (SQLException | JSONException e) {
            e.printStackTrace();
        }
        return null;

    }
}


