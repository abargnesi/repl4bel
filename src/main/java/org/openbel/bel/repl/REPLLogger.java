package org.openbel.bel.repl;

import static java.lang.System.out;
import static java.lang.System.err;

public class REPLLogger implements Logger {

    public final static String UNREADABLE_OBJECT_PREFIX = "# ";
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void logOutput(String msg) {
        out.print(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logOutputLine(String msg) {
        out.println(msg);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void logError(String msg) {
        err.print(msg);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void logErrorLine(String msg) {
        err.println(msg);
    }

    @Override
    public void logUnreadableWarning(String uobj) {
        logOutputLine(UNREADABLE_OBJECT_PREFIX + uobj);
    }

    @Override
    public void logUnreadableError(String uobj) {
        logErrorLine(UNREADABLE_OBJECT_PREFIX + uobj);
    }

    @Override
    public void logObject(String obj) {
        logOutputLine(obj);
    }
}
