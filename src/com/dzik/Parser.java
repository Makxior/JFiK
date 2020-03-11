package com.dzik;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    List<Skaner.Token> tokens;
    List<String> errors = new ArrayList<String>();
    public Parser(Skaner sk) {
        this.tokens = sk.tokens;
    }

    public void result() {
        if(errors.size()>0)
            System.out.println(errors);
        else
            System.out.println("Brak błędów");
    }

    private int check() {

        for(int i = 0; i < tokens.size(); i++)
        {
            if(tokens.get(i).keyword  == "id")
                System.out.println("chuj dupa cyce");

        }

        return 0;
    }


    public int start() {

        System.out.println("\nMoje rozwiaznie");

        //pozbycie sie nadmiaru spacji
        for(int i = 0; i < tokens.size()-1; i++)
        {
            if(tokens.get(i).keyword == tokens.get(i+1).keyword && tokens.get(i).keyword == "SPACE")
            {
                //System.out.println(tokens.get(i).keyword);
                tokens.remove(i+1);
                i--;
            }
        }
        for(Skaner.Token token : tokens) {
            System.out.print(token.keyword+" ");
        }
        System.out.println("\n");

        //sprawdzenie poprawnosci start i end
        if(tokens.get(0).keyword != "BLPAREN" || tokens.get(tokens.size()-1).keyword != "EOF"){
            errors.add("Brakuje \"{\" na początku programu lub EOF na końcu ");
            return 0;
        }

        //sprawdzenie liczby klamr { }
        int counter = 0;
        for(int i = 0; i < tokens.size()-1; i++){
            if(tokens.get(i).keyword == "BLPAREN")
                counter ++ ;
            if(tokens.get(i).keyword == "BRPAREN")
                counter --;
        }
        if(counter !=0){
            errors.add("Brakuje \"{\" lub \"}\"");
            return 0;
        }


        //sprawdzenie liczby klamr [ ]
        counter = 0;
        for(int i = 0; i < tokens.size()-1; i++){
            if(tokens.get(i).keyword == "SLPAREN")
                counter ++ ;
            if(tokens.get(i).keyword == "SRPAREN")
                counter --;
        }
        if(counter !=0){
            errors.add("Brakuje \"[\" lub \"]\"");
            return 0;
        }
        check();
        return 0;
    }
}

//
//"{"  "BLPAREN"
//"}" "BRPAREN"
//"["  "SLPAREN"
//"]"  "SRPAREN"
//"$schema"  "SCHEMA"
//"$id"  "ID"
//":"  "COLON"
// "  "SPACE"
//"  "  "COMMA"
//"title"  "TITLE"
//"type"  "TYPE"
//"properties"  "PROPERTIES"
//"description"  "DESCRIPTION"
//"required"  "REQUIRED"
//"minLength"  "MINLENGTH"
//"maxLength"  "MAXLENGTH"
//"minimum"  "MINIMUM"
//"maximum"  "MAXIMUM"
//"enum" "ENUM"
//"$ref"  "REF"
//"definitions"  "DEFINITIONS"
//"link"  "LINK"
//"address"  "ADDRESS"
//"string"  "STRING"
//"eof"  "EOF"
