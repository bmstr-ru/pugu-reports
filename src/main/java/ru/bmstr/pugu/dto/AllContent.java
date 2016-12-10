package ru.bmstr.pugu.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bmstr.pugu.domain.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bmstr on 30.11.2016.
 */
@Component
public class AllContent {

    private static final Logger log = LogManager.getLogger(AllContent.class);
    private static final File DATA_PATH = new File("данные");
    private static final String DEFAULT_CONTENT_FILENAME = "allData.json";

    private List<Suit> suits = new ArrayList<>();

    @Autowired
    private SuitComparator comparator;

    @PostConstruct
    private void loadLastData() {
        DATA_PATH.mkdirs();
        File dataFile = new File(DATA_PATH, DEFAULT_CONTENT_FILENAME);
        restoreAndOverwrite(dataFile);
    }

    public void sort() {
        suits.sort(comparator);
    }

    private List<Suit> restore(File dataFile) {
        try {
            if (dataFile.exists()) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
                mapper.enable(SerializationFeature.INDENT_OUTPUT);
                return mapper.readValue(dataFile, mapper.getTypeFactory().constructCollectionType(List.class, Suit.class));
            }
        } catch (IOException e) {
            log.error("Error while restoring data from json file", e);
        }
        return Collections.emptyList();
    }

    public void restoreAndOverwrite(File dataFile) {
        suits = restore(dataFile);
        sort();
    }

    public void restoreAndAdd(File dataFile) {
        suits.addAll(restore(dataFile));
        sort();
    }

    @PreDestroy
    public void store() {
        store(new File(DATA_PATH, DEFAULT_CONTENT_FILENAME));
    }

    public void store(File saveTo) {
        try {
            if (saveTo.exists()) {
                backUp(saveTo);
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(saveTo, suits);
        } catch (IOException e) {
            log.error("Error while storing data to json file", e);
        }
    }

    public void backUp(File file) {
        LocalDateTime timePoint = LocalDateTime.now();
        String backUpFilePath = file.getAbsolutePath()
                .replace(
                        ".json",
                        "_" + timePoint.getYear() + "-" +
                                timePoint.getMonthValue() + "-" +
                                timePoint.getDayOfMonth() + "_" +
                                timePoint.getHour() + "-" +
                                timePoint.getMinute() + "-" +
                                timePoint.getSecond() + ".json");
        file.renameTo(new File(backUpFilePath));

    }

    public void addRow(Suit suit) {
        suits.add(suit);
        sort();
    }

    public void deleteRows(int[] rows) {
        Arrays.asList(ArrayUtils.toObject(rows))
                .stream()
                .sorted((i1, i2) -> Integer.compare(i2, i1))
                .forEach(i -> {
                    suits.remove(i.intValue());
                });
        sort();
    }

    public int getRowCount() {
        return suits.size();
    }

    public Suit getSuit(int rowId) {
        return suits.get(rowId);
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Suit suit = suits.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return suit.getYear();
            case 2:
                return suit.getType();
            case 3:
                return suit.getCategory();
            case 4:
                return suit.getPlaintiff();
            case 5:
                return suit.getInitialSumm();
            case 6:
                return suit.getDefendant();
            case 7:
                return suit.getResult();
            case 8:
                return suit.getAgreedSumm();
            default:
                throw new RuntimeException("Tried to get column number " + columnIndex + " which is missing");
        }
    }
}
