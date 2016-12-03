package ru.bmstr.pugu.domain;

/**
 * Created by bmstr on 27.11.2016.
 */
public enum Category {
    EMPTY(""),
    TYSHA_69("1069"),
    OSPARIVANII_RESH_GOS_ORG("Об оспаривании решения гос. органов"),
    IZYATIE_IMUSHESTVA("Изъятие имущзества"),
    IVS("ИВС"),
    TYSHA_70("1070"),
    SUDOPROIZVODSTVO("Судопроизводство"),
    OSPARIV_NORM_ACTA("Об оспариваании нормативного акта"),
    OBZHALOV_PRIKAZ_3_3("Об обжаловании приказа 3.3"),
    VOSSTANOVLENIE("Восстановление"),
    SNYATIYE_DISC_VZYSK("О снятии дисциплинарного взыскания"),
    VOSM_VREDA_ZDOROV("Возмещение вреда здоровью"),
    RESHENIE_PO_ZHILISH("Решение по жилищн."),
    VSYSK_OBYAZAT_PLATEZH("Взыскание обязательных платежей"),
    INDEKSACIYA_DENEZHNYH_SUMM("Индексация денежных сумм"),
    PENSIONNIYE("Пенсионные"),
    ISPOLN_DOGOVOR_OBYAZ("Об исполнении договорных обязательств"),
    ZASHITA_CHEST("О защите чести"),
    NARUSH_PORYADKA_RASSMOTR_OBRASHENIYA("Нарушение порядка рассмотрен обращения"),
    MIGRATSIYA("По миграции"),
    INIYE("Иные"),
    OSOBOGO_PROIZVODSTVA("Особого производства")
    ;

    String name;
    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }
}
