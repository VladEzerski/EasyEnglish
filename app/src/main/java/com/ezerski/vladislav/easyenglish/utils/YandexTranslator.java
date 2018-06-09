package com.ezerski.vladislav.easyenglish.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class YandexTranslator {

    private static final String YANDEX_API_KEY =
            "trnsl.1.1.20180605T225648Z.803ae66620c7c442.d8d2574b398ddb8af1d68813f3b43518095047f4";

    public YandexTranslator() {
    }

    public String getTranslatedWord(String textForTranslating, String languagePair) {
        String jsonString;
        try {
            String requestUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate?key="
                    + YANDEX_API_KEY + "&text=" + textForTranslating + "&lang=" + languagePair;
            URL yandextranslateURL = new URL(requestUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) yandextranslateURL.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader buff = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder jsonStringBuilder = new StringBuilder();
            while ((jsonString = buff.readLine()) != null){
                jsonStringBuilder.append(jsonString).append("/n");
            }
            buff.close();
            inputStream.close();
            urlConnection.disconnect();
            String resultString = jsonStringBuilder.toString().trim();
            //Getting the characters between [ and ]
            resultString = resultString.substring(resultString.indexOf('[')+1);
            resultString = resultString.substring(0,resultString.indexOf("]"));
            //Getting the characters between " and "
            resultString = resultString.substring(resultString.indexOf("\"")+1);
            resultString = resultString.substring(0,resultString.indexOf("\""));
            return resultString;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}