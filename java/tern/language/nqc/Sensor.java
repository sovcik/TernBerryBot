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
package tern.language.nqc;

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

		switch (top.getCode()) {
		case PRESS:
			a.add("SENSOR_1 == 0");
			break;
		case RELEASE:
			a.add("SENSOR_1 == 1");
			break;
		case DARK:
			a.add("SensorValue(S3) > 42");
			break;
		case LIGHT:
			a.add("SensorValue(S3) < 42");
			break;
		case OBJECT:
			a.add("SensorUS(IN_4) < 15");
			break;
		default:
			a.add("1");
		}

		return a;
	}


	public String getSensorID() {
		switch (top.getCode()) {
			case PRESS:
			case RELEASE:
				return "IN_1";

			case DARK:
			case LIGHT:
				return "IN_3";

			case OBJECT:
				return "IN_4";

			// commented out by Jozef Sovcik
			//case SOUND:
			//case MUTE:
			//	return "IN_2";

			default:
				return "IN_1";

		}
	}

	public String getType() {
		switch (top.getCode()) {
			case PRESS:
			case RELEASE:
				return	"Touch";

			case LIGHT:
			case DARK:
				return "Light";

			case OBJECT:
				return "Ultrasonic";

			// Commented out by Jozef Sovcik - EV3 does not support?
			//case SOUND:
			//case MUTE:
			//	return "Sound";

			default:
				return "Touch";
		}
	}


	public void compile(Program program) throws CompileException {
		setDebugInfo(program);
		if (this.next != null) next.compile(program);
	}
	
}
