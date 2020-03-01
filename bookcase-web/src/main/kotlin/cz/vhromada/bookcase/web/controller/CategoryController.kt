package cz.vhromada.bookcase.web.controller

import cz.vhromada.bookcase.entity.Category
import cz.vhromada.bookcase.facade.CategoryFacade
import cz.vhromada.bookcase.web.exception.IllegalRequestException
import cz.vhromada.bookcase.web.fo.CategoryFO
import cz.vhromada.bookcase.web.mapper.CategoryMapper
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
 * A class represents controller for categories.
 *
 * @author Vladimir Hromada
 */
@Controller("categoryController")
@RequestMapping("/categories")
class CategoryController(
        private val categoryFacade: CategoryFacade,
        private val categoryMapper: CategoryMapper) : AbstractResultController() {

    /**
     * Process new data.
     *
     * @return view for redirect to page with list of categories
     */
    @GetMapping("/new")
    fun processNew(): String {
        processResults(categoryFacade.newData())

        return LIST_REDIRECT_URL
    }

    /**
     * Shows page with list of categories.
     *
     * @param model model
     * @return view for page with list of categories
     */
    @GetMapping("", "/list")
    fun showList(model: Model): String {
        val categoriesResult = categoryFacade.getAll()
        processResults(categoriesResult)

        model.addAttribute("categories", categoriesResult.data)
        model.addAttribute("title", "Categories")

        return "category/index"
    }

    /**
     * Shows page for adding category.
     *
     * @param model model
     * @return view for page for adding category
     */
    @GetMapping("/add")
    fun showAdd(model: Model): String {
        val category = CategoryFO(
                id = null,
                name = null,
                position = null)
        return createFormView(model, category, "Add category", "add")
    }

    /**
     * Process adding category.
     *
     * @param model    model
     * @param category FO for category
     * @param errors   errors
     * @return view for redirect to page with list of categories (no errors) or view for page for adding category (errors)
     * @throws IllegalArgumentException if ID isn't null
     */
    @PostMapping(value = ["/add"], params = ["create"])
    fun processAdd(model: Model, @ModelAttribute("category") @Valid category: CategoryFO, errors: Errors): String {
        require(category.id == null) { "ID must be null." }

        if (errors.hasErrors()) {
            return createFormView(model, category, "Add category", "add")
        }
        processResults(categoryFacade.add(categoryMapper.mapBack(category)))

        return LIST_REDIRECT_URL
    }

    /**
     * Cancel adding category.
     *
     * @return view for redirect to page with list of categories
     */
    @PostMapping(value = ["/add"], params = ["cancel"])
    fun cancelAdd(): String {
        return LIST_REDIRECT_URL
    }

    /**
     * Shows page for editing category.
     *
     * @param model model
     * @param id    ID of editing category
     * @return view for page for editing category
     * @throws IllegalRequestException  if category doesn't exist
     */
    @GetMapping("/edit/{id}")
    fun showEdit(model: Model, @PathVariable("id") id: Int): String {
        val result = categoryFacade.get(id)
        processResults(result)

        val category = result.data
        return if (category != null) {
            createFormView(model, categoryMapper.map(category), "Edit category", "edit")
        } else {
            throw IllegalRequestException(ILLEGAL_REQUEST_MESSAGE)
        }
    }

    /**
     * Process editing category.
     *
     * @param model    model
     * @param category FO for category
     * @param errors   errors
     * @return view for redirect to page with list of categories (no errors) or view for page for editing category (errors)
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if category doesn't exist
     */
    @PostMapping(value = ["/edit"], params = ["update"])
    fun processEdit(model: Model, @ModelAttribute("category") @Valid category: CategoryFO, errors: Errors): String {
        require(category.id != null) { "ID mustn't be null." }

        if (errors.hasErrors()) {
            return createFormView(model, category, "Edit category", "edit")
        }
        processResults(categoryFacade.update(processCategory(categoryMapper.mapBack(category))))

        return LIST_REDIRECT_URL
    }

    /**
     * Cancel editing category.
     *
     * @return view for redirect to page with list of categories
     */
    @PostMapping(value = ["/edit"], params = ["cancel"])
    fun processEdit(): String {
        return LIST_REDIRECT_URL
    }

    /**
     * Process duplicating category.
     *
     * @param id ID of duplicating category
     * @return view for redirect to page with list of categories
     * @throws IllegalRequestException  if category doesn't exist
     */
    @GetMapping("/duplicate/{id}")
    fun processDuplicate(@PathVariable("id") id: Int): String {
        processResults(categoryFacade.duplicate(getCategory(id)))

        return LIST_REDIRECT_URL
    }

    /**
     * Process removing category.
     *
     * @param id ID of removing category
     * @return view for redirect to page with list of categories
     * @throws IllegalRequestException  if category doesn't exist
     */
    @GetMapping("/remove/{id}")
    fun processRemove(@PathVariable("id") id: Int): String {
        processResults(categoryFacade.remove(getCategory(id)))

        return LIST_REDIRECT_URL
    }

    /**
     * Process moving category up.
     *
     * @param id ID of moving category
     * @return view for redirect to page with list of categories
     * @throws IllegalRequestException  if category doesn't exist
     */
    @GetMapping("/moveUp/{id}")
    fun processMoveUp(@PathVariable("id") id: Int): String {
        processResults(categoryFacade.moveUp(getCategory(id)))

        return LIST_REDIRECT_URL
    }

    /**
     * Process moving category down.
     *
     * @param id ID of moving category
     * @return view for redirect to page with list of categories
     * @throws IllegalRequestException  if category doesn't exist
     */
    @GetMapping("/moveDown/{id}")
    fun processMoveDown(@PathVariable("id") id: Int): String {
        processResults(categoryFacade.moveDown(getCategory(id)))

        return LIST_REDIRECT_URL
    }

    /**
     * Process updating positions.
     *
     * @return view for redirect to page with list of categories
     */
    @GetMapping("/update")
    fun processUpdatePositions(): String {
        processResults(categoryFacade.updatePositions())

        return LIST_REDIRECT_URL
    }

    /**
     * Returns category with ID.
     *
     * @param id ID
     * @return category with ID
     * @throws IllegalRequestException  if category doesn't exist
     */
    private fun getCategory(id: Int): Category {
        val category = Category(
                id = id,
                name = null,
                position = null)
        return processCategory(category)
    }

    /**
     * Returns processed category.
     *
     * @param category for processing
     * @return processed category
     * @throws IllegalRequestException if category doesn't exist
     */
    private fun processCategory(category: Category): Category {
        val categoryResult = categoryFacade.get(category.id!!)
        processResults(categoryResult)

        if (categoryResult.data != null) {
            return category
        }
        throw IllegalRequestException(ILLEGAL_REQUEST_MESSAGE)
    }

    /**
     * Returns page's view with form.
     *
     * @param model    model
     * @param category FO for category
     * @param title    page's title
     * @param action   action
     * @return page's view with form
     */
    private fun createFormView(model: Model, category: CategoryFO?, title: String, action: String): String {
        model.addAttribute("category", category)
        model.addAttribute("title", title)
        model.addAttribute("action", action)

        return "category/form"
    }

    companion object {

        /**
         * Redirect URL to list
         */
        private const val LIST_REDIRECT_URL = "redirect:/categories/list"

        /**
         * Message for illegal request
         */
        private const val ILLEGAL_REQUEST_MESSAGE = "Category doesn't exist."

    }

}
