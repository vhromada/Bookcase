package com.github.vhromada.bookcase.web.controller

import com.github.vhromada.bookcase.entity.Author
import com.github.vhromada.bookcase.entity.Book
import com.github.vhromada.bookcase.facade.AuthorFacade
import com.github.vhromada.bookcase.facade.BookFacade
import com.github.vhromada.bookcase.web.exception.IllegalRequestException
import com.github.vhromada.bookcase.web.fo.BookFO
import com.github.vhromada.bookcase.web.mapper.BookMapper
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
 * A class represents controller for books.
 *
 * @author Vladimir Hromada
 */
@Controller("bookController")
@RequestMapping("/books")
class BookController(
        private val bookFacade: BookFacade,
        private val authorFacade: AuthorFacade,
        private val bookMapper: BookMapper) : AbstractResultController() {

    /**
     * Process new data.
     *
     * @return view for redirect to page with list of books
     */
    @GetMapping("/new")
    fun processNew(): String {
        processResults(bookFacade.newData())

        return LIST_REDIRECT_URL
    }

    /**
     * Shows page with list of books.
     *
     * @param model model
     * @return view for page with list of books
     */
    @GetMapping("", "/list")
    fun showList(model: Model): String {
        val booksResult = bookFacade.getAll()
        processResults(booksResult)

        model.addAttribute("books", booksResult.data)
        model.addAttribute("title", "Books")

        return "book/index"
    }

    /**
     * Shows page with detail of book.
     *
     * @param model model
     * @param id    ID of editing book
     * @return view for page with detail of book
     * @throws IllegalRequestException  if book doesn't exist
     */
    @GetMapping("/{id}/detail")
    fun showDetail(model: Model, @PathVariable("id") id: Int): String {
        val result = bookFacade.get(id)
        processResults(result)

        val book = result.data
        if (book != null) {
            model.addAttribute("book", book)
            model.addAttribute("title", "Book detail")

            return "book/detail"
        } else {
            throw IllegalRequestException(ILLEGAL_REQUEST_MESSAGE)
        }
    }

    /**
     * Shows page for adding book.
     *
     * @param model model
     * @return view for page for adding book
     */
    @GetMapping("/add")
    fun showAdd(model: Model): String {
        val book = BookFO(
                id = null,
                czechName = null,
                originalName = null,
                isbn = null,
                issueYear = null,
                description = null,
                note = null,
                position = null,
                authors = null)
        return createFormView(model, book, "Add book", "add")
    }

    /**
     * Process adding book.
     *
     * @param model  model
     * @param book   FO for book
     * @param errors errors
     * @return view for redirect to page with list of books (no errors) or view for page for adding book (errors)
     * @throws IllegalArgumentException if ID isn't null
     */
    @PostMapping(value = ["/add"], params = ["create"])
    fun processAdd(model: Model, @ModelAttribute("book") @Valid book: BookFO, errors: Errors): String {
        require(book.id == null) { "ID must be null." }

        if (errors.hasErrors()) {
            return createFormView(model, book, "Add book", "add")
        }

        processResults(bookFacade.add(bookMapper.mapBack(book).copy(authors = getAuthors(book.authors!!))))

        return LIST_REDIRECT_URL
    }

    /**
     * Cancel adding book.
     *
     * @return view for redirect to page with list of books
     */
    @PostMapping(value = ["/add"], params = ["cancel"])
    fun cancelAdd(): String {
        return LIST_REDIRECT_URL
    }

    /**
     * Shows page for editing book.
     *
     * @param model model
     * @param id    ID of editing book
     * @return view for page for editing book
     * @throws IllegalRequestException  if book doesn't exist
     */
    @GetMapping("/edit/{id}")
    fun showEdit(model: Model, @PathVariable("id") id: Int): String {
        val result = bookFacade.get(id)
        processResults(result)

        val book = result.data
        return if (book != null) {
            createFormView(model, bookMapper.map(book), "Edit book", "edit")
        } else {
            throw IllegalRequestException(ILLEGAL_REQUEST_MESSAGE)
        }
    }

    /**
     * Process editing book.
     *
     * @param model  model
     * @param book   FO for book
     * @param errors errors
     * @return view for redirect to page with list of books (no errors) or view for page for editing book (errors)
     * @throws IllegalArgumentException if D is null
     * @throws IllegalRequestException  if book doesn't exist
     */
    @PostMapping(value = ["/edit"], params = ["update"])
    fun processEdit(model: Model, @ModelAttribute("book") @Valid book: BookFO, errors: Errors): String {
        require(book.id != null) { "ID mustn't be null." }

        if (errors.hasErrors()) {
            return createFormView(model, book, "Edit book", "edit")
        }

        processResults(bookFacade.update(processBook(bookMapper.mapBack(book).copy(authors = getAuthors(book.authors!!)))))

        return LIST_REDIRECT_URL
    }

    /**
     * Cancel editing book.
     *
     * @return view for redirect to page with list of books
     */
    @PostMapping(value = ["/edit"], params = ["cancel"])
    fun processEdit(): String {
        return LIST_REDIRECT_URL
    }

    /**
     * Process duplicating book.
     *
     * @param id ID of duplicating book
     * @return view for redirect to page with list of books
     * @throws IllegalRequestException  if book doesn't exist
     */
    @GetMapping("/duplicate/{id}")
    fun processDuplicate(@PathVariable("id") id: Int): String {
        processResults(bookFacade.duplicate(getBook(id)))

        return LIST_REDIRECT_URL
    }

    /**
     * Process removing book.
     *
     * @param id ID of removing book
     * @return view for redirect to page with list of books
     * @throws IllegalRequestException  if book doesn't exist
     */
    @GetMapping("/remove/{id}")
    fun processRemove(@PathVariable("id") id: Int): String {
        processResults(bookFacade.remove(getBook(id)))

        return LIST_REDIRECT_URL
    }

    /**
     * Process moving book up.
     *
     * @param id ID of moving book
     * @return view for redirect to page with list of books
     * @throws IllegalRequestException  if book doesn't exist
     */
    @GetMapping("/moveUp/{id}")
    fun processMoveUp(@PathVariable("id") id: Int): String {
        processResults(bookFacade.moveUp(getBook(id)))

        return LIST_REDIRECT_URL
    }

    /**
     * Process moving book down.
     *
     * @param id ID of moving book
     * @return view for redirect to page with list of books
     * @throws IllegalRequestException  if book doesn't exist
     */
    @GetMapping("/moveDown/{id}")
    fun processMoveDown(@PathVariable("id") id: Int): String {
        processResults(bookFacade.moveDown(getBook(id)))

        return LIST_REDIRECT_URL
    }

    /**
     * Process updating positions.
     *
     * @return view for redirect to page with list of books
     */
    @GetMapping("/update")
    fun processUpdatePositions(): String {
        processResults(bookFacade.updatePositions())

        return LIST_REDIRECT_URL
    }

    /**
     * Returns page's view with form.
     *
     * @param model  model
     * @param book   FO for book
     * @param title  page's title
     * @param action action
     * @return page's view with form
     */
    private fun createFormView(model: Model, book: BookFO, title: String, action: String): String {
        val authors = authorFacade.getAll()
        processResults(authors)

        model.addAttribute("book", book)
        model.addAttribute("title", title)
        model.addAttribute("authors", authors.data)
        model.addAttribute("action", action)

        return "book/form"
    }

    /**
     * Returns book with ID.
     *
     * @param id ID
     * @return book with ID
     * @throws IllegalRequestException  if book doesn't exist
     */
    private fun getBook(id: Int): Book {
        val book = Book(
                id = id,
                czechName = null,
                originalName = null,
                isbn = null,
                issueYear = null,
                description = null,
                note = null,
                position = null,
                authors = null)

        return processBook(book)
    }

    /**
     * Returns processed book.
     *
     * @param book for processing
     * @return processed book
     * @throws IllegalRequestException if book doesn't exist
     */
    private fun processBook(book: Book): Book {
        val bookResult = bookFacade.get(book.id!!)
        processResults(bookResult)

        if (bookResult.data != null) {
            return book
        }
        throw IllegalRequestException(ILLEGAL_REQUEST_MESSAGE)
    }

    /**
     * Returns authors.
     *
     * @param source list of authors' ID
     * @return authors
     */
    private fun getAuthors(source: List<Int?>): List<Author> {
        return source.map {
            val result = authorFacade.get(it!!)
            processResults(result)

            result.data!!
        }
    }

    companion object {

        /**
         * Redirect URL to list
         */
        private const val LIST_REDIRECT_URL = "redirect:/books/list"

        /**
         * Message for illegal request
         */
        private const val ILLEGAL_REQUEST_MESSAGE = "Book doesn't exist."


    }

}
