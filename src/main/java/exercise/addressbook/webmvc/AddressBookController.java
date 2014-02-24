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
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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

	static boolean CONTROLLER_INITIALIZED = false;

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
	public ModelAndView index() {
		LOGGER.info("ADDRESS BOOK EXERCISE REQUEST ==========");
		ModelMap model = new ModelMap();

		model.addAttribute("contacts", addressBookService.getAllContacts());

		return new ModelAndView("addressbook", model);
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
	@ResponseBody
	public QueryResponse getAgeDifferenceQuery(@RequestParam String name1,
			@RequestParam String name2) {
		LOGGER.info("Retrieving DOB difference in days...");

		Long dobDifference = addressBookService
				.getDateDifferenceInDaysBetweenContacts(name1, name2);

		QueryResponse response = new QueryResponse();

		response.setError(false);
		response.setErrorMsg(null);
		response.setValue(dobDifference);

		return response;
	}

	/**
	 * Search for oldest contact.
	 * 
	 */
	@RequestMapping(value = "/getEldestQuery", method = RequestMethod.GET)
	@ResponseBody
	public QueryResponse runGetEldestQuery() {
		LOGGER.info("Searching the eldest...");

		List<Contact> contacts = new ArrayList<Contact>();

		Contact contact = addressBookService.getEldestContact();
		contacts.add(contact);

		QueryResponse response = new QueryResponse();

		response.setError(false);
		response.setErrorMsg(null);
		response.setValue(contacts);

		return response;
	}

	/**
	 * Retrieves all contacts by gender.
	 * 
	 * @param genderParam
	 *            Gender string passed.
	 * @throws IllegalArgumentException
	 */
	@RequestMapping(value = "/getGenderQuery", method = RequestMethod.GET)
	@ResponseBody
	public QueryResponse runGetGenderQuery(@RequestParam String genderParam)
			throws IllegalArgumentException {
		LOGGER.info("Searching by gender...");

		QueryResponse response = new QueryResponse();

		try {
			GenderEnum gender = GenderEnum.getGenderForString(genderParam);
			if (gender == null) {
				throw new IllegalArgumentException("Invalid gender selected");
			}

			List<Contact> contacts = addressBookService
					.getAllContactsByGender(gender);

			response.setError(false);
			response.setErrorMsg(null);
			response.setValue(contacts);
		} catch (IllegalArgumentException e) {
			response.setError(true);
			response.setErrorMsg(e.getMessage());
			response.setValue(null);
		}

		return response;
	}

	/**
	 * Boots the DB with data.
	 */
	@Override
	public synchronized void afterPropertiesSet() throws Exception {
		LOGGER.info("**** BOOTSTRAPPING ADDRESSBOOK");

		if (!CONTROLLER_INITIALIZED) {
			bootstrap.boot();
			CONTROLLER_INITIALIZED = true;
		}
	}
}
