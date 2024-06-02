package ru.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class for handling the home page requests.
 */
@Controller
public class HomeController {
    /**
     * Handles the root URL ("/") and returns the view name for the home page.
     *
     * @return the name of the view to render for the home page
     */
    @GetMapping("/")
    public String get() {
        return "home";
    }
}
