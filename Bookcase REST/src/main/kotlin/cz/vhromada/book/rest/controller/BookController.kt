package cz.vhromada.book.rest.controller

import cz.vhromada.book.entity.Book
import cz.vhromada.book.facade.BookFacade
import cz.vhromada.result.Result
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * A class represents controller for books.
 *
 * @author Vladimir Hromada
 */
@RestController("bookController")
@RequestMapping("/bookcase/books")
class BookController(

    /**
     * Facade for books
     */
    private val bookFacade: BookFacade) : AbstractBookcaseController() {

    /**
     * Creates new data.
     *
     * @return result
     */
    @PostMapping("/new")
    fun newData(): ResponseEntity<Result<Void>> {
        return processResult(bookFacade.newData())
    }

    /**
     * Returns list of books.
     *
     * @return result with list of books
     */
    @GetMapping("", "/", "/list")
    fun getBooks(): ResponseEntity<Result<List<Book>>> {
        return processResult(bookFacade.getAll())
    }

    /**
     * Returns book with ID or null if there isn't such book.
     *
     * @param id ID
     * @return result with book
     */
    @GetMapping("/{id}")
    fun getBook(@PathVariable("id") id: Int): ResponseEntity<Result<Book>> {
        return processResult(bookFacade.get(id))
    }

    /**
     * Adds book. Sets new ID and position.
     *
     * Validation errors:
     * * ID isn't null
     * * Czech name is empty string
     * * Original name is empty string
     * * ISBN is empty string
     * * Issue year isn't between 1940 and current year
     * * Description is empty string
     * * Note is empty string
     * * Author ID is null
     * * Author first name is empty string
     * * Author middle name is empty string
     * * Author last name is empty string
     * * Author doesn't exist
     * * Category ID is null
     * * Category name is empty string
     * * Category doesn't exist
     *
     * @param book book
     * @return result with validation errors
     */
    @PutMapping("/add")
    fun add(@RequestBody book: Book): ResponseEntity<Result<Void>> {
        return processResult(bookFacade.add(book), HttpStatus.CREATED)
    }

    /**
     * Updates book.
     *
     * Validation errors:
     * * ID is null
     * * Czech name is empty string
     * * Original name is empty string
     * * ISBN is empty string
     * * Issue year isn't between 1940 and current year
     * * Description is empty string
     * * Note is empty string
     * * Author ID is null
     * * Author first name is empty string
     * * Author middle name is empty string
     * * Author last name is empty string
     * * Author doesn't exist
     * * Category ID is null
     * * Category name is empty string
     * * Category doesn't exist
     * * Book doesn't exist
     *
     * @param book new value of book
     * @return result with validation errors
     */
    @PostMapping("/update")
    fun update(@RequestBody book: Book): ResponseEntity<Result<Void>> {
        return processResult(bookFacade.update(book))
    }

    /**
     * Removes book.
     *
     * Validation errors:
     * * ID is null
     * * Book doesn't exist
     *
     * @param book book
     * @return result with validation errors
     */
    @DeleteMapping("/remove")
    fun remove(@RequestBody book: Book): ResponseEntity<Result<Void>> {
        return processResult(bookFacade.remove(book))
    }

    /**
     * Duplicates book.
     *
     * Validation errors:
     * * ID is null
     * * Book doesn't exist
     *
     * @param book book
     * @return result with validation errors
     */
    @PostMapping("/duplicate")
    fun duplicate(@RequestBody book: Book): ResponseEntity<Result<Void>> {
        return processResult(bookFacade.duplicate(book), HttpStatus.CREATED)
    }

    /**
     * Moves book in list one position up.
     *
     * Validation errors:
     * * ID is null
     * * Book can't be moved up
     * * Book doesn't exist
     *
     * @param book book
     * @return result with validation errors
     */
    @PostMapping("/moveUp")
    fun moveUp(@RequestBody book: Book): ResponseEntity<Result<Void>> {
        return processResult(bookFacade.moveUp(book))
    }

    /**
     * Moves book in list one position down.
     *
     * Validation errors:
     * * ID is null
     * * Book can't be moved down
     * * Book doesn't exist
     *
     *
     * @param book book
     * @return result with validation errors
     */
    @PostMapping("/moveDown")
    fun moveDown(@RequestBody book: Book): ResponseEntity<Result<Void>> {
        return processResult(bookFacade.moveDown(book))
    }

    /**
     * Updates positions.
     *
     * @return result
     */
    @PostMapping("/updatePositions")
    fun updatePositions(): ResponseEntity<Result<Void>> {
        return processResult(bookFacade.updatePositions())
    }

}