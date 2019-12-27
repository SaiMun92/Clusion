/** * Copyright (C) 2016 Tarik Moataz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * This Class tests the text processing functionality
 * It outputs two multi-maps: the first associates keywords to the documents identifiers while the second associates the doc identifiers to keywords
 */

package org.crypto.sse;

import com.google.common.collect.Multimap;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// C:\Users\smlee\Desktop\temp_files
public class TestIndexing {

	private static Multimap<String, String> lookup1, lookup2;

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

					OutputStream os1 = new FileOutputStream("lookup1_old.txt");
					TextExtractPar.write(TextExtractPar.lp1, os1);

					OutputStream os2 = new FileOutputStream("lookup2.txt");
					TextExtractPar.write(TextExtractPar.lp2, os2);

					System.out.println("\nFirst multi-map " + TextExtractPar.lp1);
					System.out.println("Second multi-map " + TextExtractPar.lp2);
					System.out.println("Text files have been created.");
					break;

				case 2:
					InputStream is1 = new FileInputStream("lookup1_old.txt");
					InputStream is2 = new FileInputStream("lookup2.txt");

					TextExtractPar.read(is1, 1);
					TextExtractPar.read(is2, 2);

					System.out.println("\nFirst multi-map " + TextExtractPar.lp1);
					System.out.println("Second multi-map " + TextExtractPar.lp2);
					break;

				default:
					System.out.println("Exiting the system");
					break;
			}

			System.out.println("Enter option: \n" +
					"1: Create the lookup text files.\n" +
					"2: Import the textfiles into lookup tables.");
			option = in.nextInt();
		}

	}

}
