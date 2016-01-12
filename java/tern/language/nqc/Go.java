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
package tern.language.nqc;

import tern.compiler.*;
import topcodes.TopCode;

public class Go extends tern.language.base.Go {

   public Go(TopCode top) {
      super(top);
   }

   public static void register() {
      StatementFactory.registerStatementType(
              new Go(new TopCode(FWD_A)));
      StatementFactory.registerStatementType(
              new Go(new TopCode(REV_A)));
      StatementFactory.registerStatementType(
              new Go(new TopCode(FWD_B)));
      StatementFactory.registerStatementType(
              new Go(new TopCode(REV_B)));
      StatementFactory.registerStatementType(
              new Go(new TopCode(FWD_C)));
      StatementFactory.registerStatementType(
              new Go(new TopCode(REV_C)));
   }

   public Statement newInstance(TopCode top) {
      return new Go(top);
   }

   public void compile(Program program) throws CompileException {
      setDebugInfo(program);
      switch (top.getCode()) {
      case FWD_A:
         program.addInstruction("StartMotor(OUT_A,3);");
         break;
      case REV_A:
         program.addInstruction("StartMotor(OUT_A,-3);");
         break;
      case FWD_B:
         program.addInstruction("StartMotor(OUT_B,3);");
         break;
      case REV_B:
         program.addInstruction("StartMotor(OUT_B,-3);");
         break;
      case FWD_C:
         program.addInstruction("StartMotor(OUT_C,3);");
         break;
      case REV_C:
         program.addInstruction("StartMotor(OUT_C,-3);");
         break;
      }
      if (this.next != null) next.compile(program);
   }

}
