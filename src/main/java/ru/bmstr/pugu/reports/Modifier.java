package ru.bmstr.pugu.reports;

import org.docx4j.Docx4J;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;

/**
 * Created by bmstr on 26.11.2016.
 */
@Component
public class Modifier {

    public void poc() {
        File result = new File("New word.docx");
        result.delete();
        WordprocessingMLPackage wordPackage = null;
        try {
            wordPackage = Docx4J.load(
                    this.getClass().getClassLoader().getResourceAsStream("template.docx")
            );
            VariablePrepare.prepare(wordPackage);
            MainDocumentPart documentPart = wordPackage.getMainDocumentPart();
            HashMap<String, String> replacementMap = new HashMap<>();
            replacementMap.put("tag_01_01","Hello world!");
            documentPart.variableReplace(replacementMap);
            wordPackage.save(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
