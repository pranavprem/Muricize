package com.unit401.pranav.muricize;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class NameFinder extends AppCompatActivity {

    public static String EXTRA_MESSAGE="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_name_finder);
    }

    public void sendMessage(View view) {
        RadioGroup radioGenderGroup = (RadioGroup) findViewById(R.id.radioGender);
        Intent intent = new Intent(this, DisplayResult.class);
        EditText editText = (EditText) findViewById(R.id.nameEditText);
        RadioButton genderSelected = (RadioButton) findViewById(radioGenderGroup.getCheckedRadioButtonId());
        String message = editText.getText().toString() + "," +genderSelected.getText();
        intent.putExtra(EXTRA_MESSAGE,message);


        startActivity(intent);

    }
}
