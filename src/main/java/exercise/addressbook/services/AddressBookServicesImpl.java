/**
 * 
 */
package exercise.addressbook.services;


import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

import exercise.addressbook.datarepositories.ContactRepository;
import exercise.addressbook.model.Contact;
import exercise.addressbook.model.GenderEnum;



/**
 * @author adam
 * 
 */
@Component
public class AddressBookServicesImpl
		implements AddressBookServices {

	@Autowired
	private ContactRepository contactRepo;



	// ========================================================================
	
	/*
	 * (non-Javadoc)
	 * @see exercise.addressbook.services.AddressBookServices#getAllContacts()
	 */
	public List<Contact> getAllContacts(){
		return this.contactRepo.findAll();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * exercise.addressbook.services.AddressBookServices#getAllContactsByGender
	 * (exercise.addressbook.model.GenderEnum)
	 */
	public List<Contact> getAllContactsByGender(GenderEnum gender) {
		return this.contactRepo.findByGender(gender.getGenderStr());
	}


	/*
	 * (non-Javadoc)
	 * @see exercise.addressbook.services.AddressBookServices#getEldestContact()
	 */
	public Contact getEldestContact() {
		Contact eldest = null;

		PageRequest page = new PageRequest(0, 1, new Sort(new Order(
				Direction.ASC, "dateOfBirth")));

		Page<Contact> pageReturned = this.contactRepo.findAll(page);

		if ((pageReturned != null) && (pageReturned.getContent().size() > 0)) {
			eldest = pageReturned.getContent().get(0);
		}

		return eldest;
	}


	/*
	 * (non-Javadoc)
	 * @see exercise.addressbook.services.AddressBookServices#
	 * getDateDifferenceInDaysBetweenContacts(java.lang.String,
	 * java.lang.String)
	 */
	public Long getDateDifferenceInDaysBetweenContacts(String name1,
			String name2) {
		Long days = null;

		List<String> namesToSearch = new ArrayList<String>();

		namesToSearch.add(name1);
		namesToSearch.add(name2);

		List<Contact> contacts = this.contactRepo.findByNameIn(namesToSearch);

		if (contacts != null && contacts.size() == 2) {
			Days diff = Days.daysBetween(new DateTime(contacts.get(0)
					.getDateOfBirth()), new DateTime(contacts.get(1)
					.getDateOfBirth()));
			days = new Long(Math.abs(diff.getDays()));
		}

		return days;
	}
}
