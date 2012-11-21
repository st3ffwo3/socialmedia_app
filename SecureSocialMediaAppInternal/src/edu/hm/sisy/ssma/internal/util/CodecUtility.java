package edu.hm.sisy.ssma.internal.util;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;

/**
 * Utility-Klasse für das Dekodieren und Encodieren.
 * 
 * @author Stefan Wörner
 */
public final class CodecUtility
{

	private CodecUtility()
	{

	}

	/**
	 * Decodiert einen Base64 encodierten String und gibt diesen als Byte-Array zurück.
	 * 
	 * @param data
	 *            Byte-Array
	 * @return Base64 encodierter String
	 */
	public static byte[] base64ToByte( String data )
	{
		return Base64.decodeBase64( data );
	}

	/**
	 * Decodiert einen Base32 encodierten String und gibt diesen als Byte-Array zurück.
	 * 
	 * @param data
	 *            Byte-Array
	 * @return Base32 encodierter String
	 */
	public static byte[] base32ToByte( String data )
	{
		Base32 decoder = new Base32();
		return decoder.decode( data );
	}

	/**
	 * Encodiert ein Byte-Array in einen Base64 encodierten String und gibt diesen zurück.
	 * 
	 * @param data
	 *            Byte-Array
	 * @return Base64 encodierter String
	 */
	public static String byteToBase64( byte[] data )
	{
		return Base64.encodeBase64String( data );
	}

	/**
	 * Encodiert ein Byte-Array in einen Base32 encodierten String und gibt diesen zurück.
	 * 
	 * @param data
	 *            Byte-Array
	 * @return Base32 encodierter String
	 */
	public static String byteToBase32( byte[] data )
	{
		Base32 encoder = new Base32();
		return encoder.encodeAsString( data );
	}
}
