package org.openbel.bel.repl;

import static java.lang.System.in;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.Tree;
import org.openbel.framework.common.cfg.SystemConfiguration;
import org.openbel.framework.common.model.Document;
import org.openbel.framework.common.model.Header;
import org.openbel.framework.common.model.Namespace;
import org.openbel.framework.common.model.StatementGroup;
import org.openbel.framework.core.annotation.DefaultAnnotationService;
import org.openbel.framework.core.compiler.DefaultValidationService;
import org.openbel.framework.core.compiler.SemanticServiceImpl;
import org.openbel.framework.core.compiler.ValidationService;
import org.openbel.framework.core.df.cache.DefaultCacheLookupService;
import org.openbel.framework.core.df.cache.DefaultCacheableResourceService;
import org.openbel.framework.core.namespace.DefaultNamespaceService;
import org.openbel.framework.core.namespace.NamespaceIndexerServiceImpl;
import org.openbel.framework.core.namespace.NamespaceService;

public class Shell {
    
    private final static String PROMPT = "bel>> ";
    private static List<REPLCommand<?>> commands = new ArrayList<>();

    public static void main(String[] args) throws RecognitionException {
        Logger log = new REPLLogger();
        
        try {
            SystemConfiguration.createSystemConfiguration(null);
        } catch (IOException e) {
            log.logErrorLine("repl error: " + e.getMessage());
        }
        
        Header hdr = new Header("Interactive", "Interactive", "1.0");
        Document doc = new Document(hdr, new StatementGroup());
        
        NamespaceService nsvc = new DefaultNamespaceService(
                new DefaultCacheableResourceService(),
                new DefaultCacheLookupService(),
                new NamespaceIndexerServiceImpl());
        
        ValidationService svc = new DefaultValidationService(
                nsvc,
                new SemanticServiceImpl(nsvc),
                new DefaultAnnotationService());

        commands.add(new StatementCommand(svc));
        
        List<String> commands = new ArrayList<>();
        try (BufferedReader r = new BufferedReader(new InputStreamReader(in))) {
            while (true) {
                log.logOutput(PROMPT);
                String input = r.readLine();

                Tree pt = parse(input);
                
                if ("NSDEF".equals(pt.getText())) {
                    DefineNamespaceCommand cmd = new DefineNamespaceCommand(nsvc);
                    String prefix = pt.getChild(0).getText();
                    String location = pt.getChild(1).getText();
                    location = location.replace("\"", "");
                    
                    cmd.execute(new Namespace(prefix, location), log);
                }
                commands.add(input);
            }
        } catch (IOException e) {
            log.logError("repl error: " + e.getMessage());
        }
    }
    
    private static Tree parse(String input) throws RecognitionException {
        CharStream stream = new ANTLRStringStream(input);
        BELScript_v1Lexer lexer = new BELScript_v1Lexer(stream);
        TokenStream tokenStream = new CommonTokenStream(lexer);
        BELScript_v1Parser parser = new BELScript_v1Parser(tokenStream);
        BELScript_v1Parser.record_return ret = parser.record();
        
        System.out.println(((Tree) ret.tree).toStringTree());
        System.out.println(((Tree) ret.tree).getText());
        System.out.println(((Tree) ret.tree).getType());
        return (Tree) ret.tree;
    }
}
