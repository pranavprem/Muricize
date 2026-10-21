package com.unit401.pranav.muricize;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.unit401.pranav.muricize.beans.Name;
import com.unit401.pranav.muricize.beans.PossibleNames;
import com.unit401.pranav.muricize.util.QuoteBank;
import com.unit401.pranav.muricize.util.Utilities;
import java.util.ArrayList;

public class DisplayResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_result);

        Intent intent = getIntent();
        ListView listView = findViewById(R.id.resultListView);
        String[] request = Utilities.splitRequest(intent.getStringExtra(NameFinder.EXTRA_MESSAGE));
        String givenName = Name.normalize(request[0]);
        String givenGender = request[1] == null ? "" : request[1];

        ArrayList<String> result = new ArrayList<String>();
        if (givenName.isEmpty() || givenGender.isEmpty()) {
            result.add(getString(R.string.no_name));
        } else {
            QuoteBank quoteBank = new QuoteBank(this);
            Name name = new Name(givenName, givenGender);
            for (PossibleNames possibleName : name.getAmerican(quoteBank.readLine(givenGender))) {
                result.add(possibleName.toString());
            }
            if (result.isEmpty()) {
                result.add(getString(R.string.no_matches));
            }
        }

        listView.setAdapter(
                new ArrayAdapter<String>(
                        this, R.layout.activity_list_view, result.toArray(new String[0])));
    }
}
