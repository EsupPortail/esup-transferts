package fr.uphf.pegase.services;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import fr.uphf.pegase.dto.*;

@Headers({"Authorization: Bearer {token}", "Accept: application/json"})
public interface PegaseInsApiService {

    @RequestLine("GET /gestion/inscriptions/{structure}/?codePeriode={periode}&objetMaquette={codeFormation}")
    PegaseInscriptionsDto getInscriptions(@Param("token") String token,
                                          @Param("structure") String structure,
                                          @Param("periode") String periode,
                                          @Param("codeFormation") String codeFormation);

    @RequestLine("GET /gestion/inscription/{structure}/{numEtu}/")
    PegaseDossierDto getDossier(@Param("token") String token,
                                @Param("structure") String structure,
                                @Param("numEtu") String numEtu);

    @RequestLine("GET /gestion/apprenants/{structure}/{numEtu}/")
    PegaseApprenantDto getApprenant(@Param("token") String token,
                                    @Param("structure") String structure,
                                    @Param("numEtu") String numEtu);

    @RequestLine("GET /gestion/apprenants/{structure}/ine/{ine}/")
    PegaseApprenantDto getApprenantByIne(@Param("token") String token,
                                         @Param("structure") String structure,
                                         @Param("ine") String ine);

    @RequestLine("GET /pistes/inscriptions/{structure}/?limit=1&recherche={numEtu}")
    PegasePistesDto getPistes(@Param("token") String token,
                              @Param("structure") String structure,
                              @Param("numEtu") String numEtu);

    @RequestLine("GET /pistes/{uuid}/premieres-inscriptions")
    PegasePremieresInscriptionsDto getPremieresInscriptions(@Param("token") String token,
                                                            @Param("uuid") String uuid);
}
