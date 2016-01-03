/*
 * @(#) WebCam.java
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

public class WebCam {

   protected int width;
   protected int height;
   protected String type = "none";

   protected boolean webCamReady = false;

   public WebCam() {}
   public WebCam(Properties props) {}


   public boolean isReady(){return webCamReady;}

   public void initialize() throws WebCamException {
      webCamReady = false;
   }

   public void uninitialize() throws WebCamException {
      webCamReady = false;
   }

   // returns file name
   public String getFileName() throws WebCamException {
      return "foo.bar";
   }

}


