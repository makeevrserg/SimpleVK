package com.makeevrserg.simplevk

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.makeevrserg.simplevk.ui.auth.AuthViewModel
import com.vk.api.sdk.VK
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: AuthViewModel by lazy {
        ViewModelProvider(this).get(AuthViewModel::class.java)
    }
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        //Устанавливаем navController для BottomNavigation и ToolBar
        bottom_nav.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController)

        //Проверяем, авторизован ли пользователь
        viewModel.authorized.observe(this, {
            if (it)
                return@observe
            println("Need auth")
            VK.login(
                this,
                viewModel.VK_PERMISSIONS
            )
        })


    }

    //Возвращение на предыдущий этап через нажатие кнопки на ToolBar'е
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    //После завершения авторизации совершается callback в vkAuthCallback()
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null || !VK.onActivityResult(
                requestCode,
                resultCode,
                data,
                viewModel.vkAuthCallback()
            )
        ) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}