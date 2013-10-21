/**
 * Create a named graph and store the data of Stanley Kubrick's movies
 * which are based on books using VCARD and Dublin Core vocabularies.
 * @author Yogeshwara Krishnan
 * @date 09/24/2013
 */
package semweb.lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sdb.SDBFactory;
import com.hp.hpl.jena.sdb.Store;
import com.hp.hpl.jena.vocabulary.DC_11;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.VCARD;

/**
 * @author Yogeshwara Krishnan
 * @date 09/24/2013
 */
public class Lab2_3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Set the user name and password as system properties.
		// These are read internally to connect to MySQL.
		System.setProperty("jena.db.user", "jenasdb");
		System.setProperty("jena.db.password", "fastdb");

		// Establish connection to SDB2 database using the ttl file.
		Store store = SDBFactory.connectStore("sdb.ttl");

		// Clear the existing data in the database.
		store.getTableFormatter().truncate();

		// Create a named model.
		Model model = createNamedModel(store);

		// Write the model to the files.
		try {
			writeModelToFiles(model);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param store
	 * @return Model object
	 * 
	 *         This function will create a named model. Resources and their
	 *         respective properties are added to the model before returning.
	 */
	public static Model createNamedModel(Store store) {
		// Declare a namespace.
		String namespace = "http://utdallas/semwebclass";

		// Attributes for the Stanley Kubrick.
		String directorFullName = "Stanley Kubrick";
		String role = "Director";

		// Connect to the named model FILM through SDBFactory.
		Model model = SDBFactory.connectNamedModel(store, "FILM");

		// Create a resource for Stanley Kubrick.
		Resource stanleyKubrick = model.createResource(namespace
				+ "/people#StanleyKubrick");

		// Add properties for the resource Stanley Kubrick.
		stanleyKubrick.addProperty(RDF.type, namespace + "/vocabulary/person")
				.addProperty(VCARD.FN, directorFullName)
				.addProperty(VCARD.ROLE, role);

		// Create a resource for Peter George.
		Resource peterGeorge = model.createResource(namespace
				+ "/people#PeterGeorge");

		// Add properties for the resource Peter George.
		peterGeorge.addProperty(RDF.type, namespace + "/vocabulary/person")
				.addProperty(VCARD.FN, "Peter George")
				.addProperty(VCARD.ROLE, "Writer");

		// Create a resource for Anthony Burgess.
		Resource anthonyBurgess = model.createResource(namespace
				+ "/people#AnthonyBurgess");

		// Add properties for the resource Anthony Burgess.
		anthonyBurgess.addProperty(RDF.type, namespace + "/vocabulary/person")
				.addProperty(VCARD.FN, "Anthony Burgess")
				.addProperty(VCARD.ROLE, "Writer");

		// Create custom properties to describe the title, year, directory,
		// and books on which the movies were made.
		Property movieTitleProperty = model.createProperty(namespace
				+ "/vocabulary/movie#Title");
		Property movieYearProperty = model.createProperty(namespace
				+ "/vocabulary/movie#Year");
		Property movieDirectorProperty = model.createProperty(namespace
				+ "/vocabulary/movie#DirectedBy");
		Property basedonBookProperty = model.createProperty(namespace
				+ "/vocabulary/movie#BasedOnBook");

		// Create a resource for the book, Red Alert.
		Resource redAlert = model.createResource(namespace + "/book#RedAlert");

		// Add properties for the book, Red Alert, using Dublin Core vocabulary.
		redAlert.addProperty(RDF.type, namespace + "/vocabulary/book")
				.addProperty(DC_11.title, "Red Alert")
				.addProperty(DC_11.creator, peterGeorge);

		// Create a resource for the book, A Clockwork Orange.
		Resource aClockworkOrangeBook = model.createResource(namespace
				+ "/book#AClockworkOrange");

		// Add properties for the book, A Clockwork Orange, using Dublin Core
		// vocabulary.
		aClockworkOrangeBook
				.addProperty(RDF.type, namespace + "/vocabulary/book")
				.addProperty(DC_11.title, "A Clockwork Orange")
				.addProperty(DC_11.creator, anthonyBurgess);

		// Create a resource for the movie, Dr. Strangelove.
		Resource drStrangelove = model.createResource(namespace
				+ "/movie#DrStrangelove");

		// Add properties for the movie, Dr. Strangelove, using
		// the custom properties which were created earlier.
		drStrangelove.addProperty(RDF.type, namespace + "/vocabulary/movie")
				.addProperty(movieTitleProperty, "Dr. Strangelove")
				.addProperty(movieYearProperty, "1961")
				.addProperty(movieDirectorProperty, stanleyKubrick)
				.addProperty(basedonBookProperty, redAlert);

		// Create a resource for the movie, A Clockwork Orange.
		Resource aClockworkOrange = model.createResource(namespace
				+ "/movie#AClockworkOrange");

		// Add properties for the movie, A Clockwork Orange, using
		// the custom properties which were created earlier.
		aClockworkOrange.addProperty(RDF.type, namespace + "/vocabulary/movie")
				.addProperty(movieTitleProperty, "A Clockwork Orange")
				.addProperty(movieYearProperty, "1971")
				.addProperty(movieDirectorProperty, stanleyKubrick)
				.addProperty(basedonBookProperty, aClockworkOrangeBook);

		return model;
	}

	/**
	 * @param model
	 * @throws FileNotFoundException
	 * 
	 *             This function writes the Model object to the file. Takes
	 *             Model object as an argument.
	 */
	private static void writeModelToFiles(Model model)
			throws FileNotFoundException {
		// Output the model as XML.
		PrintWriter printWriter = new PrintWriter(new File(
				"Lab2_3_YKrishnan.xml"));
		model.write(printWriter);
		printWriter.close();

		// Output the model as N3.
		printWriter = new PrintWriter(new File("Lab2_3_YKrishnan.n3"));
		model.write(printWriter, "N3");
		printWriter.close();
	}
}
