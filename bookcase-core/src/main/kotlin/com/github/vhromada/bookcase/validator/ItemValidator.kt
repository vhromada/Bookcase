package com.github.vhromada.bookcase.validator

import com.github.vhromada.bookcase.domain.Book
import com.github.vhromada.bookcase.entity.Item
import com.github.vhromada.common.entity.Movable
import com.github.vhromada.common.result.Event
import com.github.vhromada.common.result.Result
import com.github.vhromada.common.result.Severity
import com.github.vhromada.common.service.MovableService
import com.github.vhromada.common.utils.sorted
import com.github.vhromada.common.validator.AbstractMovableValidator
import org.springframework.stereotype.Component
import java.util.Optional

/**
 * A class represents validator for item.
 *
 * @author Vladimir Hromada
 */
@Component("itemValidator")
class ItemValidator(bookService: MovableService<Book>) : AbstractMovableValidator<Item, Book>("Item", bookService) {

    override fun getData(data: Item): Optional<Movable> {
        for (book in service.getAll()) {
            for (item in book.items) {
                if (data.id == item.id) {
                    return Optional.of(item)
                }
            }
        }

        return Optional.empty()
    }

    override fun getList(data: Item): List<com.github.vhromada.bookcase.domain.Item> {
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
