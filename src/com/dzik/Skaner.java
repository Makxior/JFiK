package com.dzik;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.TreeMap;

public class Skaner {
    public static Map<String,String> Keyword_value = new TreeMap<>();
    public List<Token> tokens;
    public Skaner(String input) { //konstruktor i jedyna metoda
        {
        Keyword_value.put( "{", "BLPAREN");
        Keyword_value.put( "}","BRPAREN");
        Keyword_value.put( "[", "SLPAREN");
        Keyword_value.put( "]","SRPAREN");
        Keyword_value.put( "$schema","SCHEMA");
        Keyword_value.put( "$id","ID");
        Keyword_value.put( ":","COLON");
        Keyword_value.put(" ","SPACE");
        Keyword_value.put(",","COMMA");
        Keyword_value.put("title","TITLE");
        Keyword_value.put("type","TYPE");
        Keyword_value.put("properties","PROPERTIES");
        Keyword_value.put("description","DESCRIPTION");
        Keyword_value.put("required","REQUIRED");
        Keyword_value.put("minLength","MINLENGTH");
        Keyword_value.put("maxLength","MAXLENGTH");
        Keyword_value.put("minimum","MINIMUM");
        Keyword_value.put("maximum","MAXIMUM");
        Keyword_value.put("$ref","REF");
        Keyword_value.put("definitions","DEFINITIONS"); }

        tokens = lex(input); // lista tokenow ktore zwroci
    }

    public static class Token { //klasa token
        public final String keyword;
        public final String value;
        public Token( String value,String type) {
            this.keyword = type;
            this.value = value;
        }
    }

    public static List<Token> lex(String input) { //tutaj najważniejsza funkcja
        List<Token> result = new ArrayList<>();
        String temp="";
        for(int i = 0; i < input.length()-2; ) { //zwiekszamy w zaleznosci od dlugosci odczytanego tokena
            temp="";
           if(input.charAt(i) == '{' || input.charAt(i) == '}' || input.charAt(i) == '[' || input.charAt(i) ==']' ||input.charAt(i) ==' ' || input.charAt(i) ==':'|| input.charAt(i)==','){
                result.add(new Token(Character.toString(input.charAt(i)),Keyword_value.get(Character.toString(input.charAt(i)))));
                i++;
            }
           else if(input.charAt(i) == '\n'){
               i++;
           }
           else if (Character.isDigit(input.charAt(i))){
               result.add(new Token(Character.toString(input.charAt(i)), "NUMBER"));
               i++;
            }
           else if(input.charAt(i) == '"'){
               while(input.charAt(i+1) != '"'){
                   temp +=input.charAt(i+1);
                   i++;
               }
               i+=2;
               if(Keyword_value.get(temp) != null)
               {
                   result.add(new Token(temp, Keyword_value.get(temp)));
               }
               else{
                   if(temp.matches("^(https?|ftp)://.*$")){ //tutaj mozna te regexy ulepszyc
                       result.add(new Token(temp, "LINK"));
                   }
                   else if(temp.matches("#/.*$"))
                   {
                       result.add(new Token(temp, "ADDRESS"));
                   }
                   else{
                       result.add(new Token(temp, "STRING"));
                   }
               }
           }
           else{
               System.out.println("Error in reading input in Skaner");
               break;
           }

        }
        result.add(new Token("eof", "EOF"));
        return result;
    }
}