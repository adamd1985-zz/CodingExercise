/**
 * 
 */
package exercise.addressbook;


import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import exercise.addressbook.datarepositories.ContactRepository;
import exercise.addressbook.model.Contact;
import exercise.addressbook.model.GenderEnum;
import exercise.addressbook.services.AddressBookBootstrapCSVImpl;
import exercise.addressbook.services.AddressBookBootstrapStrategy;
import exercise.addressbook.services.AddressBookServices;



/**
 * Program entry point.
 * 
 * @author adam
 * @version 1
 */
public class AddressBookMain {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AddressBookMain.class);

	private static final String USAGE = "\n\njava -jar addressbook ADDRESSBOOKFILE QUERY [QUERYPARAMS...]\n"
			+ "- ADDRESSBOOKFILE\n"
			+ "-   The location of an address book with comma separated values as contact fields and each new line a new record. \n"
			+ "- QUERY: [searchGender, getEldest or getAgeDifference]\n"
			+ "-   searchGender: search for gender, gender parameter required.\n"
			+ "-   getEldest: get eldest.\n"
			+ "-   getAgeDifference: Get age difference in days of two contacts, two name parameters required.\n"
			+ "- QUERYPARAMS:\n"
			+ "-   for query searchGender: [\"Male\" or \"Female\"].\n"
			+ "-   for query getAgeDifference these two space separated (use quotes): \"Firstname Lastname\" \"Firstname Lastname\"\n"
			+ "\n\n- EXAMPLES\n"
			+ "-   java -jar addressbook searchGender \"Female\" \"C:\\addressbook.csv\"\n"
			+ "-   java -jar addressbook getEldest \"C:\\addressbook.csv\"\n"
			+ "-   java -jar addressbook getAgeDifference \"Adam Darmanin\" \"Joe Temple\" \"C:\\addressbook.csv\"\n";

	private static final String SEARCHGENDERQUERY = "searchGender";
	private static final String GETELDESTQUERY = "getEldest";
	private static final String GETAGEDIFFERENCEQUERY = "getAgeDifference";



	// ========================================================================


	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args)
			throws Exception {
		LOGGER.info("STARTED Gumtree address book with args {}", args);

		ClassPathXmlApplicationContext contextApp = null;

		try {
			if (args.length < 2) {
				throw new IllegalArgumentException(
						"Insufficient program arguments");
			}

			LOGGER.info("**** STARTING SPRING");
			contextApp = new ClassPathXmlApplicationContext("classpath:beans.addressbook.xml");
			ContactRepository contactRepository = (ContactRepository) contextApp
					.getBean(ContactRepository.class);
			AddressBookServices addressBookService = (AddressBookServices) contextApp
					.getBean(AddressBookServices.class);

			File bootStrapFile = new File(args[0]);
			if ((!bootStrapFile.exists()) && (!bootStrapFile.isFile())) {
				throw new IllegalArgumentException(
						"No or invalid AddressBook file loaded.");
			}

			LOGGER.info("**** BOOTSTRAPPING ADDRESSBOOK");
			AddressBookBootstrapStrategy bootstrap = new AddressBookBootstrapCSVImpl(
					bootStrapFile, contactRepository);
			bootstrap.boot();

			processUserQuery(args, addressBookService);
		}
		catch (IllegalArgumentException exception) {
			System.out.println(exception.getMessage());
			System.out.println(USAGE);
		}
		catch (Exception exception) {
			LOGGER.info("ERROR with program execution: ", exception);
		}
	}


	/**
	 * Process user query.
	 * 
	 * @param args
	 *            program args
	 * @param addressBookService
	 *            Data service to use.
	 * @throws IllegalArgumentException
	 */
	private static void processUserQuery(String[] args, AddressBookServices addressBookService)
			throws IllegalArgumentException {
		String userQuery = args[1].trim();
		if (userQuery.equals(SEARCHGENDERQUERY)) {
			System.out.println("Searching by gender...");
			if (args.length < 3) {
				throw new IllegalArgumentException(
						"Insufficient arguments to search by gender");
			}
			GenderEnum gender = GenderEnum.getGenderForString(args[2]);
			if (gender == null) {
				throw new IllegalArgumentException(
						"Invalid gender selected");
			}

			List<Contact> contacts = addressBookService
					.getAllContactsByGender(gender);
			System.out.println(String.format(
					"...Found %d contacts by gender %s", contacts.size(),
					gender.getGenderStr()));
			for (Contact contact : contacts) {
				System.out.println(contact);
			}
		}
		else if (userQuery.equals(GETELDESTQUERY)) {
			System.out.println("Searching the eldest...");
			Contact contact = addressBookService.getEldestContact();
			System.out
					.println((contact == null ? "...results returned nothing"
							: "... eldest: " + contact));
		}
		else if (userQuery.equals(GETAGEDIFFERENCEQUERY)) {
			System.out.println("Retrieving DOB difference in days...");
			if (args.length < 4) {
				throw new IllegalArgumentException(
						"Insufficient arguments for DOB difference");
			}
			Long dobDifference = addressBookService
					.getDateDifferenceInDaysBetweenContacts(args[2],
							args[3]);
			System.out
					.println((dobDifference == null ? "...contacts couldn't be found"
							: "...difference in days is: " + dobDifference));
		}
		else {
			throw new IllegalArgumentException("Unknown Query");
		}
	}
}
