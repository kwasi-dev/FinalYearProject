package com.logan20.trackfit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.logan20.trackfit.database.DatabaseHandler;

import java.sql.SQLException;

public class RegisterActivity extends AppCompatActivity {
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        try {
            DatabaseHandler.init();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void submit(View view) {
        String fName = ((TextView)findViewById(R.id.et_fName)).getText().toString();
        String lName = ((TextView)findViewById(R.id.et_lName)).getText().toString();
        String gender = getGender();
        String email = ((TextView)findViewById(R.id.et_email)).getText().toString();
        String pass = ((TextView)findViewById(R.id.et_password)).getText().toString();
        String passConf = ((TextView)findViewById(R.id.et_passwordConfirm)).getText().toString();
        String weight = ((TextView)findViewById(R.id.et_weight)).getText().toString();
        String  height = ((TextView)findViewById(R.id.et_height)).getText().toString();
        String exCategory=getExerciseCategory();
        DatePicker dp = ((DatePicker)findViewById(R.id.dp_dateOfBirth));
        int day = dp.getDayOfMonth();
        int month = dp.getMonth();
        int year = dp.getYear();
      
        if (fName.isEmpty() | lName.isEmpty() || gender.isEmpty() || email.isEmpty() || pass.isEmpty() || passConf.isEmpty() || weight.isEmpty() || height.isEmpty() ||exCategory.isEmpty()){
            makeToast("Enter all fields!");
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            makeToast("Enter a valid email address!");
            return;
        }
        if (!pass.equals(passConf)){
            makeToast("Passwords do not match!");
            return;
        }
        if (DatabaseHandler.createUser(fName,lName,gender,email,pass,weight,height,exCategory,day,month,year)){
            makeToast("Successfully registered!");
        }else{
            makeToast("An error has occured, please try again later");
        }
    }
//     need to include current date grab to add date profile created and get current year 
//         public int age(int year){
//         DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//         LocalDate localDate = LocalDate.now();
//         return localDate.getYear()-year;
//          }
    private void makeToast(String str) {
        if (toast!=null){
            toast.cancel();
        }
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public String getExerciseCategory() {
        RadioGroup rg = ((RadioGroup)findViewById(R.id.rg_exerciseType));
        int id = rg.getCheckedRadioButtonId();
        if (id!=-1){
            View radioButton = rg.findViewById(id);
            int radioId = rg.indexOfChild(radioButton);
            RadioButton btn = (RadioButton) rg.getChildAt(radioId);
             return (String) btn.getText();
        }
        return "";
    }

    public String getGender() {
        RadioGroup rg = ((RadioGroup)findViewById(R.id.rg_gender));
        int id = rg.getCheckedRadioButtonId();
        if (id!=-1){
            View radioButton = rg.findViewById(id);
            int radioId = rg.indexOfChild(radioButton);
            RadioButton btn = (RadioButton) rg.getChildAt(radioId);
            return (String) btn.getText();
        }
        return "";
    }
}
