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
      program.addInstruction("DATA8 senTouch // global register for touch sensor value");
      program.addInstruction("DATA8 senLight // global register for light sensor value");
      program.addInstruction("DATA8 senUltrasonic  // global register for ultrasonic sensor value");
      if (next != null) next.compile(program);
      program.codeOutdent();
      program.addInstruction("}");

      program.addInstruction("");
      addSubCalls(program);
   }

   private void addDefines(Program program){
      program.addInstruction("define LAYER     0");
      program.addInstruction("");
      program.addInstruction("define SOUND_VOLUME  50");
      program.addInstruction("");
      program.addInstruction("define MOTOR_A   1");
      program.addInstruction("define MOTOR_B   2");
      program.addInstruction("define MOTOR_C   4");
      program.addInstruction("define MOTOR_D   8");
      program.addInstruction("define MOTOR_AB  3");
      program.addInstruction("define MOTOR_BC  6");
      program.addInstruction("");
      program.addInstruction("define BRAKE_STOP  1");
      program.addInstruction("define BRAKE_COAST 0");
      program.addInstruction("");
      program.addInstruction("define MOTOR_LEFT   MOTOR_B");
      program.addInstruction("define MOTOR_RIGHT  MOTOR_C ");
      program.addInstruction("define MOTOR_BOTH   MOTOR_BC");
      program.addInstruction("define MOTOR_SPEED  30");
      program.addInstruction("define MOTOR_NEGATIVE_SPEED -30");
      program.addInstruction("define MOTOR_STEP	1000");
      program.addInstruction("");
      program.addInstruction("define PORT_TOUCH 0        //TOUCH SENSOR PORT ");
      program.addInstruction("define PORT_LIGHT 2        //LIGHT SENSOR PORT ");
      program.addInstruction("define PORT_ULTRASONIC 3   //ULTRASONIC SENSOR PORT ");
      program.addInstruction("");
      program.addInstruction("define SEN_TYPE_TOUCH      16   //EV3 TOUCH SENSOR");
      program.addInstruction("define SEN_TYPE_LIGHT      29   //EV3 COLOR SENSOR");
      program.addInstruction("define SEN_TYPE_ULTRASONIC 30   //EV3 ULTRASONIC SENSOR");
      program.addInstruction("define SEN_TYPE_INFRARED   33   //EV3 INFRARED SENSOR");
      program.addInstruction("define SEN_TYPE_GYRO       32   //EV3 GYRO SENSOR");
      program.addInstruction("");
      program.addInstruction("define SEN_MODE_TOUCH_TOUCH         0   //TOUCH MODE: TOUCH");
      program.addInstruction("define SEN_MODE_LIGHT_REFLECTED     29  //COLOR MODE: REFLECTED");
      program.addInstruction("define SEN_MODE_ULTRASONIC_CM       0   //ULTRASONIC MODE: CENTIMETERS");
      program.addInstruction("define SEN_MODE_ULTRASONIC_INCH     1   //ULTRASONIC MODE: INCHES");
      program.addInstruction("define SEN_MODE_ULTRASONIC_LISTEN   2   //ULTRASONIC MODE: LISTEN");
      program.addInstruction("define SEN_MODE_INFRARED_PROXIMITY  0   //INFRARED MODE: PROXIMITY");
      program.addInstruction("define SEN_MODE_INFRARED_SEEKER     1   //INFRARED MODE: SEEKER");
      program.addInstruction("define SEN_MODE_GYRO_ANGLE          0   //GYRO MODE: ANGLE");
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
      program.addInstruction("   OUTPUT_STEP_SPEED(LAYER, MOTOR_RIGHT, 25, 20, 180, 20, BRAKE_STOP)");
      program.addInstruction("   OUTPUT_STEP_SPEED(LAYER, MOTOR_LEFT, -25, 20, 180, 20, BRAKE_STOP)");
      program.addInstruction("   CALL (wait_for_motors)");
      program.addInstruction("}");

      program.addInstruction("//-----------------------------------");
      program.addInstruction("subcall Right {");
      program.addInstruction("   OUTPUT_STEP_SPEED(LAYER, MOTOR_RIGHT, -25, 20, 180, 20, BRAKE_STOP)");
      program.addInstruction("   OUTPUT_STEP_SPEED(LAYER, MOTOR_LEFT,   25, 20, 180, 20, BRAKE_STOP)");
      program.addInstruction("   CALL (wait_for_motors)");
      program.addInstruction("}");

      program.addInstruction("//-----------------------------------");
      program.addInstruction("subcall Spin {");
      program.addInstruction("   OUTPUT_STEP_SPEED(LAYER, MOTOR_RIGHT, -25, 20, 720, 20, BRAKE_STOP)");
      program.addInstruction("   OUTPUT_STEP_SPEED(LAYER, MOTOR_LEFT,   25, 20, 720, 20, BRAKE_STOP)");
      program.addInstruction("   CALL (wait_for_motors)");
      program.addInstruction("}");

      program.addInstruction("//-----------------------------------");
      program.addInstruction("subcall Shake {");
      program.addInstruction("   DATA32 timer");
      program.addInstruction("   DATA8  i");
      program.addInstruction("   MOVE8_8(0,i)");
      program.addInstruction("   shake_loop:");
      program.addInstruction("     OUTPUT_SPEED(LAYER, MOTOR_RIGHT, 20)");
      program.addInstruction("     OUTPUT_SPEED(LAYER, MOTOR_LEFT, -20)");
      program.addInstruction("     OUTPUT_START(LAYER, MOTOR_BOTH)");
      program.addInstruction("     TIMER_WAIT(10,timer)");
      program.addInstruction("     TIMER_READY(timer)");
      program.addInstruction("     OUTPUT_SPEED(LAYER, MOTOR_RIGHT, -20)");
      program.addInstruction("     OUTPUT_SPEED(LAYER, MOTOR_LEFT,  20)");
      program.addInstruction("     TIMER_WAIT(10,timer)");
      program.addInstruction("     TIMER_READY(timer)");
      program.addInstruction("     ADD8 (1,i,i)");
      program.addInstruction("   JR_LT8(i,10,shake_loop)");
      program.addInstruction("   OUTPUT_STOP(LAYER, MOTOR_BOTH)");
      program.addInstruction("}");

      program.addInstruction("//-----------------------------------");
      program.addInstruction("subcall Shuffle {");
      program.addInstruction("   DATA32 timer");
      program.addInstruction("   DATA8  i");
      program.addInstruction("   MOVE8_8(0,i)");
      program.addInstruction("   shuffle_loop:");
      program.addInstruction("     OUTPUT_SPEED(LAYER, MOTOR_BOTH, 20)");
      program.addInstruction("     OUTPUT_START(LAYER, MOTOR_BOTH)");
      program.addInstruction("     TIMER_WAIT(10,timer)");
      program.addInstruction("     TIMER_READY(timer)");
      program.addInstruction("     OUTPUT_SPEED(LAYER, MOTOR_BOTH, -20)");
      program.addInstruction("     TIMER_WAIT(10,timer)");
      program.addInstruction("     TIMER_READY(timer)");
      program.addInstruction("     ADD8 (1,i,i)");
      program.addInstruction("   JR_LT8(i,4,shuffle_loop)");
      program.addInstruction("   OUTPUT_STOP(LAYER, MOTOR_BOTH)");
      program.addInstruction("}");

      program.addInstruction("//-----------------------------------");
      program.addInstruction("subcall Wiggle {");
      program.addInstruction("   DATA32 timer");
      program.addInstruction("   DATA8  i");
      program.addInstruction("   MOVE8_8(0,i)");
      program.addInstruction("   wiggle_loop:");
      program.addInstruction("     OUTPUT_SPEED(LAYER, MOTOR_RIGHT, 50)");
      program.addInstruction("     OUTPUT_SPEED(LAYER, MOTOR_LEFT, 0)");
      program.addInstruction("     OUTPUT_START(LAYER, MOTOR_BOTH)");
      program.addInstruction("     TIMER_WAIT(50,timer)");
      program.addInstruction("     TIMER_READY(timer)");
      program.addInstruction("     OUTPUT_SPEED(LAYER, MOTOR_RIGHT, 0)");
      program.addInstruction("     OUTPUT_SPEED(LAYER, MOTOR_LEFT,  50)");
      program.addInstruction("     TIMER_WAIT(50,timer)");
      program.addInstruction("     TIMER_READY(timer)");
      program.addInstruction("     ADD8 (1,i,i)");
      program.addInstruction("   JR_LT8(i,5,wiggle_loop)");
      program.addInstruction("   OUTPUT_STOP(LAYER, MOTOR_BOTH)");
      program.addInstruction("}");

      program.addInstruction("//-----------------------------------");
      program.addInstruction("subcall Sing {");
      program.addInstruction("   SOUND(PLAY,SOUND_VOLUME,up.rsf)");
      program.addInstruction("   SOUND_READY");
      program.addInstruction("   SOUND(PLAY,SOUND_VOLUME,down.rsf)");
      program.addInstruction("   SOUND_READY");
      program.addInstruction("   SOUND(PLAY,SOUND_VOLUME,up.rsf)");
      program.addInstruction("   SOUND_READY");
      program.addInstruction("}");

      program.addInstruction("//-----------------------------------");
      program.addInstruction("subcall Beep {");
      program.addInstruction("   SOUND(TONE,SOUND_VOLUME,600,500)");
      program.addInstruction("   SOUND_READY");
      program.addInstruction("}");

      program.addInstruction("//-----------------------------------");
      program.addInstruction("subcall Growl {");
      program.addInstruction("   SOUND(TONE,SOUND_VOLUME,500,500)");
      program.addInstruction("   SOUND_READY");
      program.addInstruction("}");

      program.addInstruction("//-----------------------------------");
      program.addInstruction("subcall Whistle {");
      program.addInstruction("   SOUND(TONE,SOUND_VOLUME,700,500)");
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

      program.addInstruction("//-----------------------------------");
      program.addInstruction("subcall StartMotor {");
      program.addInstruction("   IO_8 motor");
      program.addInstruction("   IO_8 speed");
      program.addInstruction("   OUTPUT_SPEED(LAYER, motor, speed)");
      program.addInstruction("   OUTPUT_START(LAYER, motor)");
      program.addInstruction("}");

      program.addInstruction("//-----------------------------------");
      program.addInstruction("subcall Wait {");
      program.addInstruction("   IO_32 wait_time");
      program.addInstruction("   DATA32 timer");
      program.addInstruction("   TIMER_WAIT(wait_time,timer)");
      program.addInstruction("   TIMER_READY(timer)");
      program.addInstruction("}");

   }


	public void toXML(java.io.PrintWriter out) {
		out.println("   <begin />");
	}
}
