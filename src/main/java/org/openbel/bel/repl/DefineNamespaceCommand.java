package org.openbel.bel.repl;

import org.openbel.framework.common.model.Namespace;
import org.openbel.framework.core.indexer.IndexingFailure;
import org.openbel.framework.core.namespace.NamespaceService;
import org.openbel.framework.core.protocol.ResourceDownloadError;

public class DefineNamespaceCommand implements REPLCommand<Namespace> {

    private final NamespaceService nsvc;

    public DefineNamespaceCommand(final NamespaceService nsvc) {
        this.nsvc = nsvc;
    }
    
    @Override
    public void execute(Namespace obj, Logger log) {
        String rl = obj.getResourceLocation();
        
        System.out.println("location: " + rl);
        if (! nsvc.isOpen(rl)) {
            try {
                nsvc.compileNamespace(rl);
                System.out.println("compiled");
            } catch (ResourceDownloadError e) {
                log.logErrorLine(e.getUserFacingMessage());
            } catch (IndexingFailure e) {
                log.logErrorLine(e.getUserFacingMessage());
            }
        }
    }
}
