package cz.vhromada.book.utils

import cz.vhromada.book.common.Movable

/**
 * An object represents utility class for working with collections.
 *
 * @author Vladimir Hromada
 */
object CollectionUtils {

    /**
     * Returns sorted data.
     *
     * @param data data for sorting
     * @return sorted data
     */
    fun <T : Movable> getSortedData(data: List<T>): MutableList<T> {
        if (org.springframework.util.CollectionUtils.isEmpty(data)) {
            return emptyList<T>().toMutableList()
        }

        return data.sortedWith(compareBy({ it.position }, { it.id })).toMutableList()
    }

}
