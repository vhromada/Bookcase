package cz.vhromada.book.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import cz.vhromada.book.entity.Author;
import cz.vhromada.book.entity.Book;
import cz.vhromada.book.entity.Category;
import cz.vhromada.book.facade.AuthorFacade;
import cz.vhromada.book.facade.BookFacade;
import cz.vhromada.book.facade.CategoryFacade;
import cz.vhromada.book.web.exception.IllegalRequestException;
import cz.vhromada.book.web.fo.BookFO;
import cz.vhromada.common.Language;
import cz.vhromada.common.Movable;
import cz.vhromada.common.facade.MovableFacade;
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
 * A class represents controller for books.
 *
 * @author Vladimir Hromada
 */
@Controller("bookController")
@RequestMapping("/books")
public class BookController extends AbstractResultController {

    /**
     * Redirect URL to list
     */
    private static final String LIST_REDIRECT_URL = "redirect:/books/list";

    /**
     * Message for illegal request
     */
    private static final String ILLEGAL_REQUEST_MESSAGE = "Book doesn't exist.";

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
     * Facade for books
     */
    private final BookFacade bookFacade;

    /**
     * Facade for authors
     */
    private final AuthorFacade authorFacade;

    /**
     * Facade for categories
     */
    private final CategoryFacade categoryFacade;

    /**
     * Converter
     */
    private final Converter converter;

    /**
     * Creates a new instance of BookController.
     *
     * @param bookFacade     facade for books
     * @param authorFacade   facade for authors
     * @param categoryFacade facade for categories
     * @param converter      converter
     * @throws IllegalArgumentException if facade for books is null
     *                                  or facade for authors is null
     *                                  or facade for categories is null
     *                                  or converter is null
     */
    @Autowired
    public BookController(final BookFacade bookFacade, final AuthorFacade authorFacade, final CategoryFacade categoryFacade, final Converter converter) {
        Assert.notNull(bookFacade, "Facade for books mustn't be null.");
        Assert.notNull(authorFacade, "Facade for authors mustn't be null.");
        Assert.notNull(categoryFacade, "Facade for categories mustn't be null.");
        Assert.notNull(converter, "Converter mustn't be null.");

        this.bookFacade = bookFacade;
        this.authorFacade = authorFacade;
        this.categoryFacade = categoryFacade;
        this.converter = converter;
    }

    /**
     * Process new data.
     *
     * @return view for redirect to page with list of books
     */
    @GetMapping("/new")
    public String processNew() {
        processResults(bookFacade.newData());

        return LIST_REDIRECT_URL;
    }

    /**
     * Shows page with list of books.
     *
     * @param model model
     * @return view for page with list of books
     * @throws IllegalArgumentException if model is null
     */
    @GetMapping({ "", "/list" })
    public String showList(final Model model) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);

        final Result<List<Book>> booksResult = bookFacade.getAll();
        processResults(booksResult);

        model.addAttribute("books", booksResult.getData());
        model.addAttribute(TITLE_ATTRIBUTE, "Books");

        return "book/index";
    }

    /**
     * Shows page with detail of book.
     *
     * @param model model
     * @param id    ID of editing book
     * @return view for page with detail of book
     * @throws IllegalArgumentException if model is null
     *                                  or ID is null
     * @throws IllegalRequestException  if book doesn't exist
     */
    @GetMapping("/{id}/detail")
    public String showDetail(final Model model, @PathVariable("id") final Integer id) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(id, NULL_ID_MESSAGE);

        final Result<Book> result = bookFacade.get(id);
        processResults(result);

        final Book book = result.getData();
        if (book != null) {
            model.addAttribute("book", book);
            model.addAttribute(TITLE_ATTRIBUTE, "Book detail");

            return "book/detail";
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }
    }

    /**
     * Shows page for adding book.
     *
     * @param model model
     * @return view for page for adding book
     * @throws IllegalArgumentException if model is null
     */
    @GetMapping("/add")
    public String showAdd(final Model model) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);

        return createFormView(model, new BookFO(), "Add book", "add");
    }

    /**
     * Process adding book.
     *
     * @param model  model
     * @param bookFO FO for book
     * @param errors errors
     * @return view for redirect to page with list of books (no errors) or view for page for adding book (errors)
     * @throws IllegalArgumentException if model is null
     *                                  or FO for book is null
     *                                  or errors are null
     *                                  or ID isn't null
     */
    @PostMapping(value = "/add", params = "create")
    public String processAdd(final Model model, @ModelAttribute("book") final @Valid BookFO bookFO, final Errors errors) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(bookFO, "FO for book mustn't be null.");
        Assert.notNull(errors, "Errors mustn't be null.");
        Assert.isNull(bookFO.getId(), "ID must be null.");

        if (errors.hasErrors()) {
            return createFormView(model, bookFO, "Add book", "add");
        }
        bookFO.normalize();
        final Book book = converter.convert(bookFO, Book.class);
        book.setAuthors(getAuthors(bookFO.getAuthors()));
        book.setCategories(getCategories(bookFO.getCategories()));
        processResults(bookFacade.add(book));

        return LIST_REDIRECT_URL;
    }

    /**
     * Cancel adding book.
     *
     * @return view for redirect to page with list of books
     */
    @PostMapping(value = "/add", params = "cancel")
    public String cancelAdd() {
        return LIST_REDIRECT_URL;
    }

    /**
     * Shows page for editing book.
     *
     * @param model model
     * @param id    ID of editing book
     * @return view for page for editing book
     * @throws IllegalArgumentException if model is null
     *                                  or ID is null
     * @throws IllegalRequestException  if book doesn't exist
     */
    @GetMapping("/edit/{id}")
    public String showEdit(final Model model, @PathVariable("id") final Integer id) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(id, NULL_ID_MESSAGE);

        final Result<Book> result = bookFacade.get(id);
        processResults(result);

        final Book book = result.getData();
        if (book != null) {
            return createFormView(model, converter.convert(book, BookFO.class), "Edit book", "edit");
        } else {
            throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
        }
    }

    /**
     * Process editing book.
     *
     * @param model  model
     * @param bookFO FO for book
     * @param errors errors
     * @return view for redirect to page with list of books (no errors) or view for page for editing book (errors)
     * @throws IllegalArgumentException if model is null
     *                                  or FO for book is null
     *                                  or errors are null
     *                                  or ID is null
     * @throws IllegalRequestException  if book doesn't exist
     */
    @PostMapping(value = "/edit", params = "update")
    public String processEdit(final Model model, @ModelAttribute("book") final @Valid BookFO bookFO, final Errors errors) {
        Assert.notNull(model, NULL_MODEL_MESSAGE);
        Assert.notNull(bookFO, "FO for book mustn't be null.");
        Assert.notNull(errors, "Errors mustn't be null.");
        Assert.notNull(bookFO.getId(), NULL_ID_MESSAGE);

        if (errors.hasErrors()) {
            return createFormView(model, bookFO, "Edit book", "edit");
        }
        bookFO.normalize();
        final Book book = converter.convert(bookFO, Book.class);
        book.setAuthors(getAuthors(bookFO.getAuthors()));
        book.setCategories(getCategories(bookFO.getCategories()));
        processResults(bookFacade.update(processBook(book)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Cancel editing book.
     *
     * @return view for redirect to page with list of books
     */
    @PostMapping(value = "/edit", params = "cancel")
    public String processEdit() {
        return LIST_REDIRECT_URL;
    }

    /**
     * Process duplicating book.
     *
     * @param id ID of duplicating book
     * @return view for redirect to page with list of books
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if book doesn't exist
     */
    @GetMapping("/duplicate/{id}")
    public String processDuplicate(@PathVariable("id") final Integer id) {
        processResults(bookFacade.duplicate(getBook(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process removing book.
     *
     * @param id ID of removing book
     * @return view for redirect to page with list of books
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if book doesn't exist
     */
    @GetMapping("/remove/{id}")
    public String processRemove(@PathVariable("id") final Integer id) {
        processResults(bookFacade.remove(getBook(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process moving book up.
     *
     * @param id ID of moving book
     * @return view for redirect to page with list of books
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if book doesn't exist
     */
    @GetMapping("/moveUp/{id}")
    public String processMoveUp(@PathVariable("id") final Integer id) {
        processResults(bookFacade.moveUp(getBook(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process moving book down.
     *
     * @param id ID of moving book
     * @return view for redirect to page with list of books
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if book doesn't exist
     */
    @GetMapping("/moveDown/{id}")
    public String processMoveDown(@PathVariable("id") final Integer id) {
        processResults(bookFacade.moveDown(getBook(id)));

        return LIST_REDIRECT_URL;
    }

    /**
     * Process updating positions.
     *
     * @return view for redirect to page with list of books
     */
    @GetMapping("/update")
    public String processUpdatePositions() {
        processResults(bookFacade.updatePositions());

        return LIST_REDIRECT_URL;
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
    private String createFormView(final Model model, final BookFO book, final String title, final String action) {
        final Result<List<Author>> authors = authorFacade.getAll();
        final Result<List<Category>> categories = categoryFacade.getAll();
        processResults(authors, categories);

        model.addAttribute("book", book);
        model.addAttribute(TITLE_ATTRIBUTE, title);
        model.addAttribute("languages", new Language[]{ Language.CZ, Language.EN });
        model.addAttribute("authors", authors.getData());
        model.addAttribute("categories", categories.getData());
        model.addAttribute("action", action);

        return "book/form";
    }

    /**
     * Returns book with ID.
     *
     * @param id ID
     * @return book with ID
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if book doesn't exist
     */
    private Book getBook(final Integer id) {
        Assert.notNull(id, NULL_ID_MESSAGE);

        final Book book = new Book();
        book.setId(id);

        return processBook(book);
    }

    /**
     * Returns processed book.
     *
     * @param book for processing
     * @return processed book
     * @throws IllegalRequestException if book doesn't exist
     */
    private Book processBook(final Book book) {
        final Result<Book> bookResult = bookFacade.get(book.getId());
        processResults(bookResult);

        if (bookResult.getData() != null) {
            return book;
        }

        throw new IllegalRequestException(ILLEGAL_REQUEST_MESSAGE);
    }

    /**
     * Returns authors.
     *
     * @param source list of authors
     * @return authors
     */
    private List<Author> getAuthors(final List<Integer> source) {
        return source.stream().map(id -> getItem(id, authorFacade)).collect(Collectors.toList());
    }

    /**
     * Returns categories.
     *
     * @param source list of categories
     * @return categories
     */
    private List<Category> getCategories(final List<Integer> source) {
        return source.stream().map(id -> getItem(id, categoryFacade)).collect(Collectors.toList());
    }

    /**
     * Returns item.
     *
     * @param source item ID
     * @param facade facade
     * @return item
     */
    private static <T extends Movable> T getItem(final Integer source, final MovableFacade<T> facade) {
        final Result<T> item = facade.get(source);
        processResults(item);

        return item.getData();
    }

}
