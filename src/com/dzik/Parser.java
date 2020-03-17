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
                        if(tokens.get(i + 1).keyword.equals("COMMA")){
                            i++;
                            return i;
                        }
                        else{
                            System.out.println("idCheck error");
                            System.exit(-1);
                        }
                    }
                    else return i;
                }
            }
        }
        System.out.println("idCheck error");
        System.exit(-1);
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
                    System.exit(-1);
                }
            }
        }
        System.out.println("schemaCheck error");
        System.exit(-1);
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
                    System.exit(-1);
                }
            }
        }

        System.out.println("titleCheck error");
        System.exit(-1);
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
        System.exit(-1);

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
                                        System.exit(-1);
                                    }


                                }
                            }
                        }
                    }
                }
                else{
                    System.out.println("reqCheck2 error");
                    System.exit(-1);
                }

            }
        }

        System.out.println("reqCheck3 error");
        System.exit(-1);
        return 0;
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
                            System.exit(-1);
                        }

                    }else{
                        return i;
                    }

                }
            }
        }
        System.out.println("typeCheck error");
        System.exit(-1);
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
                                        boolean ifTYPE = tokens.get(i+1).keyword.equals("TYPE");
                                        i = defDeepCheck(i);
                                        if((tokens.get(i+1).keyword.equals("BRPAREN") || ifTYPE)){
                                            if(!(ifTYPE)) i++;
                                            if(elementsList.contains(tokens.get(i+2).keyword)){
                                                if(tokens.get(i+1).keyword.equals("COMMA")){
                                                    i++;
                                                    return i;

                                                }
                                                else{
                                                    System.out.println("defCheck error");
                                                    System.exit(-1);
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
        System.exit(-1);
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
        System.exit(-1);
        return tokens.size()+1;
    }

    private int propCheck(int i){

        if(tokens.get(i+1).keyword.equals("COLON")){
            i++;
            if(tokens.get(i+1).keyword.equals("SPACE")){
                i++;
                if(tokens.get(i+1).keyword.equals("BLPAREN")){
                    i++;
                    i = propCheckDeep(i);
                    if (tokens.get(i + 1).keyword.equals("BRPAREN")) {
                        i++;
                        if ((elementsList.contains(tokens.get(i + 2).keyword)) || (tokens.get(i + 2).keyword.equals("STRING"))) {
                            if (tokens.get(i + 1).keyword.equals("COMMA")) {
                                i++;
                                while(tokens.get(i + 1).keyword.equals("STRING") || tokens.get(i + 3).keyword.equals("STRING")){
                                    if(tokens.get(i + 3).keyword.equals("STRING")) i+=2;
                                    i = propCheckDeep(i);
                                }
                                return i;
                            }
                        }

                    } else if (!tokens.get(i + 1).keyword.equals("BRPAREN")) {
                        return i;
                    }
                }
            }
        }

        System.out.println("propCheck error");
        System.exit(-1);
        return tokens.size()+1;

    }

    private int propCheckDeep(int i) {
        if((tokens.get(i+1).keyword.equals("STRING"))){
            i++;
            if((tokens.get(i+1).keyword.equals("COLON"))|| (tokens.get(i+1).keyword.equals("BLPAREN"))){
                i++;
                if((tokens.get(i+1).keyword.equals("SPACE"))|| (tokens.get(i+1).keyword.equals("REF"))){
                    if(tokens.get(i+1).keyword.equals("REF")){
                        i-=2;
                    }
                    i++;
                    if(tokens.get(i+1).keyword.equals("BLPAREN")) {
                        i++;
                        i = propMultiChoice(i);
                        while(tokens.get(i).keyword.equals("COMMA")) {
                            i = propMultiChoice(i);
                        }
                        return i;
                    }
                }

            }
        }
        System.exit(-1);
        return tokens.size()+1;
    }

    private int propMultiChoice(int i){

        switch(tokens.get(i+1).keyword)
        {
            case "TYPE": i = propType(i+1);return i;
            case "DESCRIPTION": i = propDesc(i+1);return i;
            case "MINIMUM": i = propMin(i+1);return i;
            case "MAXIMUM": i = propMax(i+1);return i;
            case "MINLENGTH": i = propMinLen(i+1);return i;
            case "MAXLENGTH": i = propMaxLen(i+1);return i;
            case "ENUM": i = propEnum(i+1);return i;
            case "REF": i = propRef(i+1);return i;
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
        System.exit(-1);
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
        System.exit(-1);
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
        System.exit(-1);
        System.out.println("Error w propMin check");
        System.exit(-1);
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
        System.exit(-1);
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
        System.exit(-1);
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
                            return i+1;
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
        System.out.println("Enum error");
        System.exit(-1);
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
        System.exit(-1);
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

        System.exit(-1);
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


        /*pozbycie sie nadmiaru spacji
        for(int i = 0; i < tokens.size()-1; i++)
        {
            if(tokens.get(i).keyword.equals(tokens.get(i+1).keyword) && tokens.get(i).keyword.equals("SPACE"))
            {
                //System.out.println(tokens.get(i).keyword);
                tokens.remove(i+1);
                i--;
            }
        } */
        //pozbycie sie nadmiaru spacji 2.0
        for(int i = 0; i < tokens.size()-1; i++)
        {
            if(tokens.get(i).keyword.equals("SLPAREN")){
                while (!(tokens.get(i).keyword.equals("SRPAREN"))){
                    i++;
                }
            }
            if(tokens.get(i).keyword.equals("SPACE") && (!(tokens.get(i-1).keyword.equals("COLON"))))
            {
                tokens.remove(i);
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
