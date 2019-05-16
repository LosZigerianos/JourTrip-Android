package com.zigerianos.jourtrip.utils;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class HttpDefaultHeadersInterceptor implements Interceptor {

    private String mUserAgent;

    public HttpDefaultHeadersInterceptor(String userAgent) {
        mUserAgent = userAgent;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        request = request.newBuilder()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("User-Agent", mUserAgent)
                .build();

        return chain.proceed(request);
    }
}
