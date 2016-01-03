/*
 * @(#) Transmitter.java
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

import java.io.BufferedInputStream;
import tern.compiler.CompileException;
import java.util.Properties;


/**
 * Generic Transmitter class - should be used for implementation of specific transmitters
 */
public class Transmitter {

    /** Compiler binary */
    protected String compiler;

    /** Brick name */
    protected String brickName;

    /** Compiler process */
    protected Process process;

    /** Command to execute */
    protected String [] command;


    public Transmitter() {} // default constructor

    public Transmitter(Properties props) {
        this.compiler = props.getProperty("brick.compiler");
        this.process  = null;
        this.command  = new String[4];
    }


    /**
     * Sends a program to the brick.
     */
    public void send(String filename) throws CompileException {

        command[0] = compiler;
        command[1] = filename;

        exec(command);
    }


    /**
     * Asynchronous process monitoring...
     */
    protected void exec(String [] command) throws CompileException {
        try {

            int b;
            String sout = "", serr = "";
            BufferedInputStream in;
            int result;


            //---------------------------------------------
            // Start external process
            //---------------------------------------------
            this.process = Runtime.getRuntime().exec(command);

            //---------------------------------------------
            // Read external process output
            //---------------------------------------------
            in = new BufferedInputStream(process.getInputStream());
            while ((b = in.read()) > 0) { sout += (char)b; }

            //---------------------------------------------
            // Read external process error
            //---------------------------------------------
            in = new BufferedInputStream(process.getErrorStream());
            while ((b = in.read()) > 0) { serr += (char)b; }

            //---------------------------------------------
            // Wait for process to complete
            //---------------------------------------------
            try { result = process.waitFor(); }
            catch (InterruptedException ix) { result = 0; }

            if (result != 0) {
                generateError(sout + serr);
            }
        }
        catch (java.io.IOException iox) {
            throw new CompileException(CompileException.ERR_NO_COMPILER);
        }
    }


    private void generateError(String err) throws CompileException {
        // This method should parse text output of external process
        // and throw relevant exception.
        // Generic implementation resolves all errors as UNKNOWN
        System.out.println(err);
        throw new CompileException(CompileException.ERR_UNKNOWN);
    }
}
