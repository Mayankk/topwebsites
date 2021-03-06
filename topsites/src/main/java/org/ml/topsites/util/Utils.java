package org.ml.topsites.util;

import java.util.Map;

/**
 * General purpose Utility class
 * @author mkurra
 *
 */
public class Utils {

	public static boolean isNullOrEmpty(String str){
		if(str==null||str.trim().length()==0){
			return true;
		}
		
		return false;
	}
	
	public static boolean isNullOrEmpty(Map map){
		if(map==null||map.size()==0){
			return true;
		}
		
		return false;
	}

}
