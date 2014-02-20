package exercise.addressbook;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
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
@Ignore
public class ContactRepositoryTest {

	private AddressBookBootstrapStrategy testClass = null;
	private File csvFile = null;

	@Autowired
	private ContactRepository repo;

	// ========================================================================

	@Transactional
	public void before() throws Exception {
		this.csvFile = new ClassPathResource("test.addressbook.csv").getFile();
		this.testClass = new AddressBookBootstrapCSVImpl(this.csvFile,
				this.repo);

		this.testClass.boot();
	}

	/**
	 * Given 5 contacts with 2 females, when queried on females only 2 should be
	 * returned.
	 * 
	 * @throws Exception
	 */
	@Transactional
	@Test
	public void testRetrievalOnGender() {
		Assert.fail();
	}

	/**
	 * Given 5 contacts, when the eldest is queried, one oldest contact must be
	 * returned.
	 * 
	 * @throws Exception
	 */
	@Transactional
	@Test
	public void testRetrievalOfEldest() {
		Assert.fail();
	}

	/**
	 * Given two contacts, the age difference in days should be calculated.
	 * 
	 * @throws Exception
	 */
	@Transactional
	@Test
	public void testRetrievalOfAgeDifference() {
		Assert.fail();
	}
}
