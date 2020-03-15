package com.dzik;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    List<Skaner.Token> tokens;
    List<String> errors = new ArrayList<String>();
    List<String> elementsList = new ArrayList<>();
    List<String> propList = new ArrayList<>();
    public Parser(Skaner sk) {
        this.tokens = sk.tokens;
    }

    public void result() {
        if(errors.size()>0)
            System.out.println(errors);
        else
            System.out.println("Brak błędów");
    }

    private int idCheck(int i){
        if(tokens.get(i+1).keyword.equals("COLON")){
            i++;
            if(tokens.get(i+1).keyword.equals("SPACE")){
                i++;
                if(tokens.get(i+1).keyword.equals("LINK")){
                    i++;
                    if(elementsList.contains(tokens.get(i+2).keyword)){
                        if(tokens.get(i+1).keyword == "COMMA"){
                            i++;
                            return i;

                        }
                        else{
                            System.out.println("idCheck error");
                            errors.add("Error w ID check");
                        }

                    }
                    else return i;

                }
            }
        }
        System.out.println("idCheck error");
        errors.add("Error w ID check");
        return tokens.size()+1;
    }
    private int schemaCheck(int i){
            if(tokens.get(i+1).keyword.equals("COLON")){
                i++;
                if(tokens.get(i+1).keyword.equals("SPACE")){
                    i++;
                    if(tokens.get(i+1).keyword.equals("LINK")){
                        i++;
                        if(elementsList.contains(tokens.get(i+2).keyword)){
                            if(tokens.get(i+1).keyword.equals("COMMA")){
                                i++;
                                return i;
                            }
                        }
                        else return i;

                    }
                    else{
                        System.out.println("schemaCheck error");
                        errors.add("Error w SCHEMA check");
                    }

                }
            }

        System.out.println("schemaCheck error");
        errors.add("Error w SCHEMA check");
        return tokens.size()+1;
    }
    private int titleCheck(int i){
        if(tokens.get(i+1).keyword.equals("COLON")){
            i++;
            if(tokens.get(i+1).keyword.equals("SPACE")){
                i++;
                if(tokens.get(i+1).keyword.equals("STRING")){
                    i++;
                    if(elementsList.contains(tokens.get(i+2).keyword)) {
                        if (tokens.get(i + 1).keyword.equals("COMMA")) {
                            i++;
                            return i;
                        }
                    }else return i;

                }
                else{
                    System.out.println("titleCheck error");
                    errors.add("Error w TITLE check");
                }

            }
        }

        System.out.println("titleCheck error");
        errors.add("Error w TITLE check");
        return tokens.size()+1;
    }

    private int reqDeepCheck(int i){
        if(tokens.get(i+1).keyword.equals("COMMA")){
            i++;
            if(tokens.get(i+1).keyword.equals("SPACE")){
                i++;
                if(tokens.get(i+1).keyword.equals("STRING")){
                    i++;
                    if(tokens.get(i+1).keyword.equals("SRPAREN")){
                        i++;
                        return --i;
                    }

                    else if(tokens.get(i+1).keyword.equals("COMMA")){
                        i = reqDeepCheck(i);
                        return i;
                    }

                }
            }
        }
        System.out.println("reqDeepCheck error");
        errors.add("Error w REQUIRED deep check");

        return tokens.size()+1;
    }

    private int reqCheck(int i){
        String temp = tokens.get(i+1).keyword;
        if(tokens.get(i+1).keyword.equals("COLON")){
            i++;
            temp = tokens.get(i+1).keyword;
            if(tokens.get(i+1).keyword.equals("SPACE")){
                i++;
                temp = tokens.get(i+1).keyword;
                if(tokens.get(i+1).keyword.equals("SLPAREN")){
                    i++;
                    temp = tokens.get(i+1).keyword;
                    if(tokens.get(i+1).keyword.equals("STRING")){
                        i++;
                        temp = tokens.get(i+1).keyword;
                        if(tokens.get(i+1).keyword.equals("SRPAREN")){
                            i++;
                            return i;
                        }
                        //rekurencja - sprawdzanie poprawnosci COMMA->SPACE->STRING wiele razy
                        else if(tokens.get(i+1).keyword.equals("COMMA")){
                                      i = reqDeepCheck(i);
                                      if(tokens.get(i+1).keyword.equals("SRPAREN")){
                                          i++;

                                          if(elementsList.contains(tokens.get(i+2).keyword)){
                                              if(tokens.get(i+1).keyword.equals("COMMA")){
                                                  i++;
                                                  return i;

                                              }
                                              else{
                                                  System.out.println("reqCheck1 error");
                                                  errors.add("Error w REQUIRED check");
                                              }


                                          }
                                      }
                        }
                    }
                }
                else{
                    System.out.println("reqCheck2 error");
                    errors.add("Error w REQUIRED check");
                }

            }
        }

        System.out.println("reqCheck3 error");
        errors.add("Error w REQUIRED check");
        return tokens.size()+1;
    }

    private int typeCheck(int i){
        if(tokens.get(i+1).keyword.equals("COLON")){
            i++;
            if(tokens.get(i+1).keyword.equals("SPACE")){
                i++;
                if(tokens.get(i+1).keyword.equals("STRING")){
                    i++;
                    if(elementsList.contains(tokens.get(i+2).keyword)){
                        if(tokens.get(i+1).keyword.equals("COMMA")){
                            i++;
                            return i;
                        }
                        else{
                            System.out.println("typeCheck error");
                            errors.add("Error w TYPE check");
                        }

                    }else{
                        return i;
                    }

                }
            }
        }
        System.out.println("typeCheck error");
        errors.add("Error w TYPE check");
        return tokens.size()+1;

    }

    private int defCheck(int i){
        if(tokens.get(i+1).keyword.equals("COLON")){
            i++;
            if(tokens.get(i+1).keyword.equals("SPACE")){
                i++;
                if(tokens.get(i+1).keyword.equals("BLPAREN")){
                    i++;
                    if(tokens.get(i+1).keyword.equals("STRING")){
                        i++;
                        if(tokens.get(i+1).keyword.equals("COLON")){
                            i++;
                            if(tokens.get(i+1).keyword.equals("SPACE")){
                                i++;
                                if(tokens.get(i+1).keyword.equals("BLPAREN")){
                                    i++;
                                    if(tokens.get(i+1).keyword.equals("TYPE") || tokens.get(i+1).keyword.equals("PROPERTIES")){
                                        i = defDeepCheck(i);
                                        if(tokens.get(i+1).keyword.equals("BRPAREN")){
                                            i++;
                                            if(elementsList.contains(tokens.get(i+2).keyword)){
                                                if(tokens.get(i+1).keyword.equals("COMMA")){
                                                    i++;
                                                    return i;

                                                }
                                                else{
                                                    System.out.println("defCheck error");
                                                    errors.add("Error w DEFINITION check");
                                                }

                                            }
                                            else return i;

                                        }

                                    }


                                }

                            }
                        }
                    }

                }
            }
        }
        System.out.println("defCheck error");
        errors.add("Error w DEFINITION check");
        return tokens.size()+1;

    }
    private int defDeepCheck(int i){
        if(tokens.get(i+1).keyword.equals("TYPE")){
            i++;
            if(tokens.get(i+1).keyword.equals("COLON")){
                i++;
                if(tokens.get(i+1).keyword.equals("SPACE")){
                    i++;
                    if(tokens.get(i+1).keyword.equals("STRING")){
                        i++;
                        return i;
                    }
                }
            }

        }else{
            i++;
            propCheck(i);

        }
        errors.add("Error w DEFINITION deep check");
        return tokens.size()+1;
    }

    private int propCheck(int i){

            if(tokens.get(i+1).keyword.equals("COLON")){
                i++;
                if(tokens.get(i+1).keyword.equals("SPACE")){
                    i++;
                    if(tokens.get(i+1).keyword.equals("BLPAREN")){
                        i++;
                        if(tokens.get(i+1).keyword.equals("STRING")){
                            i++;
                            if(tokens.get(i+1).keyword.equals("COLON")){
                                i++;
                                if(tokens.get(i+1).keyword.equals("SPACE")){
                                    i++;
                                    if(tokens.get(i+1).keyword.equals("BLPAREN")) {
                                        i++;
                                        i = propMultiChoice(i);
                                        if (tokens.get(i + 1).keyword.equals("BRPAREN")) {
                                            i++;
                                            if (elementsList.contains(tokens.get(i + 2).keyword)) {
                                                if (tokens.get(i + 1).keyword.equals("COMMA")) {
                                                    i++;
                                                    return i;
                                                }
                                            }


                                        } else if (!tokens.get(i + 1).keyword.equals("BRPAREN")) {
                                            return i;
                                        }
                                    }
                                }

                            }
                            }
                    }
                }
            }

        System.out.println("propCheck error");
        errors.add("Error w PROPERTIES check");
        return tokens.size()+1;

    }

    private int propMultiChoice(int i){

        switch(tokens.get(i+1).keyword)
        {
            case "TYPE": i = propType(i+1);return i;
            case "DESCRIPTION": i = propDesc(i);return i;
            case "MINIMUM": i = propMin(i);return i;
            case "MAXIMUM": i = propMax(i);return i;
            case "MINLENGTH": i = propMinLen(i);return i;
            case "MAXLENGTH": i = propMaxLen(i);return i;
            case "ENUM": i = propEnum(i);return i;
            case "REF": i = propRef(i);return i;
            default: break;

        }

        return tokens.size()+1;
    }

    private int propType(int i){
        if(tokens.get(i+1).keyword.equals("COLON")) {
            i++;
            if(tokens.get(i+1).keyword.equals("SPACE")){
                i++;
                if(tokens.get(i+1).keyword.equals("STRING")){
                    i++;
                    if (propList.contains(tokens.get(i + 2).keyword)) {
                        if(tokens.get(i+1).keyword.equals("COMMA")){
                            i++;
                            return i;
                        }
                    }else {
                        return i;
                    }

                }
            }
        }
        errors.add("Error w propType check");
        return tokens.size()+1;
    }

    private int propDesc(int i){
        if(tokens.get(i+1).keyword.equals("COLON")) {
            i++;
            if(tokens.get(i+1).keyword.equals("SPACE")){
                i++;
                if(tokens.get(i+1).keyword.equals("STRING")){
                    i++;
                    if (propList.contains(tokens.get(i + 2).keyword)) {
                        if(tokens.get(i+1).keyword.equals("COMMA")){
                            i++;
                            return i;
                        }
                    }else {
                        return i;
                    }

                }
            }
        }
        errors.add("Error w propDesc check");
        return tokens.size()+1;
    }
    private int propMin(int i){
        if(tokens.get(i+1).keyword.equals("COLON")) {
            i++;
            if(tokens.get(i+1).keyword.equals("SPACE")){
                i++;
                if(tokens.get(i+1).keyword.equals("NUMBER")){
                    i++;
                    if (propList.contains(tokens.get(i + 2).keyword)) {
                        if(tokens.get(i+1).keyword.equals("COMMA")){
                            i++;
                            return i;
                        }
                    }else {
                        return i;
                    }

                }
            }
        }
        errors.add("Error w propMin check");
        return tokens.size()+1;
    }
    private int propMax(int i){
        if(tokens.get(i+1).keyword.equals("COLON")) {
            i++;
            if(tokens.get(i+1).keyword.equals("SPACE")){
                i++;
                if(tokens.get(i+1).keyword.equals("NUMBER")){
                    i++;
                    if (propList.contains(tokens.get(i + 2).keyword)) {
                        if(tokens.get(i+1).keyword.equals("COMMA")){
                            i++;
                            return i;
                        }
                    }else {
                        return i;
                    }

                }
            }
        }
        errors.add("Error w propMax check");
        return tokens.size()+1;
    }
    private int propMinLen(int i){
        if(tokens.get(i+1).keyword.equals("COLON")) {
            i++;
            if(tokens.get(i+1).keyword.equals("SPACE")){
                i++;
                if(tokens.get(i+1).keyword.equals("NUMBER")){
                    i++;
                    if (propList.contains(tokens.get(i + 2).keyword)) {
                        if(tokens.get(i+1).keyword.equals("COMMA")){
                            i++;
                            return i;
                        }
                    }else {
                        return i;
                    }

                }
            }
        }
        errors.add("Error w propMinLen check");
        return tokens.size()+1;
    }
    private int propMaxLen(int i){
        if(tokens.get(i+1).keyword.equals("COLON")) {
            i++;
            if(tokens.get(i+1).keyword.equals("SPACE")){
                i++;
                if(tokens.get(i+1).keyword.equals("NUMBER")){
                    i++;
                    if (propList.contains(tokens.get(i + 2).keyword)) {
                        if(tokens.get(i+1).keyword.equals("COMMA")){
                            i++;
                            return i;
                        }
                    }else {
                        return i;
                    }

                }
            }
        }
        errors.add("Error w propMaxLen check");
        return tokens.size()+1;
    }
    private int propEnum(int i){
        if(tokens.get(i+1).keyword.equals("COLON")) {
            i++;
            if(tokens.get(i+1).keyword.equals("SPACE")){
                i++;
                if(tokens.get(i+1).keyword.equals("SLPAREN")){
                    i++;
                    if(tokens.get(i+1).keyword.equals("STRING")){
                        i++;
                        if(tokens.get(i+1).keyword.equals("COMMA")){
                            i = propEnum2(i);
                           }else if(tokens.get(i+1).keyword.equals("SRPAREN")){
                                     i++;
                                        if (elementsList.contains(tokens.get(i + 2).keyword)) {
                                              if (tokens.get(i + 1).keyword.equals("COMMA")) {
                                                  i++;
                                                    return i;
                                                    }
                                                }
                                         }
                    }


                }
            }
        }
        errors.add("Error w ENUM check");
        return tokens.size()+1;
    }
    private int propRef(int i){
        if(tokens.get(i+1).keyword.equals("COLON")) {
            i++;
            if(tokens.get(i+1).keyword.equals("SPACE")){
                i++;
                if(tokens.get(i+1).keyword.equals("ADDRESS")){
                    i++;
                    if (propList.contains(tokens.get(i + 2).keyword)) {
                        if(tokens.get(i+1).keyword.equals("COMMA")){
                            i++;
                            return i;
                        }
                    }else {
                        return i;
                    }

                }
            }
        }
        errors.add("Error w propRef check");
        return tokens.size()+1;
    }

    private int propEnum2(int i){

        if(tokens.get(i+1).keyword.equals("COMMA")) {
            i++;
            if(tokens.get(i+1).keyword.equals("SPACE")) {
                i++;
                if(tokens.get(i+1).keyword.equals("STRING")) {
                    i++;
                    if(tokens.get(i+1).keyword.equals("COMMA")){
                        propEnum2(i);
                    }else {
                        return i;
                    }
                }

            }
        }

        errors.add("Error w propEnum2 check");
        return tokens.size()+1;
    }

    private int check() {


        for(int i = 0; i < tokens.size(); i++)
        {

            switch(tokens.get(i).keyword)
            {
                case "ID": i = idCheck(i); break;
                case "SCHEMA": i = schemaCheck(i); break;
                case "TITLE": i = titleCheck(i); break;
                case "REQUIRED": i = reqCheck(i); break;
                case "TYPE": i = typeCheck(i); break;
                case "PROPERTIES": i = propCheck(i); break;
                case "DEFINITIONS": i = defCheck(i); break;
                default: break;

            }

        }

        return 0;
    }


    public int start() {

        elementsList.add("ID");
        elementsList.add("SCHEMA");
        elementsList.add("TITLE");
        elementsList.add("REQUIRED");
        elementsList.add("TYPE");
        elementsList.add("PROPERTIES");
        elementsList.add("DEFINITIONS");

        propList.add("TYPE");
        propList.add("DESCRIPTION");
        propList.add("MINIMUM");
        propList.add("MAXIMUM");
        propList.add("MINLENGTH");
        propList.add("MAXLENGTH");
        propList.add("ENUM");
        propList.add("REF");




        System.out.println("\nMoje rozwiaznie");

        //pozbycie sie nadmiaru spacji
        for(int i = 0; i < tokens.size()-1; i++)
        {
            if(tokens.get(i).keyword.equals(tokens.get(i+1).keyword) && tokens.get(i).keyword.equals("SPACE"))
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
            //return 0;
        }

        //sprawdzenie liczby klamr { }
        int counter = 0;
        for(int i = 0; i < tokens.size()-1; i++){
            if(tokens.get(i).keyword.equals("BLPAREN"))
                counter ++ ;
            if(tokens.get(i).keyword.equals("BRPAREN"))
                counter --;
        }
        if(counter !=0){
            errors.add("Brakuje \"{\" lub \"}\"");
            //return 0;
        }


        //sprawdzenie liczby klamr [ ]
        counter = 0;
        for(int i = 0; i < tokens.size()-1; i++){
            if(tokens.get(i).keyword.equals("SLPAREN"))
                counter ++ ;
            if(tokens.get(i).keyword.equals("SRPAREN"))
                counter --;
        }
        if(counter !=0){
            errors.add("Brakuje \"[\" lub \"]\"");
            //return 0;
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
