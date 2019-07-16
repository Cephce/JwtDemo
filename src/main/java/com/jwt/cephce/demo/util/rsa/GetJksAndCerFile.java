package com.jwt.cephce.demo.util.rsa;

import org.bouncycastle.util.encoders.Base64;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Enumeration;

/**
 * @author: cephce
 * @date: 2019/10/15 15:00
 *
 * 生成jks文件和cer证书文件，以及jks文件和cer文件的读取
 * https://blog.csdn.net/S_First/article/details/78701156
 *
 * 生成jks文件的keytool命令参数：
 * -alias <alias>                  要处理的条目的别名
 * -keyalg <keyalg>                密钥算法名称
 * -keysize <keysize>              密钥位大小
 * -sigalg <sigalg>                签名算法名称
 * -destalias <destalias>          目标别名
 * -dname <dname>                  唯一判别名
 * -startdate <startdate>          证书有效期开始日期/时间
 * -ext <value>                    X.509 扩展
 * -validity <valDays>             有效天数
 * -keypass <arg>                  密钥口令
 * -keystore <keystore>            密钥库名称
 * -storepass <arg>                密钥库口令
 * -storetype <storetype>          密钥库类型
 * -providername <providername>    提供方名称
 * -providerclass <providerclass>  提供方类名
 * -providerarg <arg>              提供方参数
 * -providerpath <pathlist>        提供方类路径
 * -v                              详细输出
 * -protected                      通过受保护的机制的口令
 * ---------------------
 *
 * 生成cer证书文件keytool命令参数：
 * -certreq            生成证书请求
 * -changealias        更改条目的别名
 * -delete             删除条目
 * -exportcert         导出证书
 * -genkeypair         生成密钥对
 * -genseckey          生成密钥
 * -gencert            根据证书请求生成证书
 * -importcert         导入证书或证书链
 * -importpass         导入口令
 * -importkeystore     从其他密钥库导入一个或所有条目
 * -keypasswd          更改条目的密钥口令
 * -list               列出密钥库中的条目
 * -printcert          打印证书内容
 * -printcertreq       打印证书请求的内容
 * -printcrl           打印 CRL 文件的内容
 * -storepasswd        更改密钥库的存储口令
 * ---------------------
 */
public class GetJksAndCerFile {

    public static void main(String[] args) {
        buildKeyAndSaveToJksFile();
        exportCerFile();
        try {
            readJks();
            readCer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void executeCommand(String[] arstringCommand) {
        try {
            Runtime.getRuntime().exec(arstringCommand);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    //生成密钥并保存到jks文件
    public static void buildKeyAndSaveToJksFile() {
        String[] command = new String[]{
                "cmd ",
                "/k",
                "start", // cmd Shell命令
                "keytool",
                "-genkeypair", //表示生成密钥
                "-alias", //要处理的条目的别名（jks文件别名）
                "sun",
                "-keyalg", //密钥算法名称(如 RSA DSA（默认是DSA）)
                "RSA",
                "-keysize",//密钥位大小(长度)
                "1024",
                "-sigalg", //签名算法名称
                "SHA1withRSA",
                "-dname",// 唯一判别名,CN=(名字与姓氏), OU=(组织单位名称), O=(组织名称), L=(城市或区域名称),
                // ST=(州或省份名称), C=(单位的两字母国家代码)"
                "CN=(张三), OU=(人民单位), O=(人民组织), L=(广州), ST=(广东), C=(中国)",
                "-validity", // 有效天数
                "36500",
                "-keypass",// 密钥口令(私钥的密码)
                "123456",
                "-keystore", //密钥库名称(jks文件路径)
                "f:/demo.jks",
                "-storepass", // 密钥库口令(jks文件的密码)
                "123456",
                "-v"// 详细输出（秘钥库中证书的详细信息）
        };
        executeCommand(command);
    }


    //从jks文件中导出证书文件
    public static void exportCerFile() {
        String[] command = new String[]{
                "cmd ", "/k",
                "start", // cmd Shell命令


                "keytool",
                "-exportcert", // - export指定为导出操作
                "-alias", // -alias指定别名，这里是ss
                "sun",
                "-keystore", // -keystore指定keystore文件，这里是d:/demo.keystore
                "f:/demo.jks",
                "-rfc",
                "-file",//-file指向导出路径
                "f:/demo.cer",
                "-storepass",// 指定密钥库的密码
                "123456"
        };
        executeCommand(command);


    }


    //读取jks文件获取公、私钥
    public static void readJks() throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(new FileInputStream("f:\\demo.jks"), "123456".toCharArray());
        Enumeration<String> aliases = keyStore.aliases();
        String alias = null;
        while (aliases.hasMoreElements()) {
            alias = aliases.nextElement();
        }
        System.out.println("jks文件别名是：" + alias);
        PrivateKey key = (PrivateKey) keyStore.getKey(alias, "123456".toCharArray());
        System.out.println("jks文件中的私钥是：" + new String(Base64.encode(key.getEncoded())));
        Certificate certificate = keyStore.getCertificate(alias);
        PublicKey publicKey = certificate.getPublicKey();
        System.out.println("jks文件中的公钥:" + new String(Base64.encode(publicKey.getEncoded())));
    }


    //读取证书文件获取公钥
    public static void readCer() throws Exception {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Certificate certificate =
                certificateFactory.generateCertificate(new FileInputStream("f:\\demo.cer"));
        PublicKey publicKey = certificate.getPublicKey();
        System.out.println("证书中的公钥:" + new String(Base64.encode(publicKey.getEncoded())));
    }

}
