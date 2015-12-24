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
package tern.language;

import topcodes.*;
import tern.compiler.*;


public class Start extends PStatement implements StartStatement {

   
   public static final int CODE = 569;

   
   public Start(TopCode top) {
      super(top);
   }


   public static void register() {
      StatementFactory.registerStatementType(
         new Start(new TopCode(CODE)));
   }

   
   public int getCode() {
      return CODE;
   }


   public Statement newInstance(TopCode top) {
      return new Start(top);
   }
   
   
   public String getName() {
      return "START";
   }


   public boolean isUnique() {
      return true;
   }


   public void registerConnections(ConnectionMap map) {
      map.addConnector(this, "next", 2.9, 0);
   }
   

   public void compile(Program program) throws CompileException {
      setDebugInfo(program);
      addDefines(program);
      program.addInstruction("vmthread main {");
      program.codeIndent();
      if (next != null) next.compile(program);
      program.codeOutdent();
      program.addInstruction("}");

      program.addInstruction("");
      addSubCalls(program);
   }

   private void addDefines(Program program){
      program.addInstruction("define LAYER     0");
      program.addInstruction("");
      program.addInstruction("define MOTOR_BC  6");
      program.addInstruction("");
      program.addInstruction("define BRAKE_STOP  1");
      program.addInstruction("define BRAKE_COAST 0");
      program.addInstruction("");
      program.addInstruction("define  MOTOR_LEFT   MOTOR_B");
      program.addInstruction("define  MOTOR_RIGHT  MOTOR_C ");
      program.addInstruction("define  MOTOR_BOTH   MOTOR_BC");
      program.addInstruction("define  MOTOR_SPEED  30");
      program.addInstruction("define  MOTOR_NEGATIVE_SPEED -30");
      program.addInstruction("define  MOTOR_STEP	1000");
      program.addInstruction("");
   }

   private void addSubCalls(Program program){
      program.addInstruction("//-----------------------------------");
      program.addInstruction("subcall Forward {");
      program.addInstruction("   OUTPUT_TIME_SPEED(LAYER, MOTOR_BOTH, MOTOR_SPEED, 500, MOTOR_STEP, 500, BRAKE_STOP )");
      program.addInstruction("   CALL (wait_for_motors)");
      program.addInstruction("}");
      program.addInstruction("");
      program.addInstruction("//-----------------------------------");
      program.addInstruction("subcall Backward {");
      program.addInstruction("   OUTPUT_TIME_SPEED(LAYER, MOTOR_BOTH, MOTOR_NEGATIVE_SPEED, 500, MOTOR_STEP, 500, BRAKE_STOP )");
      program.addInstruction("   CALL (wait_for_motors)");
      program.addInstruction("}");

      program.addInstruction("//-----------------------------------");
      program.addInstruction("subcall Left {");
      program.addInstruction("   OUTPUT_STEP_SPEED(LAYER, MOTOR_RIGHT, 25, 20, 140, 20, BRAKE_STOP)");
      program.addInstruction("   OUTPUT_STEP_SPEED(LAYER, MOTOR_LEFT, -25, 20, 140, 20, BRAKE_STOP)");
      program.addInstruction("   CALL (wait_for_motors)");
      program.addInstruction("}");

      program.addInstruction("//-----------------------------------");
      program.addInstruction("subcall Right {");
      program.addInstruction("   OUTPUT_STEP_SPEED(LAYER, MOTOR_RIGHT, -25, 20, 140, 20, BRAKE_STOP)");
      program.addInstruction("   OUTPUT_STEP_SPEED(LAYER, MOTOR_LEFT,   25, 20, 140, 20, BRAKE_STOP)");
      program.addInstruction("   CALL (wait_for_motors)");
      program.addInstruction("}");

      program.addInstruction("//-----------------------------------");
      program.addInstruction("subcall beep {");
      program.addInstruction("   SOUND(TONE,50,262,500)");
      program.addInstruction("   SOUND_READY");
      program.addInstruction("}");

      program.addInstruction("//-----------------------------------");
      program.addInstruction("subcall wait_for_motors {");
      program.addInstruction("   DATA32 timer");
      program.addInstruction("   DATA8	 yn");

      program.addInstruction("   motor_running:");
      program.addInstruction("   TIMER_WAIT(100,timer)");
      program.addInstruction("   TIMER_READY(timer)");
      program.addInstruction("   OUTPUT_TEST(LAYER, MOTOR_BOTH, yn)");

      program.addInstruction("   JR_TRUE(yn, motor_running)");
      program.addInstruction("}");
   }


	public void toXML(java.io.PrintWriter out) {
		out.println("   <begin />");
	}
}
