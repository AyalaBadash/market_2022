//package com.example.server.dataLayer;
//
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//import java.io.File;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Scanner;
//
//@Configuration
//public class DataSourceConfig {
//
//    @Bean
//    public DataSource getDataSource() {
//        Map<String, String> args= readFromFile(DataConfig.fileName);
//        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//        if(args.containsKey("url")&args.containsKey("username")&args.containsKey("password") ) {
//            dataSourceBuilder.driverClassName("org.h2.Driver");
//            dataSourceBuilder.url(args.get("url"));
//            dataSourceBuilder.username(args.get("username"));
//            dataSourceBuilder.password(args.get("password"));
//        }
//        return dataSourceBuilder.build();
//    }
//
//    private Map<String, String> readFromFile(String fileName) {
//        Map<String,String> ret= new HashMap<>();
//        try {
//
//            File myObj = new File(getConfigDir() +fileName);
//            Scanner myReader = new Scanner(myObj);
//            while (myReader.hasNextLine()) {
//                String data = myReader.nextLine();
//                String[] vals = data.split("=");
//                setData(vals[0], vals[1],ret);
//            }
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        return ret;
//    }
//
//    private void setData(String val, String val1,Map<String,String> data) {
//        if(val.toLowerCase().contains("url")){
//            if(!data.containsKey("url")){
//                data.put("url",val1);
//            }
//        }
//        else if(val.toLowerCase().contains("username")){
//            if(!data.containsKey("username")){
//                data.put("username",val1);
//            }
//        }
//        else if(val.toLowerCase().contains("password")){
//            if(!data.containsKey("password")){
//                data.put("password",val1);
//            }
//        }
//    }
//
//    private String getConfigDir() {
//        String dir = System.getProperty("user.dir");
//        dir += "\\server\\config\\";
//        return dir;
//    }
//}
//
