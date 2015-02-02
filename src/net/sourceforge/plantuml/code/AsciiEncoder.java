/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * (C) Copyright 2009-2014, Arnaud Roques
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
package net.sourceforge.plantuml.code;

public class AsciiEncoder implements URLEncoder {

	final private char encode6bit[] = new char[64];
	final private byte decode6bit[] = new byte[128];

	public AsciiEncoder() {
		for (byte b = 0; b < 64; b++) {
			encode6bit[b] = encode6bit(b);
			decode6bit[encode6bit[b]] = b;
		}
	}

	public String encode(byte data[]) {
		final StringBuilder resu = new StringBuilder((data.length * 4 + 2) / 3);
		for (int i = 0; i < data.length; i += 3) {
			append3bytes(resu, data[i] & 0xFF, i + 1 < data.length ? data[i + 1] & 0xFF : 0,
					i + 2 < data.length ? data[i + 2] & 0xFF : 0);
		}
		return resu.toString();
	}

	public byte[] decode(String s) {
		if (s.length() % 4 != 0) {
			throw new IllegalArgumentException("Cannot decode " + s);
		}
		final byte data[] = new byte[(s.length() * 3 + 3) / 4];
		int pos = 0;
		for (int i = 0; i < s.length(); i += 4) {
			decode3bytes(data, pos, s.charAt(i), s.charAt(i + 1), s.charAt(i + 2), s.charAt(i + 3));
			pos += 3;
		}
		return data;
	}

	public int decode6bit(char c) {
		return decode6bit[c];
	}

	public char encode6bit(byte b) {
		assert b >= 0 && b < 64;
		if (b < 10) {
			return (char) ('0' + b);
		}
		b -= 10;
		if (b < 26) {
			return (char) ('A' + b);
		}
		b -= 26;
		if (b < 26) {
			return (char) ('a' + b);
		}
		b -= 26;
		if (b == 0) {
			return '-';
		}
		if (b == 1) {
			return '_';
		}
		assert false;
		return '?';
	}

	private void append3bytes(StringBuilder sb, int b1, int b2, int b3) {
		final int c1 = b1 >> 2;
		final int c2 = ((b1 & 0x3) << 4) | (b2 >> 4);
		final int c3 = ((b2 & 0xF) << 2) | (b3 >> 6);
		final int c4 = b3 & 0x3F;
		sb.append(encode6bit[c1 & 0x3F]);
		sb.append(encode6bit[c2 & 0x3F]);
		sb.append(encode6bit[c3 & 0x3F]);
		sb.append(encode6bit[c4 & 0x3F]);
	}

	private void decode3bytes(byte r[], int pos, char cc1, char cc2, char cc3, char cc4) {
		final int c1 = decode6bit[cc1];
		final int c2 = decode6bit[cc2];
		final int c3 = decode6bit[cc3];
		final int c4 = decode6bit[cc4];
		r[pos] = (byte) ((c1 << 2) | (c2 >> 4));
		r[pos + 1] = (byte) (((c2 & 0x0F) << 4) | (c3 >> 2));
		r[pos + 2] = (byte) (((c3 & 0x3) << 6) | c4);
	}

}
