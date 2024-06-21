package qa.automation.exceltdd;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class ExcelDataProviders {

    @DataProvider
    public Object[][] userFromSheet1() throws IOException {
        String path = "src/test/resources/users.xlsx";
        ExcelReader excelReader = new ExcelReader(path);
        return excelReader.getSheetDataForTDD();
    }

    @DataProvider
    public Object[][] userFromCustomSheet() throws IOException {
        String path = "src/test/resources/users.xlsx";
        ExcelReader excelReader = new ExcelReader(path, "Лист2");
        return excelReader.getCustomSheetDataForTDD();
    }

    @DataProvider
    public Object[][] usersFromApi() throws IOException {
        String path = "src/test/resources/usersForReqres.xlsx";
        ExcelReader excelReader = new ExcelReader(path);
        return excelReader.getSheetDataForTDD();
    }
}
