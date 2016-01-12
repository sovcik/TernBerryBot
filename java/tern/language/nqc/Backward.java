package tern.language.nqc;

import tern.compiler.CompileException;
import tern.compiler.Program;
import tern.compiler.Statement;
import tern.compiler.StatementFactory;
import topcodes.TopCode;

public class Backward extends tern.language.base.Backward{

    public Backward(TopCode top) {
        super(top);
    }

    public static void register() {
        StatementFactory.registerStatementType(
                new Backward(new TopCode(CODE)));
    }

    public Statement newInstance(TopCode top) {
        return new Backward(top);
    }

    public void compile(Program program) throws CompileException {
        setDebugInfo(program);
        program.addInstruction("Backward();");
        if (this.next != null) next.compile(program);
    }

}
