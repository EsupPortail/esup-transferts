package fr.uphf.pegase.services;

import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import fr.uphf.pegase.dto.*;

import java.util.List;

@Headers({"Authorization: Bearer {token}", "Accept: application/json"})
public interface PegaseRefApiService {

    @RequestLine("GET /nomenclatures/communes/codePostal/{codePostal}")
    List<PegaseCommuneDto> getCommunes(@Param("token") String token,
                                       @Param("codePostal") String codePostal);

    @RequestLine("GET /nomenclatures/PaysNationalite")
    List<PegasePaysDto> getCountries(@Param("token") String token);

    @RequestLine("GET /nomenclatures/Departement")
    List<PegaseDepartementDto> getDepartements(@Param("token") String token);

    @RequestLine("POST /nomenclatures/EtablissementFrancais/rechercher")
    @Body("%7B\"numeroUai\": %7B\"operateur\":\"~\",\"valeur\":\"{uai}\"%7D\"%7D")
    List<PegaseEtablissementDto> getEtablissementsByUai(@Param("token") String token,
                                                        @Param("uai") String uai);

    @RequestLine("GET /nomenclatures/EtablissementFrancais")
    List<PegaseEtablissementDto> getEtablissements(@Param("token") String token);

    @RequestLine("GET /nomenclatures/TypeDiplome")
    List<PegaseTypeDiplome> getTypesDiplomes(@Param("token") String token);

    @RequestLine("POST /nomenclatures/SerieBac/rechercher")
    @Body("%7B\"codeBcn\": %7B\"operateur\": \"=\", \"valeur\":\"{codeBcn}\"%7D%7D")
    List<PegaseBacDto> getBacByCodeBcn(@Param("token") String token,
                                       @Param("codeBcn") String codeBcn);

    @RequestLine("GET /structures")
    List<PegaseStructureDto> getStructures(@Param("token") String token);
}
