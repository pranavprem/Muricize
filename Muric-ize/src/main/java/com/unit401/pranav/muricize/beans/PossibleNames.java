package com.unit401.pranav.muricize.beans;

/** Created by Pranav on 11-02-2017. */
public class PossibleNames {

    private final String possibility;
    private final int match;

    public PossibleNames(String americanName, int match) {
        this.possibility = americanName;
        this.match = match;
    }

    public String getPossibility() {
        return possibility;
    }

    public int getMatch() {
        return match;
    }

    @Override
    public String toString() {
        return this.possibility + "  (Score= " + this.match + ")";
    }
}
