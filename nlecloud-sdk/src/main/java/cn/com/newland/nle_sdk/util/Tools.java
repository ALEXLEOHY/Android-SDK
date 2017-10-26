package cn.com.newland.nle_sdk.util;

import java.lang.reflect.Field;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by marco on 2017/8/21.
 * 工具类
 */

public class Tools {

    public static ApiService buildService(String baseUrl) {
        return new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(baseUrl).build().create(ApiService.class);
    }

    public static String getObjectFieldMsg(Object object, int spaceNum) {
        if (object == null) {
            return "";
        }
        String spaceStr = "";
        for (int i = 0; i < spaceNum; i++) {
            spaceStr = spaceStr + "" + "\t";
        }
        Field[] fields = object.getClass().getDeclaredFields();
        StringBuffer stringBuffer = new StringBuffer();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                stringBuffer.append(spaceStr).append(field.getName()).append(" : ").append(field.get(object)).append("\n");
            } catch (IllegalAccessException ex) {
                return "";
            }

        }
        return String.valueOf(stringBuffer);
    }
}
