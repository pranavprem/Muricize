package com.unit401.pranav.muricize.beans;

/**
 * Created by Pranav on 11-02-2017.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import com.unit401.pranav.muricize.util.Utilities;

public class Name {

    String name;
    String gender;

    public Name(String name, String gender){
        this.name=name;
        this.gender=gender;
    }

    public ArrayList<PossibleNames> getAmerican(ArrayList<String> americanNames){

        ArrayList<PossibleNames>possibilities = new ArrayList<PossibleNames>();
        Integer match;

        for(String americanName: americanNames){
            if(americanName.charAt(0)==this.name.charAt(0))
            {
                match=1;
                match+=Utilities.VowelMatcher(this.name, americanName);
                possibilities.add(new PossibleNames(americanName,match));
            }
        }

        Collections.sort(possibilities, new Comparator<PossibleNames>() {
            @Override
            public int compare(PossibleNames n1, PossibleNames n2){
                return n2.match-n1.match;
            }
        });
        return possibilities;


    }

}

