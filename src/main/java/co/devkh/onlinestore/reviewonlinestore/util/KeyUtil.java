package co.devkh.onlinestore.reviewonlinestore.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

@Component
@Slf4j
public class KeyUtil {
    @Value("${access-token.private}")
    private String accessTokenPrivateKeyPath;
    @Value("${access-token.public}")
    private String accessTokenPublicKeyPath;
    @Value("${refresh-token.private}")
    private String refreshTokenPrivateKeyPath;
    @Value("${refresh-token.public}")
    private String refreshTokenPublicKeyPath;

    private KeyPair accessTokenKeyPair;
    private KeyPair refreshTokenKeyPair;

    public KeyPair getAccessTokenKeyPair(){
        if (Objects.isNull(accessTokenKeyPair)){
            accessTokenKeyPair = getKeyPair(accessTokenPublicKeyPath,accessTokenPrivateKeyPath);
        }
        return accessTokenKeyPair;
    }
    public KeyPair getRefreshTokenKeyPair() {

        if (Objects.isNull(refreshTokenKeyPair)) {
            refreshTokenKeyPair = getKeyPair(refreshTokenPublicKeyPath, refreshTokenPrivateKeyPath);
        }

        return refreshTokenKeyPair;
    }
    private KeyPair getKeyPair(String publicKeyPath,String privateKeyPath){
        KeyPair keyPair;

        // Create File form Path File
        File publicKeyFile = new File(publicKeyPath);
        File privateKeyFile = new File(privateKeyPath);

        // Check pairs of path exist or not
        if (publicKeyFile.exists() && privateKeyFile.exists()){
            // if true
            log.info("loading keys from file : {} , {}",publicKeyPath,privateKeyPath);
            try {
                // remark algorithm we use to create KeyPair
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");

                // use datatype byte for store file when we read all the bytes from a file from [publicKeyFile.toPath()]
                byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());

                // Creates a new X509EncodedKeySpec with the given encoded key. this algorithm use for encode [publicKeyBytes]
                EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
                PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

                // use datatype byte for store file when we read all the bytes from a file from [privateKeyBytes]
                byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());

                // Creates a new PKCS8EncodedKeySpec with the given encoded key. this algorithm use for encode [privateKeyBytes]
                PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
                PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

                keyPair = new KeyPair(publicKey, privateKey);

                return keyPair;

            } catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }
        }else {
              /*if (Arrays.asList(environment.getActiveProfiles()).contains("prod")) {
                throw new RuntimeException("Public and private keys don't exist.");
            }*/
        }

        File directory = new File("keys");
        log.info("directory exists : {}",directory.exists());
        log.info("directory absolute-path : {}",directory.getAbsolutePath());
        if (!directory.exists()){
            directory.mkdirs();
        }

        try{
            log.info("Generating new public and private keys : {} , {}",publicKeyPath,privateKeyPath);

            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();

            try (FileOutputStream fos = new FileOutputStream(publicKeyPath)) {
                log.info("FOS: {}", fos);
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyPair.getPublic().getEncoded());
                fos.write(keySpec.getEncoded());
            }

            try (FileOutputStream fos = new FileOutputStream(privateKeyPath)) {
                log.info("FOS: {}", fos);
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyPair.getPrivate().getEncoded());
                fos.write(keySpec.getEncoded());
            }

        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }

        return keyPair;
    }
    public RSAPublicKey getAccessTokenPublicKey() {
        return (RSAPublicKey) getAccessTokenKeyPair().getPublic();
    }

    public RSAPrivateKey getAccessTokenPrivateKey() {
        return (RSAPrivateKey) getAccessTokenKeyPair().getPrivate();
    }

    public RSAPublicKey getRefreshTokenPublicKey() {
        return (RSAPublicKey) getRefreshTokenKeyPair().getPublic();
    }

    public RSAPrivateKey getRefreshTokenPrivateKey() {
        return (RSAPrivateKey) getRefreshTokenKeyPair().getPrivate();
    }
}
