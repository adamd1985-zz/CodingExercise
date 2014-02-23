/**
 * 
 */
package exercise.addressbook.services;

import java.util.List;

import exercise.addressbook.model.Contact;
import exercise.addressbook.model.GenderEnum;

/**
 * Used to perform query operations on an address book.
 * 
 * @author adam
 * @version 1
 */
public interface AddressBookServices {

	/**
	 * Get by gender.
	 * 
	 * @param gender
	 * @return List of contacts by gender.
	 */
	public List<Contact> getAllContactsByGender(GenderEnum gender);

	/**
	 * Get the oldest in the group.
	 * 
	 * @return Eldest.
	 */
	public Contact getEldestContact();

	/**
	 * Calculates the date difference between these two.
	 * 
	 * @param name1
	 *            Name of first contact
	 * @param name2
	 *            Name of second.
	 * @return difference in days or NULL if contacts were not found.
	 */
	public Long getDateDifferenceInDaysBetweenContacts(String name1,
			String name2);

}
