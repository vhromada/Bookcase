package cz.vhromada.book.web.controller;

import java.util.List;

import javax.validation.Valid;

import cz.vhromada.book.entity.Category;
import cz.vhromada.book.facade.CategoryFacade;
import cz.vhromada.book.web.exception.IllegalRequestException;
import cz.vhromada.book.web.fo.CategoryFO;
import cz.vhromada.converter.Converter;
import cz.vhromada.result.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * A class represents controller for categories.
 *
 * @author Vladimir Hromada
 */
@Controller("categoryController")
@RequestMapping("/categories")
public class CategoryController extends AbstractResultController {

    /**
     * Redirect URL to list
     */
    private static final String LIST_REDIRECT_URL = "redirect:/categories/list";

    /**
     * Message for illegal request
     */
    private static final String ILLEGAL_REQUEST_MESSAGE = "Category doesn't exist.";

    /**
     * Message for null model
     */
    private static final String NULL_MODEL_MESSAGE = "Model mustn't be null.";

    /**
     * Message for null ID
     */
    private static final String NULL_ID_MESSAGE = "ID mustn't be null.";

    /**
     * Title model attribute
     */
    private static final String TITLE_ATTRIBUTE = "title";

    /**
     * Facade for categories
     */
    private final CategoryFacade categoryFacade;

    /**
     * Converter
     */
    private final Converter converter;

    /**
     * Creates a new instance of CategoryController.
     *
     * @param categoryFacade facade for categories
     * @param converter      converter
     * @throws IllegalArgumentException if facade for categories is null
     *                                  or converter is null
     */
    @Autowired
    public CategoryController(final CategoryFacade categoryFacade, final Converter converter) {
        Assert.notNull(categoryFacade, "Facade for categories mustn't be null.");
        Assert.notNull(converter, "Converter mustn't be null.");

        this.categoryFacade = categoryFacade;
        this.converter = converter;
    }

    /**
     * Process new data.
     *
     * @return view for redirect to page with list of categories
     */
    @GetMapping("/new")
    public String processNew() {
        processResults(categoryFacade.newData());

        return LIST_REDIRECT_URL;
    }

    /**
     * Shows page with list of categories.
     *
     * @param model model
     * @return view for page with list of categories
     * @throws IllegalArgumentException if model is null
     */
    @GetMapping({ "", "/list" })
    public String showList(final Model model) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);

        final Result<List<Category>> categoriesResult = categoryFacade.getAll();
        processResults(categoriesResult);

        model.addAttribute("categories", categoriesResult.getData());
        model.addAttribute(TITLE_ATTRIBUTE, "Categories");

        return "category/index";
    }

    /**
     * Shows page for adding category.
     *
     * @param model model
     * @return view for page for adding category
     * @throws IllegalArgumentException if model is null
     */
    @GetMapping("/add")
    public String showAdd(final Model model) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);

        return createFormView(model, new CategoryFO(), "Add category", "add");
    }

    /**
     * Process adding category.
     *
     * @param model    model
     * @param category FO for category
     * @param errors   errors
     * @return view for redirect to page with list of categories (no errors) or view for page for adding category (errors)
     * @throws IllegalArgumentException if model is null
     *                                  or FO for category is null
     *                                  or errors are null
     *                                  or ID isn't null
     */
    @PostMapping(value = "/add", params = "create")
    public String processAdd(final Model model, @ModelAttribute("category") final @Valid CategoryFO category, final Errors errors) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(category, "FO for category mustn't be null.");
        Assert.notNull(errors, "Errors mustn't be null.");
        Assert.isNull(category.getId(), "ID must be null.");

        if (errors.hasErrors()) {
            return createFormView(model, category, "Add category", "add");
        }
        processResults(categoryFacade.add(converter.convert(category, Category.class)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Cancel adding category.
     *
     * @return view for redirect to page with list of categories
     */
    @PostMapping(value = "/add", params = "cancel")
    public String cancelAdd() {
        return LIST_REDIRECT_URL;
    }

    /**
     * Shows page for editing category.
     *
     * @param model model
     * @param id    ID of editing category
     * @return view for page for editing category
     * @throws IllegalArgumentException if model is null
     *                                  or ID is null
     * @throws IllegalRequestException  if category doesn't exist
     */
    @GetMapping("/edit/{id}")
    public String showEdit(final Model model, @PathVariable("id") final Integer id) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(id, NULL_ID_MESSAGE);

        final Result<Category> result = categoryFacade.get(id);
        processResults(result);

        final Category category = result.getData();
        if (category != null) {
            return createFormView(model, converter.convert(category, CategoryFO.class), "Edit category", "edit");
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }
    }

    /**
     * Process editing category.
     *
     * @param model    model
     * @param category FO for category
     * @param errors   errors
     * @return view for redirect to page with list of categories (no errors) or view for page for editing category (errors)
     * @throws IllegalArgumentException if model is null
     *                                  or FO for category is null
     *                                  or errors are null
     *                                  or ID is null
     * @throws IllegalRequestException  if category doesn't exist
     */
    @PostMapping(value = "/edit", params = "update")
    public String processEdit(final Model model, @ModelAttribute("category") final @Valid CategoryFO category, final Errors errors) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(category, "FO for category mustn't be null.");
        Assert.notNull(errors, "Errors mustn't be null.");
        Assert.notNull(category.getId(), NULL_ID_MESSAGE);

        if (errors.hasErrors()) {
            return createFormView(model, category, "Edit category", "edit");
        }
        processResults(categoryFacade.update(processCategory(converter.convert(category, Category.class))));

        return LIST_REDIRECT_URL;
    }

    /**
     * Cancel editing category.
     *
     * @return view for redirect to page with list of categories
     */
    @PostMapping(value = "/edit", params = "cancel")
    public String processEdit() {
        return LIST_REDIRECT_URL;
    }

    /**
     * Process duplicating category.
     *
     * @param id ID of duplicating category
     * @return view for redirect to page with list of categories
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if category doesn't exist
     */
    @GetMapping("/duplicate/{id}")
    public String processDuplicate(@PathVariable("id") final Integer id) {
        processResults(categoryFacade.duplicate(getCategory(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process removing category.
     *
     * @param id ID of removing category
     * @return view for redirect to page with list of categories
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if category doesn't exist
     */
    @GetMapping("/remove/{id}")
    public String processRemove(@PathVariable("id") final Integer id) {
        processResults(categoryFacade.remove(getCategory(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process moving category up.
     *
     * @param id ID of moving category
     * @return view for redirect to page with list of categories
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if category doesn't exist
     */
    @GetMapping("/moveUp/{id}")
    public String processMoveUp(@PathVariable("id") final Integer id) {
        processResults(categoryFacade.moveUp(getCategory(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process moving category down.
     *
     * @param id ID of moving category
     * @return view for redirect to page with list of categories
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if category doesn't exist
     */
    @GetMapping("/moveDown/{id}")
    public String processMoveDown(@PathVariable("id") final Integer id) {
        processResults(categoryFacade.moveDown(getCategory(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process updating positions.
     *
     * @return view for redirect to page with list of categories
     */
    @GetMapping("/update")
    public String processUpdatePositions() {
        processResults(categoryFacade.updatePositions());

        return LIST_REDIRECT_URL;
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
    private static String createFormView(final Model model, final CategoryFO category, final String title, final String action) {
        model.addAttribute("category", category);
        model.addAttribute(TITLE_ATTRIBUTE, title);
        model.addAttribute("action", action);

        return "category/form";
    }

    /**
     * Returns category with ID.
     *
     * @param id ID
     * @return category with ID
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if category doesn't exist
     */
    private Category getCategory(final Integer id) {
        Assert.notNull(id, NULL_ID_MESSAGE);

        final Category category = new Category();
        category.setId(id);

        return processCategory(category);
    }

    /**
     * Returns processed category.
     *
     * @param category for processing
     * @return processed category
     * @throws IllegalRequestException if category doesn't exist
     */
    private Category processCategory(final Category category) {
        final Result<Category> categoryResult = categoryFacade.get(category.getId());
        processResults(categoryResult);

        if (categoryResult.getData() != null) {
            return category;
        }

        throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
    }

}
