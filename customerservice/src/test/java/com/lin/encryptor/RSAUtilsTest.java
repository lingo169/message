package com.lin.encryptor;

import com.lin.common.error.CustomRuntimeException;
import com.lin.common.utils.security.RSAUtils;
import com.lin.common.utils.security.SecuritySHA1Utils;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;

public class RSAUtilsTest {
    /**
     * 需要把秘钥对转换成PKCS12，参考keygen.md
     */
    public String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCuxSxyQueKDe00MWMUYj7El5GHXRiplsG3cgghwIyqmchlH+1QJ9Jm9cZ5OOR4C9+Z83/pQUr3ZAYvzt5JImso+ZJIDPEcJXZ2kv0Luc/W0ldWZtoDA6H9ePukhHSmeoIYB4ABOCJgh36hgkqYDPk63qWPy0NQUdUDZeZs9lcVUQIDAQAB";
    public String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAK7FLHJC54oN7TQxYxRiPsSXkYddGKmWwbdyCCHAjKqZyGUf7VAn0mb1xnk45HgL35nzf+lBSvdkBi/O3kkiayj5kkgM8RwldnaS/Qu5z9bSV1Zm2gMDof14+6SEdKZ6ghgHgAE4ImCHfqGCSpgM+TrepY/LQ1BR1QNl5mz2VxVRAgMBAAECgYAJuGOxczEnh4DC32BQWOp+yqNIcRAXFpBtoIT9Q5VPQ8BWh4EObTiRi2ffhB+I4bgs+tMJaR0/Ryvk6s+IJ8bn5/oB/WBXS68vyvje1Eb4vr7iHw5lKyA7pDDnB/dNoRNTNDZBw6AerkO3FekmheH6O36HcBzsskZXBT4eb26mMQJBANUR8wX+/ez/wbGL/QxJpI6vk74t6iNR7I+S5/JHMkC71CUPVl6pm/EKvBE61tvkL3+pO7LaErNTaDTubZfP7hUCQQDR+7v2RvuYBGdU/7cENLlprXVoF3fLftuTMd5uwayqpHagG4viijwHAjv+xoqMR6yp3vnM9IbJxHKOOofvIdVNAkEAu0g0qc+RWLERisL2YMeWSgjQPzjSdcs7uJMzJ0UnSbkPZqfNjhVdSYja9/YVB2DR009odvOxvkgQ732nfOo6ZQJAI/pq6TaeUxymC9VIqWrTPf0febdTqkVRH/ZSc02x5QcK4EH4BNfEkzrZxryD7qc1OWHgETLwiU+khSQYOGvmnQJAc/oETaayG2DYPnxIg3tNOF5uoLQACaJxNQ4F6UQfQw2xOyq/d2C0ylMHQjvN25Cg1af91aVn48XIKjNYwvhMHw==";

    @Test
    @Ignore
    public void publicEncryptAndprivateDecrypt() throws InvalidKeySpecException, NoSuchAlgorithmException {
        RSAPublicKey pub = RSAUtils.getPublicKey(publicKey);
        RSAPrivateKey pk = RSAUtils.getPrivateKey(privateKey);
        String srcData=" hello world";
        String pube = RSAUtils.publicEncrypt(srcData,pub);
        System.out.println(pube);
        String result = RSAUtils.privateDecrypt(srcData, pk);
        Assert.assertEquals(srcData,result);
    }

    @Test
    public void Sha1Test() throws Exception {
        String pass="123qwe";
        String s1=DigestUtils.sha1Hex(pass);
        String s2= SecuritySHA1Utils.shaEncode(pass);
        Assert.assertEquals(s1,s2);
    }

    @Test
    public void longpase() throws CustomRuntimeException {
//        String s = "2022030416083900000539";
        String s = "2022030415511600538";
        Long l = Long.parseLong(s);
        int a=2;
        int b=2;
        System.out.println(a^b);
        System.out.println(true^false);
        System.out.println(l);
    }
}
