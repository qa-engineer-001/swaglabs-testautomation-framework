package utils;

import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider(name = "userData")
    public Object[][] userData() throws Exception {
    /*	return ExcelReader.getData(
            System.getProperty("user.dir") + "/src/test/resources/testdata.xlsx",
            "Users"
    	);	*/
    	
    	return DBReader.getUserCredentials();
    	
    }
}
