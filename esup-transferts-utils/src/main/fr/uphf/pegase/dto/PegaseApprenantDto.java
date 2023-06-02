package fr.uphf.pegase.dto;

import fr.uphf.pegase.dto.fragments.Bac;
import fr.uphf.pegase.dto.fragments.EtatCivil;
import fr.uphf.pegase.dto.fragments.Naissance;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public  class PegaseApprenantDto {
    private String code;

    private EtatCivil etatCivil;

    private Naissance naissance;

    private Bac bac;

    private List<Contact> contacts;

    @Getter
    @Setter
    public static class Contact {
        private Integer pays;
        private String libellePays;
        private String ligne1OuEtage;
        private String ligne2OuBatiment;
        private String ligne3OuVoie;
        private String ligne4OuComplement;
        private String ligne5Etranger;
        private String codePostal;
        private String commune;
        private String libelleCommune;

        private String mail;

        private String telephone;

        private DemandeDeContact demandeDeContact;

        @Getter
        @Setter
        public static class DemandeDeContact {
            private String code;
        }
    }
}
