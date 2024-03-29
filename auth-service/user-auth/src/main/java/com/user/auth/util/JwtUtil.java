package com.user.auth.util;

import com.user.auth.constant.ApiConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JwtUtil {

    public static final String SECRET = "Shivmohan52415241$$56235fgfgfgfgfgh6212";


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private  <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Boolean validateIsAccessToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) && extractAllClaims(token).get(ApiConstant.TOKEN_TYPE).equals(ApiConstant.ACCESS_TOKEN));
    }

    public Boolean validateIsRefreshToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) && extractAllClaims(token).get(ApiConstant.TOKEN_TYPE).equals(ApiConstant.REFRESH_TOKEN));
    }


    public String generateAccessToken(String userName){
        Map<String,Object> claims=new HashMap<>();
        claims.put(ApiConstant.TOKEN_TYPE,ApiConstant.ACCESS_TOKEN);
        return createToken(claims,userName,12);
    }

    public String generateRefreshToken(String userName){
        Map<String,Object> claims=new HashMap<>();
        claims.put(ApiConstant.TOKEN_TYPE,ApiConstant.REFRESH_TOKEN);
        return createToken(claims,userName,24*7);
    }

    private String createToken(Map<String, Object> claims, String userName,long expiryTimeInHour) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*expiryTimeInHour))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(DatatypeConverter.printHexBinary(SECRET.getBytes()));
        return Keys.hmacShaKeyFor(keyBytes);
    }
}