package com.unit401.pranav.muricize.beans;

import com.unit401.pranav.muricize.util.Utilities;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

/**
 * A first name plus the gender list it should be scored against.
 *
 * <p>Created by Pranav on 11-02-2017.
 */
public class Name {

    private final String name;
    private final String gender;

    public Name(String name, String gender) {
        this.name = name;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    /**
     * Title-cases the first whitespace-delimited token, using {@link Locale#US} so the result does
     * not depend on the phone's locale. Returns an empty string when there is nothing to score.
     */
    public static String normalize(String raw) {
        if (raw == null) {
            return "";
        }
        String trimmed = raw.trim();
        if (trimmed.isEmpty()) {
            return "";
        }
        String firstToken = trimmed.split("\\s+")[0];
        if (firstToken.isEmpty()) {
            return "";
        }
        String lower = firstToken.toLowerCase(Locale.US);
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }

    /**
     * Keeps candidates that share this name's first letter, scores them, and returns the highest
     * scores first. Equal scores stay in input order.
     */
    public ArrayList<PossibleNames> getAmerican(ArrayList<String> americanNames) {
        ArrayList<PossibleNames> possibilities = new ArrayList<PossibleNames>();
        if (name == null || name.isEmpty() || americanNames == null) {
            return possibilities;
        }

        for (String americanName : americanNames) {
            if (americanName == null || americanName.isEmpty()) {
                continue;
            }
            if (americanName.charAt(0) == this.name.charAt(0)) {
                int match = 1 + Utilities.vowelMatcher(this.name, americanName);
                possibilities.add(new PossibleNames(americanName, match));
            }
        }

        Collections.sort(possibilities, (n1, n2) -> Integer.compare(n2.getMatch(), n1.getMatch()));
        return possibilities;
    }
}
