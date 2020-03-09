package com.dzik;

import java.util.List;
import java.util.ArrayList;

public class Skaner {
    public Skaner(String input) { //konstruktor i jedyna metoda
        List<Token> tokens = lex(input); // lista tokenow ktore zwroci
        for(Token t : tokens) {
            System.out.println(t); //tutaj na razie wyswietlanie
        }
    }
    String[] Value_specification = {"link","string","number","stringTable","ref"}; //mozliwe wartoscsi ktore przyjmuje value, inne to błąd
    public enum KEYWORD { //możliwe nazwy tokenow
        LPAREN, RPAREN, COLON, COMMA,SPACE, ATOM, ID﻿, SCHEMA﻿, TITLE, TYPE,  PROPERTIES, DESCRIPTION ,REQUIRED, MINIMUM,  MAXIMUM, MINLENGTH, MAXLENGTH, ENUM, DEFINITIONS, REF﻿
    }
    public static class Token { //klasa token
        public final KEYWORD keyword;
        public final String value;
        public Token(KEYWORD type, String value) {
            this.keyword = type;
            this.value = value;
        }
        public String toString() { //jesli token to wartosc(value) a nie keyword
            if(keyword == KEYWORD.ATOM) {
                return "V<" + value + ">";
            }
            return keyword.toString();
        }
    }

    public static String getAtom(String s, int i) { //tutaj wartosc value nie do konca ogarniam po co
        int j = i;
        for( ; j < s.length(); ) {
            if(Character.isLetter(s.charAt(j))) {
                j++;
            } else {
                return s.substring(i, j);
            }
        }
        return s.substring(i, j);
    }

    public static List<Token> lex(String input) { //tutaj najważniejsza funkcja
        List<Token> result = new ArrayList<Token>();
        for(int i = 0; i < input.length(); ) { //zwiekszamy w zaleznosci od dlugosci odczytanego tokena
            if(input.charAt(i) == 0) //tutaj testujemy czy to nie jest

        }
        return result;
    }
}
/*            switch(input.charAt(i)) {
                case '(':
                    result.add(new Token(Type.LPAREN, "("));
                    i++;
                    break;
                case ')':
                    result.add(new Token(Type.RPAREN, ")"));
                    i++;
                    break;
                default:
                    if(Character.isWhitespace(input.charAt(i))) {
                        i++;
                    } else {
                        String atom = getAtom(input, i);
                        i += atom.length();
                        result.add(new Token(Type.ATOM, atom));
                    }
                    break;
            }

 */