package org.openbel.bel.repl;

public interface REPLCommand<T> {

    public void execute(T obj, Logger log);
}
