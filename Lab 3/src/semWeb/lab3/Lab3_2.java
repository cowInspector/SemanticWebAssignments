/**
 * The file monterey.rdf is read into a model and loaded into a default SDB triple store.
 * The time taken to read the file and load it into database will be printed on the console.
 * Query and ResultSet classes of Jena are being used to retrieve all the information about the monterey 
 * incident #1. The query result is written to an XML file.
 * 
 * Note that the time taken to read the file into model and storing the same in a triple store varies 
 * from machine to machine. Multiple runs in the same machine outputs different values of total time.
 * The time taken can be as low as 10 seconds or as high as 140 seconds. It certainly takes time to
 * insert 17k rows into sdb2.nodes and 55k rows into sdb2.triples.
 * 
 * 
 * @author Yogeshwara Krishnan
 * @date 10/15/2013
 */

package semWeb.lab3;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.sdb.SDBFactory;
import com.hp.hpl.jena.sdb.Store;
import com.hp.hpl.jena.util.FileManager;

public class Lab3_2 {

	static final String inputFileName = "75b_Lab3-monterey.rdf";
	static Model model;

	public static void main(String[] args) {

		// Set the user name and password as system properties.
		// These are read internally to connect to MySQL.
		System.setProperty("jena.db.user", "jenasdb");
		System.setProperty("jena.db.password", "fastdb");

		// Start time of reading the file into the model.
		Date startDate = new Date();

		// Read the file contents and store it in a model.
		readModelFromFile();

		// Establish connection to SDB2 database using the ttl file.
		Store store = SDBFactory.connectStore("sdb.ttl");

		// Clear the existing data in the database.
		store.getTableFormatter().truncate();

		// Connect to a Dataset and add the model.
		Dataset dataset = SDBFactory.connectDataset(store);
		dataset.getDefaultModel().add(model);

		// Close the connection to the store.
		store.close();

		// End time capturing when the file was read completely.
		Date endDate = new Date();

		// Print the time taken to load the model into default SDB triple store
		// in MySQL.
		System.out.println("Load of monterey.rdf took "
				+ (float) ((endDate.getTime() - startDate.getTime()) / 1000.00)
				+ " seconds");

		// Call executeQuery to get all the info about monterey incident 1.
		executeQuery();
	}

	/**
	 * This function will query the model to get all the info about monterey
	 * incident 1. The result set is then written into an XML file.
	 */
	private static void executeQuery() {
		String queryString = "SELECT ?p ?o WHERE { <urn:monterey:#incident1> ?p ?o }";
		Query query = QueryFactory.create(queryString);

		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet rs = qe.execSelect();

		try {
			ResultSetFormatter.outputAsXML(new FileOutputStream(
					"Lab3_2_YKrishnan.xml"), rs);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function opens monterey.rdf and stores the same in a default model.
	 * 
	 * @throws IllegalArgumentException
	 *             if the file is not found.
	 */
	public static void readModelFromFile() {

		// Create a default model.
		model = ModelFactory.createDefaultModel();

		// Open the rdf file.
		InputStream in = FileManager.get().open(inputFileName);

		// Handle the exception if file doesn't exist.
		if (in == null) {
			throw new IllegalArgumentException("File: " + inputFileName
					+ " not found");
		}

		// read the RDF/XML file
		model.read(in, "");

		// Close the file.
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
