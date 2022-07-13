package com.example.dubaipractical.di

import com.example.dubaipractical.BuildConfig
import com.example.dubaipractical.api.ApiExceptions
import com.example.dubaipractical.api.WebServiceInterface
import com.example.dubaipractical.utils.Constant
import com.example.dubaipractical.utils.PrefUtils
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.URISyntaxException
import javax.inject.Singleton


/**
 * Network Manger Class all the API Calls will be made by this class
 */
@Module
class NetworkModule {

    /**
     * final variable having Base Server URL of project
     * */
    private val mBaseUrl = BuildConfig.BASE_URL

    /**
     * Gives OkHttpClient
     * @return object of [OkHttpClient]
     * */
    @Singleton
    @Provides
    fun providesOkHttp(prefUtils: PrefUtils): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor(getChangeURLInterceptor(prefUtils)).build()

    /**
     * @return Object of [WebServiceInterface]
     * */
    @Singleton
    @Provides
    fun provideApiService(oktHttpClient: OkHttpClient): WebServiceInterface =
        Retrofit.Builder().client(oktHttpClient)
            .baseUrl(mBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WebServiceInterface::class.java)

    @Provides
    @Singleton
    fun getChangeURLInterceptor(prefUtils: PrefUtils): RequestTokenInterceptor {
        return RequestTokenInterceptor(prefUtils)
    }

    /**
     * To Add Authorization token and proceeds with API request
     */
    class RequestTokenInterceptor(private val prefUtils: PrefUtils) : Interceptor {

        var host = BuildConfig.BASE_URL.toHttpUrlOrNull()

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
//            host = BuildConfig.BUYER_BASE_URL.toHttpUrlOrNull()
//            if (prefUtils.isBuyerBaseUrl()) {
//                host = BuildConfig.BUYER_BASE_URL.toHttpUrlOrNull()
//            } else {
//                host = BuildConfig.SELLER_BASE_URL.toHttpUrlOrNull()
//            }
            val encodedPath = chain.request().url.encodedPath
            var request = chain.request()
            try {
                return if (encodedPath.contains("login") || encodedPath == "/") {
                    chain.proceed(chain.request())
                } else {
                    request = chain.request()
//                    if (prefUtils.getAuthToken() != null) {
//                        request = request.newBuilder()
//                            .addHeader(
//                                Constant.HEADER_AUTHORIZATION,
//                                Constant.TOKEN + " " + prefUtils.getAuthToken()
//                            ).build()
//                    }
                    //Set Buyer and Seller url Runtime START
                    if (host != null) {
                        var newUrl: HttpUrl? = null
                        try {
                            newUrl = request.url.newBuilder()
                                .scheme(host!!.scheme)
                                .host(host!!.toUrl().toURI().host)
                                .build()
                        } catch (e: URISyntaxException) {
                            e.printStackTrace()
                        }
                        assert(newUrl != null)
                        request = request.newBuilder()
                            .url(newUrl!!)
                            .build()
                    } else {
                        throw ApiExceptions("host is null in Network module")
                    }
                    //Set Buyer and Seller url Runtime END
                    chain.proceed(request)
                }
            } catch (e: SocketTimeoutException) {
                throw ApiExceptions(Constant.SOCKET_TIMEOUT_EXCEPTIONS)
            } catch (e: Exception) {
                throw ApiExceptions(e.message!!)
            }
            return chain.proceed(request)
        }
    }
}