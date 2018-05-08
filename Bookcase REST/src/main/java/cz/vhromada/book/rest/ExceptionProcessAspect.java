package cz.vhromada.book.rest;

import cz.vhromada.result.Result;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * A class represents aspect for processing exception.
 *
 * @author Vladimir Hromada
 */
@Aspect
@Component
public class ExceptionProcessAspect {

    /**
     * Process exception.
     *
     * @param proceedingJoinPoint proceeding join point
     * @return result of calling method
     * @throws Throwable if calling method fails
     */
    @Around("controllerPointcut()")
    @SuppressWarnings("MethodMayBeStatic")
    //CHECKSTYLE.OFF: IllegalThrows
    public Object process(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (final Exception exception) {
            logException(proceedingJoinPoint, exception);

            return new ResponseEntity<>(Result.error("ERROR", exception.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //CHECKSTYLE.OFF: IllegalThrows

    /**
     * Pointcut for controller layer
     */
    @Pointcut("execution(public * cz.vhromada.book..*Controller.*(..))")
    public void controllerPointcut() {
    }

    /**
     * Logs exception.
     *
     * @param proceedingJoinPoint proceeding join point
     * @param exception           exception
     */
    private static void logException(final ProceedingJoinPoint proceedingJoinPoint, final Exception exception) {
        LoggerFactory.getLogger(proceedingJoinPoint.getTarget().getClass()).error("Error in processing data.", exception);
    }

}
