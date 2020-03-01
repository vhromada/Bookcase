package cz.vhromada.bookcase.validator

import cz.vhromada.bookcase.domain.Book
import cz.vhromada.bookcase.entity.Item
import cz.vhromada.common.result.Event
import cz.vhromada.common.result.Result
import cz.vhromada.common.result.Severity
import cz.vhromada.common.service.MovableService
import cz.vhromada.common.utils.sorted
import cz.vhromada.common.validator.AbstractMovableValidator
import org.springframework.stereotype.Component

/**
 * A class represents validator for item.
 *
 * @author Vladimir Hromada
 */
@Component("itemValidator")
class ItemValidator(bookService: MovableService<Book>) : AbstractMovableValidator<Item, Book>("Item", bookService) {

    override fun getData(data: Item): cz.vhromada.bookcase.domain.Item? {
        for (book in service.getAll()) {
            for (item in book.items) {
                if (data.id == item.id) {
                    return item
                }
            }
        }

        return null
    }

    override fun getList(data: Item): List<cz.vhromada.bookcase.domain.Item> {
        for (book in service.getAll()) {
            for (song in book.items) {
                if (data.id == song.id) {
                    return book.items
                            .sorted()
                }
            }
        }

        return emptyList()
    }

    /**
     * Validates item deeply.
     * <br></br>
     * Validation errors:
     *
     *  * Languages are null
     *  * Languages are empty
     *  * Languages contain null value
     *  * Format is null
     *  * Note is null
     *
     * @param data   validating item
     * @param result result with validation errors
     */
    override fun validateDataDeep(data: Item, result: Result<Unit>) {
        when {
            data.languages == null -> {
                result.addEvent(Event(Severity.ERROR, "ITEM_LANGUAGES_NULL", "Languages mustn't be null."))
            }
            data.languages.isEmpty() -> {
                result.addEvent(Event(Severity.ERROR, "ITEM_LANGUAGES_EMPTY", "Languages mustn't be empty."))
            }
            data.languages.contains(null) -> {
                result.addEvent(Event(Severity.ERROR, "ITEM_LANGUAGES_CONTAIN_NULL", "Languages mustn't contain null value."))
            }
        }
        if (data.format == null) {
            result.addEvent(Event(Severity.ERROR, "ITEM_FORMAT_NULL", "Format mustn't be null."))
        }
        if (data.note == null) {
            result.addEvent(Event(Severity.ERROR, "ITEM_NOTE_NULL", "Note mustn't be null."))
        }
    }

}
