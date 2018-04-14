package cz.vhromada.book.rest.controller

import cz.vhromada.book.entity.Category
import cz.vhromada.book.facade.CategoryFacade
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
 * A class represents controller for categories.
 *
 * @author Vladimir Hromada
 */
@RestController("categoryController")
@RequestMapping("/bookcase/categories")
class CategoryController(

    /**
     * Facade for categories
     */
    private val categoryFacade: CategoryFacade) : AbstractBookcaseController() {

    /**
     * Creates new data.
     *
     * @return result
     */
    @PostMapping("/new")
    fun newData(): ResponseEntity<Result<Void>> {
        return processResult(categoryFacade.newData())
    }

    /**
     * Returns list of categories.
     *
     * @return result with list of categories
     */
    @GetMapping("", "/", "/list")
    fun getCategories(): ResponseEntity<Result<List<Category>>> {
        return processResult(categoryFacade.getAll())
    }

    /**
     * Returns category with ID or null if there isn't such category.
     *
     * @param id ID
     * @return result with category
     */
    @GetMapping("/{id}")
    fun getCategory(@PathVariable("id") id: Int): ResponseEntity<Result<Category>> {
        return processResult(categoryFacade.get(id))
    }

    /**
     * Adds category. Sets new ID and position.
     *
     * Validation errors:
     * * ID isn't null
     * * Name is empty string
     *
     * @param category category
     * @return result with validation errors
     */
    @PutMapping("/add")
    fun add(@RequestBody category: Category): ResponseEntity<Result<Void>> {
        return processResult(categoryFacade.add(category), HttpStatus.CREATED)
    }

    /**
     * Updates category.
     *
     * Validation errors:
     * * ID is null
     * * Name is empty string
     * * Category doesn't exist
     *
     * @param category new value of category
     * @return result with validation errors
     */
    @PostMapping("/update")
    fun update(@RequestBody category: Category): ResponseEntity<Result<Void>> {
        return processResult(categoryFacade.update(category))
    }

    /**
     * Removes category.
     *
     * Validation errors:
     * * ID is null
     * * Category doesn't exist
     *
     * @param category category
     * @return result with validation errors
     */
    @DeleteMapping("/remove")
    fun remove(@RequestBody category: Category): ResponseEntity<Result<Void>> {
        return processResult(categoryFacade.remove(category))
    }

    /**
     * Duplicates category.
     *
     * Validation errors:
     * * ID is null
     * * Category doesn't exist
     *
     * @param category category
     * @return result with validation errors
     */
    @PostMapping("/duplicate")
    fun duplicate(@RequestBody category: Category): ResponseEntity<Result<Void>> {
        return processResult(categoryFacade.duplicate(category), HttpStatus.CREATED)
    }

    /**
     * Moves category in list one position up.
     *
     * Validation errors:
     * * ID is null
     * * Category can't be moved up
     * * Category doesn't exist
     *
     * @param category category
     * @return result with validation errors
     */
    @PostMapping("/moveUp")
    fun moveUp(@RequestBody category: Category): ResponseEntity<Result<Void>> {
        return processResult(categoryFacade.moveUp(category))
    }

    /**
     * Moves category in list one position down.
     *
     * Validation errors:
     * * ID is null
     * * Category can't be moved down
     * * Category doesn't exist
     *
     *
     * @param category category
     * @return result with validation errors
     */
    @PostMapping("/moveDown")
    fun moveDown(@RequestBody category: Category): ResponseEntity<Result<Void>> {
        return processResult(categoryFacade.moveDown(category))
    }

    /**
     * Updates positions.
     *
     * @return result
     */
    @PostMapping("/updatePositions")
    fun updatePositions(): ResponseEntity<Result<Void>> {
        return processResult(categoryFacade.updatePositions())
    }

}
