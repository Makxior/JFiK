package com.dzik;

import java.util.List;

public class Parser {
    List<Skaner.Token> tokens;
    public Parser(Skaner sk) {
        this.tokens = sk.tokens;
    }

    public void start() {
        for(Skaner.Token token : tokens) {
            System.out.print(token.keyword+" ");
        }
    }
}

//możliwe tokeny Keyword_value.put( "{", "BLPAREN");
//        Keyword_value.put( "}","BRPAREN");
//        Keyword_value.put( "[", "SLPAREN");
//        Keyword_value.put( "]","SRPAREN");
//        Keyword_value.put( "$schema","SCHEMA");
//        Keyword_value.put( "$id","ID");
//        Keyword_value.put( ":","COLON");
//        Keyword_value.put(" ","SPACE");
//        Keyword_value.put(",","COMMA");
//        Keyword_value.put("title","TITLE");
//        Keyword_value.put("type","TYPE");
//        Keyword_value.put("properties","PROPERTIES");
//        Keyword_value.put("description","DESCRIPTION");
//        Keyword_value.put("required","REQUIRED");
//        Keyword_value.put("minLength","MINLENGTH");
//        Keyword_value.put("maxLength","MAXLENGTH");
//        Keyword_value.put("minimum","MINIMUM");
//        Keyword_value.put("maximum","MAXIMUM");
//        Keyword_value.put("$ref","REF");
//        Keyword_value.put("definitions","DEFINITIONS");
//        Keyword_value.put("link","LINK");
//        Keyword_value.put("address","ADDRESS");
//        Keyword_value.put("string","STRING");
//        Keyword_value.put("eof","EOF"); // to jest ostatni token
