package cz.vhromada.bookcase.utils

import cz.vhromada.common.domain.Audit
import cz.vhromada.common.test.utils.TestConstants
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions
import java.time.LocalDateTime

/**
 * A class represents utility class for audit.
 *
 * @author Vladimir Hromada
 */
object AuditUtils {

    /**
     * Returns audit.
     *
     * @return audit
     */
    fun newAudit(): Audit {
        return Audit(user = TestConstants.ACCOUNT_ID, time = TestConstants.TIME)
    }

    /**
     * Returns audit.
     *
     * @return audit
     */
    fun getAudit(): Audit {
        return Audit(
                createdUser = 10,
                createdTime = LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                updatedUser = 2,
                updatedTime = LocalDateTime.of(2020, 1, 2, 0, 0, 0))
    }

    /**
     * Asserts audit deep equals.
     *
     * @param expected expected audit
     * @param actual   actual audit
     */
    fun assertAuditDeepEquals(expected: Audit?, actual: Audit?) {
        if (expected == null) {
            assertThat(actual).isNull()
        } else {
            assertThat(actual).isNotNull
            SoftAssertions.assertSoftly {
                it.assertThat(expected.createdUser).isEqualTo(actual!!.createdUser)
                it.assertThat(expected.createdTime).isEqualToIgnoringNanos(actual.createdTime)
                it.assertThat(expected.updatedUser).isEqualTo(actual.updatedUser)
                it.assertThat(expected.updatedTime).isEqualToIgnoringNanos(actual.updatedTime)
            }
        }
    }

}