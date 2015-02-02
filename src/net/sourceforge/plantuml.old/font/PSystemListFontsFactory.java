/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * (C) Copyright 2009-2013, Arnaud Roques
 *
 * Project Info:  http://plantuml.sourceforge.net
 * 
 * This file is part of PlantUML.
 *
 * PlantUML is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PlantUML distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 *
 * Original Author:  Arnaud Roques
 */
package net.sourceforge.plantuml.font;

import net.sourceforge.plantuml.AbstractPSystem;
import net.sourceforge.plantuml.command.PSystemSingleLineFactory;

public class PSystemListFontsFactory extends PSystemSingleLineFactory {

	@Override
	protected AbstractPSystem executeLine(String line) {
		final String lineLower = line.toLowerCase();
		if (lineLower.equals("listfont") || lineLower.equals("listfonts") || lineLower.startsWith("listfont ")
				|| lineLower.startsWith("listfonts ")) {
			final int idx = line.indexOf(' ');
			return new PSystemListFonts(idx == -1 ? "This is a test" : line.substring(idx).trim());
		}
		return null;
	}

}