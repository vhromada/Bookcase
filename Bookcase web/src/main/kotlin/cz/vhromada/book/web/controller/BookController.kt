package cz.vhromada.book.web.controller

import cz.vhromada.book.common.Language
import cz.vhromada.book.common.Movable
import cz.vhromada.book.entity.Author
import cz.vhromada.book.entity.Book
import cz.vhromada.book.entity.Category
import cz.vhromada.book.facade.AuthorFacade
import cz.vhromada.book.facade.BookFacade
import cz.vhromada.book.facade.BookcaseFacade
import cz.vhromada.book.facade.CategoryFacade
import cz.vhromada.book.web.exception.IllegalRequestException
import cz.vhromada.book.web.fo.BookFO
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
 * A class represents controller for books.
 *
 * @author Vladimir Hromada
 */
@Controller("bookController")
@RequestMapping("/books")
class BookController(

    /**
     * Facade for books
     */
    private val bookFacade: BookFacade,

    /**
     * Facade for authors
     */
    private val authorFacade: AuthorFacade,

    /**
     * Facade for categories
     */
    private val categoryFacade: CategoryFacade) : AbstractResultController() {

    /**
     * Redirect URL to list
     */
    private val listRedirectUrl = "redirect:/books/list"

    /**
     * Message for illegal request
     */
    private val illegalRequestMessage = "Book doesn't exist."

    /**
     * Title model attribute
     */
    private val titleAttribute = "title"

    /**
     * Process new data.
     *
     * @return view for redirect to page with list of books
     */
    @GetMapping("/new")
    fun processNew(): String {
        processResults(bookFacade.newData())

        return listRedirectUrl
    }

    /**
     * Shows page with list of books.
     *
     * @param model model
     * @return view for page with list of books
     */
    @GetMapping("", "/", "/list")
    fun showList(model: Model): String {
        val booksResult = bookFacade.getAll()
        processResults(booksResult)

        model.addAttribute("books", booksResult.data)
        model.addAttribute(titleAttribute, "Books")

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
            model.addAttribute(titleAttribute, "Book detail")

            return "book/detail"
        } else {
            throw IllegalRequestException(illegalRequestMessage)
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
        return createFormView(model, BookFO(), "Add book", "add")
    }

    /**
     * Process adding book.
     *
     * @param model  model
     * @param book FO for book
     * @param errors errors
     * @return view for redirect to page with list of books (no errors) or view for page for adding book (errors)
     * @throws IllegalArgumentException if ID isn't null
     */
    @PostMapping(value = ["/add"], params = ["create"])
    fun processAdd(model: Model, @ModelAttribute("book") @Valid book: BookFO, errors: Errors): String {
        Assert.isNull(book.id, "ID must be null.")

        if (errors.hasErrors()) {
            return createFormView(model, book, "Add book", "add")
        }
        book.normalize()
        processResults(bookFacade.add(Book(book.id, book.czechName!!, book.originalName!!, book.languages, book.isbn, book.issueYear!!.toInt(), book.description!!, book.electronic, book.paper, book.note, book.position, getAuthors(book.authors), getCategories(book.categories))))

        return listRedirectUrl
    }

    /**
     * Cancel adding book.
     *
     * @return view for redirect to page with list of books
     */
    @PostMapping(value = ["/add"], params = ["cancel"])
    fun cancelAdd(): String {
        return listRedirectUrl
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
            createFormView(model, BookFO(book.id, book.czechName, book.originalName, book.languages, book.isbn, book.issueYear.toString(), book.description, book.electronic, book.paper, book.note, book.position, getId(book.authors), getId(book.categories)), "Edit book", "edit")
        } else {
            throw IllegalRequestException(illegalRequestMessage)
        }
    }

    /**
     * Process editing book.
     *
     * @param model  model
     * @param book FO for book
     * @param errors errors
     * @return view for redirect to page with list of books (no errors) or view for page for editing book (errors)
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if book doesn't exist
     */
    @PostMapping(value = ["/edit"], params = ["update"])
    fun processEdit(model: Model, @ModelAttribute("book") @Valid book: BookFO, errors: Errors): String {
        Assert.notNull(book.id, "ID mustn't be null.")

        if (errors.hasErrors()) {
            return createFormView(model, book, "Edit book", "edit")
        }
        book.normalize()
        processResults(bookFacade.update(processBook(Book(book.id, book.czechName!!, book.originalName!!, book.languages, book.isbn, book.issueYear!!.toInt(), book.description!!, book.electronic, book.paper, book.note, book.position, getAuthors(book.authors), getCategories(book.categories)))))

        return listRedirectUrl
    }

    /**
     * Cancel editing book.
     *
     * @return view for redirect to page with list of books
     */
    @PostMapping(value = ["/edit"], params = ["cancel"])
    fun processEdit(): String {
        return listRedirectUrl
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

        return listRedirectUrl
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

        return listRedirectUrl
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

        return listRedirectUrl
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

        return listRedirectUrl
    }

    /**
     * Process updating positions.
     *
     * @return view for redirect to page with list of books
     */
    @GetMapping("/update")
    fun processUpdatePositions(): String {
        processResults(bookFacade.updatePositions())

        return listRedirectUrl
    }

    /**
     * Returns book with ID.
     *
     * @param id ID
     * @return book with ID
     * @throws IllegalRequestException  if book doesn't exist
     */
    private fun getBook(id: Int): Book {
        val book = Book(id, "", "", emptyList(), null, 2000, "", false, false, null, 0, emptyList(), emptyList())

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

        throw IllegalRequestException(illegalRequestMessage)
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
        val categories = categoryFacade.getAll()
        processResults(authors, categories)

        model.addAttribute("book", book)
        model.addAttribute(titleAttribute, title)
        model.addAttribute("action", action)
        model.addAttribute("languages", Language.values())
        model.addAttribute("authors", authors.data)
        model.addAttribute("categories", categories.data)

        return "book/form"
    }

    /**
     * Returns authors.
     *
     * @param source list of authors
     * @return authors
     */
    private fun getAuthors(source: List<Int>): List<Author> {
        return source.map { id -> getItem(id, authorFacade) }
    }

    /**
     * Returns categories.
     *
     * @param source list of categories
     * @return categories
     */
    private fun getCategories(source: List<Int>): List<Category> {
        return source.map { id -> getItem(id, categoryFacade) }
    }

    /**
     * Returns item.
     *
     * @param source item ID
     * @param facade facade
     * @return item
     */
    private fun <T : Movable> getItem(source: Int, facade: BookcaseFacade<T>): T {
        val item = facade.get(source)
        processResults(item)

        return item.data
    }

    /**
     * Returns IDs.
     *
     * @param source list of data
     * @return IDs
     */
    private fun getId(source: List<Movable>): List<Int> {
        return source.map { item -> item.id!! }
    }

}
