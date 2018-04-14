package cz.vhromada.book.web.controller

import cz.vhromada.book.entity.Author
import cz.vhromada.book.facade.AuthorFacade
import cz.vhromada.book.web.exception.IllegalRequestException
import cz.vhromada.book.web.fo.AuthorFO
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.util.Assert
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

    /**
     * Facade for authors
     */
    private val authorFacade: AuthorFacade) : AbstractResultController() {

    /**
     * Redirect URL to list
     */
    private val listRedirectUrl = "redirect:/authors/list"

    /**
     * Message for illegal request
     */
    private val illegalRequestMessage = "Author doesn't exist."

    /**
     * Title model attribute
     */
    private val titleAttribute = "title"

    /**
     * Process new data.
     *
     * @return view for redirect to page with list of authors
     */
    @GetMapping("/new")
    fun processNew(): String {
        processResults(authorFacade.newData())

        return listRedirectUrl
    }

    /**
     * Shows page with list of authors.
     *
     * @param model model
     * @return view for page with list of authors
     */
    @GetMapping("", "/", "/list")
    fun showList(model: Model): String {
        val authorsResult = authorFacade.getAll()
        processResults(authorsResult)

        model.addAttribute("authors", authorsResult.data)
        model.addAttribute(titleAttribute, "Authors")

        return "author/index"
    }

    /**
     * Shows page with detail of author.
     *
     * @param model model
     * @param id    ID of editing author
     * @return view for page with detail of author
     * @throws IllegalRequestException  if author doesn't exist
     */
    @GetMapping("/{id}/detail")
    fun showDetail(model: Model, @PathVariable("id") id: Int): String {
        val result = authorFacade.get(id)
        processResults(result)

        val author = result.data
        if (author != null) {
            model.addAttribute("author", author)
            model.addAttribute(titleAttribute, "Author detail")

            return "author/detail"
        } else {
            throw IllegalRequestException(illegalRequestMessage)
        }
    }

    /**
     * Shows page for adding author.
     *
     * @param model model
     * @return view for page for adding author
     */
    @GetMapping("/add")
    fun showAdd(model: Model): String {
        return createFormView(model, AuthorFO(), "Add author", "add")
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
        Assert.isNull(author.id, "ID must be null.")

        if (errors.hasErrors()) {
            return createFormView(model, author, "Add author", "add")
        }
        author.normalize()
        processResults(authorFacade.add(Author(author.id, author.firstName!!, author.middleName, author.lastName!!, author.position)))

        return listRedirectUrl
    }

    /**
     * Cancel adding author.
     *
     * @return view for redirect to page with list of authors
     */
    @PostMapping(value = ["/add"], params = ["cancel"])
    fun cancelAdd(): String {
        return listRedirectUrl
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
            createFormView(model, AuthorFO(author.id, author.firstName, author.middleName, author.lastName, author.position), "Edit author", "edit")
        } else {
            throw IllegalRequestException(illegalRequestMessage)
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
        Assert.notNull(author.id, "ID mustn't be null.")

        if (errors.hasErrors()) {
            return createFormView(model, author, "Edit author", "edit")
        }
        author.normalize()
        processResults(authorFacade.update(processAuthor(Author(author.id, author.firstName!!, author.middleName, author.lastName!!, author.position))))

        return listRedirectUrl
    }

    /**
     * Cancel editing author.
     *
     * @return view for redirect to page with list of authors
     */
    @PostMapping(value = ["/edit"], params = ["cancel"])
    fun processEdit(): String {
        return listRedirectUrl
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

        return listRedirectUrl
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

        return listRedirectUrl
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

        return listRedirectUrl
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

        return listRedirectUrl
    }

    /**
     * Process updating positions.
     *
     * @return view for redirect to page with list of authors
     */
    @GetMapping("/update")
    fun processUpdatePositions(): String {
        processResults(authorFacade.updatePositions())

        return listRedirectUrl
    }

    /**
     * Returns author with ID.
     *
     * @param id ID
     * @return author with ID
     * @throws IllegalRequestException  if author doesn't exist
     */
    private fun getAuthor(id: Int): Author {
        val author = Author(id, "", null, "", 0)

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

        throw IllegalRequestException(illegalRequestMessage)
    }

    /**
     * Returns page's view with form.
     *
     * @param model  model
     * @param author   FO for author
     * @param title  page's title
     * @param action action
     * @return page's view with form
     */
    private fun createFormView(model: Model, author: AuthorFO, title: String, action: String): String {
        model.addAttribute("author", author)
        model.addAttribute(titleAttribute, title)
        model.addAttribute("action", action)

        return "author/form"
    }

}
