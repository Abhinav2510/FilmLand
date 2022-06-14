package org.filmland.catalog.security.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * {@link AuthUtils} is utility class for handling JWT related stuff
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthUtils {
    /**
     * generates a JWT which is used in identifying users later in Authentication and Authorization filters
     * @param email input email
     * @param authenticationSigningSecret secret used for signing the JWT token which should be same while parsing input JWT
     * @return JWT with subject user and expiration time of 240 hours
     */
    public static String generateJWT(String email, String authenticationSigningSecret) {

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + 864_000_000))
                .signWith(SignatureAlgorithm.HS384, authenticationSigningSecret.getBytes()).compact();
    }

    /**
     * Gets user from input JWT using authenticationSigningSecret
     * @param token input JWT token
     * @param authenticationSigningSecret same secret used for generating JWT token
     * @return email from subject of JWT
     */
    public static String getUser(String token,String authenticationSigningSecret){
        return Jwts.parserBuilder().setSigningKey(authenticationSigningSecret.getBytes()).build().parseClaimsJws
                (token.replace("Bearer", ""))
                .getBody()
                .getSubject();
    }
}
