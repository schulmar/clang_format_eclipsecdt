package net.github.clang_formateclipse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class FormatOptionsLoader {

	static public Map<String, Object> loadFrom(String fileName) throws FileNotFoundException {
		return loadFrom(new FileInputStream(new File(fileName)));
	}
	
	@SuppressWarnings("unchecked")
	static private Map<String, Object> loadFrom(InputStream inputStream) {
		Yaml yaml = new Yaml();
		return (Map<String, Object>) yaml.load(inputStream);
	}
}
