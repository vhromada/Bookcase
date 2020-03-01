package cz.vhromada.bookcase.web.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

/**
 * A class represents controller for index.
 *
 * @author Vladimir Hromada
 */
@Controller("bookcaseController")
@RequestMapping
class BookcaseController {

    /**
     * Show index page.
     *
     * @param model model
     * @return view for index page
     * @throws IllegalArgumentException if model is null
     */
    @GetMapping
    fun showIndex(model: Model): String {
        model.addAttribute("title", "Bookcase")

        return "index"
    }
}
