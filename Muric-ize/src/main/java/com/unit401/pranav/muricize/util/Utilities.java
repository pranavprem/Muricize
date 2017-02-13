package com.unit401.pranav.muricize.util;

/**
 * Created by Pranav on 11-02-2017.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Utilities {


    public static ArrayList<Character> getVowels(String name){
        ArrayList<Character> vowels = new ArrayList<Character>();

        for(int i=0;i<name.length();++i){
            if(name.charAt(i)=='a' || name.charAt(i)=='e' || name.charAt(i)=='i' || name.charAt(i)=='o' || name.charAt(i)=='u'){
                vowels.add(name.charAt(i));
            }
        }

        return vowels;
    }

    public static Integer VowelMatcher(String name, String americanName){
        Integer score=0;
        ArrayList<Character>americanNameVowels=getVowels(americanName);
        ArrayList<Character>nameVowels=getVowels(name);
        int i=0,j=0;
        for(Character c: nameVowels){
            for(Character ch:americanNameVowels){
                if(c.equals(ch)){
                    score++;
                    if(i==j){
                        score+=nameVowels.size()-i;
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

