package com.e.skiapp.network

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.*
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.*
import javax.net.ssl.*


class RetrofitClient {

    companion object {
        private val BASE_URL = "https://10.0.2.2:8443/"

        private var retrofit: Retrofit? = null

        fun getInstance(context: Context): Retrofit? {
            if (retrofit == null) {
                retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(getUnsafeOkHttpClient()).build()
            }
            return retrofit
        }

        private fun getUnsafeOkHttpClient(): OkHttpClient? {
            return try {
                val trustAllCerts = arrayOf<TrustManager>(
                        object : X509TrustManager {
                            override fun checkClientTrusted(
                                    chain: Array<X509Certificate?>?,
                                    authType: String?
                            ) {
                            }

                            override fun checkServerTrusted(
                                    chain: Array<X509Certificate?>?,
                                    authType: String?
                            ) {
                            }

                            override fun getAcceptedIssuers(): Array<X509Certificate?>? {
                                return arrayOf()
                            }
                        }
                )

                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, SecureRandom())
                val sslSocketFactory = sslContext.socketFactory
                val trustManagerFactory: TrustManagerFactory =
                        TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
                trustManagerFactory.init(null as KeyStore?)
                val trustManagers: Array<TrustManager> =
                        trustManagerFactory.trustManagers
                if(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
                    "Unexpected default trust managers:" + trustManagers.toString()
                }

                val trustManager =
                        trustManagers[0] as X509TrustManager


                val builder = OkHttpClient.Builder()
                builder.sslSocketFactory(sslSocketFactory, trustManager)
                builder.hostnameVerifier(HostnameVerifier { _, _ -> true })
                builder.build()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }
}