/*
 * @(#) Sensor.java
 * 
 * Tern Tangible Programming System
 * Copyright (C) 2009 Michael S. Horn
 * Portions Copyright (C) Jozef Sovcik
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
 * 
 * Modified by Mariam Hussien - Oct,2011
 */
package tern.language.ev3asm;

import tern.compiler.*;
import topcodes.TopCode;

import java.util.ArrayList;


public class Sensor extends tern.language.base.Sensor {

	public Sensor(TopCode top) {
		super(top);
	}

	public static void register() {
		StatementFactory.registerStatementType(
				new Sensor(new TopCode(PRESS)));
		StatementFactory.registerStatementType(
				new Sensor(new TopCode(RELEASE)));
		StatementFactory.registerStatementType(
				new Sensor(new TopCode(LIGHT)));
		StatementFactory.registerStatementType(
				new Sensor(new TopCode(DARK)));
		StatementFactory.registerStatementType(
				new Sensor(new TopCode(OBJECT)));
	}


	public Statement newInstance(TopCode top) {
		return new Sensor(top);
	}


	public ArrayList<String> getTest(String label) {
		ArrayList<String> a = new ArrayList<String>();

		// INPUT_READ(layer,port,sensor_type,sensor_mode,variable)
		switch (top.getCode()) {
		case PRESS:
			a.add("INPUT_READ(LAYER, PORT_TOUCH, SEN_TYPE_TOUCH, SEN_MODE_TOUCH_TOUCH, senTouch)");
			a.add("JR_EQ8(senTouch, 1, "+label+")");
			break;
		case RELEASE:
			a.add("INPUT_READ(LAYER, PORT_TOUCH, SEN_TYPE_TOUCH, SEN_MODE_TOUCH_TOUCH, senTouch)");
			a.add("JR_EQ8(senTouch, 0, "+label+")");
			break;
		case DARK:
			a.add("INPUT_READ(LAYER, PORT_LIGHT, SEN_TYPE_LIGHT, SEN_MODE_LIGHT_REFLECTED, senLight)");
			a.add("JR_GT8(senLight, 42, "+label+")");
			break;
		case LIGHT:
			a.add("INPUT_READ(LAYER, PORT_LIGHT, SEN_TYPE_LIGHT, SEN_MODE_LIGHT_REFLECTED, senLight)");
			a.add("JR_LTEQ8(senLight, 42, "+label+")");
			break;
		case OBJECT:
			a.add("INPUT_READ(LAYER, PORT_ULTRASONIC, SEN_TYPE_ULTRASONIC, SEN_MODE_ULTRASONIC_CM, senUltrasonic)");
			a.add("JR_LTEQ8(senUltrasonic, 15, "+label+")");
			break;
		default:
			a.add("JR("+label+")");
		}

		return a;
	}


	public void compile(Program program) throws CompileException {
		setDebugInfo(program);
		if (this.next != null) next.compile(program);
	}
	
}
