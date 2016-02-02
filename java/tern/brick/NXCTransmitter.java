/*
 * @(#) NXCTransmitter.java
 *
 * Tern Tangible Programming System
 * Copyright (C) 2009 Michael S. Horn
 * Copyright (C) 2009 Maruma
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
 *
 * @author Maruma
 */
public class NXCTransmitter extends Transmitter{

    private String Path;

	
	public NXCTransmitter(Properties props, Logger logger) {
		this.log = logger;
		this.compiler = props.getProperty("nxc.compiler");
		this.process  = null;
		this.Path = props.getProperty("nxc.path");
    }
        
       
	//Mariam
	//04-09-2011
	public void Run(String filename)throws CompileException
	{
		String[] command = new String[4];

		command[0] = compiler;
		command[1] = "-r";
		command[2] = filename;
		command[3] = "";

		exec(command);
	}


	private void processOutput(int resultCode, String sOut, String sErr) throws CompileException {

		if (sErr.contains("Download failed")) {
			throw new CompileException(CompileException.ERR_NO_LEGO_BRICK, "Download to brick failed.");
		} else {
			System.out.println(sErr);
			throw new CompileException(CompileException.ERR_UNKNOWN, sErr);
		}
	}
    
}
