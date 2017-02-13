package com.unit401.pranav.muricize.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pranav on 11-02-2017.
 */

public class QuoteBank {
    private Context mContext;

    public QuoteBank(Context context) {
        this.mContext = context;
    }

    public ArrayList<String> readLine(String path) {
        ArrayList<String> mLines = new ArrayList<String>();

        AssetManager am = mContext.getAssets();

        try {
            InputStream is = am.open(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = reader.readLine()) != null)
                mLines.add(line);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return mLines;
    }
}
