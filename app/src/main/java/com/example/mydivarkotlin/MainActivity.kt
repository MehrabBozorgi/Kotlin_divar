package com.example.mydivarkotlin

import Fragment.Fragment_Account
import Fragment.Fragment_Cat
import Fragment.Fragment_Home
import Fragment.Fragment_adv
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {


    var manager = supportFragmentManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var nav_buttom =
            findViewById<BottomNavigationView>(R.id.nav_main_navigation) //باتن نویگیشن رو کست کردیم

        ///////////////////
//      اینجا تبهای باتن نویگیشن میباشد
        ///////////////////
        val homeFragment = Fragment_Home()
        val catFragment = Fragment_Cat()
        val advFragment = Fragment_adv()
        val accountFragment = Fragment_Account()
//      خط پایین یعنی فرگمنت از هوم فرگمنت اغاز بشه
        createFragment(homeFragment)
//      مقادیر بایتها رو مقدار دهی کردیم
        nav_buttom.setOnNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.tab_cat -> createFragment(catFragment)
                R.id.tab_circle -> createFragment(advFragment)
                R.id.tab_myAccount -> createFragment(accountFragment)
                R.id.tab_home -> createFragment(homeFragment)

            }
            true
        }

    }

    fun createFragment(fragment: Fragment) {

        var transAction = manager.beginTransaction()
        transAction.replace(R.id.container, fragment)
        transAction.commit()

    }


}

