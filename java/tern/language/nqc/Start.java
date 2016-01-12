/*
 * @(#) Start.java
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
package tern.language.nqc;

import topcodes.*;
import tern.compiler.*;


public class Start extends tern.language.base.Start implements StartStatement {

   public Start(TopCode top) {
      super(top);
   }

   public static void register() {
      StatementFactory.registerStatementType(
              new Start(new TopCode(CODE)));
   }


   public Statement newInstance(TopCode top) {
      return new Start(top);
   }


   public void compile(Program program) throws CompileException {
      setDebugInfo(program);
      //for nqc
      //  program.addInstruction("#include \"nqc/base.nqc\"");

      //for nxc
      program.addInstruction("#include \"base.h\"");
      program.addInstruction("");
      program.addInstruction("task main() {");

      // program.addInstruction("Begin();");

      if (next != null) next.compile(program);

      program.addInstruction("}");
   }

}
