package com.google.wallet.objects.utils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

/**
 * Created by pying on 2/7/14.
 */
public class Config {

  private static Config config = new Config();
  private Map<String, WobCredentials> credentials;

  private Config() {
      credentials = new HashMap<String, WobCredentials>();
  }

  public static Config getInstance() {
    if (config == null) {
        config = new Config();
    }
    return config;
  }

  public WobCredentials getCredentials(String serviceEmail, String privateKeyPath, String applicationName, String issuer)
      throws IOException, GeneralSecurityException {

    if (serviceEmail.startsWith("YourServiceAccountEmail")) {
      System.err.println("ERROR: ServiceAccountEmail not properly configured in web.xml");
      return null;
    }

    if (issuer.startsWith("YourIssuerId")) {
      System.err.println("ERROR: IssuerId not properly configured in web.xml");
      return null;
    }

    String key = issuer + privateKeyPath;

    WobCredentials credential = credentials.get(key);

    if (credential == null) {
      credential = new WobCredentials(
          serviceEmail,
          privateKeyPath,
          applicationName,
          issuer
      );
      credentials.put(key, credential);
    }
    return credential;
  }
  public WobCredentials getCredentials(ServletContext context) throws IOException, GeneralSecurityException {
    return getCredentials(context.getInitParameter("ServiceAccountEmailAddress"),
        context.getInitParameter("ServiceAccountPrivateKey"),
        context.getInitParameter("ApplicationName"),
        context.getInitParameter("IssuerId"));
  }
}
