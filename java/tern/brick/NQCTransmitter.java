/*
 * @(#) NQCTransmitter.java
 * 
 * Tern Tangible Programming System
 * Copyright (C) 2009 Michael S. Horn
 * Copyright (C) 2016 Jozef Sovcik
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

import tern.Logger;
import tern.compiler.CompileException;
import java.util.Properties;


/**
 * Transmits compiled programs and firmware to the RCX
 * processor via the NQC compiler.
 *
 * @author Michael Horn
 * @version $Revision: 1.4 $, $Date: 2009/02/12 23:06:27 $
 */
public class NQCTransmitter extends Transmitter{

	/** The Lego Mindstorms firmware binary. */
    private String firmware = "firm0328.lgo";

	/** LEGO tower port */
	private String port;


	public NQCTransmitter(Properties props, Logger logger) {
		this.log = logger;
		this.firmware = props.getProperty("brick.firmware");
		this.compiler = props.getProperty("brick.compiler");
		this.port     = props.getProperty("brick.port");
		this.process  = null;

   }


	/**
	 * Sends a program to the RCX.
	 */
	public void send(String filename) throws CompileException {

		String[] command = new String[4];
         
		/*
		 * Format brick.exe command line:
		 *
		 *     brick -S<port> -d <program>
		 */
		command[0] = compiler;
		command[1] = "-S" + port;
		command[2] = "-d";
		command[3] = filename;
		
		exec(command);
	}


	private void processOutput(int resultCode, String sOut, String sErr) throws CompileException {
		if (resultCode > 0)
			log.log("[W] Compiler ended with errors:\n"+sErr);
		else
			return;

		if (sErr.contains("No reply from RCX")) {
			throw new CompileException(CompileException.ERR_NO_LEGO_BRICK, "No reply from RCX.");
		}
		else if (sErr.contains("Could not open serial port or USB device")) {
			throw new CompileException(CompileException.ERR_NO_TOWER, "Could not open serial port or USB device");
		}
		else if (sErr.contains("No firmware")) {
			throw new CompileException(CompileException.ERR_FIRMWARE, "No firmware");
		}
		else {
			System.out.println(sErr);
			throw new CompileException(CompileException.ERR_UNKNOWN, sErr);
		}
	}
}
