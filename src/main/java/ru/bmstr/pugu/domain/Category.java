package ru.bmstr.pugu.domain;

import ru.bmstr.pugu.properties.EnumNameHelper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bmstr on 27.11.2016.
 */
public enum Category {
    EMPTY,

    OSPARIVANII_RESH_GOS_ORG(SuitType.USUAL),
        TYSHA_69(SuitType.USUAL, OSPARIVANII_RESH_GOS_ORG),
            IZYATIE_IMUSHESTVA(SuitType.USUAL, TYSHA_69),
            IVS(SuitType.USUAL, TYSHA_69),
        TYSHA_70(SuitType.USUAL, OSPARIVANII_RESH_GOS_ORG),
        SUDOPROIZVODSTVO(SuitType.USUAL, OSPARIVANII_RESH_GOS_ORG),
        INIYE216(SuitType.USUAL, OSPARIVANII_RESH_GOS_ORG),

    OSPARIV_NORM_ACTA(SuitType.USUAL),

    OBZHALOV_PRIKAZA(SuitType.USUAL),
        VOSSTANOVLENIE(SuitType.USUAL, OBZHALOV_PRIKAZA),
        SNYATIYE_DISC_VZYSK(SuitType.USUAL, OBZHALOV_PRIKAZA),
        INIYE223(SuitType.USUAL, OBZHALOV_PRIKAZA),

    VOSM_VREDA_ZDOROV(SuitType.USUAL),

    RESHENIE_PO_ZHILISH(SuitType.USUAL),

    VSYSK_OBYAZAT_PLATEZH(SuitType.USUAL),
        INDEKSACIYA_DENEZHNYH_SUMM(SuitType.USUAL, VSYSK_OBYAZAT_PLATEZH),
        PENSIONNIYE(SuitType.USUAL, VSYSK_OBYAZAT_PLATEZH),
        INIYE263(SuitType.USUAL, VSYSK_OBYAZAT_PLATEZH),

    ISPOLN_DOGOVOR_OBYAZ(SuitType.USUAL),
    ZASHITA_CHEST(SuitType.USUAL),
    NARUSH_PORYADKA_RASSMOTR_OBRASHENIYA(SuitType.USUAL),
    MIGRATSIYA(SuitType.USUAL),
    INIYE211(SuitType.USUAL),

    THIRD_PARTY(SuitType.USUAL),

    ISPOLNEN_DOGOVORNYH(SuitType.OUR),
    REGRESS(SuitType.OUR),
        VOZMESH_USHERB_VINOVNYH_SOTRUDNIKOV(SuitType.OUR, REGRESS),
        VZYSKANII_S_VINOVNYH(SuitType.OUR, REGRESS),
        REGRESS_INIYE(SuitType.OUR, REGRESS),
    ZHILISHNYE(SuitType.OUR),
    ZASHITE_DELOVOY(SuitType.OUR),
    INIYE_OUR(SuitType.OUR)
    ;

    SuitType type;
    Category parent;

    Category() {
    }

    Category(SuitType type) {
        this.type = type;
    }

    Category(SuitType type, Category parent) {
        this.parent = parent;
        this.type = type;
    }

    public SuitType getType() {
        return type;
    }

    public Category getParent() {
        return parent;
    }

    public String toString() {
        return EnumNameHelper.getName(name());
    }

    private boolean hasParent(Category searchParent) {
        if (searchParent == this || (this.parent != null && this.parent == searchParent)) {
            return true;
        } else {
            if (this.parent == null) {
                return false;
            } else {
                return this.parent.hasParent(searchParent);
            }
        }
    }

    public static List<Category> childsOf(Category searchParent) {
        return Arrays.stream(Category.values())
                .filter(child -> child.hasParent(searchParent))
                .collect(Collectors.toList());
    }
}
