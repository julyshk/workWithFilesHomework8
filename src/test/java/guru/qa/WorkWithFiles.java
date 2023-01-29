package guru.qa;

import org.junit.jupiter.api.Test;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;

import static org.assertj.core.api.Assertions.assertThat;

public class WorkWithFiles {
    ClassLoader cl = WorkWithFiles.class.getClassLoader();

    @Test
    public void zipParseTest() throws Exception {
        try (
                InputStream resource = cl.getResourceAsStream("arch.zip");
                ZipInputStream zis = new ZipInputStream(resource);
        ) {

            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().contains(".csv")) {
                    CSVReader reader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> content = reader.readAll();
                    assertThat(content.get(2)[0]).contains("Егоров");
                } else if (entry.getName().contains(".pdf")) {
                    PDF content = new PDF(zis);
                    assertThat(content.text).contains("Пример pdf");
                } else if (entry.getName().contains(".xlsx")) {
                    XLS content = new XLS(zis);
                    assertThat(content.excel.getSheetAt(0).getRow(1).getCell(1).
                            getStringCellValue()).contains("зайчик");
                }
            }
        }
    }
}
