package com.zigerianos.jourtrip.utils;

import com.zigerianos.jourtrip.auth.AuthManager;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class HttpDefaultHeadersInterceptor implements Interceptor {

    private String mUserAgent;
    private AuthManager mAuthManager;

    public HttpDefaultHeadersInterceptor(String userAgent, AuthManager authManager) {
        mUserAgent = userAgent;
        mAuthManager = authManager;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        request = request.newBuilder()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("x-access-token", mAuthManager.getCurrentAccessToken())
                .header("User-Agent", mUserAgent)
                .build();

        return chain.proceed(request);
    }
}
