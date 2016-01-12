/*
 * @(#) Growl.java
 * 
 * Tern Tangible Programming System
 * Copyright (C) 2009 Michael S. Horn
 * Portions Copyright (C) 2015 Jozef Sovcik
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
package tern.language.base;

import tern.compiler.*;
import topcodes.TopCode;


public abstract class Growl extends tern.language.PStatement {

   public static final int CODE = 307;

   public Growl(TopCode top) {
      super(top);
   }


   public String getName() {
      return "GROWL";
   }


   public void toXML(java.io.PrintWriter out) {
		out.println("   <growl />");
	}
}
