/**
 * 
 */
package semweb.lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.VCARD;

/**
 * @author Yogeshwara Krishnan
 * 
 */
public class Lab2_1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String namespace = "http://utdallas/semwebclass";

		String directorFullName = "Stanley Kubrick";
		String role = "Director";

		Model model = ModelFactory.createDefaultModel();

		Resource stanleyKubrick = model.createResource(namespace + "/people#StanleyKubrick");

		stanleyKubrick.addProperty(VCARD.FN, directorFullName).addProperty(
				VCARD.ROLE, role);

		Property movieTitleProperty = model.createProperty(namespace
				+ "/vocabulary/movie#Title");
		Property movieYearProperty = model.createProperty(namespace
				+ "/vocabulary/movie#Year");
		Property movieDirectorProperty = model.createProperty(namespace
				+ "/vocabulary/movie#DirectedBy");

		Resource drStrangelove = model.createResource(namespace
				+ "/movie#DrStrangelove");
		drStrangelove.addProperty(movieTitleProperty, "Dr. Strangelove")
				.addProperty(movieYearProperty, "1961")
				.addProperty(movieDirectorProperty, stanleyKubrick);

		Resource aClockworkOrange = model.createResource(namespace
				+ "/movie#AClockworkOrange");

		aClockworkOrange.addProperty(movieTitleProperty, "A Clockwork Orange")
				.addProperty(movieYearProperty, "1971")
				.addProperty(movieDirectorProperty, stanleyKubrick);
		
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
				"Lab2_1_YKrishnan.xml"));
		model.write(printWriter);
		printWriter.close();

		// Output the model as N3.
		printWriter = new PrintWriter(new File("Lab2_1_YKrishnan.n3"));
		model.write(printWriter, "N3");
		printWriter.close();
	}

}
