package com.narola.finalproject.utility;

import javax.servlet.http.Part;
import java.io.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Random;

public class Utility {

    private Utility() {

    }

    public static int generateVerificationCode() {
        return new Random().nextInt(900000) + 100000;
    }

    public static String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPasswordBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPasswordBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error encrypting password", e);
        }
    }
    public static String getSubmittedFileName(Part part){
        String header = String.valueOf(part.getHeader("content-disposition"));
        if (header == null) {
            return null;
        }
        for (String headerPart : header.split(";")) {
            if (headerPart.trim().startsWith("filename")) {
                return headerPart.substring(headerPart.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    public static void putImageToDirectory(String filePath, Part filePart) throws IOException {
        OutputStream out = new FileOutputStream(filePath);
        InputStream fileContent = filePart.getInputStream();
        int read;
        byte[] buffer = new byte[4096];
        while ((read = fileContent.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

}
