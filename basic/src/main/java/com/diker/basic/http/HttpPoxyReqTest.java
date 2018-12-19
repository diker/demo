package com.diker.basic.http;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import sun.misc.IOUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author diker
 * @since 2018/11/16
 */
public class HttpPoxyReqTest {

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClients.custom().setProxy(HttpHost.create("127.0.0.1:80")).build();
//        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://127.0.0.1:8772/test/statusq");

        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("httpStatus", "404"));
        UrlEncodedFormEntity httpEntity = new UrlEncodedFormEntity(list);
        httpPost.setEntity(httpEntity);

        CloseableHttpResponse response = httpClient.execute(httpPost);
        System.out.println(response.getStatusLine());
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

}
