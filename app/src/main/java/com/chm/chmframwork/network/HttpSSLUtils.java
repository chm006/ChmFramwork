package com.chm.chmframwork.network;

import android.annotation.SuppressLint;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.OkHttpClient;

/**
 * https 证书
 * Created by chenmin on 2017/2/23.
 */

public class HttpSSLUtils {
    public static String CSR30 = "-----BEGIN CERTIFICATE-----\n" +
            "MIIC0DCCAjmgAwIBAgIJAJTZOR99ku0dMA0GCSqGSIb3DQEBBQUAMIGAMQswCQYD\n" +
            "VQQGEwJjbjERMA8GA1UECAwIemhlamlhbmcxETAPBgNVBAcMCGhhbmd6aG91MQ0w\n" +
            "CwYDVQQKDARoaGtqMQ0wCwYDVQQLDAR0ZWNoMQwwCgYDVQQDDANsZW8xHzAdBgkq\n" +
            "hkiG9w0BCQEWEG1mdHkxOTgwQHNpbmEuY20wHhcNMTYxMjIyMDI1MzE1WhcNMTcx\n" +
            "MjIyMDI1MzE1WjCBgDELMAkGA1UEBhMCY24xETAPBgNVBAgMCHpoZWppYW5nMREw\n" +
            "DwYDVQQHDAhoYW5nemhvdTENMAsGA1UECgwEaGhrajENMAsGA1UECwwEdGVjaDEM\n" +
            "MAoGA1UEAwwDbGVvMR8wHQYJKoZIhvcNAQkBFhBtZnR5MTk4MEBzaW5hLmNtMIGf\n" +
            "MA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDhGUGBgqL+vNUgvMvccBJqi3fg6pCG\n" +
            "wePN1ThAERAsC61xRfDNZh7SAWyJrKoGAGZuzzmQIexW+M/tUjrWNqmYYzQx4GR4\n" +
            "Dfl10OBj0t+iLlZFzKK7lj4TmD3w6J3TYkZu8Fh+34ILHNUv4++y/tc6PWYXT64L\n" +
            "9QFODYvl5rUC0wIDAQABo1AwTjAdBgNVHQ4EFgQUpcrQf1ERmlm6BtELBwQGtiI9\n" +
            "IvgwHwYDVR0jBBgwFoAUpcrQf1ERmlm6BtELBwQGtiI9IvgwDAYDVR0TBAUwAwEB\n" +
            "/zANBgkqhkiG9w0BAQUFAAOBgQDCqgJ63FoDg2eMZB/Aa/KyxTA5j1UK1DmBLUuA\n" +
            "rbAONdXqEobvygHe2d5sBAM2tXOxt0TjBmL+tG4iCAvZr0cK8xvccSgIHq6vjenp\n" +
            "ymIcpcKQ7xUykNg39mBF/1xEq5dBianbqAxulcFifHTGmKNCTge7cLpqQ3aYL/8m\n" +
            "oIEuiA==\n" +
            "-----END CERTIFICATE-----\n";

    public static void setOkHttpSSL(OkHttpClient.Builder builder, InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null,
                    trustManagerFactory.getTrustManagers(),
                    new SecureRandom());
            builder.sslSocketFactory(sslContext.getSocketFactory());
            builder.hostnameVerifier(new HostnameVerifier() {
                @SuppressLint("BadHostnameVerifier")
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
