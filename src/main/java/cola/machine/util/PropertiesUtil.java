package cola.machine.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	public static Properties confProperties;

	{
		if(confProperties == null) {
	    	confProperties = new Properties();
	    }

		InputStream in = ClassLoader.getSystemResourceAsStream("app-config.properties");
		if(in == null) {
			in = ClassLoader.getSystemResourceAsStream("resources/app-config.properties");
		}

		try {
			confProperties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void init() throws Exception {
		File rootDirectory = new File(PropertiesUtil.class.getClassLoader().getResource("").getPath());
	    File[] propertiesFiles = rootDirectory.listFiles(new FileNameSelector("properties"));
		
	    if(confProperties == null) {
	    	confProperties = new Properties();
	    }
		for(File file : propertiesFiles) {
			FileInputStream fis = new FileInputStream(file);
			confProperties.load(fis);
		}
	}
	
	public static Properties getProperties() throws Exception{
		Properties props = new Properties();

		InputStream in = ClassLoader.getSystemResourceAsStream("app-config.properties");
		if(in == null){
			in = ClassLoader.getSystemResourceAsStream("resources/app-config.properties");
		}

		props.load(in);
		in.close();
		return props;
	}
	
	public static void clear(){
		confProperties.clear();
		confProperties = null;
	}
	
	public static String get(String key) throws Exception{
		if(confProperties == null){
			init();
		}
		return confProperties.getProperty(key);
	}
	
	public static void main(String[] args) throws Exception {
		init();
		System.out.println(PropertiesUtil.get("server.portal.hostname"));
	}
}
