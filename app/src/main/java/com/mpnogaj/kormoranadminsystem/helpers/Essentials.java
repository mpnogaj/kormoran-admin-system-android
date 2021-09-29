package com.mpnogaj.kormoranadminsystem.helpers;

import android.util.Pair;

import com.mpnogaj.kormoranadminsystem.R;

public class Essentials {
    public static Pair<Integer, Integer> getColorsByState(String state){
        if (state.equals("ready-to-play")) {
            return new Pair<>(R.color.yellow, R.color.black);
        } else if (state.equals("active")) {
            return new Pair<>(R.color.green, R.color.white);
        }
        return new Pair<>(R.color.red, R.color.white);
    }

    public static String getStateReadableText(String state){
        if (state.equals("ready-to-play")) {
            return "Do rozpoczęcia";
        } else if (state.equals("active")) {
            return "Trwa";
        }
        return "Zakończony";
    }
}
