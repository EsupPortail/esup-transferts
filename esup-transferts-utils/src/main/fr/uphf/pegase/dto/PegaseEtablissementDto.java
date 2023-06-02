package fr.uphf.pegase.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PegaseEtablissementDto {
    private String libelleAffichage;

    private Departement departement;

    private String numeroUai;
    private TypeUai typeUai;

    private String adresseUai;
    private String codePostalUai;

    private String academie;

    private String academieLibe;

    @Getter
    @Setter
    public static class Departement {
        private String code;

        private String libelleAffichage;
    }

    @Getter
    @Setter
    public static class TypeUai {
        private String typeUai;
    }
}
