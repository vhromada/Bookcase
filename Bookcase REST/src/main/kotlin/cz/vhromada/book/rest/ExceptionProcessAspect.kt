package cz.vhromada.book.rest

import cz.vhromada.result.Result

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

/**
 * A class represents aspect for processing exception.
 *
 * @author Vladimir Hromada
 */
@Aspect
@Component
class ExceptionProcessAspect {

    /**
     * Process exception.
     *
     * @param proceedingJoinPoint proceeding join point
     * @return result of calling method
     */
    @Around("controllerPointcut()")
    fun process(proceedingJoinPoint: ProceedingJoinPoint): Any {
        return try {
            proceedingJoinPoint.proceed()
        } catch (exception: Exception) {
            logException(proceedingJoinPoint, exception)

            ResponseEntity(Result.error<Any>("ERROR", exception.toString()), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    /**
     * Pointcut for controller layer
     */
    @Pointcut("execution(public * cz.vhromada.bookcase..*Controller.*(..))")
    fun controllerPointcut() {
    }

    /**
     * Logs exception.
     *
     * @param proceedingJoinPoint proceeding join point
     * @param exception           exception
     */
    private fun logException(proceedingJoinPoint: ProceedingJoinPoint, exception: Exception) {
        LoggerFactory.getLogger(proceedingJoinPoint.target.javaClass).error("Error in processing data.", exception)
    }

}
