package cz.vhromada.bookcase.web.controller

import cz.vhromada.bookcase.common.Format
import cz.vhromada.bookcase.entity.Book
import cz.vhromada.bookcase.entity.Item
import cz.vhromada.bookcase.facade.BookFacade
import cz.vhromada.bookcase.facade.ItemFacade
import cz.vhromada.bookcase.web.exception.IllegalRequestException
import cz.vhromada.bookcase.web.fo.ItemFO
import cz.vhromada.bookcase.web.mapper.ItemMapper
import cz.vhromada.common.entity.Language
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
 * A class represents controller for items.
 *
 * @author Vladimir Hromada
 */
@Controller("itemController")
@RequestMapping("/books/{bookId}/items")
class ItemController(
        private val bookFacade: BookFacade,
        private val itemFacade: ItemFacade,
        private val itemMapper: ItemMapper) : AbstractResultController() {

    /**
     * Shows page with list of items.
     *
     * @param model   model
     * @param bookId book ID
     * @return view for page with list of items
     * @throws IllegalRequestException  if book doesn't exist
     */
    @GetMapping("", "/list")
    fun showList(model: Model, @PathVariable("bookId") bookId: Int): String {
        val book = getBook(bookId)

        val result = itemFacade.find(book)
        processResults(result)

        model.addAttribute("items", result.data)
        model.addAttribute("book", bookId)
        model.addAttribute("title", "Items")

        return "item/index"
    }


    /**
     * Shows page with detail of item.
     *
     * @param model  model
     * @param bookId book ID
     * @param id     ID of editing item
     * @return view for page with detail of item
     * @throws IllegalRequestException  if item doesn't exist
     */
    @GetMapping("/{id}/detail")
    fun showDetail(model: Model, @PathVariable("bookId") bookId: Int, @PathVariable("id") id: Int): String {
        getBook(bookId)

        val result = itemFacade.get(id)
        processResults(result)

        val item = result.data
        if (item != null) {
            model.addAttribute("item", item)
            model.addAttribute("book", bookId)
            model.addAttribute("title", "Item detail")

            return "item/detail"
        } else {
            throw IllegalRequestException(ILLEGAL_REQUEST_MESSAGE)
        }
    }

    /**
     * Shows page for adding item.
     *
     * @param model   model
     * @param bookId book ID
     * @return view for page for adding item
     * @throws IllegalRequestException  if book doesn't exist
     */
    @GetMapping("/add")
    fun showAdd(model: Model, @PathVariable("bookId") bookId: Int): String {
        getBook(bookId)

        val item = ItemFO(
                id = null,
                languages = null,
                format = null,
                note = null,
                position = null)
        return createFormView(model, item, bookId, "Add item", "add")
    }

    /**
     * Process adding item.
     *
     * @param model   model
     * @param bookId book ID
     * @param item    FO for item
     * @param errors  errors
     * @return view for redirect to page with list of items (no errors) or view for page for adding item (errors)
     * @throws IllegalArgumentException if ID isn't null
     * @throws IllegalRequestException  if book doesn't exist
     */
    @PostMapping(value = ["/add"], params = ["create"])
    fun processAdd(model: Model, @PathVariable("bookId") bookId: Int, @ModelAttribute("item") @Valid item: ItemFO, errors: Errors): String {
        require(item.id == null) { "ID must be null." }

        val book = getBook(bookId)

        if (errors.hasErrors()) {
            return createFormView(model, item, bookId, "Add item", "add")
        }
        processResults(itemFacade.add(book, itemMapper.mapBack(item)))

        return getListRedirectUrl(bookId)
    }

    /**
     * Cancel adding item.
     *
     * @param bookId book ID
     * @return view for redirect to page with list of items
     * @throws IllegalRequestException  if book doesn't exist
     */
    @PostMapping(value = ["/add"], params = ["cancel"])
    fun cancelAdd(@PathVariable("bookId") bookId: Int): String {
        return cancel(bookId)
    }

    /**
     * Shows page for editing item.
     *
     * @param model   model
     * @param bookId book ID
     * @param id      ID of editing item
     * @return view for page for editing item
     * @throws IllegalRequestException  if book doesn't exist
     * or item doesn't exist
     */
    @GetMapping("/edit/{id}")
    fun showEdit(model: Model, @PathVariable("bookId") bookId: Int, @PathVariable("id") id: Int): String {
        getBook(bookId)

        val result = itemFacade.get(id)
        processResults(result)

        val item = result.data
        return if (item != null) {
            createFormView(model, itemMapper.map(item), bookId, "Edit item", "edit")
        } else {
            throw IllegalRequestException(ILLEGAL_REQUEST_MESSAGE)
        }
    }

    /**
     * Process editing item.
     *
     * @param model   model
     * @param bookId book ID
     * @param item    FO for item
     * @param errors  errors
     * @return view for redirect to page with list of items (no errors) or view for page for editing item (errors)
     * @throws IllegalArgumentException if ID is null
     * @throws IllegalRequestException  if book doesn't exist
     * or item doesn't exist
     */
    @PostMapping(value = ["/edit"], params = ["update"])
    fun processEdit(model: Model, @PathVariable("bookId") bookId: Int, @ModelAttribute("item") @Valid item: ItemFO, errors: Errors): String {
        require(item.id != null) { "ID mustn't be null." }
        getBook(bookId)

        if (errors.hasErrors()) {
            return createFormView(model, item, bookId, "Edit item", "edit")
        }
        processResults(itemFacade.update(processItem(itemMapper.mapBack(item))))

        return getListRedirectUrl(bookId)
    }

    /**
     * Cancel editing item.
     *
     * @param bookId book ID
     * @return view for redirect to page with list of items
     * @throws IllegalRequestException  if book doesn't exist
     */
    @PostMapping("/edit")
    fun cancelEdit(@PathVariable("bookId") bookId: Int): String {
        return cancel(bookId)
    }

    /**
     * Process duplicating item.
     *
     * @param bookId book ID
     * @param id      ID of duplicating item
     * @return view for redirect to page with list of items
     * @throws IllegalRequestException  if book doesn't exist
     * or item doesn't exist
     */
    @GetMapping("/duplicate/{id}")
    fun processDuplicate(@PathVariable("bookId") bookId: Int, @PathVariable("id") id: Int): String {
        processResults(itemFacade.duplicate(getItem(bookId, id)))

        return getListRedirectUrl(bookId)
    }

    /**
     * Process removing item.
     *
     * @param bookId book ID
     * @param id      ID of removing item
     * @return view for redirect to page with list of items
     * @throws IllegalRequestException  if book doesn't exist
     * or item doesn't exist
     */
    @GetMapping("/remove/{id}")
    fun processRemove(@PathVariable("bookId") bookId: Int, @PathVariable("id") id: Int): String {
        processResults(itemFacade.remove(getItem(bookId, id)))

        return getListRedirectUrl(bookId)
    }

    /**
     * Process moving item up.
     *
     * @param bookId book ID
     * @param id      ID of moving item
     * @return view for redirect to page with list of items
     * @throws IllegalRequestException  if book doesn't exist
     * or item doesn't exist
     */
    @GetMapping("/moveUp/{id}")
    fun processMoveUp(@PathVariable("bookId") bookId: Int, @PathVariable("id") id: Int): String {
        processResults(itemFacade.moveUp(getItem(bookId, id)))

        return getListRedirectUrl(bookId)
    }

    /**
     * Process moving item down.
     *
     * @param bookId book ID
     * @param id      ID of moving item
     * @return view for redirect to page with list of items
     * @throws IllegalRequestException  if book doesn't exist
     * or item doesn't exist
     */
    @GetMapping("/moveDown/{id}")
    fun processMoveDown(@PathVariable("bookId") bookId: Int, @PathVariable("id") id: Int): String {
        processResults(itemFacade.moveDown(getItem(bookId, id)))

        return getListRedirectUrl(bookId)
    }

    /**
     * Cancel processing item.
     *
     * @param bookId book ID
     * @return view for redirect to page with list of items
     * @throws IllegalRequestException  if book doesn't exist
     */
    private fun cancel(bookId: Int): String {
        getBook(bookId)

        return getListRedirectUrl(bookId)
    }

    /**
     * Returns book.
     *
     * @param id book ID
     * @return book
     * @throws IllegalRequestException if book doesn't exist
     */
    private fun getBook(id: Int): Book {
        val bookResult = bookFacade.get(id)
        processResults(bookResult)

        return bookResult.data ?: throw IllegalRequestException("Book doesn't exist.")
    }

    /**
     * Returns item with ID.
     *
     * @param bookId book ID
     * @param id      ID
     * @return item with ID
     * @throws IllegalRequestException  if book doesn't exist
     * or item doesn't exist
     */
    private fun getItem(bookId: Int, id: Int): Item {
        getBook(bookId)

        val item = Item(
                id = id,
                languages = null,
                format = null,
                note = null,
                position = null)

        return processItem(item)
    }

    /**
     * Returns processed item.
     *
     * @param item for processing
     * @return processed item
     * @throws IllegalRequestException if item doesn't exist
     */
    private fun processItem(item: Item): Item {
        val itemResult = itemFacade.get(item.id!!)
        processResults(itemResult)

        if (itemResult.data != null) {
            return item
        }

        throw IllegalRequestException(ILLEGAL_REQUEST_MESSAGE)
    }


    /**
     * Returns page's view with form.
     *
     * @param model   model
     * @param item    FO for item
     * @param bookId book ID
     * @param title   page's title
     * @param action  action
     * @return page's view with form
     */
    private fun createFormView(model: Model, item: ItemFO, bookId: Int, title: String, action: String): String {
        model.addAttribute("item", item)
        model.addAttribute("book", bookId)
        model.addAttribute("languages", arrayOf(Language.CZ, Language.EN))
        model.addAttribute("formats", Format.values())
        model.addAttribute("title", title)
        model.addAttribute("action", action)

        return "item/form"
    }

    /**
     * Returns redirect URL to list.
     *
     * @param bookId book ID
     * @return redirect URL to list
     */
    private fun getListRedirectUrl(bookId: Int): String {
        return "redirect:/books/$bookId/items/list"
    }

    companion object {

        /**
         * Message for illegal request
         */
        private const val ILLEGAL_REQUEST_MESSAGE = "item doesn't exist."

    }

}
