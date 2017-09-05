/**
 * 
 */
package com.yn.cfer.common.sts.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author user
 * 锁处理
 */
public class LockUtils {
	private final static Map<String, String> lock = new HashMap<String, String>();
	// return 1: 锁定中   0：获取锁成功
	public static int getLock(String name, String content) {
		String saved = lock.get(name);
		if(StringUtils.isNotBlank(saved)) {
			return 1;
		}
		lock.put(name, content);
		return 0;
	}
	public static void releaseLock(String name) {
		String saved = lock.get(name);
		if(lock.containsKey(name)) {
			lock.remove(name);
		}
	}
}
