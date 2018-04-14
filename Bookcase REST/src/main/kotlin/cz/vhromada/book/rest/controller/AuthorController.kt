package cz.vhromada.book.rest.controller

import cz.vhromada.book.entity.Author
import cz.vhromada.book.facade.AuthorFacade
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
 * A class represents controller for authors.
 *
 * @author Vladimir Hromada
 */
@RestController("authorController")
@RequestMapping("/bookcase/authors")
class AuthorController(

    /**
     * Facade for authors
     */
    private val authorFacade: AuthorFacade) : AbstractBookcaseController() {

    /**
     * Creates new data.
     *
     * @return result
     */
    @PostMapping("/new")
    fun newData(): ResponseEntity<Result<Void>> {
        return processResult(authorFacade.newData())
    }

    /**
     * Returns list of authors.
     *
     * @return result with list of authors
     */
    @GetMapping("", "/", "/list")
    fun getAuthors(): ResponseEntity<Result<List<Author>>> {
        return processResult(authorFacade.getAll())
    }

    /**
     * Returns author with ID or null if there isn't such author.
     *
     * @param id ID
     * @return result with author
     */
    @GetMapping("/{id}")
    fun getAuthor(@PathVariable("id") id: Int): ResponseEntity<Result<Author>> {
        return processResult(authorFacade.get(id))
    }

    /**
     * Adds author. Sets new ID and position.
     *
     * Validation errors:
     * * ID isn't null
     * * First name is empty string
     * * Middle name is empty string
     * * Last name is empty string
     *
     * @param author author
     * @return result with validation errors
     */
    @PutMapping("/add")
    fun add(@RequestBody author: Author): ResponseEntity<Result<Void>> {
        return processResult(authorFacade.add(author), HttpStatus.CREATED)
    }

    /**
     * Updates author.
     *
     * Validation errors:
     * * ID is null
     * * First name is empty string
     * * Middle name is empty string
     * * Last name is empty string
     * * Author doesn't exist
     *
     * @param author new value of author
     * @return result with validation errors
     */
    @PostMapping("/update")
    fun update(@RequestBody author: Author): ResponseEntity<Result<Void>> {
        return processResult(authorFacade.update(author))
    }

    /**
     * Removes author.
     *
     * Validation errors:
     * * ID is null
     * * Author doesn't exist
     *
     * @param author author
     * @return result with validation errors
     */
    @DeleteMapping("/remove")
    fun remove(@RequestBody author: Author): ResponseEntity<Result<Void>> {
        return processResult(authorFacade.remove(author))
    }

    /**
     * Duplicates author.
     *
     * Validation errors:
     * * ID is null
     * * Author doesn't exist
     *
     * @param author author
     * @return result with validation errors
     */
    @PostMapping("/duplicate")
    fun duplicate(@RequestBody author: Author): ResponseEntity<Result<Void>> {
        return processResult(authorFacade.duplicate(author), HttpStatus.CREATED)
    }

    /**
     * Moves author in list one position up.
     *
     * Validation errors:
     * * ID is null
     * * Author can't be moved up
     * * Author doesn't exist
     *
     * @param author author
     * @return result with validation errors
     */
    @PostMapping("/moveUp")
    fun moveUp(@RequestBody author: Author): ResponseEntity<Result<Void>> {
        return processResult(authorFacade.moveUp(author))
    }

    /**
     * Moves author in list one position down.
     *
     * Validation errors:
     * * ID is null
     * * Author can't be moved down
     * * Author doesn't exist
     *
     *
     * @param author author
     * @return result with validation errors
     */
    @PostMapping("/moveDown")
    fun moveDown(@RequestBody author: Author): ResponseEntity<Result<Void>> {
        return processResult(authorFacade.moveDown(author))
    }

    /**
     * Updates positions.
     *
     * @return result
     */
    @PostMapping("/updatePositions")
    fun updatePositions(): ResponseEntity<Result<Void>> {
        return processResult(authorFacade.updatePositions())
    }

}
