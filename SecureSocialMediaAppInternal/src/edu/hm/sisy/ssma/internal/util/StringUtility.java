package edu.hm.sisy.ssma.internal.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Utility-Klasse für Strings.
 * 
 * @author Stefan Wörner
 */
public final class StringUtility
{

	private StringUtility()
	{

	}

	/**
	 * Fügt in einen String alle "splitWith" Zeichen den "splitChar" ein und gibt den resultirenden String zurück.
	 * 
	 * @param input
	 *            Zu trennender String
	 * @param splitChar
	 *            Einzufügendens Zeichen
	 * @param splitWith
	 *            Schrittweite
	 * @return Neuer gesplitteter String
	 */
	public static String splitString( String input, String splitChar, int splitWith )
	{
		String[] split = input.split( "(?<=\\G.{" + splitWith + "})" );
		return StringUtils.join( split, splitChar );
	}
}
