/*
 * @(#) EV3ASMTransmitter.java
 *
 * Tern Tangible Programming System
 * Copyright (C) 2009 Michael S. Horn
 * Copyright (C) 2015 Jozef Sovcik
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package tern.brick;

import tern.compiler.CompileException;

import java.io.File;
import java.util.Properties;


/**
 * Compiles assembler file into EV3 bytecodes and transmits it to EV3 brick
 */
public class EV3ASMTransmitter extends Transmitter {

    public EV3ASMTransmitter(Properties props) {
        this.compiler = props.getProperty("brick.compiler");
        this.brickName  = props.getProperty("brick.bluetooth.name");
        this.process  = null;
    }


    /**
     * Sends a program to the brick.
     */
    public void send(String filename) throws CompileException {

        String compiledProg;
		/*
		 * Format brick.exe command line:
		 *
		 *     brick -S<port> -d <program>
		 */
        compiledProg = compile(filename);
        transmitOverBT(compiledProg);
    }


    private void generateError(String err) throws CompileException {
        if (err.indexOf("No reply from RCX") >= 0) {
            throw new CompileException(CompileException.ERR_NO_RCX);
        }
        else if (err.indexOf("Could not open serial port or USB device") >= 0) {
            throw new CompileException(CompileException.ERR_NO_TOWER);
        }
        else if (err.indexOf("No firmware") >= 0) {
            throw new CompileException(CompileException.ERR_FIRMWARE);
        }
        else {
            System.out.println(err);
            throw new CompileException(CompileException.ERR_UNKNOWN);
        }
    }

    private String compile(String progFileName) throws CompileException {

        String bytecodeFile;
        String asmFile;
        int i;

        i = progFileName.lastIndexOf(".");
        if (i>0)
            asmFile = progFileName.substring(0, i); //extension .lms required, but not passed to assembler
        else
            asmFile = progFileName;

        bytecodeFile = asmFile + ".rbf";

        String[] command = compiler.replace("{program}",asmFile).split(" ");

        // delete any existing previously created file
        File f = new File(bytecodeFile);
        f.delete();

        // Compile program to bytecode
        exec(command);

        return bytecodeFile;

    }

    private void transmitOverBT(String filename) {

    }

}
