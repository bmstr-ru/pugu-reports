package ru.bmstr.pugu.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.bmstr.pugu.db.DatabaseManager;
import ru.bmstr.pugu.domain.*;
import ru.bmstr.pugu.properties.PropertyLoader;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by bmstr on 30.11.2016.
 */
@Component
public class AllContent {

    private static final Logger log = LogManager.getLogger(AllContent.class);

    private volatile boolean filtered = false;
    private volatile Representative filterRepresentative;
    private volatile String filterString;

    private volatile boolean changed = false;
    private volatile LocalDateTime lastUpdateTime = LocalDateTime.now();
    List<Suit> suits;

    @Autowired
    private SuitComparator comparator;

    @Autowired
    private PropertyLoader propertyLoader;

    @Autowired
    private DatabaseManager databaseManager;

    public void addRow(Suit suit) {
        databaseManager.create(suit);
    }

    public void deleteRows(int[] rows) {
        List<Integer> toDeleteRows = Arrays.stream(ArrayUtils.toObject(rows))
                .sorted((i1, i2) -> Integer.compare(i2, i1))
                .collect(Collectors.toList());
        toDeleteRows.forEach(i -> {
            Suit suit = getSuits().get(i.intValue());
            if (suit.getAppeal() != null) {
                databaseManager.delete(suit.getAppeal());
            }
            if (suit.getCassation() != null) {
                databaseManager.delete(suit.getCassation());
            }
            databaseManager.delete(suit);
        });
    }

    public int getRowCount() {
        return getSuits().size();
    }

    public Suit getSuit(int rowId) {
        return getSuits().get(rowId);
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Suit suit;
        suit = getSuits().get(rowIndex);

        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            default:
                return suit.getAt(columnIndex);
        }
    }

    public void store(File saveTo) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(saveTo, getSuits());
        } catch (IOException e) {
            log.error("Error while storing data to json file", e);
        }
    }


    public void restoreAndAdd(File dataFile) {
        restore(dataFile).stream().forEach(suit -> {
            if (suit.getAppeal() != null) {
                suit.getAppeal().setId(0);
                databaseManager.create(suit.getAppeal());
            }
            if (suit.getCassation() != null) {
                suit.getCassation().setId(0);
                databaseManager.create(suit.getCassation());
            }
            suit.setId(0);
            databaseManager.create(suit);
        });
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

    public HashMap<String, String> calculateTags() {
        HashMap<String, String> result = new HashMap<>();
        IntStream.range(1, 41).forEach(row ->
                IntStream.range(1, 11).forEach(column ->
                        result.put(String.format("%02d_%02d", column, row), "")
                )
        );
        changed = true;
        List<Suit> finalSuits = getSuits().stream().map( suit -> suit.getFinalSuit()).collect(Collectors.toList());
        result.putAll(calculateLine("01", finalSuits.stream().filter(suit -> SuitType.TO_US.equals(suit.getType())).collect(Collectors.toList())));
        result.putAll(calculateLineOfCategory("02", "2.1", suits));
        result.putAll(calculateLineOfCategory("03", "2.1.1", suits));
        result.putAll(calculateLineOfCategory("04", "2.1.2", suits));
        result.putAll(calculateLineOfCategory("05", "2.1.3", suits));
        result.putAll(calculateLineOfCategory("06", "2.1.4", suits));
        result.putAll(calculateLineOfCategory("07", "2.1.5", suits));
        result.putAll(calculateLineOfCategory("08", "2.2", suits));
        result.putAll(calculateLineOfCategory("09", "2.3", suits));
        result.putAll(calculateLineOfCategory("10", "2.3.1", suits));
        result.putAll(calculateLineOfCategory("11", "2.3.2", suits));
        result.putAll(calculateLineOfCategory("12", "2.4", suits));
        result.putAll(calculateLineOfCategory("13", "2.5", suits));
        result.putAll(calculateLineOfCategory("14", "2.6", suits));
        result.putAll(calculateLineOfCategory("15", "2.6.1", suits));
        result.putAll(calculateLineOfCategory("16", "2.6.2", suits));
        result.putAll(calculateLineOfCategory("17", "2.7", suits));
        result.putAll(calculateLineOfCategory("18", "2.8", suits));
        result.putAll(calculateLineOfCategory("19", "2.9", suits));
        result.putAll(calculateLineOfCategory("20", "2.10", suits));
        result.putAll(calculateLineOfCategory("21", "2.11", suits));
        result.putAll(calculateLine("22", suits.stream().filter(suit -> SuitType.SPECIAL.equals(suit.getType())).collect(Collectors.toList())));
        result.putAll(calculateLineOfTypeAndDefendant("23", SuitType.TO_US, Defendant.KAZNA, suits));
        result.putAll(calculateLineOfTypeAndDefendant("24", SuitType.TO_US, Defendant.MVD, suits));
        result.putAll(calculateLineOfTypeAndDefendant("25", SuitType.TO_US, Defendant.GU, suits));

//        result.putAll(calculateLine("26", suits.stream().filter(suit -> SuitType.TO_US.equals(suit.getType()) && suit.getAppeal() != null).collect(Collectors.toList())));
//        result.putAll(calculateLine("27", suits.stream().filter(suit -> SuitType.TO_US.equals(suit.getType()) && suit.getCassation() != null).collect(Collectors.toList())));

        result.putAll(calculateLine("30", suits.stream().filter(suit -> SuitType.OUR.equals(suit.getType())).collect(Collectors.toList())));
        result.putAll(calculateLineOfCategory("31", "9.1", suits));
        result.putAll(calculateLineOfCategory("32", "9.2", suits));
        result.putAll(calculateLineOfCategory("33", "9.2.1", suits));
        result.putAll(calculateLineOfCategory("34", "9.2.2", suits));
        result.putAll(calculateLineOfCategory("35", "9.3", suits));
        result.putAll(calculateLineOfCategory("36", "9.4", suits));
        result.putAll(calculateLineOfCategory("37", "9.5", suits));

//        result.putAll(calculateLine("38", suits.stream().filter(suit -> SuitType.OUR.equals(suit.getType()) && suit.getAppeal() != null).collect(Collectors.toList())));
//        result.putAll(calculateLine("39", suits.stream().filter(suit -> SuitType.OUR.equals(suit.getType()) && suit.getCassation() != null).collect(Collectors.toList())));

        result.forEach((k, v) -> {
            if (!v.isEmpty()) {
                log.info(k + ": " + v);
            }
        });
        return result;
    }

    public Map<String, String> calculateLineOfTypeAndDefendant(String row, SuitType type, Defendant defendant, List<Suit> suitsList) {
        return calculateLine(row, suitsList.stream().filter(suit ->
                type.equals(suit.getType())  && defendant.equals(suit.getDefendant())
        ).collect(Collectors.toList()));
    }

    public Map<String, String> calculateLineOfCategory(String row, String categoryCode, List<Suit> suitsList) {
        Category category = databaseManager.categoryOfCode(categoryCode);
        return calculateLine(row, suitsList.stream().filter(suit ->
                databaseManager.childsOf(category).contains(suit.getCategory())
        ).collect(Collectors.toList()));
    }

    public Map<String, String> calculateLine(String row, List<Suit> suitsList) {
        Integer currentYear = LocalDateTime.now().getYear();
        List<Suit> usualSuitsColumn1 = suitsList.stream()
                .filter(suit -> currentYear.equals(suit.getYear()))
                .collect(Collectors.toList());
        List<Suit> approvedSuitsColumn2 = suitsList.stream()
                .filter(suit -> Result.APPROVED.equals(suit.getResult()))
                .collect(Collectors.toList());
        List<Suit> declineSuitsColumn3 = suitsList.stream()
                .filter(suit -> Result.DECLINED.equals(suit.getResult()))
                .collect(Collectors.toList());
        List<Suit> agreedSuitsColumn4 = suitsList.stream()
                .filter(suit -> Result.AGREED.equals(suit.getResult()))
                .collect(Collectors.toList());
        List<Suit> postponedSuitsColumn5 = suitsList.stream()
                .filter(suit -> Result.UNRESOLVED.equals(suit.getResult()))
                .collect(Collectors.toList());

        Map<String, String> result = new HashMap<>();
        result.put("01_" + row, String.valueOf(usualSuitsColumn1.size()));
        result.put("02_" + row, String.valueOf(
                usualSuitsColumn1.stream().mapToInt(suit -> suit.getInitialSumm()).sum() / 1000
                )
        );
        result.put("03_" + row, String.valueOf(approvedSuitsColumn2.size()));
        result.put("04_" + row, String.valueOf(
                approvedSuitsColumn2.stream().mapToInt(suit -> suit.getAgreedSumm()).sum() / 1000
                )
        );
        result.put("05_" + row, String.valueOf(declineSuitsColumn3.size()));
        result.put("06_" + row, String.valueOf(
                (declineSuitsColumn3.stream().mapToInt(suit -> suit.getInitialSumm()).sum() +
                        approvedSuitsColumn2.stream().mapToInt(suit -> {
                            int declinedSumm = suit.getInitialSumm() - suit.getAgreedSumm();
                            return declinedSumm > 0 ? declinedSumm : 0;
                        }).sum()
                ) / 1000
                )
        );
        result.put("07_" + row, String.valueOf(agreedSuitsColumn4.size()));
        result.put("08_" + row, String.valueOf(
                agreedSuitsColumn4.stream().mapToInt(suit -> suit.getInitialSumm()).sum() / 1000
                )
        );
        result.put("09_" + row, String.valueOf(postponedSuitsColumn5.size()));
        result.put("10_" + row, String.valueOf(
                postponedSuitsColumn5.stream().mapToInt(suit -> suit.getInitialSumm()).sum() / 1000
                )
        );
        return result;
    }

    public void unFilter() {
        filtered = false;
        fireChanged();
    }

    public void filter(Representative representative, String subString) {
        filterRepresentative = representative;
        filterString = subString;
        fireChanged();
        filtered = true;
    }

    private List<Suit> getSuits() {
        if (suits == null || changed || lastUpdateTime.until(LocalDateTime.now(), ChronoUnit.SECONDS) > 10) {
            suits = databaseManager.retriveAll(Suit.class);
            if (filtered) {
                suits = suits.stream().filter(suit ->
                        (Representative.isEmpty(filterRepresentative) ? true : suit.getRepresentative().equals(filterRepresentative))
                                &&
                                (StringUtils.isEmpty(filterString) ? true : suit.toString().toLowerCase().contains(filterString.toLowerCase()))
                ).collect(Collectors.toList());
            }
            suits.sort(comparator);
            changed = false;
            lastUpdateTime = LocalDateTime.now();
        }
        return suits;
    }

    public void fireChanged() {
        changed = true;
    }
}
