/**
 * 
 */
package exercise.addressbook;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

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
@Component
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

	/**
	 * Define user commands.
	 * 
	 */
	enum USERQUERY {
		searchGender, getEldest, getAgeDifference
	}

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private AddressBookServices addressBookService;

	// ========================================================================

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		LOGGER.info("STARTED Gumtree address book with args {}", args);

		ClassPathXmlApplicationContext contextApp = null;

		try {
			if (args.length < 2) {
				throw new IllegalArgumentException(
						"Insufficient program arguments");
			}

			LOGGER.info("**** STARTING SPRING");
			contextApp = new ClassPathXmlApplicationContext(
					"classpath:beans.addressbook.xml");

			AddressBookMain main = (AddressBookMain) contextApp
					.getBean(AddressBookMain.class);

			main.init(args[0].trim());
			main.processUserQuery(args[1].trim(), Arrays.copyOfRange(args,
					Math.min(args.length - 1, 2), args.length));

		} catch (IllegalArgumentException exception) {
			System.out.println(exception.getMessage());
			System.out.println(USAGE);
		} catch (Exception exception) {
			LOGGER.info("ERROR with program execution: ", exception);
		}
	}

	// ------------------------------------------------------------------------

	/**
	 * Initialises environment for user query.
	 * 
	 * @param bootStrapFileName
	 * @throws Exception
	 */
	public void init(String bootStrapFileName) throws Exception {
		File bootStrapFile = new File(bootStrapFileName);
		if ((!bootStrapFile.exists()) && (!bootStrapFile.isFile())) {
			throw new IllegalArgumentException(
					"No or invalid AddressBook file loaded.");
		}

		LOGGER.info("**** BOOTSTRAPPING ADDRESSBOOK");
		AddressBookBootstrapStrategy bootstrap = new AddressBookBootstrapCSVImpl(
				bootStrapFile, contactRepository);
		bootstrap.boot();
	}

	/**
	 * Process user query from the command line.
	 * 
	 * @param userQueryStr
	 *            The user command.
	 * @param queryParams
	 *            the command params.
	 * @throws IllegalArgumentException
	 */
	public void processUserQuery(String userQueryStr, String[] queryParams)
			throws IllegalArgumentException {

		USERQUERY userQuery = USERQUERY.valueOf(userQueryStr);

		if (userQuery == null) {
			throw new IllegalArgumentException("Unknown Query command: "
					+ userQuery);
		}

		switch (userQuery) {
		case searchGender:
			if (queryParams.length != 1) {
				throw new IllegalArgumentException(
						"Insufficient arguments to search by gender");
			}
			runGetGenderQuery(queryParams[0]);
			break;

		case getEldest:
			runGetEldestQuery();
			break;

		case getAgeDifference:
			if (queryParams.length != 2) {
				throw new IllegalArgumentException(
						"Insufficient arguments for DOB difference");
			}
			runGetAgeDifferenceQuery(queryParams[0], queryParams[1]);
			break;
		}
	}

	/**
	 * Called with: addressbook getAgeDifference "Adam Darmanin" "Joe Temple"
	 * "C:\addressbook.csv"
	 * 
	 * @param name1
	 *            Name of first contact to compare.
	 * @param name2
	 *            Name of second contact to compare.
	 */
	private void runGetAgeDifferenceQuery(String name1, String name2) {
		System.out.println("Retrieving DOB difference in days...");

		Long dobDifference = addressBookService
				.getDateDifferenceInDaysBetweenContacts(name1, name2);

		System.out
				.println((dobDifference == null ? "...contacts couldn't be found"
						: "...difference in days is: " + dobDifference));
	}

	/**
	 * Called with: addressbook getEldest "C:\addressbook.csv"
	 * 
	 */
	private void runGetEldestQuery() {
		System.out.println("Searching the eldest...");
		Contact contact = addressBookService.getEldestContact();
		System.out.println((contact == null ? "...results returned nothing"
				: "... eldest: " + contact));
	}

	/**
	 * Called with: java -jar addressbook searchGender "Female"
	 * "C:\addressbook.csv"
	 * 
	 * @param genderParam
	 *            Gender string passed.
	 * @throws IllegalArgumentException
	 */
	private void runGetGenderQuery(String genderParam)
			throws IllegalArgumentException {
		System.out.println("Searching by gender...");

		GenderEnum gender = GenderEnum.getGenderForString(genderParam);
		if (gender == null) {
			throw new IllegalArgumentException("Invalid gender selected");
		}

		List<Contact> contacts = addressBookService
				.getAllContactsByGender(gender);
		System.out.println(String.format("...Found %d contacts by gender %s",
				contacts.size(), gender.getGenderStr()));
		for (Contact contact : contacts) {
			System.out.println(contact);
		}
	}

	public ContactRepository getContactRepository() {
		return contactRepository;
	}

	public void setContactRepository(ContactRepository contactRepository) {
		this.contactRepository = contactRepository;
	}

	public AddressBookServices getAddressBookService() {
		return addressBookService;
	}

	public void setAddressBookService(AddressBookServices addressBookService) {
		this.addressBookService = addressBookService;
	}

}
