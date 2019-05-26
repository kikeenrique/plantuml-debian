/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * (C) Copyright 2009-2020, Arnaud Roques
 *
 * Project Info:  http://plantuml.com
 * 
 * If you like this project or if you find it useful, you can support us at:
 * 
 * http://plantuml.com/patreon (only 1$ per month!)
 * http://plantuml.com/paypal
 * 
 * This file is part of PlantUML.
 *
 * PlantUML is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PlantUML distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public
 * License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 *
 * Original Author:  Arnaud Roques
 * 
 *
 */
package net.sourceforge.plantuml.preproc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sourceforge.plantuml.StringLocated;
import net.sourceforge.plantuml.tim.EaterException;
import net.sourceforge.plantuml.tim.EaterStartsub;
import net.sourceforge.plantuml.tim.TContext;
import net.sourceforge.plantuml.tim.TLineType;
import net.sourceforge.plantuml.tim.TMemory;

public class Sub2 {

	private final String name;
	private final List<StringLocated> lines = new ArrayList<StringLocated>();

	public Sub2(String name) {
		this.name = name;
	}

	public void add(StringLocated s) {
		this.lines.add(s);
	}

	public final List<StringLocated> lines() {
		return Collections.unmodifiableList(lines);
	}

	public static Sub2 fromFile(ReadLine reader, String blocname, TContext context, TMemory memory) throws IOException,
			EaterException {
		Sub2 result = null;
		StringLocated s = null;
		while ((s = reader.readLine()) != null) {
			final TLineType type = TLineType.getFromLine(s.getStringTrimmed());
			if (type == TLineType.STARTSUB) {
				final EaterStartsub eater = new EaterStartsub(s.getStringTrimmed());
				eater.execute(context, memory);
				if (eater.getSubname().equals(blocname)) {
					result = new Sub2(blocname);
				}
				continue;
			}
			if (type == TLineType.ENDSUB && result != null) {
				reader.close();
				return result;
			}
			if (result != null) {
				result.add(s);
			}
		}
		reader.close();
		return null;
	}

}