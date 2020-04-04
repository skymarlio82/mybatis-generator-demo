
package com.wiz.test.demo.util;

import java.io.*;

public class MyConfig extends WXPayConfig {

    private byte[] certData;

    public MyConfig() throws Exception {
        String certPath = "/root/software/1544571151_20200329_cert/apiclient_cert.p12";
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    public String getAppID() {
        return "wxba3211abca2a188c";
    }

    public String getMchID() {
        return "1544571151";
    }

    public String getKey() {
        return "10c57d4598c54704873ec455722c9876";
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    @Override
    IWXPayDomain getWXPayDomain() {
        return WXPayDomainSimpleImpl.instance();
    }
}