package org.openbel.bel.repl;

import org.openbel.framework.common.model.Statement;
import org.openbel.framework.core.compiler.ValidationResult;
import org.openbel.framework.core.compiler.ValidationService;

public class StatementCommand implements REPLCommand<Statement> {

    private final ValidationService svc;

    public StatementCommand(ValidationService svc) {
        this.svc = svc;
    }
    
    @Override
    public void execute(Statement obj, Logger log) {
        ValidationResult res = svc.validate(obj);
        if (res.hasErrors()) {
            for (String err : res.getErrors()) {
                log.logUnreadableError(err);
            }
        } else if (res.hasWarnings()) {
            for (String warn : res.getWarnings()) {
                log.logUnreadableWarning(warn);
            }
        } else {
            log.logObject(obj.toBELLongForm());
        }
    }
}
