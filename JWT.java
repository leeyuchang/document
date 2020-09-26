package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class JWT {

  private static final ObjectMapper mapper = new ObjectMapper();

  String header() throws JsonProcessingException {
    var header = mapper.writeValueAsString(new Header());
    return Base64
        .getUrlEncoder()
        .withoutPadding()
        .encodeToString(header.getBytes());
  }

  String payLoad() throws JsonProcessingException {
    var payLoad = mapper.writeValueAsString(new Payload());
    return Base64
        .getUrlEncoder()
        .withoutPadding()
        .encodeToString(payLoad.getBytes());
  }

  byte[] hmacSha256(byte[] data, String secretKey) {
    try {
      Mac mac = Mac.getInstance("HmacSHA256");
      SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
      mac.init(secretKeySpec);
      return mac.doFinal(data);
    } catch (Exception e) {
      throw new RuntimeException("Failed to calculate hmac-sha256", e);
    }
  }

  byte[] unsignedToken() throws JsonProcessingException {
    return String.format("%s.%s",header(), payLoad()).getBytes();
  }

  String signedToken() throws JsonProcessingException {
    return Base64
        .getUrlEncoder()
        .withoutPadding()
        .encodeToString(hmacSha256(unsignedToken(),"secret"));
  }

  String jwt() throws JsonProcessingException {
    return String.format("%s.%s.%s", header(), payLoad(), signedToken());
  }

  private class Header {
    public String alg = "HS256";
    public String typ = "JWT";
  }

  private class Payload {
    public String sub = "1234567890";
    public String name ="John Doe";
    public Integer iat = 1516239022;
  }

  @Test
  void run() throws JsonProcessingException {
    System.out.println("jwt : " + jwt());
  }

  @Test
  void decodeHeader() {
    var headerString = Base64
        .getUrlDecoder()
        .decode("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
    System.out.println(new String(headerString));
  }

  @Test
  void decodePayLoad() {
    var payLoadString = Base64
        .getUrlDecoder()
        .decode("eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ");
    System.out.println(new String(payLoadString));
  }
}
