package cz.vhromada.bookcase.validator

import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import cz.vhromada.bookcase.domain.Book
import cz.vhromada.bookcase.entity.Item
import cz.vhromada.bookcase.utils.BookUtils
import cz.vhromada.bookcase.utils.ItemUtils
import cz.vhromada.common.entity.Language
import cz.vhromada.common.result.Event
import cz.vhromada.common.result.Severity
import cz.vhromada.common.result.Status
import cz.vhromada.common.test.validator.MovableValidatorTest
import cz.vhromada.common.validator.MovableValidator
import cz.vhromada.common.validator.ValidationType
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.Test

/**
 * A class represents test for class [ItemValidator].
 *
 * @author Vladimir Hromada
 */
class ItemValidatorTest : MovableValidatorTest<Item, Book>() {

    /**
     * Test method for [ItemValidator.validate] with [ValidationType.DEEP] with data with null languages.
     */
    @Test
    fun validateDeepNullLanguages() {
        val item = getValidatingData(1)
                .copy(languages = null)

        val result = getValidator().validate(item, ValidationType.DEEP)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "ITEM_LANGUAGES_NULL", "Languages mustn't be null.")))
        }

        verifyZeroInteractions(service)
    }

    /**
     * Test method for [ItemValidator.validate] with [ValidationType.DEEP] with data with empty languages.
     */
    @Test
    fun validateDeepEmptyLanguages() {
        val item = getValidatingData(1)
                .copy(languages = emptyList())

        val result = getValidator().validate(item, ValidationType.DEEP)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "ITEM_LANGUAGES_EMPTY", "Languages mustn't be empty.")))
        }

        verifyZeroInteractions(service)
    }

    /**
     * Test method for [ItemValidator.validate] with [ValidationType.DEEP] with data with languages with null value.
     */
    @Test
    fun validateDeepBadLanguages() {
        val item = getValidatingData(1)
                .copy(languages = listOf(Language.CZ, null))

        val result = getValidator().validate(item, ValidationType.DEEP)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "ITEM_LANGUAGES_CONTAIN_NULL", "Languages mustn't contain null value.")))
        }

        verifyZeroInteractions(service)
    }

    /**
     * Test method for [ItemValidator.validate] with [ValidationType.DEEP] with data with null format.
     */
    @Test
    fun validateDeepNullFormat() {
        val item = getValidatingData(1)
                .copy(format = null)

        val result = getValidator().validate(item, ValidationType.DEEP)
        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "ITEM_FORMAT_NULL", "Format mustn't be null.")))
        }

        verifyZeroInteractions(service)
    }

    /**
     * Test method for [ItemValidator.validate] with [ValidationType.DEEP] with data with null note.
     */
    @Test
    fun validateDeepNullNote() {
        val item = getValidatingData(1)
                .copy(note = null)

        val result = getValidator().validate(item, ValidationType.DEEP)
        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "ITEM_NOTE_NULL", "Note mustn't be null.")))
        }

        verifyZeroInteractions(service)
    }

    override fun getValidator(): MovableValidator<Item> {
        return ItemValidator(service)
    }

    override fun getValidatingData(id: Int?): Item {
        return ItemUtils.newItem(id)
    }

    override fun getValidatingData(id: Int?, position: Int?): Item {
        return ItemUtils.newItem(id)
                .copy(position = position)
    }

    override fun getRepositoryData(validatingData: Item): Book {
        return BookUtils.newBookDomain(validatingData.id)
    }

    override fun getItem1(): Book {
        return BookUtils.newBookDomain(1)
    }

    override fun getItem2(): Book {
        return BookUtils.newBookDomain(2)
    }

    override fun getName(): String {
        return "Item"
    }

    override fun initExistsMock(validatingData: Item, exists: Boolean) {
        val book = if (exists) BookUtils.newBookWithItems(validatingData.id) else BookUtils.newBookDomain(Integer.MAX_VALUE)

        whenever(service.getAll()).thenReturn(listOf(book))
    }

    override fun verifyExistsMock(validatingData: Item) {
        verify(service).getAll()
        verifyNoMoreInteractions(service)
    }

    override fun initMovingMock(validatingData: Item, up: Boolean, valid: Boolean) {
        val items = if (up && valid || !up && !valid) {
            listOf(ItemUtils.newItemDomain(1), ItemUtils.newItemDomain(validatingData.id))
        } else {
            listOf(ItemUtils.newItemDomain(validatingData.id), ItemUtils.newItemDomain(Integer.MAX_VALUE))
        }
        val book = BookUtils.newBookDomain(1)
                .copy(items = items)

        whenever(service.getAll()).thenReturn(listOf(book))
    }

    override fun verifyMovingMock(validatingData: Item) {
        verify(service, times(2)).getAll()
        verifyNoMoreInteractions(service)
    }

}
