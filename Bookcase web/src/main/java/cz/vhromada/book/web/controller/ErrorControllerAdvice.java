package cz.vhromada.book.web.controller;

import cz.vhromada.book.web.exception.IllegalRequestException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * A class represents advice for errors.
 *
 * @author Vladimir Hromada
 */
@ControllerAdvice
public class ErrorControllerAdvice {

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ErrorControllerAdvice.class);

    /**
     * Shows page for illegal argument exception.
     *
     * @param model     model
     * @param exception exception
     * @return view for page for known exception
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public String processKnownException(final Model model, final Exception exception) {
        return processException(model, exception, "There was error in working with data.");
    }

    /**
     * Shows page for exception in request.
     *
     * @param model     model
     * @param exception exception
     * @return view for exception in request
     */
    @ExceptionHandler(IllegalRequestException.class)
    public String processRequestException(final Model model, final Exception exception) {
        return processException(model, exception, "There was illegal changes in pages or call on non existing data.");
    }

    /**
     * Shows page for unknown exception.
     *
     * @param model     model
     * @param exception exception
     * @return view for page for unknown exception
     */
    @ExceptionHandler(Exception.class)
    public String processUnknownException(final Model model, final Exception exception) {
        return processException(model, exception, "There was unexpected error.");
    }

    /**
     * Process exception.
     *
     * @param model        model
     * @param exception    exception
     * @param errorMessage error message
     * @return view for page for exception
     */
    private static String processException(final Model model, final Exception exception, final String errorMessage) {
        logger.error("Web exception", exception);

        return process(model, errorMessage);
    }

    /**
     * Adds data to model and returns view for page for exception.
     *
     * @param model        model
     * @param errorMessage error message
     * @return view for page for exception
     */
    private static String process(final Model model, final String errorMessage) {
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("title", "Error");
        model.addAttribute("inner", false);

        return "errors";
    }

}
