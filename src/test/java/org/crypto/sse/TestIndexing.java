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

public class TestIndexing {

	private static Multimap<String, String> lookup;

	public static void main(String[] args) throws Exception {
		
		Printer.addPrinter(new Printer(Printer.LEVEL.EXTRA));

		BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));

/*		System.out.println("Enter the relative path name of the folder that contains the files to make searchable:");

		String pathName = keyRead.readLine();

		ArrayList<File> listOfFile = new ArrayList<File>();
		TextProc.listf(pathName, listOfFile);

		// Beginning of text extraction, inside this function calls the TextExtractPar
		TextProc.TextProc(false, pathName);

		OutputStream os1 = new FileOutputStream("lookup1.txt");
		TextExtractPar.write(TextExtractPar.lp1, os1);

		OutputStream os2 = new FileOutputStream("lookup2.txt");
		TextExtractPar.write(TextExtractPar.lp2, os2);
		*/

		InputStream is = new FileInputStream("file.txt");
		lookup = TextExtractPar.read(is);
		TextExtractPar extractPar = new TextExtractPar(lookup, lookup);
		System.out.println("First multi-map " + extractPar.getL1());


//		System.out.println("\nFirst multi-map " + TextExtractPar.lp1);
//		System.out.println("Second multi-map " + TextExtractPar.lp2);

	}

}
