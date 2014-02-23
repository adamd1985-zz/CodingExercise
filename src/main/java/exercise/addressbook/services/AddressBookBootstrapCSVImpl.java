package exercise.addressbook.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import exercise.addressbook.datarepositories.ContactRepository;
import exercise.addressbook.model.Contact;
import exercise.addressbook.model.GenderEnum;

/**
 * Booting strategy for comma seperated address book records.
 * 
 * The CSV file is made of the following components in the given order:
 * <ol>
 * <li>NAME (AND SURNAME)</li>
 * <li>GENDER: Must be {@link GenderEnum}</li>
 * <li>DATE OF BIRTH: format dd/mm/yy</li>
 * </ol>
 * None of the data above can be missing. This strategy skips any malformed line
 * and continues loading valid records into the DB.
 * 
 * @author adam
 * @version 1
 */
@Component
public class AddressBookBootstrapCSVImpl implements
		AddressBookBootstrapStrategy {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AddressBookBootstrapCSVImpl.class);

	/** The file to boot from. */
	private File csvFile;

	private final static DateTimeFormatter DOB_FORMATTER = DateTimeFormat
			.forPattern("dd/MM/yy");

	@Autowired
	private ContactRepository contactRepository;
	
	

	// ========================================================================

	public AddressBookBootstrapCSVImpl() {

	}

	public AddressBookBootstrapCSVImpl(File csvFile,
			ContactRepository contactRepository) {
		setCsvFile(csvFile);
		setContactRepository(contactRepository);
	}

	public File getCsvFile() {
		return csvFile;
	}

	@Value("classpath:${addressbookBootfileName}")
	public void setCsvFile(File csvFile) {
		this.csvFile = csvFile;
	}

	public ContactRepository getContactRepository() {
		return contactRepository;
	}

	public void setContactRepository(ContactRepository contactRepository) {
		this.contactRepository = contactRepository;
	}

	/**
	 * Boot data
	 * 
	 * @param csvFile
	 * @throws Exception
	 */
	@Transactional
	public void boot() throws Exception {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(this.csvFile));

			String csvContactStr = in.readLine();
			while (csvContactStr != null) {
				Contact contact = mapToContact(csvContactStr);
				if (contact != null) {
					this.contactRepository.save(contact);
				}
				csvContactStr = in.readLine();
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

	/**
	 * Validates the given comma separated string and maps to a model object.
	 * 
	 * @param csvContactStr
	 *            the string to be mapped.
	 * @return model object.
	 */
	private Contact mapToContact(String csvContactStr) {
		String[] contactStrs = StringUtils
				.commaDelimitedListToStringArray(csvContactStr);

		if ((contactStrs.length != 3) || (contactStrs[0].isEmpty())
				|| (contactStrs[1].isEmpty()) || (contactStrs[2].isEmpty())) {
			LOGGER.debug("EMPTY entry: {}", csvContactStr);
			return null;
		}

		GenderEnum gender = GenderEnum
				.getGenderForString(contactStrs[1].trim());
		if (gender == null) {
			LOGGER.debug("IVALID gender: {}", contactStrs[1]);
			return null;
		}

		DateTime dob = null;
		try {
			dob = DOB_FORMATTER.parseDateTime(contactStrs[2].trim());
		} catch (Exception e) {
			LOGGER.debug("IVALID DOB: {}", contactStrs[2]);
			return null;
		}

		Contact contact = new Contact();

		contact.setName(contactStrs[0].trim());
		contact.setGender(gender.getGenderStr());
		contact.setDateOfBirth(dob.toDate());

		return contact;
	}
}
