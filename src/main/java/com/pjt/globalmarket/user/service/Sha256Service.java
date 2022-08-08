package com.pjt.globalmarket.user.service;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;

@Service
public class Sha256Service implements SecurityService{

    private static final String SHA_256 = Sha256Hash.ALGORITHM_NAME;
    private SecureRandomNumberGenerator secureRandomGenerator = new SecureRandomNumberGenerator();

    @Override
    public String hash(String context, String salt) {
        if(salt == null) {
            return hash(context);
        }
        return new SimpleHash(SHA_256, context, salt).toHex();
    }

    private String hash(String context) {
        return new SimpleHash(SHA_256, context).toHex();
    }

    @Override
    public String generateSalt() {
        return secureRandomGenerator.nextBytes().toBase64();
    }
}
