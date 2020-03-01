package cz.vhromada.bookcase.web.controller

import cz.vhromada.bookcase.entity.Author
import cz.vhromada.bookcase.facade.AuthorFacade
import cz.vhromada.bookcase.web.exception.IllegalRequestException
import cz.vhromada.bookcase.web.fo.AuthorFO
import cz.vhromada.bookcase.web.mapper.AuthorMapper
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.validation.Valid

/**
 * A class represents controller for authors.
 *
 * @author Vladimir Hromada
 */
@Controller("authorController")
@RequestMapping("/authors")
class AuthorController(
        private val authorFacade: AuthorFacade,
        private val authorMapper: AuthorMapper) : AbstractResultController() {

    /**
     * Process new data.
     *
     * @return view for redirect to page with list of authors
     */
    @GetMapping("/new")
    fun processNew(): String {
        processResults(authorFacade.newData())

        return LIST_REDIRECT_URL
    }

    /**
     * Shows page with list of authors.
     *
     * @param model model
     * @return view for page with list of authors
     */
    @GetMapping("", "/list")
    fun showList(model: Model): String {
        val authorsResult = authorFacade.getAll()
        processResults(authorsResult)

        model.addAttribute("authors", authorsResult.data)
        model.addAttribute("title", "Authors")

        return "author/index"
    }

    /**
     * Shows page for adding author.
     *
     * @param model model
     * @return view for page for adding author
     */
    @GetMapping("/add")
    fun showAdd(model: Model): String {
        val author = AuthorFO(
                id = null,
                firstName = null,
                middleName = null,
                lastName = null,
                position = null)
        return createFormView(model, author, "Add author", "add")
    }

    /**
     * Process adding author.
     *
     * @param model  model
     * @param author FO for author
     * @param errors errors
     * @return view for redirect to page with list of authors (no errors) or view for page for adding author (errors)
     * @throws IllegalArgumentException if ID isn't null
     */
    @PostMapping(value = ["/add"], params = ["create"])
    fun processAdd(model: Model, @ModelAttribute("author") @Valid author: AuthorFO, errors: Errors): String {
        require(author.id == null) { "ID must be null." }

        if (errors.hasErrors()) {
            return createFormView(model, author, "Add author", "add")
        }
        processResults(authorFacade.add(authorMapper.mapBack(author)))

        return LIST_REDIRECT_URL
    }

    /**
     * Cancel adding author.
     *
     * @return view for redirect to page with list of authors
     */
    @PostMapping(value = ["/add"], params = ["cancel"])
    fun cancelAdd(): String {
        return LIST_REDIRECT_URL
    }

    /**
     * Shows page for editing author.
     *
     * @param model model
     * @param id    ID of editing author
     * @return view for page for editing author
     * @throws IllegalRequestException  if author doesn't exist
     */
    @GetMapping("/edit/{id}")
    fun showEdit(model: Model, @PathVariable("id") id: Int): String {
        val result = authorFacade.get(id)
        processResults(result)

        val author = result.data
        return if (author != null) {
            createFormView(model, authorMapper.map(author), "Edit author", "edit")
        } else {
            throw IllegalRequestException(ILLEGAL_REQUEST_MESSAGE)
        }
    }

    /**
     * Process editing author.
     *
     * @param model  model
     * @param author FO for author
     * @param errors errors
     * @return view for redirect to page with list of authors (no errors) or view for page for editing author (errors)
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if author doesn't exist
     */
    @PostMapping(value = ["/edit"], params = ["update"])
    fun processEdit(model: Model, @ModelAttribute("author") @Valid author: AuthorFO, errors: Errors): String {
        require(author.id != null) { "ID mustn't be null." }

        if (errors.hasErrors()) {
            return createFormView(model, author, "Edit author", "edit")
        }
        processResults(authorFacade.update(processAuthor(authorMapper.mapBack(author))))

        return LIST_REDIRECT_URL
    }

    /**
     * Cancel editing author.
     *
     * @return view for redirect to page with list of authors
     */
    @PostMapping(value = ["/edit"], params = ["cancel"])
    fun processEdit(): String {
        return LIST_REDIRECT_URL
    }

    /**
     * Process duplicating author.
     *
     * @param id ID of duplicating author
     * @return view for redirect to page with list of authors
     * @throws IllegalRequestException  if author doesn't exist
     */
    @GetMapping("/duplicate/{id}")
    fun processDuplicate(@PathVariable("id") id: Int): String {
        processResults(authorFacade.duplicate(getAuthor(id)))

        return LIST_REDIRECT_URL
    }

    /**
     * Process removing author.
     *
     * @param id ID of removing author
     * @return view for redirect to page with list of authors
     * @throws IllegalRequestException  if author doesn't exist
     */
    @GetMapping("/remove/{id}")
    fun processRemove(@PathVariable("id") id: Int): String {
        processResults(authorFacade.remove(getAuthor(id)))

        return LIST_REDIRECT_URL
    }

    /**
     * Process moving author up.
     *
     * @param id ID of moving author
     * @return view for redirect to page with list of authors
     * @throws IllegalRequestException  if author doesn't exist
     */
    @GetMapping("/moveUp/{id}")
    fun processMoveUp(@PathVariable("id") id: Int): String {
        processResults(authorFacade.moveUp(getAuthor(id)))

        return LIST_REDIRECT_URL
    }

    /**
     * Process moving author down.
     *
     * @param id ID of moving author
     * @return view for redirect to page with list of authors
     * @throws IllegalRequestException  if author doesn't exist
     */
    @GetMapping("/moveDown/{id}")
    fun processMoveDown(@PathVariable("id") id: Int): String {
        processResults(authorFacade.moveDown(getAuthor(id)))

        return LIST_REDIRECT_URL
    }

    /**
     * Process updating positions.
     *
     * @return view for redirect to page with list of authors
     */
    @GetMapping("/update")
    fun processUpdatePositions(): String {
        processResults(authorFacade.updatePositions())

        return LIST_REDIRECT_URL
    }

    /**
     * Returns author with ID.
     *
     * @param id ID
     * @return author with ID
     * @throws IllegalRequestException  if author doesn't exist
     */
    private fun getAuthor(id: Int): Author {
        val author = Author(
                id = id,
                firstName = null,
                middleName = null,
                lastName = null,
                position = null)
        return processAuthor(author)
    }

    /**
     * Returns processed author.
     *
     * @param author for processing
     * @return processed author
     * @throws IllegalRequestException if author doesn't exist
     */
    private fun processAuthor(author: Author): Author {
        val authorResult = authorFacade.get(author.id!!)
        processResults(authorResult)

        if (authorResult.data != null) {
            return author
        }
        throw IllegalRequestException(ILLEGAL_REQUEST_MESSAGE)
    }

    /**
     * Returns page's view with form.
     *
     * @param model  model
     * @param author FO for author
     * @param title  page's title
     * @param action action
     * @return page's view with form
     */
    private fun createFormView(model: Model, author: AuthorFO?, title: String, action: String): String {
        model.addAttribute("author", author)
        model.addAttribute("title", title)
        model.addAttribute("action", action)

        return "author/form"
    }

    companion object {

        /**
         * Redirect URL to list
         */
        private const val LIST_REDIRECT_URL = "redirect:/authors/list"

        /**
         * Message for illegal request
         */
        private const val ILLEGAL_REQUEST_MESSAGE = "Author doesn't exist."

    }

}
