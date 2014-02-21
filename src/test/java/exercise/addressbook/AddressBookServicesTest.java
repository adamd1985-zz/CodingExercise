package exercise.addressbook;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
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
import exercise.addressbook.model.GenderEnum;
import exercise.addressbook.services.AddressBookBootstrapCSVImpl;
import exercise.addressbook.services.AddressBookBootstrapStrategy;
import exercise.addressbook.services.AddressBookServices;

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
@Transactional
public class AddressBookServicesTest {

	private AddressBookBootstrapStrategy testClass = null;
	private File csvFile = null;

	@Autowired
	private ContactRepository repo;

	@Autowired
	private AddressBookServices services;

	// ========================================================================

	@Before
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
	 */
	@Test
	public void testRetrievalOnGender() {
		List<Contact> females = this.services
				.getAllContactsByGender(GenderEnum.FEMALE);

		Assert.assertEquals(2, females.size());
		Assert.assertEquals(females.get(0).getGender(),
				GenderEnum.FEMALE.getGenderStr());
		Assert.assertEquals(females.get(1).getGender(),
				GenderEnum.FEMALE.getGenderStr());
	}

	/**
	 * Same as above but to see that on an empty database it returns nothing
	 * without error.
	 * 
	 */
	@Test
	public void testRetrievalOnGenderOnEmptyDB() {
		this.repo.deleteAll();

		List<Contact> females = this.services
				.getAllContactsByGender(GenderEnum.FEMALE);

		Assert.assertEquals(0, females.size());
	}

	/**
	 * Given 5 contacts, when the eldest is queried, one oldest contact must be
	 * returned.
	 * 
	 */
	@Test
	public void testRetrievalOfEldest() {
		Contact eldest = this.services.getEldestContact();
		Assert.assertEquals("Wes Jackson", eldest.getName());

	}

	/**
	 * Same as above but to see that on an empty database it returns nothing
	 * without error.
	 */
	@Test
	public void testRetrievalOfEldestOnEmptyDB() {
		this.repo.deleteAll();
		Contact eldest = this.services.getEldestContact();
		Assert.assertNull(eldest);
	}

	/**
	 * Given two contacts, the age difference in days should be calculated.
	 */
	@Test
	public void testRetrievalOfAgeDifference() {
		Long days = this.services.getDateDifferenceInDaysBetweenContacts(
				"Paul Robinson", "Bill McKnight");

		Assert.assertEquals(2862L, days.longValue());
	}

	/**
	 * Same as above but to see that on an empty database it returns nothing
	 * without error.
	 * 
	 * Should deal with the case where all or one is missing from the names given.
	 */
	@Test
	public void testRetrievalOfAgeDifferenceOnEmptyDB() {
		this.repo.deleteAll();
		
		Long days = this.services.getDateDifferenceInDaysBetweenContacts(
				"Paul Robinson", "Bill McKnight");

		Assert.assertNull(days);
	}
}
