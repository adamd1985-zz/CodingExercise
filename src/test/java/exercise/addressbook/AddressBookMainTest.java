/**
 * 
 */
package exercise.addressbook;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import exercise.addressbook.datarepositories.ContactRepository;
import exercise.addressbook.model.Contact;
import exercise.addressbook.model.GenderEnum;
import exercise.addressbook.services.AddressBookServices;

/**
 * Testing program entry point and potential user entries.
 * 
 * @author adam
 * @version 1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:beans.test.addressbook.xml" })
public class AddressBookMainTest {

	private AddressBookMain mainTest;

	private ContactRepository repoMock;

	private AddressBookServices addrMock;

	private Contact dummyContact;

	// ========================================================================

	/**
	 * Setup test classes and mocks.
	 */
	@Before
	public void setup() {
		mainTest = new AddressBookMain();

		repoMock = EasyMock.createMock(ContactRepository.class);

		mainTest.setContactRepository(repoMock);

		addrMock = EasyMock.createMock(AddressBookServices.class);

		mainTest.setAddressBookService(addrMock);

		dummyContact = new Contact();
		dummyContact.setDateOfBirth(new Date());
		dummyContact.setGender(GenderEnum.FEMALE.getGenderStr());
		dummyContact.setName("Rose");
	}

	/**
	 * Test error when user inputs an invalid command.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidQueryCommand() {
		mainTest.processUserQuery("invalidQuery", null);
	}

	/**
	 * Test when user inputs a valid command.
	 */
	@Test
	public void testValidEldestQuery() {
		EasyMock.expect(this.addrMock.getEldestContact()).andReturn(
				dummyContact);
		EasyMock.replay(this.addrMock);

		mainTest.processUserQuery(AddressBookMain.USERQUERY.getEldest.name(),
				null);

		EasyMock.verify(this.addrMock);
	}

	/**
	 * Test when user inputs a valid command.
	 */
	@Test
	public void testValidGenderQuery() {
		String[] params = { GenderEnum.FEMALE.getGenderStr() };

		List<Contact> dummyContacts = new ArrayList<Contact>();
		dummyContacts.add(dummyContact);

		EasyMock.expect(this.addrMock.getAllContactsByGender(GenderEnum.FEMALE))
				.andReturn(dummyContacts);
		EasyMock.replay(this.addrMock);

		mainTest.processUserQuery(
				AddressBookMain.USERQUERY.searchGender.name(), params);

		EasyMock.verify(this.addrMock);
	}

	/**
	 * Test error when user inputs an invalid command.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidGenderQueryParams() {
		// No params should elicit exception
		String[] params = { "" };

		mainTest.processUserQuery(
				AddressBookMain.USERQUERY.searchGender.name(), params);
	}

	/**
	 * Test error when user inputs an invalid command.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidDOBDifferenceQueryParams() {
		// 1 params should elicit exception
		String[] params = { "adam" };

		mainTest.processUserQuery(
				AddressBookMain.USERQUERY.getAgeDifference.name(), params);
	}

	/**
	 * Test when user inputs a valid command.
	 */
	@Test
	public void testValidDOBDifferenceQuery() {
		String[] params = { "adam", "joe" };

		EasyMock.expect(
				this.addrMock.getDateDifferenceInDaysBetweenContacts(params[0],
						params[1])).andReturn(215L);
		EasyMock.replay(this.addrMock);

		mainTest.processUserQuery(
				AddressBookMain.USERQUERY.getAgeDifference.name(), params);

		EasyMock.verify(this.addrMock);
	}
}
