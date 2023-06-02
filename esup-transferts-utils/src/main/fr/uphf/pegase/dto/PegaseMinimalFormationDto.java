package fr.uphf.pegase.dto;

import feign.RequestLine;
import fr.uphf.pegase.dto.fragments.Periode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PegaseMinimalFormationDto {
    private String code;

    private Integer version;

    private String codeTypeDiplome;

    private String libelleLong;

    private StructureBudgetaire structureBudgetaire;

    private Periode periode;

    @Getter
    @Setter
    public static class StructureBudgetaire {
        private String codeUai;

        private String denominationPrincipale;
    }
}
