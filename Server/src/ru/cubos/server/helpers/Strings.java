package ru.cubos.server.helpers;

import ru.cubos.server.Server;

import java.util.ArrayList;
import java.util.List;

public class Strings {
    static public int getStringWidth(Server server, String string, int fontSize){
        return server.settings.getSystemCharWidth() * string.length() * fontSize;
    }

    static public List<String> splitTextOnWords(String string){

        List<String> result = new ArrayList<>();
        int position = 0;
        String word = "";
        while (position < string.length())
        {
            word += string.charAt(position);
            if(
                string.charAt(position)==' '
                || string.charAt(position)==','
                || string.charAt(position)=='.'
                || string.charAt(position)==':'
                || string.charAt(position)==';'
                || string.charAt(position)=='!'
                || string.charAt(position)=='?'
                || string.charAt(position)=='-'
                || string.charAt(position)=='\n'
            ){
                result.add(word);
                word="";
            }

            position++;
        }
        if(!word.equals("")) result.add(word);
        return result;
    }

    static public List<String> createStrings(Server server, List<String> words, int fontSize, int maxWidth){
        List<String> result = new ArrayList<>();
        String string = "";
        for(int i=0; i<words.size()+1; i++){
            if ( i==words.size() || getStringWidth(server, string + words.get(i), fontSize)> maxWidth){
                // String is ready

                if(getStringWidth(server, string, fontSize)>maxWidth) {
                    String new_string = "";
                    for(int x=0; x<string.length() ; x++){
                        int string_size = Strings.getStringWidth(server, new_string + string.charAt(x), fontSize);
                        if(string_size>maxWidth){
                            result.add("" + new_string.trim());
                            new_string = "";
                        }
                        new_string += string.charAt(x);
                    }
                    string = new_string;
                    if(i==words.size()) result.add("" + new_string.trim());
                }else{
                    if(!string.equals("")) result.add("" + string.trim());
                    string = "";
                }
            }

            if(i!=words.size())string += words.get(i);
        }
        //result.add(string);

        return result;
    }
}
