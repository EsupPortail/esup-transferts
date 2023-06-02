package fr.uphf.pegase.dto;

import fr.uphf.pegase.dto.fragments.Periode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PegaseInscriptionDto {

    private Cible cible;

    private boolean principale;

    @Getter
    @Setter
    public static class Cible {
        private String code;

        private String libelleLong;

        private Periode periode;
    }
}
