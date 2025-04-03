package com.spring.graph.api.encryptionservice;


import org.springframework.stereotype.Service;

import com.spring.graph.api.algorithms.AESUtil;

import javax.crypto.SecretKey;

@Service
public class EncryptionService {

    private final SecretKey secretKey;

    public EncryptionService() throws Exception {
        // Generate a new AES key. You can save this key securely and reuse it.
        this.secretKey = AESUtil.generateKey(128);
    }

    public String encryptData(byte[] bs) throws Exception {
        return AESUtil.encrypt(bs, secretKey);
    }

    public String decryptData(String encryptedData) throws Exception {
        return AESUtil.decrypt(encryptedData, secretKey);
    }

    public String getSecretKey() {
        return AESUtil.keyToString(secretKey);
    }
}