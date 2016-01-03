/*
 * @(#) ExtCamApp.java
 *
 * Tangible Object Placement Codes (TopCodes)
 * Copyright (c) 2007 Michael S. Horn
 * Copyright (C) 2015 Jozef Sovcik
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License (version 2) as
 * published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package webcam;

import java.util.Properties;
import java.io.File;

/*
   Generic wrapper class for webcams operated through external apps
 */
public class ExtCamApp extends WebCam {

    // the path to the external camera app executable.
    protected String appPath;

    // app options
    protected String appOptions;

    // the file picture will be saved to
    protected String outFile;

    // time need for an app to take picture (in miliseconds)
    protected int appTimeout;

    // Default class constructor.
    public ExtCamApp(Properties props) {
        type = "ExtCamApp";
        width = Integer.parseInt(props.getProperty("webcam.width", "2592")); //including default width
        height = Integer.parseInt(props.getProperty("webcam.height", "1944")); // including default height

        appPath = props.getProperty("webcam.app","/opt/vc/bin/raspistill"); // including default path
        appTimeout = Integer.parseInt(props.getProperty("webcam.app.timeout", "5000")); // including default app timeout
        outFile = props.getProperty("webcam.app.outfile","tern-program.jpg"); // including default file name
        appOptions = props.getProperty("webcam.app.options","-n -bm -t {webcam.app.timeout} -w {webcam.width} -h {webcam.height} -q 100 -e jpg -o {webcam.app.outfile}"); // including default app options

        appOptions = appOptions.replace("{webcam.width}", Integer.toString(width));
        appOptions = appOptions.replace("{webcam.height}", Integer.toString(height));
        appOptions = appOptions.replace("{webcam.app.outfile}", outFile);
        appOptions = appOptions.replace("{webcam.app.timeout}", Integer.toString(appTimeout));

    }

    public void initialize() throws WebCamException {
        File f = new File(appPath);
        if(f.exists() && !f.isDirectory())
            webCamReady = true;
        else
            throw new WebCamException("External camera app does not exist.");
    }

    public String getFileName() {

        String cmd = "";

        try {

            cmd = appPath + " " + appOptions;

            // Invoke external app to take the photo.
            Runtime.getRuntime().exec(cmd);

            // Pause to allow the camera time to take the photo.
            Thread.sleep(appTimeout);

            File f = new File(outFile);
            if(!f.exists() || f.isDirectory())
                throw new Exception("Error: External app did not create any file. Expected: " + outFile);

        }
        catch (Exception e) {
            // Exit the application with the exception's hash code.
            System.exit(e.hashCode());
        }

        return outFile;
    }


}