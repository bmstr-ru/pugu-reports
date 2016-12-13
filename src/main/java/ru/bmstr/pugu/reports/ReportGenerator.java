package ru.bmstr.pugu.reports;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.docx4j.Docx4J;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bmstr.pugu.dto.AllContent;
import ru.bmstr.pugu.properties.PropertyLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static ru.bmstr.pugu.properties.PropertyNames.*;

/**
 * Created by bmstr on 26.11.2016.
 */
@Component
public class ReportGenerator {

    private static final Logger log = LogManager.getLogger(ReportGenerator.class);

    @Autowired
    private AllContent allContent;

    @Autowired
    private PropertyLoader propertyLoader;

    public void generateReport() {
        WordprocessingMLPackage wordPackage;
        try {
            InputStream templateStream;
            File templateFile = new File(propertyLoader.getProperty(REPORT_TEMPLATE));
            if (templateFile.exists()) {
                templateStream = new FileInputStream(templateFile);
            } else {
                templateStream = this.getClass().getClassLoader().getResourceAsStream(propertyLoader.getProperty(REPORT_TEMPLATE));
            }
            if (templateStream != null) {
                wordPackage = Docx4J.load(templateStream);
                VariablePrepare.prepare(wordPackage);
                MainDocumentPart documentPart = wordPackage.getMainDocumentPart();
                documentPart.variableReplace(allContent.calculateTags());
                File reportFile = new File(propertyLoader.getProperty(REPORT_FILENAME));
                if (reportFile.exists()) {
                    reportFile.delete();
                }
                wordPackage.save(reportFile);
            } else {
                log.error(propertyLoader.getProperty(REPORT_TEMPLATE_MISSING));
            }
        } catch (Exception e) {
            log.error("Error while generating report", e);
        }
    }
}
