package com.sdz.newsapp.main

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.billy.cc.core.component.CC
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.sdz.base.activity.MVVMActivity
import com.sdz.base.viewmodel.MVVMBaseViewModel
import com.sdz.newsapp.R
import com.sdz.newsapp.main.otherfragments.AccountFragment
import com.sdz.newsapp.main.otherfragments.CategoryFragment
import com.sdz.newsapp.main.otherfragments.ServiceFragment
import kotlinx.android.synthetic.main.activity_main.*
import q.rorbin.badgeview.QBadgeView
import java.lang.reflect.Field

class MainActivity : MVVMActivity<MVVMBaseViewModel<*,*>>() {

    lateinit var mHomeFragment:Fragment
    private val mCategoryFragment: CategoryFragment = CategoryFragment()
    private val mServiceFragment: ServiceFragment = ServiceFragment()
    private val mAccountFragment: AccountFragment = AccountFragment()

    private lateinit var fromFragment:Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val result = CC.obtainBuilder("News").setActionName("getHeadlineFragment")
            .build().call()
        mHomeFragment = result.dataMap.get("fragment") as Fragment
        fromFragment = mHomeFragment

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(getString(R.string.menu_home))
        //todo 沉浸式
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = resources.getColor(R.color.colorPrimary)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            disableShiftMode(bottom_view)
        }
        bottom_view.setOnNavigationItemSelectedListener(object:BottomNavigationView.OnNavigationItemSelectedListener{
            lateinit var fragment:Fragment
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.menu_home -> fragment = mHomeFragment
                    R.id.menu_categories -> fragment = mCategoryFragment
                    R.id.menu_services -> fragment = mServiceFragment
                    R.id.menu_account -> fragment = mAccountFragment
                }
                supportActionBar?.setTitle(item.title)
                switchFragment(fromFragment, fragment)
                fromFragment = fragment
                return true
            }

        })

        bottom_view.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        val transaction:FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(container.id, mHomeFragment);
        transaction.commit();
        showBadgeView(3,5)

    }

    private fun switchFragment(
        from: Fragment?,
        to: Fragment?
    ) {
        if (from !== to) {
            val manger = supportFragmentManager
            val transaction = manger.beginTransaction()
            if (!to!!.isAdded) {
                if (from != null) {
                    transaction.hide(from)
                }
                if (to != null) {
                    transaction.add(R.id.container, to).commit()
                }
            } else {
                if (from != null) {
                    transaction.hide(from)
                }
                if (to != null) {
                    transaction.show(to).commit()
                }
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private fun disableShiftMode(bottomNavigationView: BottomNavigationView) {
        var menuView: BottomNavigationMenuView = bottomNavigationView.getChildAt(0) as BottomNavigationMenuView
        try {
            var shiftingMode: Field? = menuView.javaClass.getDeclaredField("mShiftingMode")
            shiftingMode?.setBoolean(menuView, false)
           for (i in 0..menuView.childCount){
                var item:BottomNavigationItemView = menuView.getChildAt(i) as BottomNavigationItemView
               item.setChecked(item.itemData.isChecked)
           }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }




    private fun showBadgeView(viewIndex: Int, showNumber: Int) {
        // 具体child的查找和view的嵌套结构请在源码中查看
        // 从bottomNavigationView中获得BottomNavigationMenuView

        // 具体child的查找和view的嵌套结构请在源码中查看
        // 从bottomNavigationView中获得BottomNavigationMenuView
        val menuView:BottomNavigationMenuView =
            bottom_view.getChildAt(0) as BottomNavigationMenuView
        // 从BottomNavigationMenuView中获得childview, BottomNavigationItemView
        // 从BottomNavigationMenuView中获得childview, BottomNavigationItemView
        if (viewIndex < menuView.childCount) {
            // 获得viewIndex对应子tab
            val view = menuView.getChildAt(viewIndex)
            // 从子tab中获得其中显示图片的ImageView
            val icon =
                view.findViewById<View>(com.google.android.material.R.id.icon)
            // 获得图标的宽度
            val iconWidth = icon.width
            // 获得tab的宽度/2
            val tabWidth = view.width / 2
            // 计算badge要距离右边的距离
            val spaceWidth = tabWidth - iconWidth

            // 显示badegeview
            QBadgeView(this).bindTarget(view)
                .setGravityOffset(spaceWidth + 50.toFloat(), 13f, false).badgeNumber = showNumber
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


    //不加注解会有问题
    @SuppressLint("ResourceType")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main;
    }

    override fun getViewModel(): MVVMBaseViewModel<*,*>? {
        return null
    }


}
