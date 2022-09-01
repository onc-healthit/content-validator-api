package org.sitenv.contentvalidator.configuration;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sitenv.contentvalidator.model.CCDARefModel;
import org.sitenv.contentvalidator.parsers.CCDAParser;
import org.springframework.beans.factory.InitializingBean;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Created by Brian on 8/15/2016.
 */
public class ScenarioLoader implements InitializingBean {
    private String scenarioFilePath;
    private CCDAParser ccdaParser;
    private HashMap<String, CCDARefModel> refModelHashMap = new HashMap<>();
    
	private static Logger log = LoggerFactory.getLogger(ScenarioLoader.class.getName());

    @Override
    public void afterPropertiesSet() throws Exception {
        loadScenarioFiles(scenarioFilePath, ccdaParser);
    }

    public void setScenarioFilePath(String scenarioFilePath) {
        this.scenarioFilePath = scenarioFilePath;
    }

    public void setCcdaParser(CCDAParser ccdaParser) {
        this.ccdaParser = ccdaParser;
    }

    public HashMap<String, CCDARefModel> getRefModelHashMap() {
        return refModelHashMap;
    }

    public CCDARefModel getRefModel(String scenario){
       return refModelHashMap.get(scenario);
    }

    private void loadScenarioFiles(String scenarioFilePath, CCDAParser ccdaParser) throws IOException{
        System.out.println("LOADING SCENARIO FILES AT " + scenarioFilePath);
        File dir = new File(scenarioFilePath);
        if (dir.isFile()) {
            throw new IOException("Directory to Load is a file and not a directory");
        } else {
            File[] list = dir.listFiles();
            for (File file : list) {
                if (!file.isDirectory() && !file.isHidden()) {
                	
                	log.info("Parsing File : " + file.getName());
                    byte[] encoded = Files.readAllBytes(Paths.get(file.toURI()));
                    
                    boolean curesUpdate = false; 
                    boolean svap2022 = false;                    
                    final String svapMatch = "svap_uscdiv2";
                    String fileName = file.getName();
                    if (fileName.contains(svapMatch)) {
                    	svap2022 = true;
                    } else {
                    	curesUpdate = true;
                    }
                    
                    // Note: This does NOT accommodate for 2015 Edition yet, but, as far as we know, we never have to rebuild 2015 (will sunset first)
                    // But, if we had to rebuild as is, we would need to hard code curesUpdate and svap2022 to false here...or finish the impl to handle that. 
                    // Right now though, the file names are identical, so we'd either have to change names, or use a different loading system (like directory, etc.)
                    String modelName = FilenameUtils.getBaseName(fileName);
                    CCDARefModel m = ccdaParser.parse(new String(encoded, "UTF-8"), curesUpdate, svap2022);
                    refModelHashMap.put(modelName, m);
                }
            }
        }
    }
}
