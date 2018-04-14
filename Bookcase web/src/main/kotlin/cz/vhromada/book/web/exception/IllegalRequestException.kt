package cz.vhromada.book.web.exception

/**
 * A class represents exception caused by illegal request.
 *
 * @author Vladimir Hromada
 */
class IllegalRequestException(override val message:String): RuntimeException(message)