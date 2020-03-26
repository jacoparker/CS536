import java.lang.StringBuilder;
import java.util.HashMap;

public class Sym {
    private String type;

    public Sym(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String toString() {
        return type;
    }
}

// Extend sym to be more specific below. We need FunctionDeclSym, IntSym,
// BoolSym, StructSym. May be able to combine int and bool into PrimSym.

/**  TODO test this
* The type field corresponds to the return type. The other field is
* the list of symbols which are in the argument list.
*/
class FnSym extends Sym {
    private Sym[] arguments;

    public FnSym(String type, Sym[] argsuments) {
        super(type);
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        Sym[] args = getArguments();
        StringBuilder sb = new StringBuilder();
        for (int i=0, n=args.length-1; i<n; i++) {
            sb.append(args[i].getType());
            sb.append(", ");
        }
        if (args.length != 0)
            sb.append(args[args.length-1]);
        sb.append("->");
        sb.append(this.getType());
        return sb.toString();
    }

    public Sym[] getArguments() {
        return this.arguments;
    }
}

/**
* TODO comment this bad boy
*
*/
class StructDefSym extends Sym {
    private HashMap<String, Sym> fields;

    public StructDefSym(String type, HashMap<String, Sym> fields) {
        super(type);
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "struct " + getType();
    }

    public HashMap<String, Sym> getFields() {
        return this.fields;
    }
}
