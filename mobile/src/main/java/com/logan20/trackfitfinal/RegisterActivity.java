package com.logan20.trackfitfinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.logan20.trackfitcommon.DatabaseHandler;
import java.util.Calendar;

/*This activity makes th registration of a user possible.*/
public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    //This function will execure when the submit button is clicked
    public void submit(View view) {
        //Variables that contain the user entered information
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
        int age = getAge(day,month,year);

        //Data validation
        if (fName.isEmpty() | lName.isEmpty() || gender.isEmpty() || email.isEmpty() || pass.isEmpty() || passConf.isEmpty() || weight.isEmpty() || height.isEmpty() ||exCategory.isEmpty()){
            makeToast("Enter all fields!");
            return;
        }
        //Email address validation
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            makeToast("Enter a valid email address!");
            return;
        }
        //Password matches validation
        if (!pass.equals(passConf)){
            makeToast("Passwords do not match!");
            return;
        }
        //Age validation
        if (age<0 || age > 130){
            makeToast("Enter a valid age");
            return;
        }

        //Data presentation
        fName=titleCase(fName);
        lName=titleCase(lName);
        if (DatabaseHandler.createUser(fName,lName,gender,email,pass,weight,height,exCategory,day,month,year)){
            makeToast("Successfully registered! Please log in");
            finish();
        }else{
            makeToast("An error has occured, please try again later");
        }
    }

    private String titleCase(String fName) {
        return ((String.valueOf(fName.charAt(0))).toUpperCase()+fName.substring(1));
    }

    //Calclates the age of a user based on the parameters entered and the current date
    private int getAge(int day, int month, int year) {
        Calendar now = Calendar.getInstance();
        int age = now.get(Calendar.YEAR) - year;
        if (month > now.get(Calendar.MONTH) || (month == now.get(Calendar.MONTH) && now.get(Calendar.DAY_OF_MONTH) > day)){
            age--;
        }
        return age;
    }

    private void makeToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    //Gets the type of exercise a user wants
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

    //Gets the gender of the user
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
