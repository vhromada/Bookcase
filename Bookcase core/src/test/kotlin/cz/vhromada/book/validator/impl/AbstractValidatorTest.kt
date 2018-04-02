package cz.vhromada.book.validator.impl

import cz.vhromada.book.common.Movable
import cz.vhromada.book.service.BookcaseService
import cz.vhromada.book.stub.impl.ServiceStubImpl
import cz.vhromada.book.validator.BookcaseValidator
import cz.vhromada.book.validator.common.ValidationType
import cz.vhromada.result.Event
import cz.vhromada.result.Severity
import cz.vhromada.result.Status
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * An abstract class represents test for validator.
 *
 * @author Vladimir Hromada
 */
abstract class AbstractValidatorTest<T : Movable, U : Movable> {

    /**
     * Instance of [BookcaseService]
     */
    protected val bookcaseService = ServiceStubImpl<U>()

    /**
     * Name of entity
     */
    protected abstract val name: String

    /**
     * ID
     */
    private val id = 5

    /**
     * Instance of [BookcaseValidator]
     */
    private lateinit var bookcaseValidator: BookcaseValidator<T>

    /**
     * Prefix for validation keys
     */
    private val prefix get() = name.toUpperCase()

    /**
     * Initializes validator.
     */
    @BeforeEach
    fun setUp() {
        bookcaseValidator = getBookcaseValidator()
    }

    /**
     * Test method for [BookcaseValidator.validate] with [ValidationType.NEW] with correct data.
     */
    @Test
    fun validate_New() {
        val result = bookcaseValidator.validate(getValidatingData(null), ValidationType.NEW)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.OK)
            softly.assertThat<Event>(result.events).isEmpty()
        }

        bookcaseService.verifyZeroInteractions()
    }

    /**
     * Test method for [BookcaseValidator.validate] with [ValidationType.NEW] with data with not null ID.
     */
    @Test
    fun validate_New_NotNullId() {
        val result = bookcaseValidator.validate(getValidatingData(Int.MAX_VALUE), ValidationType.NEW)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, prefix + "_ID_NOT_NULL", "ID must be null.")))
        }

        bookcaseService.verifyZeroInteractions()
    }

    /**
     * Test method for [BookcaseValidator.validate] with [ValidationType.EXISTS] with correct data.
     */
    @Test
    fun validate_Exists() {
        val validatingData = getValidatingData(id)
        bookcaseService.getResult = getRepositoryData(validatingData)

        val result = bookcaseValidator.validate(validatingData, ValidationType.EXISTS)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.OK)
            softly.assertThat<Event>(result.events).isEmpty()
        }

        bookcaseService.verifyGet(validatingData.id!!)
        bookcaseService.verifyNoMoreInteractions()
    }

    /**
     * Test method for [BookcaseValidator.validate] with [ValidationType.EXISTS] with data with null ID.
     */
    @Test
    fun validate_Exists_NullId() {
        val result = bookcaseValidator.validate(getValidatingData(null), ValidationType.EXISTS)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, prefix + "_ID_NULL", "ID mustn't be null.")))
        }

        bookcaseService.verifyZeroInteractions()
    }

    /**
     * Test method for [BookcaseValidator.validate] with [ValidationType.EXISTS] with not existing data.
     */
    @Test
    fun validate_Exists_NotExistingData() {
        val validatingData = getValidatingData(id)

        val result = bookcaseValidator.validate(validatingData, ValidationType.EXISTS)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, prefix + "_NOT_EXIST", "$name doesn't exist.")))
        }

        bookcaseService.verifyGet(validatingData.id!!)
        bookcaseService.verifyNoMoreInteractions()
    }

    /**
     * Test method for [BookcaseValidator.validate] with [ValidationType.UP] with correct data.
     */
    @Test
    fun validate_Up() {
        val validatingData = getValidatingData(id)

        initMovingMock(validatingData, true, true)

        val result = bookcaseValidator.validate(validatingData, ValidationType.UP)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.OK)
            softly.assertThat<Event>(result.events).isEmpty()
        }

        bookcaseService.verifyGetAll()
        bookcaseService.verifyGet(validatingData.id!!)
        bookcaseService.verifyNoMoreInteractions()
    }

    /**
     * Test method for [BookcaseValidator.validate] with [ValidationType.UP] with invalid data.
     */
    @Test
    fun validate_Up_Invalid() {
        val validatingData = getValidatingData(Int.MAX_VALUE)

        initMovingMock(validatingData, true, false)

        val result = bookcaseValidator.validate(validatingData, ValidationType.UP)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, prefix + "_NOT_MOVABLE", "$name can't be moved up.")))
        }

        bookcaseService.verifyGetAll()
        bookcaseService.verifyGet(validatingData.id!!)
        bookcaseService.verifyNoMoreInteractions()
    }

    /**
     * Test method for [BookcaseValidator.validate] with [ValidationType.DOWN] with correct data.
     */
    @Test
    fun validate_Down() {
        val validatingData = getValidatingData(id)

        initMovingMock(validatingData, false, true)

        val result = bookcaseValidator.validate(validatingData, ValidationType.DOWN)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.OK)
            softly.assertThat<Event>(result.events).isEmpty()
        }

        bookcaseService.verifyGetAll()
        bookcaseService.verifyGet(validatingData.id!!)
        bookcaseService.verifyNoMoreInteractions()
    }

    /**
     * Test method for [BookcaseValidator.validate] with [ValidationType.DOWN] with invalid data.
     */
    @Test
    fun validate_Down_Invalid() {
        val validatingData = getValidatingData(Int.MAX_VALUE)

        initMovingMock(validatingData, false, false)

        val result = bookcaseValidator.validate(validatingData, ValidationType.DOWN)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.ERROR)
            softly.assertThat<Event>(result.events).isEqualTo(listOf(Event(Severity.ERROR, prefix + "_NOT_MOVABLE", "$name can't be moved down.")))
        }

        bookcaseService.verifyGetAll()
        bookcaseService.verifyGet(validatingData.id!!)
        bookcaseService.verifyNoMoreInteractions()
    }

    /**
     * Test method for [BookcaseValidator.validate] with [ValidationType.DEEP] with correct data.
     */
    @Test
    fun validate_Deep() {
        val result = bookcaseValidator.validate(getValidatingData(id), ValidationType.DEEP)

        assertSoftly { softly ->
            softly.assertThat<Status>(result.status).isEqualTo(Status.OK)
            softly.assertThat<Event>(result.events).isEmpty()
        }

        bookcaseService.verifyZeroInteractions()
    }

    /**
     * Returns instance of [BookcaseValidator].
     *
     * @return instance of [BookcaseValidator]
     */
    protected abstract fun getBookcaseValidator(): BookcaseValidator<T>

    /**
     * Returns instance of [T].
     *
     * @param id ID
     * @return instance of [T]
     */
    protected abstract fun getValidatingData(id: Int?): T

    /**
     * Returns instance of [U].
     *
     * @param validatingData validating data
     * @return instance of [U]
     */
    protected abstract fun getRepositoryData(validatingData: T): U

    /**
     * Returns 1st item in data list.
     *
     * @return 1st item in data list
     */
    protected abstract fun getItem1(): U

    /**
     * Returns 2nd item in data list.
     *
     * @return 2nd item in data list
     */
    protected abstract fun getItem2(): U

    /**
     * Initializes mock for moving.
     *
     * @param validatingData validating data
     * @param up             true if moving up
     * @param valid          true if data should be valid
     */
    private fun initMovingMock(validatingData: T, up: Boolean, valid: Boolean) {
        val dataList = mutableListOf(getItem1(), getItem2())
        val repositoryData = getRepositoryData(validatingData)
        if (up && valid || !up && !valid) {
            dataList.add(repositoryData)
        } else {
            dataList.add(0, repositoryData)
        }

        bookcaseService.getAllResult = dataList
        bookcaseService.getResult = repositoryData
    }

}
