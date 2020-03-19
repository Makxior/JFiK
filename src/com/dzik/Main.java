package com.dzik;
import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);
        String temp;
        String Input="";
        File file = new File("input.txt"); //wczytywanie z pliku
        BufferedReader br = new BufferedReader(new FileReader(file));
        while (( temp = br.readLine()) != null){
            Input +=temp;
            Input +="\n";
        }

        Skaner sk = new Skaner(Input);
        Parser pr = new Parser(sk);
        pr.start();
        pr.result();
        scanner.nextLine();
    }
}