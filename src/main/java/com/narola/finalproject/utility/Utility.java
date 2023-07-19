package com.narola.finalproject.utility;

import javax.servlet.http.Part;
import java.io.*;

import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

    public static Map<String, String> decodeUrlToMap(String requestBody) throws UnsupportedEncodingException {
        String decodedRequestBody = URLDecoder.decode(requestBody, "UTF-8");
        String[] keyValuePairs = decodedRequestBody.split("&");

        Map<String, String> map = new HashMap<>();
        for (String keyValuePair : keyValuePairs) {
            String[] parts = keyValuePair.split("=");
            if (parts.length == 2) {
                String key = parts[0];
                String value = parts[1];
                map.put(key, value);
            }
        }
        return map;
    }

    public static String formatDate(LocalDateTime value) {
        LocalDateTime dateTime = LocalDateTime.parse(String.valueOf(value));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDateTime = dateTime.format(formatter);
        return formattedDateTime;
    }
}
