package com.sdz.usercenter

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sdz.base.preference.PreferencesUtil
import com.sdz.usercenter.databinding.ActivityLogin1Binding


class LoginActivity : AppCompatActivity() {

    private val TAG = LoginActivity::class.java.simpleName
    private lateinit var mBinding: ActivityLogin1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_login1)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        init()


    }

    fun init() {

        // Login button Click Event
        mBinding.btnLogin.setOnClickListener(View.OnClickListener { // Hide Keyboard
            PreferencesUtil.getInstance()
                .setString("UserName", mBinding.lTextEmail.getEditText()?.getText().toString())
            finish()
        })
    }

}