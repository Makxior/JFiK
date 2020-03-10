package com.dzik;
import java.io.*;

public class Main {

    public static void main(String[] args) throws Exception{
        String temp;
        String Input="";
        File file = new File("C:\\Users\\Grzdz\\IdeaProjects\\JFiKProjekt1\\src\\com\\dzik\\input.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        while (( temp = br.readLine()) != null){
            Input +=temp;
            Input +="\n";
        }

        System.out.println(Input);

        Skaner sk = new Skaner(Input);
        Parser pr = new Parser(sk);
        pr.start();
    }
}