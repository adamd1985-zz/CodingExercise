package exercise.addressbook;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import exercise.addressbook.datarepositories.ContactRepository;
import exercise.addressbook.model.Contact;
import exercise.addressbook.services.AddressBookBootstrapCSVImpl;
import exercise.addressbook.services.AddressBookBootstrapStrategy;

/**
 * Tests that the bootstrapping correctly happens and all correct data is
 * available on the DB.
 * 
 * @author adam
 * @version 1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:beans.test.addressbook.xml" })
@TransactionConfiguration
public class AddressBookBootstrapCSVTest {

	private AddressBookBootstrapStrategy testClass = null;
	private File csvFile = null;

	@Autowired
	private ContactRepository repo;

	// ========================================================================

	@Transactional
	@Test
	public void testBootingOfValidData() throws Exception {

		this.csvFile = new ClassPathResource("test.addressbook.csv").getFile();
		this.testClass = new AddressBookBootstrapCSVImpl(this.csvFile,
				this.repo);

		this.testClass.boot();
		List<Contact> contacts = this.repo.findAll();

		Assert.assertNotNull(contacts);
		Assert.assertEquals(3, contacts.size());
	}
}
