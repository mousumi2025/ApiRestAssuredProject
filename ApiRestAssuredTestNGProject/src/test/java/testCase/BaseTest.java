package testCase;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import utilities.ConfigReader;
import utilities.ExcelReader;
import utilities.LoggerLoad;

public class BaseTest  {
    
    public ExcelReader excelReader;
    public static List<Map<String, String>> search;
    public static List<Map<String, String>> create;
    public static List<Map<String, String>> update;
    public static List<Map<String, String>> delete;


    
    @BeforeClass
      
    public void setupExcel() throws IOException {
    	 LoggerLoad.info("RestAssured Swagger API Execution");
    	 LoggerLoad.info("Initializing Workbook");
        try {
        	String path = ConfigReader.getProperty("Excelpath"); 
            LoggerLoad.info("Initializing Excel Reader with path: " + path);
            excelReader = new ExcelReader();
            search = excelReader.getData(path, "Search");
            create = excelReader.getData(path, "Create");
            update = excelReader.getData(path, "Update");
            delete = excelReader.getData(path, "Delete");
            
           } catch (Exception e) {
        	 LoggerLoad.error("Error initializing Excel data: " + e.getMessage());
            throw new RuntimeException("Excel initialization failed", e);
            }
         }
      

    @AfterClass
    public void closeExcel() {
    	  ExcelReader.closeAllWorkbooks();
    	  LoggerLoad.info("Closing Workbook");
    }
   

String Username = ConfigReader.getProperty("username");
String Password = ConfigReader.getProperty("password");
String BaseURL = ConfigReader.getProperty("BaseURL");
String InBaseURL = ConfigReader.getProperty("InvalidBase");
String SearchAllEp = ConfigReader.getProperty("GetAllEP");
String SearchByidEP = ConfigReader.getProperty("GetByIdEP");
String SearchByNamEP = ConfigReader.getProperty("GetByNameEP");
String CreateEP = ConfigReader.getProperty("PostEP");
String UpdateEP = ConfigReader.getProperty("PutEP");
String DeleteByIdEP = ConfigReader.getProperty("DeleteByIdEP");
String DeleteNameEP = ConfigReader.getProperty("DeleteByNameEP");
String Json = ConfigReader.getProperty("Json");
String eUserName ="", ePassword = "";


}


