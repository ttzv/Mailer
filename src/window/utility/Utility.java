package window.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Utility {

    public static String reformatString(String input){
        String[] polArrFind = {"ą", "ć", "ę", "ż", "ń", "ó", "ł", "ź", "ś"};//, "Ą", "Ę", "Ć", "Ż", "Ł", "Ś", "Ź"};
        String[] replArr = {"a", "c", "e", "z", "n", "o", "l", "z", "s"};//, "A", "E", "C", "Z", "L", "S", "Z"};

        ArrayList<String> polList = new ArrayList<>(Arrays.asList(polArrFind));
        ArrayList<String> npolList = new ArrayList<>(Arrays.asList(replArr));

        String reformatted = input.trim().replace(" ", ".").toLowerCase();
       // System.out.println(reformatted);

        for(String c : polArrFind){
            if(reformatted.contains(c)){
                reformatted = reformatted.replace(c, npolList.get(polList.indexOf(c)));
                //System.out.println("replaced: " + c + " with " + npolList.get(polList.indexOf(c)) + " word is: " + reformatted);
            }
        }
        return reformatted;
    }

    public static ArrayList<String> stringToArray(String string){
        return new ArrayList<>(Arrays.asList(string.substring(1, string.length() - 1).replaceAll("\\s", "").split(",")));
    }
}