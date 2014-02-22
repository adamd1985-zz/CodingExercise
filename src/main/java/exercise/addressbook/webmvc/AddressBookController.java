/**
 * 
 */
package exercise.addressbook.webmvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Spring MVC controller for the coding exercise.
 * 
 * @author adam
 * @version 1
 */
@Controller
public class AddressBookController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AddressBookController.class);

	// ========================================================================

	/**
	 * Sets up the page to show.
	 * 
	 * @param model
	 * @return The index view (FTL)
	 */
	@RequestMapping(value = "/exercise", method = RequestMethod.GET)
	public String index(@ModelAttribute("model") ModelMap model) {

		LOGGER.info("ADDRESS BOOK REQUEST ==========");

		// model.addAttribute("userList", userList);

		return "addressbook";
	}
}
