package com.example.server.businessLayer.Market.ResourcesObjects;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class DataSourceConfigReader {

    private static DataSourceConfigReader instance;
    public Properties prop;

    private DataSourceConfigReader(String path) throws MarketException {
        FileInputStream propsInput;
        try {
            propsInput = new FileInputStream(path);
        }catch (FileNotFoundException ex){
            throw new MarketException("Data source config file not found.");
        }
        try {
            this.prop = new Properties();
            prop.load(propsInput);
        }
        catch (Exception e){
            throw new MarketException("Failed to init properties out of Data source config file.");
        }
    }

    public static DataSourceConfigReader getInstance(String path) throws MarketException {
        if (instance == null){
            instance = new DataSourceConfigReader(path);
        }
        return instance;
    }

    public String getProperty(String prop) {
        return this.prop.getProperty(prop);
    }
}
