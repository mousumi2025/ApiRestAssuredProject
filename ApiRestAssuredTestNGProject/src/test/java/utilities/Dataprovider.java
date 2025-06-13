package utilities;

import java.util.List;
import java.util.Map;
import org.testng.annotations.DataProvider;

import testCase.BaseTest;

public class Dataprovider extends BaseTest{

	
    @DataProvider(name = "GetUser")
    public Object[][] getDataSearch() {
        return convertToDataProviderFormat(search);
    }

    @DataProvider(name = "PostUser")
    public Object[][] getDataCreate() {
        return convertToDataProviderFormat(create);
    }
    
    @DataProvider(name = "UpdateUser")
    public Object[][] getDataUpdate() {
        return convertToDataProviderFormat(update);
    }
    
    @DataProvider(name = "DeleteUser")
    public Object[][] getDataDelete() {
        return convertToDataProviderFormat(delete);
    }
  
    private Object[][] convertToDataProviderFormat(List<Map<String, String>> data) {
        Object[][] dataProvider = new Object[data.size()][1];

        for (int i = 0; i < data.size(); i++) {
            dataProvider[i][0] = data.get(i);          }
		return dataProvider;
	}
	
}
	
