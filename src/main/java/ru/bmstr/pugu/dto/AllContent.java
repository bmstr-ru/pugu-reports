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
//        result.putAll(calculateLine("01", suits.stream().filter(suit -> suit.getType() == SuitType.USUAL).collect(Collectors.toList())));
//        result.putAll(calculateLineOfTypeAndCategory("02", SuitType.USUAL, Category.OSPARIVANII_RESH_GOS_ORG, suits));
//        result.putAll(calculateLineOfTypeAndCategory("03", SuitType.USUAL, Category.TYSHA_69, suits));
//        result.putAll(calculateLineOfTypeAndCategory("04", SuitType.USUAL, Category.IZYATIE_IMUSHESTVA, suits));
//        result.putAll(calculateLineOfTypeAndCategory("05", SuitType.USUAL, Category.IVS, suits));
//        result.putAll(calculateLineOfTypeAndCategory("06", SuitType.USUAL, Category.TYSHA_70, suits));
//        result.putAll(calculateLineOfTypeAndCategory("07", SuitType.USUAL, Category.SUDOPROIZVODSTVO, suits));
//        result.putAll(calculateLineOfTypeAndCategory("08", SuitType.USUAL, Category.OSPARIV_NORM_ACTA, suits));
//        result.putAll(calculateLineOfTypeAndCategory("09", SuitType.USUAL, Category.OBZHALOV_PRIKAZA, suits));
//        result.putAll(calculateLineOfTypeAndCategory("10", SuitType.USUAL, Category.VOSSTANOVLENIE, suits));
//        result.putAll(calculateLineOfTypeAndCategory("11", SuitType.USUAL, Category.SNYATIYE_DISC_VZYSK, suits));
//        result.putAll(calculateLineOfTypeAndCategory("12", SuitType.USUAL, Category.VOSM_VREDA_ZDOROV, suits));
//        result.putAll(calculateLineOfTypeAndCategory("13", SuitType.USUAL, Category.RESHENIE_PO_ZHILISH, suits));
//        result.putAll(calculateLineOfTypeAndCategory("14", SuitType.USUAL, Category.VSYSK_OBYAZAT_PLATEZH, suits));
//        result.putAll(calculateLineOfTypeAndCategory("15", SuitType.USUAL, Category.INDEKSACIYA_DENEZHNYH_SUMM, suits));
//        result.putAll(calculateLineOfTypeAndCategory("16", SuitType.USUAL, Category.PENSIONNIYE, suits));
//        result.putAll(calculateLineOfTypeAndCategory("17", SuitType.USUAL, Category.ISPOLN_DOGOVOR_OBYAZ, suits));
//        result.putAll(calculateLineOfTypeAndCategory("18", SuitType.USUAL, Category.ZASHITA_CHEST, suits));
//        result.putAll(calculateLineOfTypeAndCategory("19", SuitType.USUAL, Category.NARUSH_PORYADKA_RASSMOTR_OBRASHENIYA, suits));
//        result.putAll(calculateLineOfTypeAndCategory("20", SuitType.USUAL, Category.MIGRATSIYA, suits));
//        result.putAll(calculateLineOfTypeAndCategory("21", SuitType.USUAL, Category.INIYE211, suits));
//        result.putAll(calculateLine("22", suits.stream().filter(suit -> suit.getType() == SuitType.SPECIAL).collect(Collectors.toList())));
//        result.putAll(calculateLineOfTypeAndDefendant("23", SuitType.USUAL, Defendant.KAZNA, suits));
//        result.putAll(calculateLineOfTypeAndDefendant("24", SuitType.USUAL, Defendant.MVD, suits));
//        result.putAll(calculateLineOfTypeAndDefendant("25", SuitType.USUAL, Defendant.PODRAZDELENIE, suits));
//
//        result.putAll(calculateLine("26", suits.stream().filter(suit -> suit.getType() == SuitType.APPELATION).collect(Collectors.toList())));
//        result.putAll(calculateLine("27", suits.stream().filter(suit -> suit.getType() == SuitType.CASSATION).collect(Collectors.toList())));
//
//        result.putAll(calculateLine("30", suits.stream().filter(suit -> suit.getType() == SuitType.OUR).collect(Collectors.toList())));
//        result.putAll(calculateLineOfTypeAndCategory("31", SuitType.OUR, Category.ISPOLNEN_DOGOVORNYH, suits));
//        result.putAll(calculateLineOfTypeAndCategory("32", SuitType.OUR, Category.REGRESS, suits));
//        result.putAll(calculateLineOfTypeAndCategory("33", SuitType.OUR, Category.VOZMESH_USHERB_VINOVNYH_SOTRUDNIKOV, suits));
//        result.putAll(calculateLineOfTypeAndCategory("34", SuitType.OUR, Category.VZYSKANII_S_VINOVNYH, suits));
//        result.putAll(calculateLineOfTypeAndCategory("35", SuitType.OUR, Category.ZHILISHNYE, suits));
//        result.putAll(calculateLineOfTypeAndCategory("36", SuitType.OUR, Category.ZASHITE_DELOVOY, suits));
//        result.putAll(calculateLineOfTypeAndCategory("37", SuitType.OUR, Category.INIYE_OUR, suits));
//
//        result.putAll(calculateLine("38", suits.stream().filter(suit -> suit.getType() == SuitType.OUR_APPELATION).collect(Collectors.toList())));
//        result.putAll(calculateLine("39", suits.stream().filter(suit -> suit.getType() == SuitType.OUR_CASSATION).collect(Collectors.toList())));

        result.forEach((k, v) -> {
            if (!v.isEmpty()) {
                log.info(k + ": " + v);
            }
        });
        return result;
    }

    public Map<String, String> calculateLineOfTypeAndDefendant(String row, SuitType type, Defendant defendant, List<Suit> suitsList) {
        return calculateLine(row, suitsList.stream().filter(suit ->
                suit.getType() == type && suit.getDefendant() == defendant
        ).collect(Collectors.toList()));
    }

    public Map<String, String> calculateLineOfTypeAndCategory(String row, SuitType type, Category category, List<Suit> suitsList) {
        return calculateLine(row, suitsList.stream().filter(suit ->
                suit.getType() == type && Category.childsOf(category).contains(suit.getCategory())
        ).collect(Collectors.toList()));
    }

    public Map<String, String> calculateLine(String row, List<Suit> suitsList) {
        Integer currentYear = LocalDateTime.now().getYear();
        List<Suit> usualSuitsColumn1 = suitsList.stream()
                .filter(suit -> suit.getYear() == currentYear)
                .collect(Collectors.toList());
        List<Suit> agreedSuitsColumn2 = suitsList.stream()
//                .filter(suit -> suit.getResult() == Result.APPROVE)
                .collect(Collectors.toList());
        List<Suit> declineSuitsColumn3 = suitsList.stream()
//                .filter(suit -> suit.getResult() == Result.DECLINE)
                .collect(Collectors.toList());
        List<Suit> agreedSuitsColumn4 = suitsList.stream()
//                .filter(suit -> suit.getResult() == Result.AGREED)
                .collect(Collectors.toList());
        List<Suit> postponedSuitsColumn5 = suitsList.stream()
//                .filter(suit -> suit.getResult() == Result.NONE)
                .collect(Collectors.toList());

        Map<String, String> result = new HashMap<>();
        result.put("01_" + row, String.valueOf(usualSuitsColumn1.size()));
        result.put("02_" + row, String.valueOf(
                usualSuitsColumn1.stream().mapToInt(suit -> suit.getInitialSumm()).sum() / 1000
                )
        );
        result.put("03_" + row, String.valueOf(agreedSuitsColumn2.size()));
        result.put("04_" + row, String.valueOf(
                agreedSuitsColumn2.stream().mapToInt(suit -> suit.getAgreedSumm()).sum() / 1000
                )
        );
        result.put("05_" + row, String.valueOf(declineSuitsColumn3.size()));
        result.put("06_" + row, String.valueOf(
                (declineSuitsColumn3.stream().mapToInt(suit -> suit.getInitialSumm()).sum() +
                        agreedSuitsColumn2.stream().mapToInt(suit -> {
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
    }

    public void filter(Representative representative, String subString) {
        filterRepresentative = representative;
        filterString = subString;
        filtered = true;
    }

    private List<Suit> getSuits() {
        suits = databaseManager.retriveAll(Suit.class);
        if (filtered) {
            suits = suits.stream().filter(suit ->
                    (Representative.isEmpty(filterRepresentative) ? true : suit.getRepresentative().equals(filterRepresentative))
                            &&
                            (StringUtils.isEmpty(filterString) ? true : suit.toString().toLowerCase().contains(filterString.toLowerCase()))
            ).collect(Collectors.toList());
        }
        suits.sort(comparator);
        return suits;
    }
}
