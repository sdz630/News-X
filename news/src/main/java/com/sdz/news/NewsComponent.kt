package com.sdz.news

import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.billy.cc.core.component.IComponent
import com.sdz.news.headline.HeadlineFragment

class NewsComponent:IComponent {
    override fun onCall(cc: CC?): Boolean {
        var actionName = cc?.actionName
        when(actionName){
            "getHeadlineFragment"->{
                val result = CCResult()
                result.addData("fragment",HeadlineFragment())
                CC.sendCCResult(cc?.callId,result)
                return true
            }
            else -> {
                CC.sendCCResult(cc?.callId, CCResult.errorUnsupportedActionName())
                return false
            }
        }
    }

    override fun getName(): String {
        return "News"
    }
}