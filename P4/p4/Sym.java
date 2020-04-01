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
    private String[] arguments;

    public FnSym(String type, String[] arguments) {
        super(type);
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        String[] args = getArguments();
        StringBuilder sb = new StringBuilder();
        for (int i=0, n=args.length-1; i<n; i++) {
            sb.append(args[i]);
            sb.append(", ");
        }
        if (args.length != 0)
            sb.append(args[args.length-1]);
        sb.append("->");
        sb.append(this.getType());
        return sb.toString();
    }

    public String[] getArguments() {
        return this.arguments;
    }
}

/**
* TODO comment this bad boy
*
*/
class StructDefSym extends Sym {
    // map of names of vars and their types
    private SymTable fields;
    // total bytes used up by this struct
    // private int size;

    StructDefSym(String structType, SymTable fields) {
        super(structType);
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "struct " + getType();
    }

    public SymTable getFields() {
        return this.fields;
    }

    // public int getSize() {
    //     return size;
    // }
}

/**
* TODO comment this bad boy
*
*/
class StructSym extends Sym {

    public StructSym(String type) {
        super(type);
    }

    @Override
    public String toString() {
        return "struct " + getType();
    }
}
