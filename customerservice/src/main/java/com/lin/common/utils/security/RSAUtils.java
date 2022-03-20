package com.lin.common.utils.security;

import org.apache.commons.io.IOUtils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


public class RSAUtils {
    public static final String CHARSET = "UTF-8";
    public static final String RSA_ALGORITHM = "RSA";


    public static Map<String, String> createKeys(int keySize) {
        //为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
        }

        //初始化KeyPairGenerator对象,密钥长度
        kpg.initialize(keySize);
        //生成密匙对
        KeyPair keyPair = kpg.generateKeyPair();
        //得到公钥
        Key publicKey = keyPair.getPublic();
        String publicKeyStr = Base64.getUrlEncoder().encodeToString(publicKey.getEncoded());
        //String publicKeyStr = Base64.encodeBase64URLSafeString(publicKey.getEncoded());
        //得到私钥
        Key privateKey = keyPair.getPrivate();
        System.out.println("私钥格式:" + privateKey.getFormat());
        //String privateKeyStr = Base64.encodeBase64URLSafeString(privateKey.getEncoded());
        String privateKeyStr = Base64.getUrlEncoder().encodeToString(privateKey.getEncoded());
        Map<String, String> keyPairMap = new HashMap<String, String>();
        keyPairMap.put("publicKey", publicKeyStr);
        keyPairMap.put("privateKey", privateKeyStr);

        return keyPairMap;
    }

    /**
     * 得到公钥
     *
     * @param publicKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        return key;
    }

    /**
     * 得到私钥
     *
     * @param privateKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        return key;
    }

    /**
     * 公钥加密
     *
     * @param data
     * @param publicKey
     * @return
     */
    public static String publicEncrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64.getUrlEncoder().encodeToString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), publicKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥解密
     *
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateDecrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.getDecoder().decode(data), privateKey.getModulus().bitLength()), CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥加密
     *
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateEncrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return Base64.getUrlEncoder().encodeToString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), privateKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 公钥解密
     *
     * @param data
     * @param publicKey
     * @return
     */

    public static String publicDecrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.getDecoder().decode(data), publicKey.getModulus().bitLength()), CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {
        int maxBlock = 0;
        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            while (datas.length > offSet) {
                if (datas.length - offSet > maxBlock) {
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
//				byte[] resultDatas = out.toByteArray();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
        /////////////////////////////public key
        String publicKeyB64 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCuxSxyQueKDe00MWMUYj7El5GHXRiplsG3cgghwIyqmchlH+1QJ9Jm9cZ5OOR4C9+Z83/pQUr3ZAYvzt5JImso+ZJIDPEcJXZ2kv0Luc/W0ldWZtoDA6H9ePukhHSmeoIYB4ABOCJgh36hgkqYDPk63qWPy0NQUdUDZeZs9lcVUQIDAQAB";

        String en = publicEncrypt("lingo", RSAUtils.getPublicKey(publicKeyB64));
        System.out.println(en);

        /////////////////////////private key
        String privateB64="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAK7FLHJC54oN7TQxYxRiPsSXkYddGKmWwbdyCCHAjKqZyGUf7VAn0mb1xnk45HgL35nzf+lBSvdkBi/O3kkiayj5kkgM8RwldnaS/Qu5z9bSV1Zm2gMDof14+6SEdKZ6ghgHgAE4ImCHfqGCSpgM+TrepY/LQ1BR1QNl5mz2VxVRAgMBAAECgYAJuGOxczEnh4DC32BQWOp+yqNIcRAXFpBtoIT9Q5VPQ8BWh4EObTiRi2ffhB+I4bgs+tMJaR0/Ryvk6s+IJ8bn5/oB/WBXS68vyvje1Eb4vr7iHw5lKyA7pDDnB/dNoRNTNDZBw6AerkO3FekmheH6O36HcBzsskZXBT4eb26mMQJBANUR8wX+/ez/wbGL/QxJpI6vk74t6iNR7I+S5/JHMkC71CUPVl6pm/EKvBE61tvkL3+pO7LaErNTaDTubZfP7hUCQQDR+7v2RvuYBGdU/7cENLlprXVoF3fLftuTMd5uwayqpHagG4viijwHAjv+xoqMR6yp3vnM9IbJxHKOOofvIdVNAkEAu0g0qc+RWLERisL2YMeWSgjQPzjSdcs7uJMzJ0UnSbkPZqfNjhVdSYja9/YVB2DR009odvOxvkgQ732nfOo6ZQJAI/pq6TaeUxymC9VIqWrTPf0febdTqkVRH/ZSc02x5QcK4EH4BNfEkzrZxryD7qc1OWHgETLwiU+khSQYOGvmnQJAc/oETaayG2DYPnxIg3tNOF5uoLQACaJxNQ4F6UQfQw2xOyq/d2C0ylMHQjvN25Cg1af91aVn48XIKjNYwvhMHw==";


        System.out.println(privateDecrypt(en, RSAUtils.getPrivateKey(privateB64)));
    }
}
