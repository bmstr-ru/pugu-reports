package ru.bmstr.pugu.domain;

import ru.bmstr.pugu.properties.EnumNameHelper;

/**
 * Created by bmstr on 27.11.2016.
 */
public enum Category {
    EMPTY(SuitType.USUAL),
    TYSHA_69(SuitType.USUAL),
    OSPARIVANII_RESH_GOS_ORG(SuitType.USUAL),
    IZYATIE_IMUSHESTVA(SuitType.USUAL),
    IVS(SuitType.USUAL),
    TYSHA_70(SuitType.USUAL),
    SUDOPROIZVODSTVO(SuitType.USUAL),
    OSPARIV_NORM_ACTA(SuitType.USUAL),
    OBZHALOV_PRIKAZ_3_3(SuitType.USUAL),
    VOSSTANOVLENIE(SuitType.USUAL),
    SNYATIYE_DISC_VZYSK(SuitType.USUAL),
    VOSM_VREDA_ZDOROV(SuitType.USUAL),
    RESHENIE_PO_ZHILISH(SuitType.USUAL),
    VSYSK_OBYAZAT_PLATEZH(SuitType.USUAL),
    INDEKSACIYA_DENEZHNYH_SUMM(SuitType.USUAL),
    PENSIONNIYE(SuitType.USUAL),
    ISPOLN_DOGOVOR_OBYAZ(SuitType.USUAL),
    ZASHITA_CHEST(SuitType.USUAL),
    NARUSH_PORYADKA_RASSMOTR_OBRASHENIYA(SuitType.USUAL),
    MIGRATSIYA(SuitType.USUAL),
    INIYE(SuitType.USUAL),
    OSOBOGO_PROIZVODSTVA(SuitType.USUAL),
    INIYE8(SuitType.USUAL),

    ISPOLNEN_DOGOVORNYH(SuitType.OUR),
    REGRESS(SuitType.OUR),
    VOZMESH_USHERB_VINOVNYH_SOTRUDNIKOV(SuitType.OUR),
    VZYSKANII_S_VINOVNYH(SuitType.OUR),
    INIYE923(SuitType.OUR),
    ZHILISHNYE(SuitType.OUR),
    ZASHITE_DELOVOY(SuitType.OUR),
    INIYE95(SuitType.OUR)
    ;

    SuitType type;
    Category(SuitType type) {
        this.type = type;
    }

    public SuitType getType() {
        return type;
    }

    public String toString() {
        return EnumNameHelper.getName(name());
    }
}
