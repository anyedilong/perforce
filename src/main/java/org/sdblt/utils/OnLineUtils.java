package org.sdblt.utils;

import java.util.Map;

public class OnLineUtils {

	/**
	 * 组验证串 (19pay)
	 * 
	 * @param array
	 *            排好序的KEY
	 * @param param
	 *            KEY VALUE
	 * @return URL
	 */
	public static String buildVerifyString(String[] array,
			Map<String, String> param, String keyMD5, String encode) {
		StringBuilder sb = new StringBuilder(500);
		if (null != array && array.length > 0) {
			for (String key : array) {
				sb.append(key).append("=").append(
						org.apache.commons.lang3.StringUtils.isBlank(param.get(key)) ? org.apache.commons.lang3.StringUtils.EMPTY
								: param.get(key)).append("&");
			}
		}
		String msg = sb.substring(0, sb.length() - 1).toString();
		return "gbk".equals(encode) ? KeyedDigestMD5.getKeyedDigestGBK(msg,
				keyMD5) : KeyedDigestMD5.getKeyedDigestUTF8(msg, keyMD5);
	}
}
