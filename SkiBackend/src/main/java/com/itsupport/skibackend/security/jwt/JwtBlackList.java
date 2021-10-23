package com.itsupport.skibackend.security.jwt;

import java.util.ArrayList;

public class JwtBlackList {

    private static JwtBlackList jwtBlackList = null;

    private ArrayList<String> tokens;

    private JwtBlackList() {
        tokens = new ArrayList<>();
    }

    public static JwtBlackList getInstance() {
        if(jwtBlackList == null) jwtBlackList = new JwtBlackList();
        return jwtBlackList;
    }

    public void addToken(String token) {
        tokens.add(token);
    }

    public boolean containsToken(String token) {
        return tokens.contains(token);
    }
}
