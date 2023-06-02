package fr.uphf.pegase.services;

import feign.Headers;
import feign.RequestLine;
import fr.uphf.pegase.dto.PegaseCredentialsDto;

public interface PegaseAuthApiService {
    @RequestLine("POST /cas/v1/tickets")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    String getToken(PegaseCredentialsDto credentials);
}
