package com.google.wallet.objects.utils;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.PemReader;
import com.google.api.client.util.PemReader.Section;
import com.google.api.client.util.SecurityUtils;
import com.google.common.io.ByteStreams;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.interfaces.RSAPrivateKey;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;

import org.json.JSONObject;

/**
 * Class to define Wallet Object related credentials. These credentials are used
 * with WobUtils to simplify the OAuth token generation and JWT signing.
 * <p/>
 * If you're using a key file, specify the privateKeyPath using the first
 * constructor. If you've defined the private key as a base 64 encoded string,
 * convert it to an RSAPrivate Key with WobUtils.getRsaPrivateKey.
 *
 * @author pying
 */
public class WobCredentials {
  String serviceAccountId;
  String serviceAccountPrivateKeyPath;
  String applicationName;
  String issuerId;
  private RSAPrivateKey serviceAccountPrivateKey;
  public static final HttpTransport httpTransport = new NetHttpTransport();
  public static final JsonFactory jsonFactory = new GsonFactory();

  public static final List<String> scopes = Collections.unmodifiableList(Arrays.asList(
      "https://www.googleapis.com/auth/wallet_object.issuer",
      "https://www.googleapis.com/auth/wallet_object_sandbox.issuer"));

  private GoogleCredential gCredential;


  /**
   * Constructor for defining a path to your key.p12 file.
   *
   * @param serviceAccountId
   * @param privateKeyPath
   * @param applicationName
   * @param issuerId
   */
  public WobCredentials(String serviceAccountId, String privateKeyPath,
      String applicationName, String issuerId) throws IOException, GeneralSecurityException {
    setServiceAccountId(serviceAccountId);
    setServiceAccountPrivateKeyPath(privateKeyPath);
    setApplicationName(applicationName);
    setIssuerId(issuerId);
    generateRsaKey();
    generateGoogleCredential();
  }

  /**
   * Constructor for a base64 encoded private key String.
   *
   * @param serviceAccountId
   * @param privateKey
   * @param applicationName
   * @param issuerId
   */
  public WobCredentials(String serviceAccountId, RSAPrivateKey privateKey,
      String applicationName, String issuerId) {
    setServiceAccountId(serviceAccountId);
    setServiceAccountPrivateKey(privateKey);
    setApplicationName(applicationName);
    setIssuerId(issuerId);
    generateGoogleCredential();
  }

  /**
   * Helper function to generate the Google Credential
   *
   * @return
   * @throws GeneralSecurityException
   * @throws IOException
   */
  private void generateGoogleCredential() {
    gCredential = new GoogleCredential.Builder().setTransport(httpTransport).setJsonFactory(jsonFactory)
        .setServiceAccountId(serviceAccountId).setServiceAccountScopes(scopes)
        .setServiceAccountPrivateKey(serviceAccountPrivateKey).build();
  }

  private void generateRsaKey() throws IOException, GeneralSecurityException {
    String content = new String(Files.readAllBytes(Paths.get(serviceAccountPrivateKeyPath)));
    JSONObject privateKeyJson = new JSONObject(content);
    String privateKeyPkcs8 = (String) privateKeyJson.get("private_key");
    Reader reader = new StringReader(privateKeyPkcs8);
    Section section = PemReader.readFirstSectionAndClose(reader, "PRIVATE KEY");
    byte[] bytes = section.getBase64DecodedBytes();
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
    Exception unexpectedException;
    KeyFactory keyFactory = SecurityUtils.getRsaKeyFactory();
    serviceAccountPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
  }

  /**
   * @return the serviceAccountPrivateKey
   */
  public RSAPrivateKey getServiceAccountPrivateKey() {
    return serviceAccountPrivateKey;
  }

  /**
   * @param serviceAccountPrivateKey the serviceAccountPrivateKey to set
   */
  public void setServiceAccountPrivateKey(RSAPrivateKey serviceAccountPrivateKey) {
    this.serviceAccountPrivateKey = serviceAccountPrivateKey;
  }

  /**
   * @return the serviceAccountId
   */
  public String getServiceAccountId() {
    return serviceAccountId;
  }

  /**
   * @param serviceAccountId the serviceAccountId to set
   */
  public void setServiceAccountId(String serviceAccountId) {
    this.serviceAccountId = serviceAccountId;
  }

  /**
   * @return the serviceAccountPrivateKey
   */
  public String getServiceAccountPrivateKeyPath() {
    return serviceAccountPrivateKeyPath;
  }

  /**
   * @param serviceAccountPrivateKey the serviceAccountPrivateKey to set
   */
  public void setServiceAccountPrivateKeyPath(String serviceAccountPrivateKey) {
    this.serviceAccountPrivateKeyPath = serviceAccountPrivateKey;
  }

  /**
   * @return the applicationName
   */
  public String getApplicationName() {
    return applicationName;
  }

  /**
   * @param applicationName the applicationName to set
   */
  public void setApplicationName(String applicationName) {
    this.applicationName = applicationName;
  }

  /**
   * @return the issuerId
   */
  public String getIssuerId() {
    return issuerId;
  }

  /**
   * @param issuerId the issuerId to set
   */
  public void setIssuerId(String issuerId) {
    this.issuerId = issuerId;
  }

  public String toString() {
    StringBuilder sb =
        new StringBuilder().append(serviceAccountId).append(serviceAccountPrivateKeyPath).append(applicationName)
            .append(issuerId).append(new String(serviceAccountPrivateKey.getEncoded()));
    return sb.toString();
  }

  public GoogleCredential getGoogleCredential() {
    return gCredential;
  }

  /**
   * Refreshes the access token and returns it.  You do not need to explicitly call this function.
   * You should only call it for one offs, like a script to request access tokens.
   *
   * @return OAuth access token
   * @throws GeneralSecurityException
   * @throws IOException
   */
  public String accessToken() throws GeneralSecurityException, IOException {
    gCredential.refreshToken();
    return gCredential.getAccessToken();
  }

}
