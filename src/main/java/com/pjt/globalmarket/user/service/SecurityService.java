package com.pjt.globalmarket.user.service;

public interface SecurityService {

    public String hash(String context, String salt);

    public String generateSalt();

}
