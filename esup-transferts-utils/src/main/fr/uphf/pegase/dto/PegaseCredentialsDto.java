package fr.uphf.pegase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PegaseCredentialsDto {
    private final String username;

    private final String password;

    private final boolean token;
}
