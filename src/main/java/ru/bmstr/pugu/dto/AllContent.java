package ru.bmstr.pugu.dto;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.bmstr.pugu.db.DatabaseManager;
import ru.bmstr.pugu.domain.*;
import ru.bmstr.pugu.properties.PropertyLoader;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by bmstr on 30.11.2016.
 */
@Component
public class AllContent {

    private static final Logger log = LogManager.getLogger(AllContent.class);

    private List<Suit> suits = new ArrayList<>();
    private List<Suit> filteredSuits;

    @Autowired
    private SuitComparator comparator;

    @Autowired
    private PropertyLoader propertyLoader;

    @Autowired
    private DatabaseManager databaseManager;

    public void sort() {
        getSuits().sort(comparator);
    }

    public void addRow(Suit suit) {
        databaseManager.create(suit);
        sort();
    }

    public void deleteRows(int[] rows) {
        List<Integer> toDeleteRows = Arrays.stream(ArrayUtils.toObject(rows))
                .sorted((i1, i2) -> Integer.compare(i2, i1))
                .collect(Collectors.toList());
        toDeleteRows.forEach(i ->
            databaseManager.delete(getSuits().get(i.intValue()))
        );
        sort();
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
        List<Suit> usualSuitsColumn1 = suitsList.stream()
                .filter(suit -> suit.getYear() == 2016)
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
        getAllSuits();
    }

    public void filter(Representative representative, String subString) {
        setSuits(getAllSuits().stream().filter( suit ->
            (Representative.isEmpty(representative) ? true : suit.getRepresentative().equals(representative))
                    &&
                    (StringUtils.isEmpty(subString) ? true : suit.toString().toLowerCase().contains(subString.toLowerCase()))
        ).collect(Collectors.toList()));
    }

    private void setSuits(List<Suit> suits) {
        this.suits = suits;
    }

    private List<Suit> getSuits() {
        if (suits == null) {
            return getAllSuits();
        } else {
            return suits;
        }
    }

    private List<Suit> getAllSuits() {
        suits = databaseManager.retriveAll(Suit.class);
        return suits;
    }
}
