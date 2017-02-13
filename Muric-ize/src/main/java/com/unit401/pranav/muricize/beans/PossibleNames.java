package com.unit401.pranav.muricize.beans;

/**
 * Created by Pranav on 11-02-2017.
 */

public class PossibleNames {

    String possibility;
    Integer match;


    public PossibleNames(String americanName, Integer match2) {
        // TODO Auto-generated constructor stub
        this.possibility=americanName;
        this.match=match2;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return this.possibility+"  (Score= "+this.match+")";
    }


}
