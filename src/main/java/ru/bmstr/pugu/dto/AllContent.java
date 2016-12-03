package ru.bmstr.pugu.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.bmstr.pugu.domain.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmstr on 30.11.2016.
 */
@Component
public class AllContent {

    private static final Logger log = LogManager.getLogger(AllContent.class);
    private static final String DEFAULT_CONTENT_FILEPATH = "allData.json";
    private static final String DEFAULT_READ_FILEPATH = "allData.bck.json";

    private List<Suit> suits = new ArrayList<>();

    @PostConstruct
    private void loadDefaultValues() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            suits = mapper.readValue(new File(DEFAULT_READ_FILEPATH), mapper.getTypeFactory().constructCollectionType(List.class, Suit.class));
//            mapper.rea(new File(DEFAULT_CONTENT_FILEPATH), suits);
        } catch (IOException e) {
            log.error("Error while storing data to json file", e);
        }

    }

    @PreDestroy
    public void store() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(new File(DEFAULT_CONTENT_FILEPATH), suits);
            System.out.println(mapper.writeValueAsString(suits));
        } catch (IOException e) {
            log.error("Error while storing data to json file", e);
        }
    }

    public void restore() {

    }

    public void draw() {

    }

    public int getRowCount() {
        return suits.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Suit suit = suits.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return suit.getCategory();
            case 2:
                return suit.getPlaintiff();
            case 3:
                return suit.getInitialSumm();
            case 4:
                return suit.getDefendant();
            case 5:
                return suit.getRepresentative();
            case 6:
                return suit.getResult();
            case 7:
                return suit.getAgreedSumm();
            default:
                throw new RuntimeException("Tried to get column number " + columnIndex + " which is missing");
        }
    }
}
