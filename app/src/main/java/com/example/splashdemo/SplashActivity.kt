package com.example.splashdemo

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class SplashActivity : AppCompatActivity() {

    private lateinit var appUpdateDialog: AppUpdateDialogFragment
    private var shouldShowAppUpdateDialog = true

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_splash)
        splashScreen.setKeepOnScreenCondition { true }
    }

    override fun onStart() {
        super.onStart()
        if (shouldShowAppUpdateDialog) {
            showHardUpdateDialog()
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showHardUpdateDialog() {
        appUpdateDialog = AppUpdateDialogFragment.newInstance(false,
            mDialogButtonClickListener = object :
                AppUpdateDialogFragment.DialogButtonClickListener {
                override fun onUpdateClick() {
                }

                override fun onExitAppClick() {
                    finish()
                }

                override fun onBackPress(
                    dialog: DialogInterface?,
                    keyCode: Int,
                    event: KeyEvent?
                ) {
                }

                override fun onDialogDismiss() {
                }
            })

        try {
            if (!supportFragmentManager.isDestroyed) {
                appUpdateDialog.show(supportFragmentManager, AppUpdateDialogFragment.TAG)
            }
        } catch (exception: Exception) {
            //do nothing
        }
    }

}