package com.arise.common.sdk.utils

import com.arise.common.sdk.BaseApplication
import okhttp3.OkHttpClient
import java.io.IOException
import java.io.InputStream
import java.security.GeneralSecurityException
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.*
import javax.net.ssl.*

/**
 * Created by wudaming on 2018/1/5.
 */

object HTTPSUtils {

    /**
     * 初始化HTTPS,添加信任证书
     * @param builder
     */
    fun configHttpsCert(builder: OkHttpClient.Builder, file:String) {
        val mContext = BaseApplication.baseInstance
        val trustManager: X509TrustManager
        val sslSocketFactory: SSLSocketFactory
        val inputStream: InputStream
        val trustManager1= object:X509TrustManager{
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }

        }
        val hostnameVerifier = HostnameVerifier { hostname, session -> true }
        try {
//            inputStream = mContext.assets.open(file) // 得到证书的输入流
            try {

//                trustManager = trustManagerForCertificates(inputStream)//以流的方式读入证书
                val sslContext = SSLContext.getInstance("TLS")
                sslContext.init(null, arrayOf<TrustManager>(trustManager1), null)
                sslSocketFactory = sslContext.socketFactory

            } catch (e: GeneralSecurityException) {
                throw RuntimeException(e)
            }


            builder.sslSocketFactory(sslSocketFactory, trustManager1)
            builder.hostnameVerifier(hostnameVerifier)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }


    /**
     * 以流的方式添加信任证书
     */
    /**
     * Returns a trust manager that trusts `certificates` and none other. HTTPS services whose
     * certificates have not been signed by these certificates will fail with a `SSLHandshakeException`.
     *
     *
     *
     * This can be used to replace the host platform's built-in trusted certificates with a custom
     * set. This is useful in development where certificate authority-trusted certificates aren't
     * available. Or in production, to avoid reliance on third-party certificate authorities.
     *
     *
     *
     *
     * <h3>Warning: Customizing Trusted Certificates is Dangerous!</h3>
     *
     *
     *
     * Relying on your own trusted certificates limits your server team's ability to update their
     * TLS certificates. By installing a specific set of trusted certificates, you take on additional
     * operational complexity and limit your ability to migrate between certificate authorities. Do
     * not use custom trusted certificates in production without the blessing of your server's TLS
     * administrator.
     */
    @Throws(GeneralSecurityException::class)
    private fun trustManagerForCertificates(`in`: InputStream): X509TrustManager {
        val certificateFactory = CertificateFactory.getInstance("X.509")
        val certificates = certificateFactory.generateCertificates(`in`)
        if (certificates.isEmpty()) {
            throw IllegalArgumentException("expected non-empty set of trusted certificates")
        }

        // Put the certificates a key store.
        val password = "password".toCharArray() // Any password will work.
        val keyStore = newEmptyKeyStore(password)
        var index = 0
        for (certificate in certificates) {
            val certificateAlias = Integer.toString(index++)
            keyStore.setCertificateEntry(certificateAlias, certificate)
        }

        // Use it to build an X509 trust manager.
        val keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm())
        keyManagerFactory.init(keyStore, password)
        val trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(keyStore)
        val trustManagers = trustManagerFactory.trustManagers
        if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) {
            throw IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers))
        }
        return trustManagers[0] as X509TrustManager
    }


    /**
     * 添加password
     * @param password
     * @return
     * @throws GeneralSecurityException
     */
    @Throws(GeneralSecurityException::class)
    private fun newEmptyKeyStore(password: CharArray): KeyStore {
        try {
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType()) // 这里添加自定义的密码，默认
            val `in`: InputStream? = null // By convention, 'null' creates an empty key store.
            keyStore.load(`in`, password)
            return keyStore
        } catch (e: IOException) {
            throw AssertionError(e)
        }

    }
}
