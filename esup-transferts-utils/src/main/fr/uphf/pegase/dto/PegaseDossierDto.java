package fr.uphf.pegase.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PegaseDossierDto {
    private PegaseApprenantDto apprenant;

    private List<PegaseInscriptionDto> inscriptions;
}
