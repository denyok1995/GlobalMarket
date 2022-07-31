package com.pjt.coupang.user.service;

public interface SecurityService {

    public String hash(String context, String salt);

    public String generateSalt();

}
