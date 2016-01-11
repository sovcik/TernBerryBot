/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tern.brick;

import tern.compiler.CompileException;
import java.util.Properties;

/**
 *
 * @author Maruma
 */
public class NXCTransmitter extends Transmitter{

    private String Path;

	
	public NXCTransmitter(Properties props) {
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


	private void generateError(String err) throws CompileException {
		if (err.indexOf("Download failed") >= 0) {
			throw new CompileException(CompileException.ERR_NO_LEGO_BRICK);
		}
		
		else {
			System.out.println(err);
			throw new CompileException(CompileException.ERR_UNKNOWN);
		}
	}
    
}
