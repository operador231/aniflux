package com.github.operador231.core.data.security

import android.content.Context
import android.util.Base64
import com.google.crypto.tink.Aead
import com.google.crypto.tink.RegistryConfiguration
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.aead.AesGcmKeyManager
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages cryptographic operations for encrypting and decrypting sensitive data.
 *
 * This class utilizes the Google Tink library to provide robust AEAD (Authenticated
 * Encryption with Associated Data) using AES256-GCM. It securely generates, stores,
 * and retrieves cryptographic keys using the Android Keystore system.
 *
 * The keyset is managed by [AndroidKeysetManager], which stores the encrypted keyset
 * in shared preferences and uses a master key from the Android Keystore for encryption.
 * This ensures that the keys are not stored in plaintext and are protected by hardware-backed
 * security where available.
 *
 * This class is provided as a Hilt singleton and is intended for internal use within the data layer.
 *
 * @param ctx The application context, used to access shared preferences and the Android Keystore.
 */
@Singleton
internal class CryptoManager @Inject constructor(
    @ApplicationContext ctx: Context
) {
    private val keysetName: String = "master_keyset"
    private val prefFileName: String = "tink_prefs"
    private val masterKeyUri: String = "android-keystore://auth_master_key"

    /**
     * Lazily initialized AEAD primitive from the Tink keyset.
     *
     * This primitive is responsible for performing the actual encryption and decryption.
     * It is configured to use the AES256-GCM template and is managed by the [AndroidKeysetManager].
     */
    private val aead: Aead by lazy {
        AeadConfig.register()

        AndroidKeysetManager.Builder()
            .withSharedPref(ctx, keysetName, prefFileName)
            .withKeyTemplate(AesGcmKeyManager.aes256GcmTemplate())
            .withMasterKeyUri(masterKeyUri)
            .build()
            .keysetHandle
            .getPrimitive(RegistryConfiguration.get(), Aead::class.java)
    }

    /**
     * Encrypts the given plaintext string.
     *
     * @param text The plaintext string to encrypt.
     * @return The Base64-encoded ciphertext, or null if an error occurs during encryption.
     */
    internal fun encrypt(text: String): String? {
        return try {
            val cipherText = aead.encrypt(text.toByteArray(), null)
            Base64.encodeToString(cipherText, Base64.NO_WRAP)
        } catch (ex: Exception) {
            Timber.e(ex)
            null
        }
    }

    /**
     * Decrypts the given Base64-encoded ciphertext.
     *
     * @param text The Base64-encoded ciphertext to decrypt.
     * @return The original plaintext string, or null if an error occurs during decryption
     * (e.g., incorrect key, corrupted data).
     */
    internal fun decrypt(text: String): String? {
        return try {
            val data = Base64.decode(text, Base64.NO_WRAP)
            String(aead.decrypt(data, null))
        } catch (ex: Exception) {
            Timber.e(ex)
            null
        }
    }
}