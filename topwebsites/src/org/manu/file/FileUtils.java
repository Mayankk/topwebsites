package org.manu.file;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
	
	public static String readFile(String location) throws IOException{
		String content = null;
		byte[] bytes = new byte[1024];
		InputStream is = new FileInputStream(location);
		BufferedInputStream bis = new BufferedInputStream(is);
		
		bis.read(bytes);
		content = new String(bytes);
		return content;
	}
	
	public static List<String> readFileLines(String location) throws IOException{
		String thisLine = null;
		List<String> content = new ArrayList<>();
		Reader r  = new FileReader(location);
		BufferedReader br = new BufferedReader(r);
        
        while ((thisLine = br.readLine()) != null) {
        	content.add(thisLine);
        }  
		return content;
	}
}
