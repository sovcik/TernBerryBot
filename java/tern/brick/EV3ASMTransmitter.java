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
        this.command  = new String[4];
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

    private String compile(String filename) throws CompileException {

        String bytecodeFile;
        String asmFile;
        int i;

        i = filename.lastIndexOf(".");
        if (i>0)
            asmFile = filename.substring(0, i);
        else
            asmFile = filename;

        bytecodeFile = asmFile + ".rbf";

        //-------------------------------------------------
        // Compile program to bytecode
        //-------------------------------------------------
        //*** TODO: TEST!!!!
        command[0] = compiler;
        command[1] = "-S";
        command[2] = "-d";
        command[3] = filename;

        exec(command);

        try {
            File f = new File(bytecodeFile);
            f.delete(); // delete any existing previously created file
            String cmd = "java -jar assembler.jar ../tern-program"; //extension .lms is added automatically
            Runtime.getRuntime().exec(cmd);
            Thread.sleep(1000);
            if(!f.exists() || f.isDirectory())
                throw new Exception("Error: No RBF file created. Expected: tern-program.rbf");
        } catch (Exception E){
            System.err.println(E.toString());
            System.exit(2);
        }

        return bytecodeFile;

    }

    private void transmitOverBT(String filename) {

    }

}
