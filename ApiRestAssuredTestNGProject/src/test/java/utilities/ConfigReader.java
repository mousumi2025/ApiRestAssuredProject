package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	 public static Properties properties; 	 
	 static {
	 		try(FileInputStream fileinputstream = new FileInputStream("src/test/resources/config.properties")){
	 			
	 			properties = new Properties();
	 			properties.load(fileinputstream);
	 		} catch (FileNotFoundException e) {
	 			e.printStackTrace();
	 			} catch (IOException e1) {
	 			e1.printStackTrace();
	 		}	 	 
	 	}
	 public static String getProperty(String key) {
	 	return properties.getProperty(key);
	 }


}
