/*
 * @(#) PStatement.java
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
package tern.language;

import tern.compiler.*;
import topcodes.*;


/**
 * A base class for all statements in the problocks language.
 *
 * @author Michael Horn
 * @version $Revision: 1.2 $, $Date: 2008/11/08 18:12:40 $
 */
public abstract class PStatement extends Statement {

   public static final int CODE = 999;

   public PStatement(TopCode top) {
      super(top);
   }

   abstract public Statement newInstance(TopCode top);

   public static void register() {}

   abstract public String getName();

   public int getCode() {
      return top.getCode();
   }

   public void registerConnections(ConnectionMap map) {
      map.addConnector(this, "next", 2.9, 0);
      map.addSocket(this, 0, 0);
   }
   

   protected void setDebugInfo(Program program) {
      setCompileID(program.COMPILE_ID++);
      program.expandBoundingBox(top.getCenterX(), top.getCenterY());
      program.addCompiledStatement(this);
   }

   abstract public void compile(Program program) throws CompileException;

   abstract public void toXML(java.io.PrintWriter out);
}
