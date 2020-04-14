package cz.vhromada.bookcase.facade

import cz.vhromada.bookcase.CoreTestConfiguration
import cz.vhromada.bookcase.entity.Book
import cz.vhromada.bookcase.entity.Item
import cz.vhromada.bookcase.utils.BookUtils
import cz.vhromada.bookcase.utils.ItemUtils
import cz.vhromada.common.entity.Language
import cz.vhromada.common.facade.MovableChildFacade
import cz.vhromada.common.result.Event
import cz.vhromada.common.result.Severity
import cz.vhromada.common.result.Status
import cz.vhromada.common.test.facade.MovableChildFacadeIntegrationTest
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.test.context.ContextConfiguration
import javax.persistence.EntityManager

/**
 * A class represents integration test for class [ItemFacade].
 *
 * @author Vladimir Hromada
 */
@ContextConfiguration(classes = [CoreTestConfiguration::class])
class ItemFacadeIntegrationTest : MovableChildFacadeIntegrationTest<Item, cz.vhromada.bookcase.domain.Item, Book>() {

    /**
     * Instance of [EntityManager]
     */
    @Autowired
    @Qualifier("containerManagedEntityManager")
    private lateinit var entityManager: EntityManager

    /**
     * Instance of (@link ItemFacade}
     */
    @Autowired
    private lateinit var facade: ItemFacade

    /**
     * Test method for [ItemFacade.add] with item with null languages.
     */
    @Test
    fun addNullLanguages() {
        val item = newChildData(null)
                .copy(languages = null)

        val result = facade.add(BookUtils.newBook(1), item)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "ITEM_LANGUAGES_NULL", "Languages mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [ItemFacade.add] with item with empty empty languages.
     */
    @Test
    fun addEmptyLanguages() {
        val item = newChildData(null)
                .copy(languages = emptyList())

        val result = facade.add(BookUtils.newBook(1), item)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "ITEM_LANGUAGES_EMPTY", "Languages mustn't be empty.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [ItemFacade.add] with item with languages with null value.
     */
    @Test
    fun addBadLanguages() {
        val item = newChildData(null)
                .copy(languages = listOf(Language.CZ, null))

        val result = facade.add(BookUtils.newBook(1), item)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "ITEM_LANGUAGES_CONTAIN_NULL", "Languages mustn't contain null value.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [ItemFacade.add] with item with null format.
     */
    @Test
    fun addNullFormat() {
        val item = newChildData(null)
                .copy(format = null)

        val result = facade.add(BookUtils.newBook(1), item)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "ITEM_FORMAT_NULL", "Format mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [ItemFacade.add] with item with null note.
     */
    @Test
    fun addNullNote() {
        val item = newChildData(null)
                .copy(note = null)

        val result = facade.add(BookUtils.newBook(1), item)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "ITEM_NOTE_NULL", "Note mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [ItemFacade.update] with item with null languages.
     */
    @Test
    fun updateNullLanguages() {
        val item = newChildData(1)
                .copy(languages = null)

        val result = facade.update(item)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "ITEM_LANGUAGES_NULL", "Languages mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [ItemFacade.update] with item with empty empty languages.
     */
    @Test
    fun updateEmptyLanguages() {
        val item = newChildData(1)
                .copy(languages = emptyList())

        val result = facade.update(item)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "ITEM_LANGUAGES_EMPTY", "Languages mustn't be empty.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [ItemFacade.update] with item with languages with null value.
     */
    @Test
    fun updateBadLanguages() {
        val item = newChildData(1)
                .copy(languages = listOf(Language.CZ, null))

        val result = facade.update(item)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "ITEM_LANGUAGES_CONTAIN_NULL", "Languages mustn't contain null value.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [ItemFacade.update] with item with null format.
     */
    @Test
    fun updateNullFormat() {
        val item = newChildData(1)
                .copy(format = null)

        val result = facade.update(item)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "ITEM_FORMAT_NULL", "Format mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    /**
     * Test method for [ItemFacade.update] with item with null note.
     */
    @Test
    fun updateNullNote() {
        val item = newChildData(1)
                .copy(note = null)

        val result = facade.update(item)

        assertSoftly {
            it.assertThat(result.status).isEqualTo(Status.ERROR)
            it.assertThat(result.events()).isEqualTo(listOf(Event(Severity.ERROR, "ITEM_NOTE_NULL", "Note mustn't be null.")))
        }

        assertDefaultRepositoryData()
    }

    override fun getFacade(): MovableChildFacade<Item, Book> {
        return facade
    }

    override fun getDefaultParentDataCount(): Int {
        return BookUtils.BOOKS_COUNT
    }

    override fun getDefaultChildDataCount(): Int {
        return ItemUtils.ITEMS_COUNT
    }

    override fun getRepositoryParentDataCount(): Int {
        return BookUtils.getBooksCount(entityManager)
    }

    override fun getRepositoryChildDataCount(): Int {
        return ItemUtils.getItemsCount(entityManager)
    }

    override fun getDataList(parentId: Int): List<cz.vhromada.bookcase.domain.Item> {
        return ItemUtils.getItems(parentId)
    }

    override fun getDomainData(index: Int): cz.vhromada.bookcase.domain.Item {
        return ItemUtils.getItem(index)
    }

    override fun newParentData(id: Int?): Book {
        return BookUtils.newBook(id)
    }

    override fun newChildData(id: Int?): Item {
        return ItemUtils.newItem(id)
    }

    override fun newDomainData(id: Int): cz.vhromada.bookcase.domain.Item {
        return ItemUtils.newItemDomain(id)
    }

    override fun getRepositoryData(id: Int): cz.vhromada.bookcase.domain.Item? {
        return ItemUtils.getItem(entityManager, id)
    }

    override fun getParentName(): String {
        return "Book"
    }

    override fun getChildName(): String {
        return "Item"
    }

    override fun assertDataListDeepEquals(expected: List<Item>, actual: List<cz.vhromada.bookcase.domain.Item>) {
        ItemUtils.assertItemListDeepEquals(expected, actual)
    }

    override fun assertDataDeepEquals(expected: Item, actual: cz.vhromada.bookcase.domain.Item) {
        ItemUtils.assertItemDeepEquals(expected, actual)
    }

    override fun assertDataDomainDeepEquals(expected: cz.vhromada.bookcase.domain.Item, actual: cz.vhromada.bookcase.domain.Item) {
        ItemUtils.assertItemDeepEquals(expected, actual)
    }

}
