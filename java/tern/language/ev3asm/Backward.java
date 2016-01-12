package tern.language.ev3asm;

import tern.compiler.CompileException;
import tern.compiler.Program;
import topcodes.TopCode;

public class Backward extends tern.language.base.Backward{

    public Backward(TopCode top) {
        super(top);
    }

    public void compile(Program program) throws CompileException {
        setDebugInfo(program);
        program.addInstruction("CALL(Backward)");
        if (this.next != null) next.compile(program);
    }

}
