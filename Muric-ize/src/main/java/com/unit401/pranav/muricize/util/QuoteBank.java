package com.unit401.pranav.muricize.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Reads one gender's name list from assets. The asset file name is the gender key ({@code Male} or
 * {@code Female}), not a quote bank. The class name is the original one.
 *
 * <p>Created by Pranav on 11-02-2017.
 */
public class QuoteBank {
    private static final String TAG = "QuoteBank";

    private final Context context;

    public QuoteBank(Context context) {
        this.context = context;
    }

    public ArrayList<String> readLine(String path) {
        ArrayList<String> lines = new ArrayList<String>();
        if (path == null || path.isEmpty()) {
            return lines;
        }

        AssetManager assets = context.getAssets();
        try (InputStream input = assets.open(path);
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            Log.e(TAG, "Could not read asset " + path, e);
        }

        return lines;
    }
}
