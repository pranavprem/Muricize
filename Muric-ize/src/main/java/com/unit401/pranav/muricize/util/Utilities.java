package com.unit401.pranav.muricize.util;

import java.util.ArrayList;

/** Created by Pranav on 11-02-2017. */
public final class Utilities {

    private Utilities() {}

    /**
     * Splits the {@code "name,gender"} extra on the last comma. A missing message or a missing
     * comma yields empty pieces instead of throwing.
     */
    public static String[] splitRequest(String message) {
        if (message == null || message.isEmpty()) {
            return new String[] {"", ""};
        }
        int comma = message.lastIndexOf(',');
        if (comma < 0) {
            return new String[] {message, ""};
        }
        return new String[] {message.substring(0, comma), message.substring(comma + 1)};
    }

    /** Lowercase vowels only. A capital first letter is ignored, which is how names are stored. */
    public static ArrayList<Character> getVowels(String name) {
        ArrayList<Character> vowels = new ArrayList<Character>();
        if (name == null) {
            return vowels;
        }

        for (int i = 0; i < name.length(); ++i) {
            char c = name.charAt(i);
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                vowels.add(c);
            }
        }

        return vowels;
    }

    /**
     * Original vowel-overlap score. {@code j} counts American vowels examined and is not reset for
     * each input vowel, so a match only earns the position bonus when that running count equals the
     * input vowel's index. Keep this; the ranked lists depend on it.
     */
    public static int vowelMatcher(String name, String americanName) {
        int score = 0;
        ArrayList<Character> americanNameVowels = getVowels(americanName);
        ArrayList<Character> nameVowels = getVowels(name);
        int i = 0;
        int j = 0;
        for (Character c : nameVowels) {
            for (Character ch : americanNameVowels) {
                if (c.equals(ch)) {
                    score++;
                    if (i == j) {
                        score += nameVowels.size() - i;
                    }
                    ++j;
                    break;
                }
                ++j;
            }
            ++i;
        }

        return score;
    }
}
