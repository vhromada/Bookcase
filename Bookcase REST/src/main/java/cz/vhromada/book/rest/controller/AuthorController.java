package cz.vhromada.book.rest.controller;

import java.util.List;

import cz.vhromada.book.entity.Author;
import cz.vhromada.book.facade.AuthorFacade;
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
 * A class represents controller for authors.
 *
 * @author Vladimir Hromada
 */
@RestController("authorController")
@RequestMapping("/bookcase/authors")
public class AuthorController extends AbstractBookcaseController {

    /**
     * Facade for authors
     */
    private AuthorFacade authorFacade;

    /**
     * Creates a new instance of AuthorController.
     *
     * @param authorFacade facade for authors
     * @throws IllegalArgumentException if facade for authors is null
     */
    @Autowired
    public AuthorController(final AuthorFacade authorFacade) {
        Assert.notNull(authorFacade, "Facade for authors mustn't be null.");

        this.authorFacade = authorFacade;
    }

    /**
     * Creates new data.
     *
     * @return result
     */
    @PostMapping("/new")
    public ResponseEntity<Result<Void>> newData() {
        return processResult(authorFacade.newData());
    }

    /**
     * Returns list of authors.
     *
     * @return result with list of authors
     */
    @GetMapping({ "", "/list" })
    public ResponseEntity<Result<List<Author>>> getAuthors() {
        return processResult(authorFacade.getAll());
    }

    /**
     * Returns author with ID or null if there isn't such author.
     * <br>
     * Validation errors:
     * <ul>
     * <li>ID is null</li>
     * </ul>
     *
     * @param id ID
     * @return result with author or validation errors
     */
    @GetMapping("/{id}")
    public ResponseEntity<Result<Author>> getAuthor(@PathVariable("id") final Integer id) {
        return processResult(authorFacade.get(id));
    }

    /**
     * Adds author. Sets new ID and position.
     * <br>
     * Validation errors:
     * <ul>
     * <li>Author is null</li>
     * <li>ID isn't null</li>
     * <li>Position isn't null</li>
     * <li>First name is null</li>
     * <li>First name is empty string</li>
     * <li>Middle name is empty string</li>
     * <li>Last name is null</li>
     * <li>Last name is empty string</li>
     * </ul>
     *
     * @param author author
     * @return result with validation errors
     */
    @PutMapping("/add")
    public ResponseEntity<Result<Void>> add(@RequestBody final Author author) {
        return processResult(authorFacade.add(author), HttpStatus.CREATED);
    }

    /**
     * Updates author.
     * <br>
     * Validation errors:
     * <ul>
     * <li>Author is null</li>
     * <li>ID is null</li>
     * <li>Position is null</li>
     * <li>First name is null</li>
     * <li>First name is empty string</li>
     * <li>Middle name is empty string</li>
     * <li>Last name is null</li>
     * <li>Last name is empty string</li>
     * <li>Author doesn't exist in data storage</li>
     * </ul>
     *
     * @param author new value of author
     * @return result with validation errors
     */
    @PostMapping("/update")
    public ResponseEntity<Result<Void>> update(@RequestBody final Author author) {
        return processResult(authorFacade.update(author));
    }

    /**
     * Removes author.
     * <br>
     * Validation errors:
     * <ul>
     * <li>Author is null</li>
     * <li>ID is null</li>
     * <li>Author doesn't exist in data storage</li>
     * </ul>
     *
     * @param author author
     * @return result with validation errors
     */
    @DeleteMapping("/remove")
    public ResponseEntity<Result<Void>> remove(@RequestBody final Author author) {
        return processResult(authorFacade.remove(author));
    }

    /**
     * Duplicates author.
     * <br>
     * Validation errors:
     * <ul>
     * <li>Author is null</li>
     * <li>ID is null</li>
     * <li>Author doesn't exist in data storage</li>
     * </ul>
     *
     * @param author author
     * @return result with validation errors
     */
    @PostMapping("/duplicate")
    public ResponseEntity<Result<Void>> duplicate(@RequestBody final Author author) {
        return processResult(authorFacade.duplicate(author), HttpStatus.CREATED);
    }

    /**
     * Moves author in list one position up.
     * <br>
     * Validation errors:
     * <ul>
     * <li>Author is null</li>
     * <li>ID is null</li>
     * <li>Author can't be moved up</li>
     * <li>Author doesn't exist in data storage</li>
     * </ul>
     *
     * @param author author
     * @return result with validation errors
     */
    @PostMapping("/moveUp")
    public ResponseEntity<Result<Void>> moveUp(@RequestBody final Author author) {
        return processResult(authorFacade.moveUp(author));
    }

    /**
     * Moves author in list one position down.
     * <br>
     * Validation errors:
     * <ul>
     * <li>Author is null</li>
     * <li>ID is null</li>
     * <li>Author can't be moved down</li>
     * <li>Author doesn't exist in data storage</li>
     * </ul>
     *
     * @param author author
     * @return result with validation errors
     */
    @PostMapping("/moveDown")
    public ResponseEntity<Result<Void>> moveDown(@RequestBody final Author author) {
        return processResult(authorFacade.moveDown(author));
    }

    /**
     * Updates positions.
     *
     * @return result
     */
    @PostMapping("/updatePositions")
    public ResponseEntity<Result<Void>> updatePositions() {
        return processResult(authorFacade.updatePositions());
    }

}
