package org.miraclesoft.domain.jwt;

import lombok.*;
import org.miraclesoft.domain.Warehouse;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class JwtResponse {
    private String jwttoken;
    private String username;
    private String email;
}
