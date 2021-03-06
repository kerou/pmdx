package org.incava.pmdx;

import java.util.Iterator;
import java.util.List;
import net.sourceforge.pmd.ast.ASTFormalParameters;
import net.sourceforge.pmd.ast.ASTNameList;
import net.sourceforge.pmd.ast.JavaParserConstants;
import net.sourceforge.pmd.ast.SimpleNode;
import net.sourceforge.pmd.ast.Token;
import org.incava.ijdk.lang.StringExt;

/**
 * Miscellaneous routines for functions (ctors and methods).
 */
public class FunctionUtil extends SimpleNodeUtil {
    /**
     * Returns the throws token, or null if none.
     */
    public static Token getThrows(SimpleNode function) {
        Token tk = function.getFirstToken();
        while (true) {
            if (tk.kind == JavaParserConstants.THROWS) {
                return tk;
            }
            else if (tk == function.getLastToken()) {
                break;
            }
            else {
                tk = tk.next;
            }
        }
        return null;
    }

    /**
     * Returns the throws list, or null if none.
     */
    public static ASTNameList getThrowsList(SimpleNode function) {
        List<Object> children = getChildren(function);
        Iterator<Object> it = children.iterator();
        while (it.hasNext()) {
            Object obj = it.next();
            if (obj instanceof Token && ((Token)obj).kind == JavaParserConstants.THROWS && it.hasNext()) {
                return (ASTNameList)it.next();
            }
        }
        return null;
    }

    protected static String toFullName(Token tk, ASTFormalParameters params) {
        List<String> types = ParameterUtil.getParameterTypes(params);
        String       args  = StringExt.join(types, ", ");
        return tk.image + "(" + args + ")";
    }
}
