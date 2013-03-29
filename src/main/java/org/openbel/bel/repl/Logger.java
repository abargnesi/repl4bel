package org.openbel.bel.repl;

public interface Logger {

    public void logOutput(String msg);
    
    public void logOutputLine(String msg);
    
    public void logError(String msg);
    
    public void logErrorLine(String msg);
    
    public void logUnreadableWarning(String uobj);
    
    public void logUnreadableError(String uobj);
    
    public void logObject(String uobj);
}
