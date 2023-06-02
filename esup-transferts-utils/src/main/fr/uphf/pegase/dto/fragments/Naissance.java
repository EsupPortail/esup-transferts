package fr.uphf.pegase.dto.fragments;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Naissance {
    private String nationalite;

    private String libelleNationalite;

    private String dateDeNaissance;

    private String communeDeNaissance;

    private String libelleCommuneDeNaissance;
}
