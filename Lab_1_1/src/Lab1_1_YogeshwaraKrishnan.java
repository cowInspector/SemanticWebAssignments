import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.VCARD;

/**
 * 
 * @author Yogeshwara Krishnan
 * 
 *         Create an in-memory Jena model (the default model). Use VCARD RDF
 *         (http://www.w3.org/TR/vcard-rdf) to represent that Steven Seida is a
 *         Senior Lecturer who was born on January 1, 1901, and he has e-mail
 *         address steven.seida@utdallas.edu. Steven should be represented as
 *         part of the http://utdallas/semclass namespace.
 * 
 */
public class Lab1_1_YogeshwaraKrishnan {

	// Declaration of variables needed to build a VCARD.
	private static String namespace = "http://utdallas/semclass#";
	private static String givenName = "Steven";
	private static String familyName = "Seida";
	private static String fullName = givenName + " " + familyName;
	private static String role = "Senior Lecturer";
	private static String dateOfBirth = "January 1, 1901";
	private static String emailId = "steven.seida@utdallas.edu";
	private static PrintWriter printWriter;

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// Create an empty model.
		Model model = ModelFactory.createDefaultModel();

		// Create a resource.
		Resource stevenSeida = model.createResource(namespace+givenName+familyName);

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

		// Output the model as XML.
		printWriter = new PrintWriter(
				new File("Lab1_1_Yogeshwara Krishnan.xml"));
		model.write(printWriter);
		printWriter.close();

		// Output the model as N-TRIPLES.
		printWriter = new PrintWriter(
				new File("Lab1_1_Yogeshwara Krishnan.ntp"));
		model.write(printWriter, "N-TRIPLES");
		printWriter.close();

		// Output the model as N3.
		printWriter = new PrintWriter(new File("Lab1_1_Yogeshwara Krishnan.n3"));
		model.write(printWriter, "N3");
		printWriter.close();
	}
}
