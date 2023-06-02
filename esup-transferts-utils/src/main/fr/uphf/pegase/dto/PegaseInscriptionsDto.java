package fr.uphf.pegase.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PegaseInscriptionsDto {
    List<InscriptionDto> resultats;

    @Getter
    @Setter
    public static class InscriptionDto {
        private Meta meta;

        @Getter
        @Setter
        public static class Meta {
            private String codeApprenant;
        }
    }
}
