package com.bsh.projectwemeet.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

public class NCloudUtil {
    //     문자로 인증 보내는것
    private static final String URL_PREFIX = "";
    private static final String ACCESS_KEY = "";
    private static final String SECRET_KEY = "";
    // 인증키는 Ncloud 홈페이지 마이페이지 인증키에서 가져오기
//    시크릿 키는 (보기) 해당란의 내용을 넣기
    private static final String SERVICE_ID = "ncp:sms:kr:307475261582:portfolio";
// Ncloud 홈페이지 콘솔 Simple & Easy Notification Service 의 project 에서 생성한 protfolio에서 서비스 id 복사하기

    private static String generateSignature(String requestMethod, String requestUrl, long timestamp) {
//        requestUrl = requestUrl.replace(NCloudUtil.URL_PREFIX, "");
//        28번 라인은 도메인을 없애는 코드
        String signature = String.format("%s %s\n%d\n%s",
                requestMethod,
                requestUrl.replace(NCloudUtil.URL_PREFIX, ""),
                timestamp,
                NCloudUtil.ACCESS_KEY);
        SecretKeySpec spec = new SecretKeySpec(NCloudUtil.SECRET_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
//        HmacSHA256은 알고리즘을 가리킨다.
        try{
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(spec);
            byte[] hmacBytes = mac.doFinal(signature.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hmacBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException e){
//            getInstace와 inti 때문에  다음과 같이 한번에 예외 처리를 하겠다.
            throw new RuntimeException(e);
        }
    }

    //    아래 코드는 http통신을 하기 위한 코드이다.
    //                             받는사람      내용
    public static void sendSms(String to, String content) {
        final String requestMethod = "POST";
        final String requestUrl = String.format("%s/sms/v2/services/%s/messages",
                NCloudUtil.URL_PREFIX,
                NCloudUtil.SERVICE_ID);
//        안의 홈페이지는
        final JSONObject requestBody = new JSONObject(){{
            put("type", "SMS");
            put("contentType", "COMM");
//            put("countryCode", "82");
            put("from", "");
            put("content", content);
            put("messages", new JSONArray() {{
                put(new JSONObject(){{
                    put("to", to);
                }});
            }});

        }};
        try {
            long timestamp = new Date().getTime();
            String signature = NCloudUtil.generateSignature(requestMethod, requestUrl, timestamp);

            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestMethod);
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setRequestProperty("x-ncp-apigw-timestamp", String.valueOf(timestamp));
            connection.setRequestProperty("x-ncp-iam-access-key", NCloudUtil.ACCESS_KEY);
            connection.setRequestProperty("x-ncp-apigw-signature-v2", signature);
            connection.setDoOutput(true);

            try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()))){
                writer.write(requestBody.toString());
                writer.flush();
            }
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
                String response = reader.readLine();
                System.out.println(response);
            }
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    private NCloudUtil() {
        super();
    }
}
