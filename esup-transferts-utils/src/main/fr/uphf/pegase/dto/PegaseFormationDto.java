package fr.uphf.pegase.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PegaseFormationDto extends PegaseMinimalFormationDto {
    private String niveauSise;

    private String codeStructurePrincipale;
}
