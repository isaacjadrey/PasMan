package com.codingwithjadrey.pasman.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.codingwithjadrey.pasman.R
import com.codingwithjadrey.pasman.databinding.ActivityAuthBinding
import java.util.concurrent.Executor

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // animator to animate our splash image
        val zoomIn = AnimationUtils.loadAnimation(this, R.anim.zoom_in)

        /** instead of having two separate files ( one for splash screen and the other for login
         * we're using one activity/screen that handles all that
         * by setting the item visibility when needed
         * */
        binding.apply {
            splashImage.visibility = View.VISIBLE
            splashImage.startAnimation(zoomIn)
            Handler(Looper.getMainLooper()).postDelayed({
                splashImage.visibility = View.GONE
                defaultLayout.visibility = View.VISIBLE
            }, 3500)
        }

        /** Great links to help in understanding biometric prompt dialog
         * quite easy actually
         * 1 - https://developer.android.com/codelabs/biometric-login
         * 2 - https://developer.android.com/training/sign-in/biometric-auth
         * */
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        applicationContext,
                        "Authentication Error: $errString",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    startActivity(Intent(this@AuthActivity, MainActivity::class.java))
                    finish()
                    Toast.makeText(
                        applicationContext,
                        "Authentication Succeeded",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "Authentication Failed", Toast.LENGTH_SHORT)
                        .show()
                }
            })

        /** shows the message to the user on the system biometric prompt
         * remember the biometric UI will differ accordingly */
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for Pas-Man")
            .setSubtitle("Login using your biometric print")
            .setNegativeButtonText("Use account password")
            .build()

        /** Prompt appears when user clicks "Unlock" button. */
        val biometricLoginBtn = binding.promptBiometricLogin
        biometricLoginBtn.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }

    /** Option to enable the prompt to appear when the application just launches
     * this option is commented out here because am not gonna use
     * however if you would like to do little tweaks you can use it here
     * and do some code cleaning up there in the top
     */
//    override fun onStart() {
//        super.onStart()
//        biometricPrompt.authenticate(promptInfo)
//        binding.apply {
//            splashImage.visibility = View.GONE
//            defaultLayout.visibility = View.GONE
//        }
//    }
}