package com.unit401.pranav.muricize;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.unit401.pranav.muricize.beans.Name;
import com.unit401.pranav.muricize.beans.PossibleNames;
import com.unit401.pranav.muricize.util.QuoteBank;

import org.w3c.dom.Text;

import java.util.ArrayList;



public class DisplayResult extends AppCompatActivity {

    private QuoteBank mQuoteBank;
    private ArrayList<String> americanNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_display_result);
        mQuoteBank = new QuoteBank(this);



        Intent intent = getIntent();
        ListView listView = (ListView) findViewById(R.id.resultListView);
        ArrayList<String> result= new ArrayList<String>();
        String givenName=intent.getStringExtra(NameFinder.EXTRA_MESSAGE).split(",")[0].split(" ")[0];
        String givenGender=intent.getStringExtra(NameFinder.EXTRA_MESSAGE).split(",")[1];

        americanNames = mQuoteBank.readLine(givenGender);
        givenName = java.lang.Character.toUpperCase(givenName.charAt(0))+givenName.substring(1).toLowerCase();

        Name name = new Name(givenName,givenGender);
        for(PossibleNames possibleName:name.getAmerican(americanNames)){
            result.add(possibleName.toString());
        }

        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.activity_list_view,result.toArray(new String[result.size()])));
    }
}
