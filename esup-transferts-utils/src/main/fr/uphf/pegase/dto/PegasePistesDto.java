package fr.uphf.pegase.dto;

import fr.uphf.pegase.dto.fragments.Bac;
import fr.uphf.pegase.dto.fragments.EtatCivil;
import fr.uphf.pegase.dto.fragments.Naissance;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PegasePistesDto {
    private List<Resultat> resultats;

    @Getter
    @Setter
    public static class Resultat {
        private String uuid;

        private EtatCivil etatCivil;

        private Naissance naissance;

        private Bac bac;
    }
}
