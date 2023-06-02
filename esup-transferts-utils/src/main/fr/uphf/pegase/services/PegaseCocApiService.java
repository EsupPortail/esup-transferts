package fr.uphf.pegase.services;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import fr.uphf.pegase.dto.PegaseResultatDto;

import java.util.List;

@Headers({"Authorization: Bearer {token}", "Accept: application/json"})
public interface PegaseCocApiService {
    @RequestLine("GET /etablissements/{structure}/periodes/{periode}/apprenants/{numEtu}/chemins/{chemin}")
    List<PegaseResultatDto> getResultats(@Param("token") String token,
                                         @Param("structure") String structure,
                                         @Param("periode") String periode,
                                         @Param("numEtu") String numEtu,
                                         @Param("chemin") String chemin);
}
