/*
 * @(#) Go.java
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

import topcodes.TopCode;

public abstract class Go extends tern.language.PStatement {

   public static final int FWD_A = 211;
   public static final int REV_A = 229;
   public static final int FWD_B = 117;
   public static final int REV_B = 87;
   public static final int FWD_C = 217;
   public static final int REV_C = 241;

   
   public Go(TopCode top) {
      super(top);
   }


   public String getName() {
      return ("GO");
   }

   
   public void toXML(java.io.PrintWriter out) {
      switch (top.getCode()) {
      case FWD_A: out.println("   <go param='fwd_a' />"); break;
      case REV_A: out.println("   <go param='rev_a' />"); break;
      case FWD_B: out.println("   <light param='on' />"); break;
      case REV_B: out.println("   <go param='rev_b' />"); break;
      case FWD_C: out.println("   <go param='fwd_c' />"); break;
      case REV_C: out.println("   <go param='rev_c' />"); break;
	  }
	}
}
