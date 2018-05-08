package cz.vhromada.book.web.controller;

import cz.vhromada.result.Result;
import cz.vhromada.result.Status;

/**
 * An abstract class represents controller for processing result.
 *
 * @author Vladimir Hromada
 */
public abstract class AbstractResultController {

    /**
     * Process results.
     *
     * @param results results
     * @throws IllegalArgumentException if results aren't OK
     */
    protected static void processResults(final Result<?>... results) {
        final Result<Void> result = new Result<>();
        for (final Result<?> resultItem : results) {
            result.addEvents(resultItem.getEvents());
        }

        if (Status.OK != result.getStatus()) {
            throw new IllegalArgumentException("Operation result with errors. " + result);
        }
    }

}
