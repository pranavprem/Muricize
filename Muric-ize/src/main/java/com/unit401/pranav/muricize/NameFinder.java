package com.unit401.pranav.muricize;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.unit401.pranav.muricize.beans.Name;

public class NameFinder extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.unit401.pranav.muricize.EXTRA_MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_finder);
    }

    public void sendMessage(View view) {
        RadioGroup radioGenderGroup = findViewById(R.id.radioGender);
        EditText editText = findViewById(R.id.nameEditText);
        int selectedId = radioGenderGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, R.string.choose_gender, Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton genderSelected = findViewById(selectedId);
        Object genderTag = genderSelected.getTag();
        if (genderTag == null) {
            Toast.makeText(this, R.string.choose_gender, Toast.LENGTH_SHORT).show();
            return;
        }

        String normalized = Name.normalize(editText.getText().toString());
        if (normalized.isEmpty()) {
            Toast.makeText(this, R.string.enter_first_name, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, DisplayResult.class);
        intent.putExtra(EXTRA_MESSAGE, normalized + "," + genderTag);
        startActivity(intent);
    }
}
