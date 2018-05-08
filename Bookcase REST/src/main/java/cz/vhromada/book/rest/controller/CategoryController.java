package cz.vhromada.book.rest.controller;

import java.util.List;

import cz.vhromada.book.entity.Category;
import cz.vhromada.book.facade.CategoryFacade;
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
 * A class represents controller for categories.
 *
 * @author Vladimir Hromada
 */
@RestController("categoryController")
@RequestMapping("/bookcase/categories")
public class CategoryController extends AbstractBookcaseController {

    /**
     * Facade for categories
     */
    private CategoryFacade categoryFacade;

    /**
     * Creates a new instance of CategoryController.
     *
     * @param categoryFacade facade for categories
     * @throws IllegalArgumentException if facade for categories is null
     */
    @Autowired
    public CategoryController(final CategoryFacade categoryFacade) {
        Assert.notNull(categoryFacade, "Facade for categories mustn't be null.");

        this.categoryFacade = categoryFacade;
    }

    /**
     * Creates new data.
     *
     * @return result
     */
    @PostMapping("/new")
    public ResponseEntity<Result<Void>> newData() {
        return processResult(categoryFacade.newData());
    }

    /**
     * Returns list of categories.
     *
     * @return result with list of categories
     */
    @GetMapping({ "", "/list" })
    public ResponseEntity<Result<List<Category>>> getCategories() {
        return processResult(categoryFacade.getAll());
    }

    /**
     * Returns category with ID or null if there isn't such category.
     * <br>
     * Validation errors:
     * <ul>
     * <li>ID is null</li>
     * </ul>
     *
     * @param id ID
     * @return result with category or validation errors
     */
    @GetMapping("/{id}")
    public ResponseEntity<Result<Category>> getCategory(@PathVariable("id") final Integer id) {
        return processResult(categoryFacade.get(id));
    }

    /**
     * Adds category. Sets new ID and position.
     * <br>
     * Validation errors:
     * <ul>
     * <li>Category is null</li>
     * <li>ID isn't null</li>
     * <li>Position isn't null</li>
     * <li>Name is null</li>
     * <li>Name is empty string</li>
     * </ul>
     *
     * @param category category
     * @return result with validation errors
     */
    @PutMapping("/add")
    public ResponseEntity<Result<Void>> add(@RequestBody final Category category) {
        return processResult(categoryFacade.add(category), HttpStatus.CREATED);
    }

    /**
     * Updates category.
     * <br>
     * Validation errors:
     * <ul>
     * <li>Category is null</li>
     * <li>ID is null</li>
     * <li>Position is null</li>
     * <li>Name is null</li>
     * <li>Name is empty string</li>
     * <li>Category doesn't exist in data storage</li>
     * </ul>
     *
     * @param category new value of category
     * @return result with validation errors
     */
    @PostMapping("/update")
    public ResponseEntity<Result<Void>> update(@RequestBody final Category category) {
        return processResult(categoryFacade.update(category));
    }

    /**
     * Removes category.
     * <br>
     * Validation errors:
     * <ul>
     * <li>Category is null</li>
     * <li>ID is null</li>
     * <li>Category doesn't exist in data storage</li>
     * </ul>
     *
     * @param category category
     * @return result with validation errors
     */
    @DeleteMapping("/remove")
    public ResponseEntity<Result<Void>> remove(@RequestBody final Category category) {
        return processResult(categoryFacade.remove(category));
    }

    /**
     * Duplicates category.
     * <br>
     * Validation errors:
     * <ul>
     * <li>Category is null</li>
     * <li>ID is null</li>
     * <li>Category doesn't exist in data storage</li>
     * </ul>
     *
     * @param category category
     * @return result with validation errors
     */
    @PostMapping("/duplicate")
    public ResponseEntity<Result<Void>> duplicate(@RequestBody final Category category) {
        return processResult(categoryFacade.duplicate(category), HttpStatus.CREATED);
    }

    /**
     * Moves category in list one position up.
     * <br>
     * Validation errors:
     * <ul>
     * <li>Category is null</li>
     * <li>ID is null</li>
     * <li>Category can't be moved up</li>
     * <li>Category doesn't exist in data storage</li>
     * </ul>
     *
     * @param category category
     * @return result with validation errors
     */
    @PostMapping("/moveUp")
    public ResponseEntity<Result<Void>> moveUp(@RequestBody final Category category) {
        return processResult(categoryFacade.moveUp(category));
    }

    /**
     * Moves category in list one position down.
     * <br>
     * Validation errors:
     * <ul>
     * <li>Category is null</li>
     * <li>ID is null</li>
     * <li>Category can't be moved down</li>
     * <li>Category doesn't exist in data storage</li>
     * </ul>
     *
     * @param category category
     * @return result with validation errors
     */
    @PostMapping("/moveDown")
    public ResponseEntity<Result<Void>> moveDown(@RequestBody final Category category) {
        return processResult(categoryFacade.moveDown(category));
    }

    /**
     * Updates positions.
     *
     * @return result
     */
    @PostMapping("/updatePositions")
    public ResponseEntity<Result<Void>> updatePositions() {
        return processResult(categoryFacade.updatePositions());
    }

}
