package com.sdz.newsapp.main.otherfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.sdz.newsapp.R
import com.sdz.newsapp.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {
    var mBinding: FragmentAccountBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)
        mBinding?.login?.setOnClickListener{
            view->
            run {
                var result: CCResult = CC.obtainBuilder("UserCenter")
                    .setActionName("login")
                    .build()
                    .call()

            }

        }
        return mBinding?.root
    }
}