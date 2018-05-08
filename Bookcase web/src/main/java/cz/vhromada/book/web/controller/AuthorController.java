package cz.vhromada.book.web.controller;

import java.util.List;

import javax.validation.Valid;

import cz.vhromada.book.entity.Author;
import cz.vhromada.book.facade.AuthorFacade;
import cz.vhromada.book.web.exception.IllegalRequestException;
import cz.vhromada.book.web.fo.AuthorFO;
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
 * A class represents controller for authors.
 *
 * @author Vladimir Hromada
 */
@Controller("authorController")
@RequestMapping("/authors")
public class AuthorController extends AbstractResultController {

    /**
     * Redirect URL to list
     */
    private static final String LIST_REDIRECT_URL = "redirect:/authors/list";

    /**
     * Message for illegal request
     */
    private static final String ILLEGAL_REQUEST_MESSAGE = "Author doesn't exist.";

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
     * Facade for authors
     */
    private final AuthorFacade authorFacade;

    /**
     * Converter
     */
    private final Converter converter;

    /**
     * Creates a new instance of AuthorController.
     *
     * @param authorFacade facade for authors
     * @param converter    converter
     * @throws IllegalArgumentException if facade for authors is null
     *                                  or converter is null
     */
    @Autowired
    public AuthorController(final AuthorFacade authorFacade, final Converter converter) {
        Assert.notNull(authorFacade, "Facade for authors mustn't be null.");
        Assert.notNull(converter, "Converter mustn't be null.");

        this.authorFacade = authorFacade;
        this.converter = converter;
    }

    /**
     * Process new data.
     *
     * @return view for redirect to page with list of authors
     */
    @GetMapping("/new")
    public String processNew() {
        processResults(authorFacade.newData());

        return LIST_REDIRECT_URL;
    }

    /**
     * Shows page with list of authors.
     *
     * @param model model
     * @return view for page with list of authors
     * @throws IllegalArgumentException if model is null
     */
    @GetMapping({ "", "/list" })
    public String showList(final Model model) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);

        final Result<List<Author>> authorsResult = authorFacade.getAll();
        processResults(authorsResult);

        model.addAttribute("authors", authorsResult.getData());
        model.addAttribute(TITLE_ATTRIBUTE, "Authors");

        return "author/index";
    }

    /**
     * Shows page for adding author.
     *
     * @param model model
     * @return view for page for adding author
     * @throws IllegalArgumentException if model is null
     */
    @GetMapping("/add")
    public String showAdd(final Model model) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);

        return createFormView(model, new AuthorFO(), "Add author", "add");
    }

    /**
     * Process adding author.
     *
     * @param model  model
     * @param author FO for author
     * @param errors errors
     * @return view for redirect to page with list of authors (no errors) or view for page for adding author (errors)
     * @throws IllegalArgumentException if model is null
     *                                  or FO for author is null
     *                                  or errors are null
     *                                  or ID isn't null
     */
    @PostMapping(value = "/add", params = "create")
    public String processAdd(final Model model, @ModelAttribute("author") final @Valid AuthorFO author, final Errors errors) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(author, "FO for author mustn't be null.");
        Assert.notNull(errors, "Errors mustn't be null.");
        Assert.isNull(author.getId(), "ID must be null.");

        if (errors.hasErrors()) {
            return createFormView(model, author, "Add author", "add");
        }
        author.normalize();
        processResults(authorFacade.add(converter.convert(author, Author.class)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Cancel adding author.
     *
     * @return view for redirect to page with list of authors
     */
    @PostMapping(value = "/add", params = "cancel")
    public String cancelAdd() {
        return LIST_REDIRECT_URL;
    }

    /**
     * Shows page for editing author.
     *
     * @param model model
     * @param id    ID of editing author
     * @return view for page for editing author
     * @throws IllegalArgumentException if model is null
     *                                  or ID is null
     * @throws IllegalRequestException  if author doesn't exist
     */
    @GetMapping("/edit/{id}")
    public String showEdit(final Model model, @PathVariable("id") final Integer id) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(id, NULL_ID_MESSAGE);

        final Result<Author> result = authorFacade.get(id);
        processResults(result);

        final Author author = result.getData();
        if (author != null) {
            return createFormView(model, converter.convert(author, AuthorFO.class), "Edit author", "edit");
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }
    }

    /**
     * Process editing author.
     *
     * @param model  model
     * @param author FO for author
     * @param errors errors
     * @return view for redirect to page with list of authors (no errors) or view for page for editing author (errors)
     * @throws IllegalArgumentException if model is null
     *                                  or FO for author is null
     *                                  or errors are null
     *                                  or ID is null
     * @throws IllegalRequestException  if author doesn't exist
     */
    @PostMapping(value = "/edit", params = "update")
    public String processEdit(final Model model, @ModelAttribute("author") final @Valid AuthorFO author, final Errors errors) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(author, "FO for author mustn't be null.");
        Assert.notNull(errors, "Errors mustn't be null.");
        Assert.notNull(author.getId(), NULL_ID_MESSAGE);

        if (errors.hasErrors()) {
            return createFormView(model, author, "Edit author", "edit");
        }
        author.normalize();
        processResults(authorFacade.update(processAuthor(converter.convert(author, Author.class))));

        return LIST_REDIRECT_URL;
    }

    /**
     * Cancel editing author.
     *
     * @return view for redirect to page with list of authors
     */
    @PostMapping(value = "/edit", params = "cancel")
    public String processEdit() {
        return LIST_REDIRECT_URL;
    }

    /**
     * Process duplicating author.
     *
     * @param id ID of duplicating author
     * @return view for redirect to page with list of authors
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if author doesn't exist
     */
    @GetMapping("/duplicate/{id}")
    public String processDuplicate(@PathVariable("id") final Integer id) {
        processResults(authorFacade.duplicate(getAuthor(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process removing author.
     *
     * @param id ID of removing author
     * @return view for redirect to page with list of authors
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if author doesn't exist
     */
    @GetMapping("/remove/{id}")
    public String processRemove(@PathVariable("id") final Integer id) {
        processResults(authorFacade.remove(getAuthor(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process moving author up.
     *
     * @param id ID of moving author
     * @return view for redirect to page with list of authors
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if author doesn't exist
     */
    @GetMapping("/moveUp/{id}")
    public String processMoveUp(@PathVariable("id") final Integer id) {
        processResults(authorFacade.moveUp(getAuthor(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process moving author down.
     *
     * @param id ID of moving author
     * @return view for redirect to page with list of authors
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if author doesn't exist
     */
    @GetMapping("/moveDown/{id}")
    public String processMoveDown(@PathVariable("id") final Integer id) {
        processResults(authorFacade.moveDown(getAuthor(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process updating positions.
     *
     * @return view for redirect to page with list of authors
     */
    @GetMapping("/update")
    public String processUpdatePositions() {
        processResults(authorFacade.updatePositions());

        return LIST_REDIRECT_URL;
    }

    /**
     * Returns page's view with form.
     *
     * @param model  model
     * @param author FO for author
     * @param title  page's title
     * @param action action
     * @return page's view with form
     */
    private static String createFormView(final Model model, final AuthorFO author, final String title, final String action) {
        model.addAttribute("author", author);
        model.addAttribute(TITLE_ATTRIBUTE, title);
        model.addAttribute("action", action);

        return "author/form";
    }

    /**
     * Returns author with ID.
     *
     * @param id ID
     * @return author with ID
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if author doesn't exist
     */
    private Author getAuthor(final Integer id) {
        Assert.notNull(id, NULL_ID_MESSAGE);

        final Author author = new Author();
        author.setId(id);

        return processAuthor(author);
    }

    /**
     * Returns processed author.
     *
     * @param author for processing
     * @return processed author
     * @throws IllegalRequestException if author doesn't exist
     */
    private Author processAuthor(final Author author) {
        final Result<Author> authorResult = authorFacade.get(author.getId());
        processResults(authorResult);

        if (authorResult.getData() != null) {
            return author;
        }

        throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
    }

}
