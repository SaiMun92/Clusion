package org.crypto.sse;

import com.google.common.collect.Multimap;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

// C:\Users\smlee\Desktop\temp_files
public class TestEverything {

    private static Multimap<String, String> lookup1, lookup2;


    public static void LocalRH2Lev() throws Exception{
        BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter your password :");

        String pass = keyRead.readLine();

        byte[] sk = RR2Lev.keyGen(256, pass, "salt/salt", 100000);
        int bigBlock = 1000;
        int smallBlock = 100;
        int dataSize = 10000;

        System.out.println("\nBeginning of Encrypted Multi-map creation \n");
        System.out.println("Number of keywords "+TextExtractPar.lp1.keySet().size());
        System.out.println("Number of pairs "+	TextExtractPar.lp1.keys().size());

        // Multimap.keys() - Returns a view collection containing the key from each key-value pair in this multimap,
        //                      without collapsing duplicates.
        // Multimap.keySet() - Returns a view collection of all distinct keys contained in this multimap.

        //start
        long startTime = System.nanoTime();
        RH2Lev twolev = RH2Lev.constructEMMParGMM(sk, TextExtractPar.lp1, bigBlock, smallBlock, dataSize);
        //end
        long endTime = System.nanoTime();
        //time elapsed
        long output = endTime - startTime;

        System.out.println("Elapsed time in seconds: " + output / 1000000000);

        while (true) {

            System.out.println("Enter the keyword to search for:");
            String keyword = keyRead.readLine();
            byte[][] token = RH2Lev.token(sk, keyword);
            System.out.println(RH2Lev.resolve(CryptoPrimitives.generateCmac(sk, 3 + new String()),
                    twolev.query(token, twolev.getDictionary(), twolev.getArray())));

        }

    }

    public static void main(String[] args) throws Exception {

        Printer.addPrinter(new Printer(Printer.LEVEL.EXTRA));

        BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
        Scanner in = new Scanner(System.in);
        System.out.println("Enter option: \n" +
                "1: Create the lookup text files.\n" +
                "2: Import the textfiles into lookup tables.");
        int option = in.nextInt();

        while(option != 4) {
            switch (option) {
                case 1:
                    System.out.println("Enter the relative path name of the folder that contains the files to make searchable:");
                    String pathName = keyRead.readLine();

                    ArrayList<File> listOfFile = new ArrayList<File>();
                    TextProc.listf(pathName, listOfFile);

                    // Beginning of text extraction, inside this function calls the TextExtractPar
                    TextProc.TextProc(false, pathName);

                    OutputStream os1 = new FileOutputStream("lookup1.txt");
                    TextExtractPar.write(TextExtractPar.lp1, os1);

                    OutputStream os2 = new FileOutputStream("lookup2.txt");
                    TextExtractPar.write(TextExtractPar.lp2, os2);

                    System.out.println("\nFirst multi-map " + TextExtractPar.lp1);
                    System.out.println("Number of keywords for 1st mm " + TextExtractPar.lp1.keySet().size());
                    System.out.println("Number of pairs for 1st mm " + TextExtractPar.lp1.keys().size());
                    System.out.println("Second multi-map " + TextExtractPar.lp2);
                    System.out.println("Text files have been created.");
                    break;

                case 2:
                    InputStream is1 = new FileInputStream("lookup1_old.txt");
                    InputStream is2 = new FileInputStream("lookup2.txt");

                    TextExtractPar.read(is1, 1);
                    TextExtractPar.read(is2, 2);

                    System.out.println("\nFirst multi-map " + TextExtractPar.lp1);
                    System.out.println("Number of keywords for 1st mm " + TextExtractPar.lp1.keySet().size());
                    System.out.println("Number of pairs for 1st mm " + TextExtractPar.lp1.keys().size());
                    System.out.println("Second multi-map " + TextExtractPar.lp2);
                    break;

                case 3:
                    LocalRH2Lev();
                    break;

                case 4:
                    System.out.println("Exiting the system");
                    break;

                default:
                    System.out.println("Exiting the system");
                    break;
            }

            System.out.println("\n Enter option: \n" +
                    "1: Create the lookup text files.\n" +
                    "2: Import the textfiles into lookup tables.\n" +
                    "3: Run LocalRH2Lev searching algorithm.\n");
            option = in.nextInt();
        }

    }

}
