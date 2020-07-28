package traderHelp;


import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class HttpTST {
    static int part = 0;
    private static String T_STORE = "D:\\Git\\ISModule\\JCSP\\TSRSA.jks";
    private static String K_STORE = "D:\\Git\\ISModule\\JCSP\\RSA.jks";

    public static String password = "P@ssw0rd";
    public static String url = "https://192.168.210.33:2443/ActiveMQ/ICLTransportJmsWebService.asmx";
    public static void main(String[] args) {

        System.setProperty("javax.net.ssl.trustStore",T_STORE);
        System.setProperty("javax.net.ssl.trustStorePassword",password);
        System.setProperty("javax.net.ssl.keyStore",K_STORE);
        System.setProperty("javax.net.ssl.keyStorePassword",password);//
        System.setProperty("javax.net.ssl.keyStoreType","JKS");
        System.setProperty("javax.net.ssl.trustStoreType","JKS");

        HttpClient httpClient = HttpClients.createDefault();
        String msg = "<soap:Envelope  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"  xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ReceiveTextMessage xmlns=\"http://gpbh2h.icl.ru/\"><clientId xmlns=\"\">test2</clientId><queueName xmlns=\"\">OUT.CLIENT1.MESSAGES</queueName></ReceiveTextMessage></soap:Body></soap:Envelope>";

        HttpPost httppost = new HttpPost(url);

        try {
            httpClient.execute(httppost);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
