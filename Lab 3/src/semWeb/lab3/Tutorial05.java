/*
 * (c) Copyright 2003, 2004, Hewlett-Packard Development Company, LP
 * All rights reserved.
 * [See end of file]
 * $Id: Tutorial05.java,v 1.4 2006/04/27 09:30:07 der Exp $
 */
package semWeb.lab3;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.sdb.SDBFactory;
import com.hp.hpl.jena.sdb.Store;
import com.hp.hpl.jena.util.FileManager;

import java.io.*;
import java.util.Date;

/**
 * Tutorial 5 - read RDF XML from a file and write it to standard out
 * 
 * @author bwm - updated by kers/Daniel
 * @version Release='$Name: $' Revision='$Revision: 1.4 $' Date='$Date:
 *          2006/04/27 09:30:07 $'
 */
public class Tutorial05 extends Object {

	/**
	 * NOTE that the file is loaded from the class-path and so requires that the
	 * data-directory, as well as the directory containing the compiled class,
	 * must be added to the class-path when running this and subsequent
	 * examples.
	 */
	static final String inputFileName = "75b_Lab3-monterey.rdf";

	public static void main(String args[]) {

		// Set the user name and password as system properties.
		// These are read internally to connect to MySQL.
		System.setProperty("jena.db.user", "jenasdb");
		System.setProperty("jena.db.password", "fastdb");

		Store store = SDBFactory.connectStore("sdb.ttl");

		// Clear the existing data in the database.
		store.getTableFormatter().truncate();

		// Create a named model.
		Model model = SDBFactory.connectNamedModel(store, "MONTEREY");

		InputStream in = FileManager.get().open(inputFileName);
		if (in == null) {
			throw new IllegalArgumentException("File: " + inputFileName
					+ " not found");
		}

		Date startDate = new Date();
		//System.out.println(startDate.getTime() + "\n+++++++++++++++++++++++\n");

		// read the RDF/XML file
		model.read(in, "");
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//System.out.println("Model read...");
		
		Date endDate = new Date();
		/*System.out.println(endDate.getTime()
				+ "\n++++++++++++++++++++++++++++\n");*/
		System.out
				.println("Load of monterey.rdf took "
						+ (double)((endDate.getTime() - startDate.getTime()) / 1000.00)
						+ " seconds");
	}
}

/*
 * (c) Copyright 2003, 2004 Hewlett-Packard Development Company, LP All rights
 * reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer. 2. Redistributions in
 * binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution. 3. The name of the author may not
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
 * EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
