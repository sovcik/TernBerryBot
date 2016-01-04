/*
 * @(#) Transmitter.java
 * 
 * Tern Tangible Programming System
 * Copyright (C) 2009 Michael S. Horn 
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


	public NQCTransmitter(Properties props) {
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
}
