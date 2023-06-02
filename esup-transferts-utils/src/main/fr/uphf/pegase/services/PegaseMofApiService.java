package fr.uphf.pegase.services;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import fr.uphf.pegase.dto.PegaseFormationDto;
import fr.uphf.pegase.dto.PegaseMinimalFormationDto;

import java.util.List;

@Headers({"Authorization: Bearer {token}", "Accept: application/json"})
public interface PegaseMofApiService {
    @RequestLine("GET /formations?codeStructureEtablissement={structure}&codePeriode={periode}")
    List<PegaseMinimalFormationDto> getFormations(@Param("token") String token,
                                                  @Param("structure") String structure,
                                                  @Param("periode") String periode);

    @RequestLine("GET /formations/{codeFormation}/periodes/{periode}?codeStructureEtablissement={structure}")
    PegaseFormationDto getFormation(@Param("token") String token,
                                    @Param("structure") String structure,
                                    @Param("codeFormation") String codeFormation,
                                    @Param("periode") String periode);
}
