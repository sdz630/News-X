package com.sdz.usercenter

import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.billy.cc.core.component.CCUtil
import com.billy.cc.core.component.IComponent

class UserCenterComponent : IComponent {
    override fun onCall(cc: CC?): Boolean {
        var actionName = cc?.actionName
        when (actionName) {
            "login" -> {
                CCUtil.navigateTo(cc,LoginActivity::class.java)
                CC.sendCCResult(cc?.callId, CCResult.success())
                return false
            }
            "registerLoginListener"->{
                CC.sendCCResult(cc?.callId, CCResult.success())
                return true
            }
            else -> {
                CC.sendCCResult(cc?.callId, CCResult.errorUnsupportedActionName())
                return false
            }
        }
    }

    override fun getName(): String {
        return "UserCenter"
    }
}