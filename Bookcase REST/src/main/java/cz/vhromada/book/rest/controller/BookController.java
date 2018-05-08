package cz.vhromada.book.rest.controller;

import java.util.List;

import cz.vhromada.book.entity.Book;
import cz.vhromada.book.facade.BookFacade;
import cz.vhromada.result.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A class represents controller for books.
 *
 * @author Vladimir Hromada
 */
@RestController("bookController")
@RequestMapping("/bookcase/books")
public class BookController extends AbstractBookcaseController {

    /**
     * Facade for books
     */
    private BookFacade bookFacade;

    /**
     * Creates a new instance of BookController.
     *
     * @param bookFacade facade for books
     * @throws IllegalArgumentException if facade for books is null
     */
    @Autowired
    public BookController(final BookFacade bookFacade) {
        Assert.notNull(bookFacade, "Facade for books mustn't be null.");

        this.bookFacade = bookFacade;
    }

    /**
     * Creates new data.
     *
     * @return result
     */
    @PostMapping("/new")
    public ResponseEntity<Result<Void>> newData() {
        return processResult(bookFacade.newData());
    }

    /**
     * Returns list of books.
     *
     * @return result with list of books
     */
    @GetMapping({ "", "/list" })
    public ResponseEntity<Result<List<Book>>> getBooks() {
        return processResult(bookFacade.getAll());
    }

    /**
     * Returns book with ID or null if there isn't such book.
     * <br>
     * Validation errors:
     * <ul>
     * <li>ID is null</li>
     * </ul>
     *
     * @param id ID
     * @return result with book or validation errors
     */
    @GetMapping("/{id}")
    public ResponseEntity<Result<Book>> getBook(@PathVariable("id") final Integer id) {
        return processResult(bookFacade.get(id));
    }

    /**
     * Adds book. Sets new ID and position.
     * <br>
     * Validation errors:
     * <ul>
     * <li>Book is null</li>
     * <li>ID isn't null</li>
     * <li>Position isn't null</li>
     * <li>Czech name is null</li>
     * <li>Czech name is empty string</li>
     * <li>Original name is null</li>
     * <li>Original name is empty string</li>
     * <li>Languages are null</li>
     * <li>Languages are empty</li>
     * <li>Languages contain null value</li>
     * <li>ISBN is empty string</li>
     * <li>Issue year isn't between 1940 and current year/</li>
     * <li>Description is null</li>
     * <li>Description is empty string</li>
     * <li>Note is empty string</li>
     * <li>Author ID is null</li>
     * <li>Author first name is empty string</li>
     * <li>Author middle name is empty string</li>
     * <li>Author last name is empty string</li>
     * <li>Author doesn't exist in data storage</li>
     * <li>Category ID is null</li>
     * <li>Category name is empty string</li>
     * <li>Category doesn't exist in data storage</li>
     * </ul>
     *
     * @param book book
     * @return result with validation errors
     */
    @PutMapping("/add")
    public ResponseEntity<Result<Void>> add(@RequestBody final Book book) {
        return processResult(bookFacade.add(book), HttpStatus.CREATED);
    }

    /**
     * Updates book.
     * <br>
     * Validation errors:
     * <ul>
     * <li>Book is null</li>
     * <li>ID is null</li>
     * <li>Position is null</li>
     * <li>Czech name is null</li>
     * <li>Czech name is empty string</li>
     * <li>Original name is null</li>
     * <li>Original name is empty string</li>
     * <li>Languages are null</li>
     * <li>Languages are empty</li>
     * <li>Languages contain null value</li>
     * <li>ISBN is empty string</li>
     * <li>Issue year isn't between 1940 and current year/</li>
     * <li>Description is null</li>
     * <li>Description is empty string</li>
     * <li>Note is empty string</li>
     * <li>Author ID is null</li>
     * <li>Author first name is empty string</li>
     * <li>Author middle name is empty string</li>
     * <li>Author last name is empty string</li>
     * <li>Author doesn't exist in data storage</li>
     * <li>Category ID is null</li>
     * <li>Category name is empty string</li>
     * <li>Category doesn't exist in data storage</li>
     * <li>Book doesn't exist in data storage</li>
     * </ul>
     *
     * @param book new value of book
     * @return result with validation errors
     */
    @PostMapping("/update")
    public ResponseEntity<Result<Void>> update(@RequestBody final Book book) {
        return processResult(bookFacade.update(book));
    }

    /**
     * Removes book.
     * <br>
     * Validation errors:
     * <ul>
     * <li>Book is null</li>
     * <li>ID is null</li>
     * <li>Book doesn't exist in data storage</li>
     * </ul>
     *
     * @param book book
     * @return result with validation errors
     */
    @DeleteMapping("/remove")
    public ResponseEntity<Result<Void>> remove(@RequestBody final Book book) {
        return processResult(bookFacade.remove(book));
    }

    /**
     * Duplicates book.
     * <br>
     * Validation errors:
     * <ul>
     * <li>Book is null</li>
     * <li>ID is null</li>
     * <li>Book doesn't exist in data storage</li>
     * </ul>
     *
     * @param book book
     * @return result with validation errors
     */
    @PostMapping("/duplicate")
    public ResponseEntity<Result<Void>> duplicate(@RequestBody final Book book) {
        return processResult(bookFacade.duplicate(book), HttpStatus.CREATED);
    }

    /**
     * Moves book in list one position up.
     * <br>
     * Validation errors:
     * <ul>
     * <li>Book is null</li>
     * <li>ID is null</li>
     * <li>Book can't be moved up</li>
     * <li>Book doesn't exist in data storage</li>
     * </ul>
     *
     * @param book book
     * @return result with validation errors
     */
    @PostMapping("/moveUp")
    public ResponseEntity<Result<Void>> moveUp(@RequestBody final Book book) {
        return processResult(bookFacade.moveUp(book));
    }

    /**
     * Moves book in list one position down.
     * <br>
     * Validation errors:
     * <ul>
     * <li>Book is null</li>
     * <li>ID is null</li>
     * <li>Book can't be moved down</li>
     * <li>Book doesn't exist in data storage</li>
     * </ul>
     *
     * @param book book
     * @return result with validation errors
     */
    @PostMapping("/moveDown")
    public ResponseEntity<Result<Void>> moveDown(@RequestBody final Book book) {
        return processResult(bookFacade.moveDown(book));
    }

    /**
     * Updates positions.
     *
     * @return result
     */
    @PostMapping("/updatePositions")
    public ResponseEntity<Result<Void>> updatePositions() {
        return processResult(bookFacade.updatePositions());
    }

}
