/*
 * @(#) WinUSBCam.java
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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Properties;


/**
 * Windows based USB camera support from original webcam.java class
 */
public class WinUSBCam extends WebCam {

    //*** TODO: It is absolutely untested - maybe I should remove it completely

    protected int [] rgba;

    static {
        String libPath = System.getProperty("user.dir")+"\\lib\\JavaWebCam.dll";
        //  System.loadLibrary("JavaWebCam");
        System.load(libPath);
    }

    public WinUSBCam(Properties props) {
        Properties e = props;
    }

    public native void initialize() throws WebCamException;

    public native void uninitialize();

    public void openCamera(int width, int height) throws WebCamException {
        this.width  = width;
        this.height = height;
        this.rgba   = new int [width * height];
        NopenCamera(width, height);
    }

    private native void NopenCamera(int w, int h) throws WebCamException;

    public native void closeCamera();

    public native void capture(String filename) throws WebCamException;

    public native void captureFrame() throws WebCamException;

    public native boolean isCameraOpen();

    public int getFrameWidth() { return this.width; }

    public int getFrameHeight() { return this.height; }

    public BufferedImage getFrameImage() {
        BufferedImage image =
                new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, width, height, rgba, 0, width);
        return image;
    }

    public void saveFrameImage(String FileName) throws WebCamException
    {
        BufferedImage image =
                new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, width, height, rgba, 0, width);
        File f =new File(FileName) ;
        try{
            ImageIO.write(image, "png",f);
        }
        catch (IOException e){
            throw new WebCamException("Failed to save captured image to file "+FileName,e);
        }
    }

    public int [] getFrameData() {
        return this.rgba;
    }


    private void callback(byte [] data) {
        int rgb, sindex = 0, dindex;
        if (data.length != rgba.length * 3) return;
        for (int j=0; j<height; j++) {
            dindex = (height - j - 1) * width;
            for (int i=0; i<width; i++) {
                rgb = (((255 & 0xff) << 24) |
                        (((int)data[sindex+2] & 0xff) << 16) |
                        (((int)data[sindex+1] & 0xff) << 8) |
                        ((int)data[sindex] & 0xff ));
                rgba[dindex] = rgb;
                dindex += 1;
                sindex += 3;
            }
        }
    }
}
