package com.logan20.trackfitfinal;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.logan20.trackfitcommon.DatabaseHandler;

import java.util.Calendar;

/**
 * Created by kwasi on 4/10/2017.
 */

public class ProfileFragment extends Fragment {
    private int id;
    private String fName;
    private String lName;
    private String email;
    private String gender;
    private String age;
    private String height;
    private String expref;
    private View v;
    private String pass;
    private DatePicker dp;
    private int exCategory;
    private float weight;
    private String passConf;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_my_profile,null);
        ((TextView)v.findViewById(R.id.et_fName)).setText(fName);
        ((TextView)v.findViewById(R.id.et_lName)).setText(lName);
        if (!gender.equals("M")){
            ((RadioButton)v.findViewById(R.id.rb_female)).setChecked(true);
        }
        else {
            ((RadioButton)v.findViewById(R.id.rb_male)).setChecked(true);

        }
        ((TextView)v.findViewById(R.id.et_email)).setText(email);
        ((TextView)v.findViewById(R.id.et_weight)).setText(String.valueOf(weight));
        ((TextView)v.findViewById(R.id.et_height)).setText(height);
        setExerciseCategory(exCategory);


        v.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Variables that contain the user entered information
                String fName = ((TextView)v.findViewById(R.id.et_fName)).getText().toString();
                String lName = ((TextView)v.findViewById(R.id.et_lName)).getText().toString();
                final String gender = getGender();
                final String email = ((TextView)v.findViewById(R.id.et_email)).getText().toString();
                final String pass = ((TextView)v.findViewById(R.id.et_password)).getText().toString();
                String passConf = ((TextView)v.findViewById(R.id.et_passwordConfirm)).getText().toString();
                final String weight = ((TextView)v.findViewById(R.id.et_weight)).getText().toString();
                final String  height = ((TextView)v.findViewById(R.id.et_height)).getText().toString();
                final String  oldPass = ((TextView)v.findViewById(R.id.et_oldPass)).getText().toString();
                final String exCategory=getExerciseCategory();
                final float bmi = getBMI(height,weight);

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

                //Data presentation
                fName=titleCase(fName);
                lName=titleCase(lName);
                final String finalFName = fName;
                final String finalLName = lName;
                new AsyncTask<Void, Void, Boolean>() {

                    @Override
                    protected Boolean doInBackground(Void... voids) {
                        return DatabaseHandler.updateUser(finalFName, finalLName,gender,email,pass,weight,height,exCategory,id,oldPass,bmi);
                    }

                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        if (aBoolean){
                            makeToast("Successfully updated!");
                        }else{
                            makeToast("Error, enter correct password");
                        }
                    }
                }.execute();

            }
        });
        return v;
    }

    public void setId(final int id) {
        this.id = id;
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                fName = DatabaseHandler.getFNameFromID(id);
                lName = DatabaseHandler.getLNameFromID(id);
                gender = DatabaseHandler.getGenderFromID(id);
                email = DatabaseHandler.getEmailFromID(id);
                weight = DatabaseHandler.getCurrentWeight(id);
                height = String.valueOf(DatabaseHandler.getHeightFromID(id));
                exCategory=DatabaseHandler.getExercisePrefFromID(id);
                return null;
            }
        }.execute();

    }

    private String titleCase(String fName) {
        return ((String.valueOf(fName.charAt(0))).toUpperCase()+fName.substring(1));
    }

    private void makeToast(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }

    //Gets the type of exercise a user wants
    public String getExerciseCategory() {
        RadioGroup rg = ((RadioGroup)v.findViewById(R.id.rg_exerciseType));
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
        RadioGroup rg = ((RadioGroup)v.findViewById(R.id.rg_gender));
        int id = rg.getCheckedRadioButtonId();
        if (id!=-1){
            View radioButton = rg.findViewById(id);
            int radioId = rg.indexOfChild(radioButton);
            RadioButton btn = (RadioButton) rg.getChildAt(radioId);
            return (String) btn.getText();
        }
        return "";
    }

    public void setExerciseCategory(int exerciseCategory) {
        switch (exerciseCategory){
            case 1:
                ((RadioButton)v.findViewById(R.id.rb_endurance)).setChecked(true);
                break;
            case 2:
                ((RadioButton)v.findViewById(R.id.rb_strength)).setChecked(true);
                break;
            case 3:
                ((RadioButton)v.findViewById(R.id.rb_balance)).setChecked(true);
                break;
            case 4:
                ((RadioButton)v.findViewById(R.id.rb_flexibility)).setChecked(true);
                break;
        }
    }

    private float getBMI(String height, String weight) {
        float h = Float.parseFloat(height);
        float w = Float.parseFloat(weight);
        return (float) ((w*0.45)/Math.pow(h/100,2));
    }

}
