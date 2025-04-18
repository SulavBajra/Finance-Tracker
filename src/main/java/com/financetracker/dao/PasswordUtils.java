package com.financetracker.dao;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtils {
    private static final int SALT_LENGTH = 16;
    private static final int HASH_ITERATION = 10000;
    private static final int KEY_LENGTH = 256;

    public static String generateSalt(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        secureRandom.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);

    }

    public static String hashPassword(String password, String salt)
    throws NoSuchAlgorithmException{
        try{
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(),Base64.getDecoder().decode(salt),HASH_ITERATION,KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Error while hashing password", e);
        }
        
    }

    public static boolean verifyPassword(String inputPassword,String storedHash, String salt)
    throws NoSuchAlgorithmException{
        String hashedInputPassword = hashPassword(inputPassword, salt);
        return hashedInputPassword.equals(storedHash);
    }

    // public static secureHash(String password){
        
    // }    

}
