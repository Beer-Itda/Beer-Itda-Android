import java.io.File
import java.util.*

object PropertiesExt {
    private const val FILE_KEYSTORE = "keystore.properties"
    private const val FILE_SECRURE = "secure.properties"
    const val KAKAO = "kakao"
    const val BASE_URL = "base_url"

    private const val DEVELOP_KEYSTORE = "develop_keystore"
    private const val DEVELOP_KEYSTORE_PASSWORD = "develop_keystore_password"
    private const val DEVELOP_KEY_ALIAS = "develop_key_alias"
    private const val DEVELOP_KEY_PASSWORD = "develop_key_password"

    private val secureProperties = secure()
    private val keyStoreProperties = keystore()

    private fun secure(): Properties {
        val secureProperties = Properties()
        File(FILE_SECRURE).run {
            println("FILE_SECRURE : ${exists()}")
            if (exists()) {
                secureProperties.load(inputStream())
            }
        }
        return secureProperties
    }

    private fun keystore(): Properties {
        val keystoreProperties = Properties()
        File(FILE_KEYSTORE).run {
            println("FILE_KEYSTORE : ${exists()}")
            if (exists()) {
                keystoreProperties.load(inputStream())
            }
        }
        return keystoreProperties
    }

    fun getKakaoKey(): String = secureProperties.getProperty(KAKAO) ?: ""
    fun getBaseUrl(): String = secureProperties.getProperty(BASE_URL) ?: ""

    fun getStoreFile(): File = File(keyStoreProperties.getProperty(DEVELOP_KEYSTORE))
    fun getStorePassword(): String = keyStoreProperties.getProperty(DEVELOP_KEYSTORE_PASSWORD)
    fun getKeyAlias(): String = keyStoreProperties.getProperty(DEVELOP_KEY_ALIAS)
    fun getKeyPassword(): String = keyStoreProperties.getProperty(DEVELOP_KEY_PASSWORD)

}
