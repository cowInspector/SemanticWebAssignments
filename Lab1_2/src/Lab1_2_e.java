import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sdb.SDBFactory;
import com.hp.hpl.jena.sdb.Store;
import com.hp.hpl.jena.vocabulary.VCARD;

/**
 * 
 * @author Yogeshwara Krishnan
 * 
 *         This code is the solution for Problem #2 Part E.
 * 
 */
public class Lab1_2_e {

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

		// Connect a Dataset
		Dataset dataset = SDBFactory.connectDataset(store);

		// Add the model to the Dataset object. This will insert the model into
		// the DB.
		dataset.getNamedModel("myrdf").add(model);

		// End the connection.
		store.close();

		try {
			writeModelToFiles(model);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
				"Lab1_2_Yogeshwara Krishnan.xml"));
		model.write(printWriter);
		printWriter.close();

		// Output the model as N-TRIPLES.
		printWriter = new PrintWriter(new File(
				"Lab1_2_Yogeshwara Krishnan.ntp"));
		model.write(printWriter, "N-TRIPLES");
		printWriter.close();

		// Output the model as N3.
		printWriter = new PrintWriter(
				new File("Lab1_2_Yogeshwara Krishnan.n3"));
		model.write(printWriter, "N3");
		printWriter.close();
	}

	/**
	 * @param store
	 * @return Model object
	 * 
	 *         This function creates a default Model. Properties are added to
	 *         the resources.
	 */
	public static Model createNamedModel(Store store) {
		// Declaration of variables needed to build a VCARD.
		String namespace = "http://utdallas/semclass#";
		String givenName = "Steven";
		String familyName = "Seida";
		String fullName = givenName + " " + familyName;
		String role = "Senior Lecturer";
		String dateOfBirth = "January 1, 1901";
		String emailId = "steven.seida@utdallas.edu";

		// Create an empty model.
		Model model = SDBFactory.connectNamedModel(store, "myrdf");

		// Create a resource.
		Resource stevenSeida = model.createResource(namespace + givenName
				+ familyName);

		// Add the properties.
		stevenSeida
				.addProperty(VCARD.FN, fullName)
				.addProperty(
						VCARD.N,
						model.createResource()
								.addProperty(VCARD.Given, givenName)
								.addProperty(VCARD.Family, familyName))
				.addProperty(VCARD.ROLE, role)
				.addProperty(VCARD.BDAY, dateOfBirth)
				.addProperty(VCARD.EMAIL, emailId);

		return model;
	}

}
