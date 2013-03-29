package org.openbel.bel.repl;

public class Constants {

    public final static String DOC_TEMPLATE = 
            "SET DOCUMENT Name = \"Shell Document\"\n" +
            "SET DOCUMENT Description = \"Shell Document\"\n" +
            "SET DOCUMENT Version = \"1.0\"\n" +
            "SET STATEMENT_GROUP = Main\n" +
            "%s\n" +
            "UNSET STATEMENT_GROUP";
    
    private Constants() {
    }
}
