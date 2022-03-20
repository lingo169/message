创建密钥对JKS格式keystore：
keytool -genkey -v -alias test -keyalg RSA -keystore test.jks
将JKS格式keystore转换成PKCS12证书文件：
keytool -importkeystore -srckeystore test.jks -destkeystore test.p12 -srcstoretype JKS -deststoretype PKCS12
使用OpenSSL工具从PKCS12证书文件导出密钥对：
openssl pkcs12 -in test.p12 -nocerts -nodes -out test.key
从密钥对中提取出公钥：
openssl rsa -in test.key -pubout -out test_public.pem
拿到公钥test_public.pem后，在cat test_public.pem查看这个公钥内容，内容是base64格式的，这个公钥就是供在前端用jsencrypt对登录密码等参数进行RSA加密用的，看下test_public.pem内容：（这里复制github上的过来，读者可以自行尝试）

-----BEGIN RSA PUBLIC KEY-----
MIICXQIBAAKBgQDlOJu6TyygqxfWT7eLtGDwajtNFOb9I5XRb6khyfD1Yt3YiCgQ
WMNW649887VGJiGr/L5i2osbl8C9+WJTeucF+S76xFxdU6jE0NQ+Z+zEdhUTooNR
aY5nZiu5PgDB0ED/ZKBUSLKL7eibMxZtMlUDHjm4gwQco1KRMDSmXSMkDwIDAQAB
AoGAfY9LpnuWK5Bs50UVep5c93SJdUi82u7yMx4iHFMc/Z2hfenfYEzu+57fI4fv
xTQ//5DbzRR/XKb8ulNv6+CHyPF31xk7YOBfkGI8qjLoq06V+FyBfDSwL8KbLyeH
m7KUZnLNQbk8yGLzB3iYKkRHlmUanQGaNMIJziWOkN+N9dECQQD0ONYRNZeuM8zd
-----END RSA PUBLIC KEY-----
接下来用一段简单的前端代码演示下jsencrypt的使用：

<!doctype html>
<html>
  <head>
    <title>jsencrypt使用</title>
    <script src="./jquery.min.js"></script>
    <script src="./jsencrypt.min.js"></script>
    <script type="text/javascript">
        $(function() {
            $('submit').click(function() {
                var data = [];
                data['username']= $('#username').val();
                data['passwd']= $('#passwd').val();
      
                var publickey = $('#publickey').val();
                encryptSend('./Jsencrypt.do', data, publickey);  // Jsencrypt.do对应服务端处理地址
            });
        });

        // 使用jsencrypt库加密前端参数
        function encryptSend(url, data, publicKey){
            var jsencrypt = new JSEncrypt();
            jsencrypt.setPublicKey(publicKey);
            // enData用来装载加密后的数据
            var enData = new Object();
            // 将参数用jsencrypt加密后赋给enData
            for(var key in data){
                enData[key] = jsencrypt.encrypt(data[key]);
            }
          
            $.ajax({
                url: url,
                type: 'post',
                data: enData,
                dataType: 'json',
                success: function (data) {                    
                    console.info(data);
                },
                error: function (xhr) {
                    console.error('Something went wrong....');
                }
            });
        }
    </script>
  </head>
  <body>
    <label for="publickey">Public Key</label><br/>
    <textarea id="publickey" rows="20" cols="60">
        -----BEGIN RSA PUBLIC KEY-----
        MIICXQIBAAKBgQDlOJu6TyygqxfWT7eLtGDwajtNFOb9I5XRb6khyfD1Yt3YiCgQ
        WMNW649887VGJiGr/L5i2osbl8C9+WJTeucF+S76xFxdU6jE0NQ+Z+zEdhUTooNR
        aY5nZiu5PgDB0ED/ZKBUSLKL7eibMxZtMlUDHjm4gwQco1KRMDSmXSMkDwIDAQAB
        AoGAfY9LpnuWK5Bs50UVep5c93SJdUi82u7yMx4iHFMc/Z2hfenfYEzu+57fI4fv
        xTQ//5DbzRR/XKb8ulNv6+CHyPF31xk7YOBfkGI8qjLoq06V+FyBfDSwL8KbLyeH
        m7KUZnLNQbk8yGLzB3iYKkRHlmUanQGaNMIJziWOkN+N9dECQQD0ONYRNZeuM8zd
        -----END RSA PUBLIC KEY-----
    </textarea>
    <br/>
    <label for="input">jsencrypt:</label><br/>
    name:<input id="username" name="username" type="text"></input><br/>
    password:<input id="passwd" name="passwd" type="password"></input><br/>
    <input id="submit" type="button" value="submit" />
  </body>
</html>
下面演示服务端解密过程，以Java为例。

import java.io.FileInputStream;
import java.security.KeyStore;
import javax.crypto.Cipher;
import org.apache.log4j.Logger;
import sun.misc.BASE64Decoder;

public class JsencryptTest {

    private static final Logger logger = Logger.getLogger(JsencryptTest.class);
    
    public static void main(String[] args) {
        byte[] bs = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            // encodePwd是前端密码使用公钥通过jscencrypt进行加密后得到的（这里也是复制github上的举例）
            String encodePwd = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQ"
                                + "DlOJu6TyygqxfWT7eLtGDwajtNFOb9I5XRb6"
                                + "khyfD1Yt3YiCgQWMNW649887VGJiGr/L5i2o"
                                + "sbl8C9+WJTeucF+S76xFxdU6jE0NQ+Z+zEdh"
                                + "UTooNRaY5nZiu5PgDB0ED/ZKBUSLKL7eibMx"
                                + "ZtMlUDHjm4gwQco1KRMDSmXSMkDwIDAQAB";
            bs = decoder.decodeBuffer(encodePwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream("D:/jsencrypt/test.jks"), "123456".toCharArray());
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, keyStore.getKey("test", "123456".toCharArray()));
            logger.info(new String(cipher.doFinal(bs)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

作者：weknow
链接：https://www.jianshu.com/p/3da3d81e1572
来源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。