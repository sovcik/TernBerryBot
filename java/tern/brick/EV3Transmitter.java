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
import java.io.IOException;
import java.util.Properties;

import EV3j.*;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;


/**
 * Compiles assembler file into EV3 bytecodes and transmits it to EV3 brick
 */
public class EV3Transmitter extends Transmitter {

    EV3Device brick;
    String URL = "";

    public EV3Transmitter(Properties props) throws CompileException {
        this.compiler = props.getProperty("brick.compiler");
        this.connType = props.getProperty("brick.connection").toLowerCase();
        this.brickName  = props.getProperty("brick."+this.connType+".name");
        this.brickAddress  = props.getProperty("brick."+this.connType+".address");
        this.process  = null;


        if (this.connType.equals("bluetooth")) {
            // set some default timeout for BT connections, otherwise we'll wait 2 minutes
            System.setProperty("bluecove.connect.timeout", "3000");

            brick = new EV3DeviceBT(this.brickName);
            if (this.brickAddress.length() == 0)
                this.brickAddress = getBTAddressFromPaired(this.brickName);

            URL = "btspp://"+this.brickAddress+":1;authenticate=false;encrypt=false;master=false";
        }

    }


    /**
     * Sends a program to the brick.
     */
    public void send(String filename) throws CompileException {
        String remoteFile = BC.vmPRJS_DIR+"/tern/terncmd.rbf";

        String compiledProg;

        // compile asm file to bytecode and return file name of file containing bytecode
        compiledProg = compile(filename);

        try {

            this.connect();

            // send file containing bytecode to brick
            brick.downloadToBrick(compiledProg, remoteFile);

            // run file which we have downloaded
            brick.runFile(remoteFile);

        } catch (EV3DeviceException e) {
            throw new CompileException(CompileException.ERR_NO_LEGO_BRICK, "Downloading of file "+compiledProg+" to brick failed.", e);
        }

    }


    private void generateError(String err) throws CompileException {
        // TODO: analyze possible error messages
        if (err.indexOf("Syntax error") >= 0) {
            throw new CompileException(CompileException.ERR_UNKNOWN);
        }
        else {
            System.out.println(err);
            throw new CompileException(CompileException.ERR_UNKNOWN);
        }
    }

    protected void connect() throws CompileException {
        try {
            if (!brick.isConnected())
                brick.connect(URL);
        } catch (EV3DeviceException e) {
            throw new CompileException(CompileException.ERR_BRICK_BT_FAILED, "Connection to brick failed.", e);
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

    public String getBTAddressFromPaired(String BTname) throws CompileException {

        String addr = "";

        try {
            RemoteDevice[] rdList = LocalDevice.getLocalDevice().getDiscoveryAgent().retrieveDevices(DiscoveryAgent.PREKNOWN);
            int deviceCount = rdList.length;

            if (deviceCount > 0) {
                for (int i = 0; i < deviceCount; i++) {

                    if (rdList[i].getFriendlyName(false).equals(BTname)) {
                        addr = rdList[i].getBluetoothAddress();
                        break;
                    }

                }
            }
        } catch (BluetoothStateException e) {
            new CompileException(CompileException.ERR_BRICK_BT_FAILED, "Bluetooth failed to initialize.", e);
        } catch (IOException e) {
            new CompileException(CompileException.ERR_BRICK_BT_FAILED, "Failed reading bluetooth device name.", e);
        }

        return addr;
    }


}
