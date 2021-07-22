package com.zzl.defineview.interceptor;

import android.text.TextUtils;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * okhttp拦截器
 * Created by zhenglin.zhu on 2021/7/10.
 */
public class CustomHttpLogInterceptor implements Interceptor {
    private static final String TAG = CustomHttpLogInterceptor.class.getSimpleName();

    /**
     * 不需要打印的请求头
     */
    private static HashMap<String, String> headerIgnoreMap = new HashMap<>();

    static {
        headerIgnoreMap.put("Host", "");
        headerIgnoreMap.put("Connection", "");
        headerIgnoreMap.put("Accept-Encoding", "");
    }

    private Level level = Level.NONE;
    /**
     * 1M的大小
     */
    private int MAX_BODY_LENGTH = 1 * 1024 * 1024;

    public enum Level {
        /**
         * No logs.
         */
        NONE,

        /**
         * Logs request and response lines.
         * <p>
         * Example:
         * ```
         * --> POST /greeting http/1.1 (3-byte body)
         * <p>
         * <-- 200 OK (22ms, 6-byte body)
         * ```
         */
        BASIC,

        /**
         * Logs request and response lines and their respective headers.
         * <p>
         * Example:
         * ```
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * --> END POST
         * <p>
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <-- END HTTP
         * ```
         */
        HEADERS, //请求和响应的header

        /**
         * Logs request and response lines and their respective headers and bodies (if present).
         * <p>
         * Example:
         * ```
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * <p>
         * Hi?
         * --> END POST
         * <p>
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <p>
         * Hello!
         * <-- END HTTP
         * ```
         */
        BODY
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void setMaxBodyLength(int maxBodyLength) {
        MAX_BODY_LENGTH = maxBodyLength;
    }

    protected void log(String message) {
        Log.d(TAG, message);
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Level level = this.level;
        log("level=" + level);
        Response response=null;
        String url = request.url().toString();
        Headers requestHeaders;
        Headers responseHeaders;
        switch (level) {
            case NONE:
                response = chain.proceed(request);
                return response;
            case HEADERS:
                //请求行url
                log("request method=" + request.method() + " ,url=" + url);
                //请求headers
                requestHeaders = request.headers();
                if (null != requestHeaders) {
                    for (int i = 0, count = requestHeaders.size(); i < count; i++) {
                        log("request headers=" + requestHeaders.name(i) + ": " + requestHeaders.value(i));
                    }
                }
                //响应headers
                response = chain.proceed(request);
                if (response != null) {
                    responseHeaders = response.headers();
                    if (responseHeaders != null) {
                        for (int i = 0; i < responseHeaders.size(); i++) {
                            Log.d(TAG, "response headers=" + responseHeaders.name(i) + ": " + responseHeaders.name(i));
                        }
                    }
                } else {
                    Log.d(TAG, "intercept response is null");
                }
                return response;
            case BODY:
                //请求行url
                log("request method=" + request.method() + " ,url=" + url);
                //请求体
                RequestBody requestBody = request.body();
                String paramString = readRequestParamString(requestBody);
                log("intercept requestBody:" + paramString);
                //响应体
                response = chain.proceed(request);
                String s = response.peekBody(MAX_BODY_LENGTH).toString();
                Log.d(TAG, "intercept responseBody=" + s);
                return response;
            case BASIC://请求行，响应体

                break;
        }
        return response;
    }

    /**
     * 只打印text，application/json这两种mediaType
     *
     * @param mediaType
     * @return
     */
    private boolean isPlainText(MediaType mediaType) {
        if (null != mediaType) {
            String mediaTypeString = (null != mediaType ? mediaType.toString() : null);
            if (!TextUtils.isEmpty(mediaTypeString)) {
                mediaTypeString = mediaTypeString.toLowerCase();
                if (mediaTypeString.contains("text") || mediaTypeString.contains("application/json")) {
                    return true;
                }
            }
        }
        return false;
    }

    private String readRequestParamString(RequestBody requestBody) {
        String paramString;
        if (requestBody instanceof MultipartBody) {//判断是否有文件
            StringBuilder sb = new StringBuilder();
            MultipartBody body = (MultipartBody) requestBody;
            List<MultipartBody.Part> parts = body.parts();
            RequestBody partBody;
            for (int i = 0, size = parts.size(); i < size; i++) {
                partBody = parts.get(i).body();
                if (null != partBody) {
                    if (sb.length() > 0) {
                        sb.append(",");
                    }
                    if (isPlainText(partBody.contentType())) {
                        sb.append(readContent(partBody));
                    } else {
                        sb.append("other-param-type=").append(partBody.contentType());
                    }
                }
            }
            paramString = sb.toString();
        } else {
            paramString = readContent(requestBody);
        }
        return paramString;
    }

    private String readContent(Response response) {
        if (response == null) {
            return "";
        }

        try {
            return response.peekBody(Long.MAX_VALUE).string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String readContent(RequestBody body) {
        if (body == null) {
            return "";
        }
        Buffer buffer = new Buffer();
        try {
            //小于2m
            if (body.contentLength() <= 2 * 1024 * 1024) {
                body.writeTo(buffer);
            } else {
                return "content is more than 2M";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.readUtf8();
    }

}
