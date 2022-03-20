package com.lin.encryptor;

import com.lin.EncryptorUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
public class EncryptorTest {

    @Test
    public void EncryptorforEncrypt(){
        String result = EncryptorUtils.encrypt("lingo","abcd");
        System.out.println(result);
        String d=EncryptorUtils.decrypt("lingo",result);
        Assert.assertEquals(d,"abcd");
    }
}
