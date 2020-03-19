package com.dzik;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {
    Scanner scanner = new Scanner(System.in);
    List<Skaner.Token> tokens;
    List<String> elementsList = new ArrayList<>();
    List<String> propList = new ArrayList<>();

    public Parser(Skaner sk) {
        this.tokens = sk.tokens;
    }

    public void result() {
        System.out.println("Brak błędów");
    }

    private int idCheck(int i) {
        if (tokens.get(i + 1).keyword.equals("COLON")) {
            i++;
            if (tokens.get(i + 1).keyword.equals("SPACE")) {
                i++;
                if (tokens.get(i + 1).keyword.equals("LINK")) {
                    i++;
                    if (elementsList.contains(tokens.get(i + 2).keyword)) {
                        if (tokens.get(i + 1).keyword.equals("COMMA")) {
                            i++;
                            return i;
                        } else {
                            System.out.println("Błąd w ID");
                            scanner.nextLine(); System.exit(-1);
                        }
                    } else return i;
                }
            }
        }
        System.out.println("Błąd w ID");
        scanner.nextLine(); System.exit(-1);
        return tokens.size() + 1;
    }

    private int schemaCheck(int i) {
        if (tokens.get(i + 1).keyword.equals("COLON")) {
            i++;
            if (tokens.get(i + 1).keyword.equals("SPACE")) {
                i++;
                if (tokens.get(i + 1).keyword.equals("LINK")) {
                    i++;
                    if (elementsList.contains(tokens.get(i + 2).keyword)) {
                        if (tokens.get(i + 1).keyword.equals("COMMA")) {
                            i++;
                            return i;
                        }
                    } else return i;
                } else {
                    System.out.println("Błąd w SCHEMA");
                    scanner.nextLine(); System.exit(-1);
                }
            }
        }
        System.out.println("Błąd w SCHEMA");
        scanner.nextLine(); System.exit(-1);
        return tokens.size() + 1;
    }

    private int titleCheck(int i) {
        if (tokens.get(i + 1).keyword.equals("COLON")) {
            i++;
            if (tokens.get(i + 1).keyword.equals("SPACE")) {
                i++;
                if (tokens.get(i + 1).keyword.equals("STRING")) {
                    i++;
                    if (elementsList.contains(tokens.get(i + 2).keyword)) {
                        if (tokens.get(i + 1).keyword.equals("COMMA")) {
                            i++;
                            return i;
                        }
                    } else return i;

                } else {
                    System.out.println("Błąd w TITLE");
                    scanner.nextLine(); System.exit(-1);
                }
            }
        }

        System.out.println("Błąd w TITLE");
        scanner.nextLine(); System.exit(-1);
        return tokens.size() + 1;
    }

    private int reqDeepCheck(int i) {
        if (tokens.get(i + 1).keyword.equals("COMMA")) {
            i++;
            if (tokens.get(i + 1).keyword.equals("SPACE")) {
                i++;
                if (tokens.get(i + 1).keyword.equals("STRING")) {
                    i++;
                    if (tokens.get(i + 1).keyword.equals("SRPAREN")) {
                        i++;
                        return --i;
                    } else if (tokens.get(i + 1).keyword.equals("COMMA")) {
                        i = reqDeepCheck(i);
                        return i;
                    }

                }
            }
        }
        System.out.println("Błąd w REQUIRED");
        scanner.nextLine(); System.exit(-1);

        return tokens.size() + 1;
    }

    private int reqCheck(int i) {
        if (tokens.get(i + 1).keyword.equals("COLON")) {
            i++;
            if (tokens.get(i + 1).keyword.equals("SPACE")) {
                i++;
                if (tokens.get(i + 1).keyword.equals("SLPAREN")) {
                    i++;
                    if (tokens.get(i + 1).keyword.equals("STRING")) {
                        i++;
                        if (tokens.get(i + 1).keyword.equals("SRPAREN")) {
                            i++;
                            return i;
                        }
                        //rekurencja - sprawdzanie poprawnosci COMMA->SPACE->STRING wiele razy
                        else if (tokens.get(i + 1).keyword.equals("COMMA")) {
                            i = reqDeepCheck(i);
                            if (tokens.get(i + 1).keyword.equals("SRPAREN")) {
                                i++;

                                if (elementsList.contains(tokens.get(i + 2).keyword)) {
                                    if (tokens.get(i + 1).keyword.equals("COMMA")) {
                                        i++;
                                        return i;

                                    } else {
                                        System.out.println("Błąd w REQUIRED");
                                        scanner.nextLine(); System.exit(-1);
                                    }


                                }
                            }
                        }
                    }
                } else {
                    System.out.println("Błąd w REQUIRED");
                    scanner.nextLine(); System.exit(-1);
                }

            }
        }

        System.out.println("Błąd w REQUIRED");
        scanner.nextLine(); System.exit(-1);
        return 0;
    }

    private int typeCheck(int i) {
        if (tokens.get(i + 1).keyword.equals("COLON")) {
            i++;
            if (tokens.get(i + 1).keyword.equals("SPACE")) {
                i++;
                if (tokens.get(i + 1).keyword.equals("STRING")) {
                    i++;
                    if (elementsList.contains(tokens.get(i + 2).keyword)) {
                        if (tokens.get(i + 1).keyword.equals("COMMA")) {
                            i++;
                            return i;
                        } else {
                            System.out.println("Błąd w TYPE");
                            scanner.nextLine(); System.exit(-1);
                        }

                    } else {
                        return i;
                    }

                }
            }
        }
        System.out.println("Błąd w TYPE");
        scanner.nextLine(); System.exit(-1);
        return tokens.size() + 1;

    }

    private int defCheck(int i) {
        if (tokens.get(i + 1).keyword.equals("COLON")) {
            i++;
            if (tokens.get(i + 1).keyword.equals("SPACE")) {
                i++;
                if (tokens.get(i + 1).keyword.equals("BLPAREN")) {
                    i++;
                    if (tokens.get(i + 1).keyword.equals("STRING")) {
                        i++;
                        if (tokens.get(i + 1).keyword.equals("COLON")) {
                            i++;
                            if (tokens.get(i + 1).keyword.equals("SPACE")) {
                                i++;
                                if (tokens.get(i + 1).keyword.equals("BLPAREN")) {
                                    i++;
                                    if (tokens.get(i + 1).keyword.equals("TYPE") || tokens.get(i + 1).keyword.equals("PROPERTIES")) {
                                        boolean ifTYPE = tokens.get(i + 1).keyword.equals("TYPE");
                                        i = defDeepCheck(i);
                                        if ((tokens.get(i + 1).keyword.equals("BRPAREN") || ifTYPE)) {
                                            if (!(ifTYPE)) i++;
                                            if (elementsList.contains(tokens.get(i + 2).keyword)) {
                                                if (tokens.get(i + 1).keyword.equals("COMMA")) {
                                                    i++;
                                                    return i;

                                                } else {
                                                    System.out.println("Błąd w DEFINITIONS");
                                                    scanner.nextLine(); System.exit(-1);
                                                }

                                            } else return i;

                                        }

                                    }


                                }

                            }
                        }
                    }

                }
            }
        }
        System.out.println("Błąd w DEFINITIONS");
        scanner.nextLine(); System.exit(-1);
        return tokens.size() + 1;

    }

    private int defDeepCheck(int i) {
        if (tokens.get(i + 1).keyword.equals("TYPE")) {
            i++;
            if (tokens.get(i + 1).keyword.equals("COLON")) {
                i++;
                if (tokens.get(i + 1).keyword.equals("SPACE")) {
                    i++;
                    if (tokens.get(i + 1).keyword.equals("STRING")) {
                        i++;
                        return i;
                    }
                }
            }

        } else {
            i++;
            propCheck(i);

        }
        System.out.println("Błąd w DEFINITIONS");
        scanner.nextLine(); System.exit(-1);
        return tokens.size() + 1;
    }

    private int propCheck(int i) {

        if (tokens.get(i + 1).keyword.equals("COLON")) {
            i++;
            if (tokens.get(i + 1).keyword.equals("SPACE")) {
                i++;
                if (tokens.get(i + 1).keyword.equals("BLPAREN")) {
                    i++;
                    i = propCheckDeep(i);
                    if (tokens.get(i + 1).keyword.equals("BRPAREN")) {
                        i++;
                        if ((elementsList.contains(tokens.get(i + 2).keyword)) || (tokens.get(i + 2).keyword.equals("STRING"))) {
                            if (tokens.get(i + 1).keyword.equals("COMMA")) {
                                i++;
                                while (tokens.get(i + 1).keyword.equals("STRING") || tokens.get(i + 3).keyword.equals("STRING")) {
                                    if (tokens.get(i + 3).keyword.equals("STRING")) i += 2;
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

        System.out.println("Błąd w PROPERTIES");
        scanner.nextLine(); System.exit(-1);
        return tokens.size() + 1;

    }

    private int propCheckDeep(int i) {
        if ((tokens.get(i + 1).keyword.equals("STRING"))) {
            i++;
            if ((tokens.get(i + 1).keyword.equals("COLON")) || (tokens.get(i + 1).keyword.equals("BLPAREN"))) {
                i++;
                if ((tokens.get(i + 1).keyword.equals("SPACE")) || (tokens.get(i + 1).keyword.equals("REF"))) {
                    if (tokens.get(i + 1).keyword.equals("REF")) {
                        i -= 2;
                    }
                    i++;
                    if (tokens.get(i + 1).keyword.equals("BLPAREN")) {
                        i++;
                        i = propMultiChoice(i);
                        while (tokens.get(i).keyword.equals("COMMA")) {
                            i = propMultiChoice(i);
                        }
                        return i;
                    }
                }

            }
        }
        System.out.println("Błąd w PROPERTIES");
        scanner.nextLine(); System.exit(-1);
        return tokens.size() + 1;
    }

    private int propMultiChoice(int i) {

        switch (tokens.get(i + 1).keyword) {
            case "TYPE":
                i = propType(i + 1);
                return i;
            case "DESCRIPTION":
                i = propDesc(i + 1);
                return i;
            case "MINIMUM":
                i = propMin(i + 1);
                return i;
            case "MAXIMUM":
                i = propMax(i + 1);
                return i;
            case "MINLENGTH":
                i = propMinLen(i + 1);
                return i;
            case "MAXLENGTH":
                i = propMaxLen(i + 1);
                return i;
            case "ENUM":
                i = propEnum(i + 1);
                return i;
            case "REF":
                i = propRef(i + 1);
                return i;
            default:
                break;

        }

        return tokens.size() + 1;
    }

    private int propType(int i) {
        if (tokens.get(i + 1).keyword.equals("COLON")) {
            i++;
            if (tokens.get(i + 1).keyword.equals("SPACE")) {
                i++;
                if (tokens.get(i + 1).keyword.equals("STRING")) {
                    i++;
                    if (propList.contains(tokens.get(i + 2).keyword)) {
                        if (tokens.get(i + 1).keyword.equals("COMMA")) {
                            i++;
                            return i;
                        }
                    } else {
                        return i;
                    }

                }
            }
        }
        System.out.println("Błąd w TYPE");
        scanner.nextLine(); System.exit(-1);
        return tokens.size() + 1;
    }

    private int propDesc(int i) {
        if (tokens.get(i + 1).keyword.equals("COLON")) {
            i++;
            if (tokens.get(i + 1).keyword.equals("SPACE")) {
                i++;
                if (tokens.get(i + 1).keyword.equals("STRING")) {
                    i++;
                    if (propList.contains(tokens.get(i + 2).keyword)) {
                        if (tokens.get(i + 1).keyword.equals("COMMA")) {
                            i++;
                            return i;
                        }
                    } else {
                        return i;
                    }

                }
            }
        }
        System.out.println("Błąd w PROPERTIES");
        scanner.nextLine(); System.exit(-1);
        return tokens.size() + 1;
    }

    private int propMin(int i) {
        if (tokens.get(i + 1).keyword.equals("COLON")) {
            i++;
            if (tokens.get(i + 1).keyword.equals("SPACE")) {
                i++;
                if (tokens.get(i + 1).keyword.equals("NUMBER")) {
                    i++;
                    if (propList.contains(tokens.get(i + 2).keyword)) {
                        if (tokens.get(i + 1).keyword.equals("COMMA")) {
                            i++;
                            return i;
                        }
                    } else {
                        return i;
                    }

                }
            }
        }
        System.out.println("Błąd w MINIMUM");
        scanner.nextLine(); System.exit(-1);
        return tokens.size() + 1;
    }

    private int propMax(int i) {
        if (tokens.get(i + 1).keyword.equals("COLON")) {
            i++;
            if (tokens.get(i + 1).keyword.equals("SPACE")) {
                i++;
                if (tokens.get(i + 1).keyword.equals("NUMBER")) {
                    i++;
                    if (propList.contains(tokens.get(i + 2).keyword)) {
                        if (tokens.get(i + 1).keyword.equals("COMMA")) {
                            i++;
                            return i;
                        }
                    } else {
                        return i;
                    }

                }
            }
        }
        System.out.println("Błąd w MAXIMUM");
        scanner.nextLine(); System.exit(-1);
        return tokens.size() + 1;
    }

    private int propMinLen(int i) {
        if (tokens.get(i + 1).keyword.equals("COLON")) {
            i++;
            if (tokens.get(i + 1).keyword.equals("SPACE")) {
                i++;
                if (tokens.get(i + 1).keyword.equals("NUMBER")) {
                    i++;
                    if (propList.contains(tokens.get(i + 2).keyword)) {
                        if (tokens.get(i + 1).keyword.equals("COMMA")) {
                            i++;
                            return i;
                        }
                    } else {
                        return i;
                    }

                }
            }
        }
        System.out.println("Błąd w MINLENGTH");
        scanner.nextLine(); System.exit(-1);
        return tokens.size() + 1;
    }

    private int propMaxLen(int i) {
        if (tokens.get(i + 1).keyword.equals("COLON")) {
            i++;
            if (tokens.get(i + 1).keyword.equals("SPACE")) {
                i++;
                if (tokens.get(i + 1).keyword.equals("NUMBER")) {
                    i++;
                    if (propList.contains(tokens.get(i + 2).keyword)) {
                        if (tokens.get(i + 1).keyword.equals("COMMA")) {
                            i++;
                            return i;
                        }
                    } else {
                        return i;
                    }

                }
            }
        }
        System.out.println("Błąd w MAXLENGTH");
        scanner.nextLine(); System.exit(-1);
        return tokens.size() + 1;
    }

    private int propEnum(int i) {
        if (tokens.get(i + 1).keyword.equals("COLON")) {
            i++;
            if (tokens.get(i + 1).keyword.equals("SPACE")) {
                i++;
                if (tokens.get(i + 1).keyword.equals("SLPAREN")) {
                    i++;
                    if (tokens.get(i + 1).keyword.equals("STRING")) {
                        i++;
                        if (tokens.get(i + 1).keyword.equals("COMMA")) {
                            i = propEnum2(i);
                            return i + 1;
                        } else if (tokens.get(i + 1).keyword.equals("SRPAREN")) {
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
        System.out.println("Błąd w ENUM");
        scanner.nextLine(); System.exit(-1);
        return tokens.size() + 1;
    }

    private int propRef(int i) {
        if (tokens.get(i + 1).keyword.equals("COLON")) {
            i++;
            if (tokens.get(i + 1).keyword.equals("SPACE")) {
                i++;
                if (tokens.get(i + 1).keyword.equals("ADDRESS")) {
                    i++;
                    if (propList.contains(tokens.get(i + 2).keyword)) {
                        if (tokens.get(i + 1).keyword.equals("COMMA")) {
                            i++;
                            return i;
                        }
                    } else {
                        return i;
                    }

                }
            }
        }
        System.out.println("Błąd w REF");
        scanner.nextLine(); System.exit(-1);
        return tokens.size() + 1;
    }

    private int propEnum2(int i) {

        if (tokens.get(i + 1).keyword.equals("COMMA")) {
            i++;
            if (tokens.get(i + 1).keyword.equals("SPACE")) {
                i++;
                if (tokens.get(i + 1).keyword.equals("STRING")) {
                    i++;
                    if (tokens.get(i + 1).keyword.equals("COMMA")) {
                        propEnum2(i);
                    } else {
                        return i;
                    }
                }

            }
        }
        System.out.println("Błąd w ENUM");
        scanner.nextLine(); System.exit(-1);
        return tokens.size() + 1;
    }

    private int check() {


        for (int i = 0; i < tokens.size(); i++) {

            switch (tokens.get(i).keyword) {
                case "ID":
                    i = idCheck(i);
                    break;
                case "SCHEMA":
                    i = schemaCheck(i);
                    break;
                case "TITLE":
                    i = titleCheck(i);
                    break;
                case "REQUIRED":
                    i = reqCheck(i);
                    break;
                case "TYPE":
                    i = typeCheck(i);
                    break;
                case "PROPERTIES":
                    i = propCheck(i);
                    break;
                case "DEFINITIONS":
                    i = defCheck(i);
                    break;
                default:
                    break;

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


        //pozbycie sie nadmiaru spacji
        for (int i = 0; i < tokens.size() - 1; i++) {
            if (tokens.get(i).keyword.equals("SLPAREN")) {
                while (!(tokens.get(i).keyword.equals("SRPAREN"))) {
                    i++;
                }
            }
            if (tokens.get(i).keyword.equals("SPACE") && (!(tokens.get(i - 1).keyword.equals("COLON")))) {
                tokens.remove(i);
                i--;
            }
        }

        //sprawdzenie poprawnosci start i end
        if (!tokens.get(0).keyword.equals("BLPAREN") || !tokens.get(tokens.size() - 1).keyword.equals("EOF")) {
            System.out.println("Brakuje \"{\" na początku programu lub EOF na końcu ");
            scanner.nextLine(); System.exit(-1);
        }

        //sprawdzenie liczby klamr { }
        int counter = 0;
        for (int i = 0; i < tokens.size() - 1; i++) {
            if (tokens.get(i).keyword.equals("BLPAREN"))
                counter++;
            if (tokens.get(i).keyword.equals("BRPAREN"))
                counter--;
        }
        if (counter != 0) {
            System.out.println("Zła ilość \"{\" lub \"}\"");
            scanner.nextLine(); System.exit(-1);
        }


        //sprawdzenie liczby klamr [ ]
        counter = 0;
        for (int i = 0; i < tokens.size() - 1; i++) {
            if (tokens.get(i).keyword.equals("SLPAREN"))
                counter++;
            if (tokens.get(i).keyword.equals("SRPAREN"))
                counter--;
        }
        if (counter != 0) {
            System.out.println("Brakuje \"[\" lub \"]\"");
            scanner.nextLine(); System.exit(-1);
        }

        check();
        return 0;
    }
}