/**
 * 
 */
package exercise.addressbook;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import exercise.addressbook.datarepositories.ContactRepository;
import exercise.addressbook.services.AddressBookBootstrapCSVImpl;
import exercise.addressbook.services.AddressBookBootstrapStrategy;

/**
 * @author adam
 * 
 */
public class AddressBookMain {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AddressBookMain.class);

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		LOGGER.info("STARTED Gumtree address book with args {}", args);

		ClassPathXmlApplicationContext app = null;

		try {
			app = new ClassPathXmlApplicationContext("beans.addressbook.xml");

			ContactRepository repo = (ContactRepository) app
					.getBean(ContactRepository.class);
			File csvFile = new ClassPathResource("addressbook.csv").getFile();

			LOGGER.info("BOOTSTRAPPING ADDRESSBOOK");

			AddressBookBootstrapStrategy bootstrap = new AddressBookBootstrapCSVImpl(
					csvFile, repo);

			bootstrap.boot();

			LOGGER.info("DATABASE populated with: {}", repo.findAll());
		} finally {
			if (app != null) {
				app.close();
			}
		}
	}

}
