package ru.bmstr.pugu.domain;

/**
 * Created by bmstr on 27.11.2016.
 */
public enum Category {
    EMPTY("", SuitType.USUAL),
    TYSHA_69("1069", SuitType.USUAL),
    OSPARIVANII_RESH_GOS_ORG("Об оспаривании решения гос. органов", SuitType.USUAL),
    IZYATIE_IMUSHESTVA("Изъятие имущества", SuitType.USUAL),
    IVS("ИВС", SuitType.USUAL),
    TYSHA_70("1070", SuitType.USUAL),
    SUDOPROIZVODSTVO("Судопроизводство", SuitType.USUAL),
    OSPARIV_NORM_ACTA("Об оспариваании нормативного акта", SuitType.USUAL),
    OBZHALOV_PRIKAZ_3_3("Об обжаловании приказа 3.3", SuitType.USUAL),
    VOSSTANOVLENIE("Восстановление", SuitType.USUAL),
    SNYATIYE_DISC_VZYSK("О снятии дисциплинарного взыскания", SuitType.USUAL),
    VOSM_VREDA_ZDOROV("Возмещение вреда здоровью", SuitType.USUAL),
    RESHENIE_PO_ZHILISH("Решение по жилищн.", SuitType.USUAL),
    VSYSK_OBYAZAT_PLATEZH("Взыскание обязательных платежей", SuitType.USUAL),
    INDEKSACIYA_DENEZHNYH_SUMM("Индексация денежных сумм", SuitType.USUAL),
    PENSIONNIYE("Пенсионные", SuitType.USUAL),
    ISPOLN_DOGOVOR_OBYAZ("Об исполнении договорных обязательств", SuitType.USUAL),
    ZASHITA_CHEST("О защите чести", SuitType.USUAL),
    NARUSH_PORYADKA_RASSMOTR_OBRASHENIYA("Нарушение порядка рассмотрен обращения", SuitType.USUAL),
    MIGRATSIYA("По миграции", SuitType.USUAL),
    INIYE("Иные", SuitType.USUAL),
    OSOBOGO_PROIZVODSTVA("Особого производства", SuitType.USUAL),
    INIYE8("Иные8", SuitType.USUAL),

    ISPOLNEN_DOGOVORNYH("Об исполнении договорных обязательств", SuitType.OUR),
    REGRESS("В порядке регресса", SuitType.OUR),
    VOZMESH_USHERB_VINOVNYH_SOTRUDNIKOV("В возмещение ущерба с виновных сотрудников", SuitType.OUR),
    VZYSKANII_S_VINOVNYH("О взыскании с виновных в возмещение вреда здоровью", SuitType.OUR),
    INIYE923("Иные 9.2.3", SuitType.OUR),
    ZHILISHNYE("По жилищным", SuitType.OUR),
    ZASHITE_DELOVOY("О защите деловой", SuitType.OUR),
    INIYE95("Иные 9.5", SuitType.OUR)
    ;

    String name;
    SuitType type;
    Category(String name, SuitType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }
}
