/*
 * @(#) Start.java
 * 
 * Tern Tangible Programming System
 * Copyright (C) 2009 Michael S. Horn
 * Portions Copyright (C) 2016 Jozef Sovcik
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
package tern.language.ev3asm;

import topcodes.*;
import tern.compiler.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;



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

      // include main set of constants
      includeFile(program, "lmsasm/tern-define.txt");

      // include header of the main thread including global variables
      includeFile(program, "lmsasm/tern-mainthread.txt");

      // add intructions to the main thread
      program.codeIndent();
      if (next != null)
         next.compile(program);

      // end of the main thread
      program.codeOutdent();
      program.addInstruction("}");

      // include definitions of subcalls
      includeFile(program, "lmsasm/tern-subcalls.txt");
   }

   private void includeFile(Program program, String fname) throws CompileException{

      File f = new File(fname);

      try {
         BufferedReader br = new BufferedReader(new FileReader(f));

         String line = null;
         while ((line = br.readLine()) != null) {
            program.addInstruction(line);
         }

         br.close();

      } catch (IOException x) {
         System.err.println("Error reading file "+fname);
         throw new CompileException(CompileException.ERR_TERN_LANG, "Error reading file "+fname);
      }
   }

}
