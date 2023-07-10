package com.efernandeza.weather.data.openweather

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val originalHttpUrl: HttpUrl = original.url

        /* TODO : We would rather not commit the appid to git and instead have a secret management
            service inject it in an environment variable, which is read by our build,
            in our CI machines. */
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("appid", "8f508f9413e842392a99a965024973cd")
            .build()

        val requestBuilder: Request.Builder = original.newBuilder()
            .url(url)

        return chain.proceed(requestBuilder.build())
    }
}
