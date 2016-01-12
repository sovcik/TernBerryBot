/*
 * @(#) Num.java
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
package tern.language.base;

import topcodes.TopCode;

public abstract class Num extends tern.language.PStatement {

   public static final int CODE_2 = 203;
   public static final int CODE_3 = 339;
   public static final int CODE_4 = 587;
   public static final int CODE_5 = 595;


   public Num(TopCode top) {
      super(top);
   }


   public String getName() {
      return "NUMBER";
   }


   public int getValue() {
      switch (getCode()) {
      case CODE_2: return 2;
      case CODE_3: return 3;
      case CODE_4: return 4;
      case CODE_5: return 5;
      default: return 0;
      }
   }


	public void toXML(java.io.PrintWriter out) {
      switch (getCode()) {
      case CODE_2: out.println("   <number-2 />"); break;
      case CODE_3: out.println("   <number-3 />"); break;
      case CODE_4: out.println("   <number-4 />"); break;
      case CODE_5: out.println("   <number-5 />"); break;
      }
	}
}
