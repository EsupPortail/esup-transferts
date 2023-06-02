package fr.uphf.pegase.dto.fragments;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bac {
    private String ine;

    private String anneeObtention;

    private String pays;

    private String departement;

    private String libelleDepartement;

    private String serie;

    private String libelleSerie;

    private String etablissement;

    private String typeEtablissement;

    private String mention;
}
