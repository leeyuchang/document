package com.home.ch02;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordFinder {

	public static void main(String[] args) {
		
		SimpleDatabase db;
		
		try {
			db = new SimpleDatabase(new FileReader("C:\\Users\\leeyu\\Documents\\workspace-spring-tool-suite-4-4.3.2.RELEASE\\Refact\\src\\com\\home\\ch02\\dbfile.txt"));
			Iterator<String> it = db.iterator();
			
			while(it.hasNext()) {
				String key = it.next();
				System.out.println("KEY   : " + key);
//				System.out.println("VALUE : " + db.getValue(key));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class SimpleDatabase {
//	private Map<String, String> _map = new HashMap<>();
	private Set<String> _set = new HashSet<>();
//	private static Pattern _pattern = Pattern.compile("([^=]+)=(.*)");
	private static Pattern _pattern = Pattern.compile("A\\d{3}");

	public SimpleDatabase(Reader r) throws IOException {
		BufferedReader reader = new BufferedReader(r);
		while (true) {
			String line = reader.readLine();
			if (line == null) {
				break;
			}

			Matcher matcher = _pattern.matcher(line);
			while (matcher.find()) {
//				String key = matcher.group(1);
//				String value = matcher.group(2);
//				_map.put(key, value);
				_set.add(matcher.group());
			}
		}
	}

//	public void putValue(String key, String value) {
//		_map.put(key, value);
//	}
//
//	public String getValue(String key) {
//		return _map.get(key);
//	}

	public Iterator<String> iterator() {
//		return _map.keySet().iterator();
		return _set.iterator();
	}
	
}
