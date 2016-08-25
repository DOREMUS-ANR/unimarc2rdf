package org.doremus.marc2rdf.main;

import org.apache.http.client.utils.URIBuilder;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class ConstructURI {
  private static URIBuilder builder = new URIBuilder().setScheme("http").setHost("data.doremus.org");

  public static URI build(String db, String classCode, String className, String identifier) throws URISyntaxException, UnsupportedEncodingException, NoSuchAlgorithmException {
    String seed = db + classCode + identifier;
   return builder.setPath("/" + className + "/" + classCode + "/" + generateUUID(seed)).build();
  }

  public static URI build(String db, String classCode, String className, String identifier, String suffix) throws URISyntaxException, UnsupportedEncodingException, NoSuchAlgorithmException {
    String seed = db + classCode + identifier;
    return builder.setPath("/" + className + "/" + classCode + "/" + generateUUID(seed) + '/' + suffix).build();
  }

  private static String generateUUID(String seed) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    // source: https://gist.github.com/giusepperizzo/630d32cc473069497ac1
    String hash = DatatypeConverter.printHexBinary(MessageDigest.getInstance("SHA-1").digest(seed.getBytes("UTF-8")));
    UUID uuid = UUID.nameUUIDFromBytes(hash.getBytes());
    return uuid.toString();
  }
}
