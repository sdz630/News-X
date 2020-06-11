package com.sdz.common.webview

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.sdz.common.R
import com.sdz.webview.AccountWebFragment
import com.sdz.webview.CommonWebFragment
import com.sdz.webview.basefragment.BaseWebviewFragment
import com.sdz.webview.command.base.Command
import com.sdz.webview.command.base.Command.COMMAND_UPDATE_TITLE
import com.sdz.webview.command.base.Command.COMMAND_UPDATE_TITLE_PARAMS_TITLE
import com.sdz.webview.command.base.ResultBack
import com.sdz.webview.command.webviewprocess.WebviewProcessCommandsManager
import com.sdz.webview.utils.WebConstants
import java.lang.Compiler.command
import java.util.*

class WebActivity : AppCompatActivity() {
    private lateinit var title: String
    private lateinit var url: String
    var webviewFragment: BaseWebviewFragment? = null

    companion object{
        @JvmStatic
        fun startCommonWeb(
            context: Context,
            title: String,
            url: String
        ) {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra(WebConstants.INTENT_TAG_TITLE, title)
            intent.putExtra(WebConstants.INTENT_TAG_URL, url)
            intent.putExtra("level", WebConstants.LEVEL_BASE)
            if (context is Service) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }

        @JvmStatic
        fun startAccountWeb(
            context: Context,
            title: String?,
            url: String?,
            headers: HashMap<String?, String?>
        ) {
            val intent = Intent(context, WebActivity::class.java)
            val bundle = Bundle()
            bundle.putString(WebConstants.INTENT_TAG_TITLE, title)
            bundle.putString(WebConstants.INTENT_TAG_URL, url)
            bundle.putSerializable(WebConstants.INTENT_TAG_HEADERS, headers)
            bundle.putInt("level", WebConstants.LEVEL_ACCOUNT)
            if (context is Service) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common_web)
        title = intent.getStringExtra(WebConstants.INTENT_TAG_TITLE)
        url = intent.getStringExtra(WebConstants.INTENT_TAG_URL)

        setTitle(title)
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        WebviewProcessCommandsManager
            .getInstance()
            ?.registerCommand(WebConstants.LEVEL_LOCAL, titleUpdateCommand)
        val level = intent.getIntExtra("level", WebConstants.LEVEL_BASE)
        webviewFragment = null
        webviewFragment = if (level == WebConstants.LEVEL_BASE) {
            CommonWebFragment.newInstance(url)
        } else {
            AccountWebFragment.newInstance(
                url,
                intent.extras
                    ?.getSerializable(WebConstants.INTENT_TAG_HEADERS) as HashMap<String, String>,
                true
            )
        }
        transaction.replace(R.id.web_view_fragment, webviewFragment!!).commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (webviewFragment != null && webviewFragment is BaseWebviewFragment) {
            val flag = webviewFragment!!.onKeyDown(keyCode, event!!)
            if (flag) {
                return flag
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * 页面路由
     */
    private val titleUpdateCommand: Command = object : Command {
        override fun name(): String {
            return COMMAND_UPDATE_TITLE
        }

        override fun exec(context: Context?, params: Map<*, *>?, resultBack: ResultBack?) {
            if (params?.containsKey(COMMAND_UPDATE_TITLE_PARAMS_TITLE)!!) {
                setTitle(params?.get(COMMAND_UPDATE_TITLE_PARAMS_TITLE) as String?)
            }
        }


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }

    @SuppressLint("ResourceType")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
}