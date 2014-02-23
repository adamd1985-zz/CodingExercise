/**
 * 
 */
package exercise.addressbook.webmvc;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import exercise.addressbook.model.Contact;
import exercise.addressbook.model.GenderEnum;
import exercise.addressbook.services.AddressBookBootstrapStrategy;
import exercise.addressbook.services.AddressBookServices;
import exercise.addressbook.tos.QueryResponse;

/**
 * Spring MVC controller for the coding exercise.
 * 
 * @author adam
 * @version 1
 */
@Controller
public class AddressBookController implements InitializingBean {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AddressBookController.class);

	@Autowired
	private AddressBookServices addressBookService;

	@Autowired
	private AddressBookBootstrapStrategy bootstrap;

	/**
	 * Define user commands.
	 * 
	 */
	enum USERQUERY {
		searchGender, getEldest, getAgeDifference
	}

	// ========================================================================

	/**
	 * Sets up the initial page elements.
	 * 
	 * @param model
	 * @return The index view (FTL)
	 */
	@RequestMapping(value = "/exercise", method = RequestMethod.GET)
	public String index(@ModelAttribute("model") ModelMap model) {
		LOGGER.info("ADDRESS BOOK EXERCISE REQUEST ==========");

		model.addAttribute("MsTime", System.currentTimeMillis());

		return "addressbook";
	}

	/**
	 * gets date difference
	 * 
	 * @param name1
	 *            Name of first contact to compare.
	 * @param name2
	 *            Name of second contact to compare.
	 */
	@RequestMapping(value = "/getAgeDifferenceQuery", method = RequestMethod.GET)
	public String getAgeDifferenceQuery(
			@ModelAttribute("model") ModelMap model,
			@RequestParam String name1, @RequestParam String name2) {
		LOGGER.info("Retrieving DOB difference in days...");

		Long dobDifference = addressBookService
				.getDateDifferenceInDaysBetweenContacts(name1, name2);

		QueryResponse response = new QueryResponse();

		response.setType(USERQUERY.getAgeDifference.name());
		response.setMsg("DOB Difference in days: " + dobDifference);
		response.setContacts(null);

		model.addAttribute("queryResponse", response);

		return "addressbook";
	}

	/**
	 * Search for oldest contact.
	 * 
	 */
	@RequestMapping(value = "/getEldestQuery", method = RequestMethod.GET)
	public String runGetEldestQuery(@ModelAttribute("model") ModelMap model) {
		LOGGER.info("Searching the eldest...");

		List<Contact> contacts = new ArrayList<Contact>();

		Contact contact = addressBookService.getEldestContact();
		contacts.add(contact);

		QueryResponse response = new QueryResponse();

		response.setType(USERQUERY.getEldest.name());
		response.setMsg(null);
		response.setContacts(contacts);

		model.addAttribute("queryResponse", response);

		return "addressbook";
	}

	/**
	 * Retrieves all contacts by gender.
	 * 
	 * @param genderParam
	 *            Gender string passed.
	 * @throws IllegalArgumentException
	 */
	@RequestMapping(value = "/getGenderQuery", method = RequestMethod.GET)
	public String runGetGenderQuery(@ModelAttribute("model") ModelMap model,
			@RequestParam String genderParam) throws IllegalArgumentException {
		LOGGER.info("Searching by gender...");

		GenderEnum gender = GenderEnum.getGenderForString(genderParam);
		if (gender == null) {
			throw new IllegalArgumentException("Invalid gender selected");
		}

		List<Contact> contacts = addressBookService
				.getAllContactsByGender(gender);

		QueryResponse response = new QueryResponse();

		response.setType(USERQUERY.searchGender.name());
		response.setMsg(null);
		response.setContacts(contacts);

		model.addAttribute("queryResponse", response);

		return "addressbook";
	}

	/**
	 * Boots the DB with data.
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		LOGGER.info("**** BOOTSTRAPPING ADDRESSBOOK");

		bootstrap.boot();
	}
}
