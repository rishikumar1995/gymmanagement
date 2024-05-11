package com.gymmanagement.Service;

import com.gymmanagement.Modal.Users;
import com.gymmanagement.Repository.TokenRepository;
import com.gymmanagement.utils.MobileResponseDTOFactory;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    //private final String SECRET_KEY = "4bb6d1dfbafb64a681139d1586b6f1160d18159afd57c8c79136d7490630407c";
    private final String SECRET_KEY = "dbtechparichay3232db3232mohdwaziddb2580qwertyasdf";
    private final TokenRepository tokenRepository;

    @Autowired
    private MobileResponseDTOFactory mobileResponseDTOFactory;

    public JwtService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public String extractUsername(String token) {
        try {
            return extractClaim(token, Claims::getSubject);
        } catch (Exception e){
            //return "JWT Token Expire";
            return null;
        }
    }

    public Object getUserid(String token) {
        try {
            Claims claims = extractAllClaims(token);
            if (claims != null) {
                Integer userId = claims.get("userid", Integer.class);
                if (userId != null) {
                    return userId;
                } else {
                    return "User ID not found in JWT token";
                }
            } else {
                return "Claims not found in JWT token";
            }
        } catch (Exception e) {
            return "Error occurred while extracting user ID from JWT token: " + e.getMessage();
        }
    }


    public boolean isValid(String token, UserDetails user) {
        String username = extractUsername(token);
        if(username==null || username.equals("")){
            return false;
        }

        boolean validToken = tokenRepository
                .findByToken(token)
                .map(t -> !t.isLoggedOut())
                .orElse(false);

        return (username.equals(user.getUsername())) && !isTokenExpired(token) && validToken;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        if(claims==null){
            //return (T) mobileResponseDTOFactory.failedMessage("JWT Token Expire");
            return null;
        }
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parser()
                    .verifyWith(getSigninKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException ex) {
            System.out.println("JWT token has expired.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


//    public String generateToken(Users user) {
//        String token = Jwts
//                .builder().subject(user.getUsername())
//                .issuedAt(new Date(System.currentTimeMillis()))
//                .expiration(new Date(System.currentTimeMillis() + 24*60*60*1000 ))
//                .signWith(getSigninKey())
//                .compact();
//
//        return token;
//    }
    public String generateToken(Users user) {
        //long expirationTimeMillis = 2 * 60 * 1000; // 30 minutes in milliseconds
        long expirationTimeMillis = 24*60*60*1000; // 24 hour in milliseconds
        long currentTimeMillis = System.currentTimeMillis();

        String token = Jwts
                .builder().subject(user.getUsername())
                .claim("userid",user.getId())
                .issuedAt(new Date(currentTimeMillis))
                .expiration(new Date(currentTimeMillis + expirationTimeMillis))
                .signWith(getSigninKey())
                .compact();

        return token;
    }


    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
