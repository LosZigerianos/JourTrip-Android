package com.zigerianos.jourtrip.biometric

import android.content.DialogInterface
import android.R.attr.negativeButtonText
import android.R.attr.description
import android.R.attr.subtitle
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.annotation.TargetApi
import android.content.Context
import android.os.CancellationSignal
import androidx.annotation.NonNull


class BiometricManager(biometricBuilder: BiometricBuilder) : BiometricManagerV23() {

    private var mCancellationSignal = CancellationSignal()


    init {
        this.mContext = biometricBuilder.context
        this.title = biometricBuilder.title
        this.subtitle = biometricBuilder.subtitle
        this.description = biometricBuilder.description
        this.negativeButtonText = biometricBuilder.negativeButtonText
    }


    fun authenticate(@NonNull biometricCallback: BiometricCallback) {

        if (title == null) {
            biometricCallback.onBiometricAuthenticationInternalError("Biometric Dialog title cannot be null")
            return
        }


        if (subtitle == null) {
            biometricCallback.onBiometricAuthenticationInternalError("Biometric Dialog subtitle cannot be null")
            return
        }


        if (description == null) {
            biometricCallback.onBiometricAuthenticationInternalError("Biometric Dialog description cannot be null")
            return
        }

        if (negativeButtonText == null) {
            biometricCallback.onBiometricAuthenticationInternalError("Biometric Dialog negative button text cannot be null")
            return
        }


        if (!BiometricUtils.isSdkVersionSupported()) {
            biometricCallback.onSdkVersionNotSupported()
            return
        }

        if (!BiometricUtils.isPermissionGranted(mContext)) {
            biometricCallback.onBiometricAuthenticationPermissionNotGranted()
            return
        }

        if (!BiometricUtils.isHardwareSupported(mContext)) {
            biometricCallback.onBiometricAuthenticationNotSupported()
            return
        }

        if (!BiometricUtils.isFingerprintAvailable(mContext)) {
            biometricCallback.onBiometricAuthenticationNotAvailable()
            return
        }

        displayBiometricDialog(biometricCallback)
    }

    fun cancelAuthentication() {
        if (BiometricUtils.isBiometricPromptEnabled()) {
            if (!mCancellationSignal.isCanceled())
                mCancellationSignal.cancel()
        } else {
            if (!mCancellationSignalV23.isCanceled())
                mCancellationSignalV23.cancel()
        }
    }


    private fun displayBiometricDialog(biometricCallback: BiometricCallback) {
        if (BiometricUtils.isBiometricPromptEnabled()) {
            displayBiometricPrompt(biometricCallback)
        } else {
            displayBiometricPromptV23(biometricCallback)
        }
    }


    @TargetApi(Build.VERSION_CODES.P)
    private fun displayBiometricPrompt(biometricCallback: BiometricCallback) {
        BiometricPrompt.Builder(mContext)
            .setTitle(title.toString())
            .setSubtitle(subtitle.toString())
            .setDescription(description.toString())
            .setNegativeButton(negativeButtonText.toString(), mContext.mainExecutor,
                DialogInterface.OnClickListener { dialogInterface, i -> biometricCallback.onAuthenticationCancelled() })
            .build()
            .authenticate(
                mCancellationSignal, mContext.mainExecutor,
                BiometricCallbackV28(biometricCallback)
            )
    }


    class BiometricBuilder(internal val context: Context) {

        internal var title: String? = null
        internal var subtitle: String? = null
        internal var description: String? = null
        internal var negativeButtonText: String? = null

        fun setTitle(@NonNull title: String): BiometricBuilder {
            this.title = title
            return this
        }

        fun setSubtitle(@NonNull subtitle: String): BiometricBuilder {
            this.subtitle = subtitle
            return this
        }

        fun setDescription(@NonNull description: String): BiometricBuilder {
            this.description = description
            return this
        }


        fun setNegativeButtonText(@NonNull negativeButtonText: String): BiometricBuilder {
            this.negativeButtonText = negativeButtonText
            return this
        }

        fun build(): BiometricManager {
            return BiometricManager(this)
        }
    }
}