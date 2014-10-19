package net.github.clang_formateclipse;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class FormatOptionsStorer {

	static public void storeTo(Map<String, Object> options, String fileName)
			throws FileNotFoundException {
		Yaml yaml = new Yaml();
		
		yaml.dump(options, new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(fileName))));
	}

}
