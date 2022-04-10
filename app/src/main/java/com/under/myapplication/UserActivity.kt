package com.under.myapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.under.myapplication.databinding.ActivityUserBinding
import com.under.myapplication.model.DataBase
import com.under.myapplication.model.State
import java.io.File

class UserActivity : AppCompatActivity(), ProfileFragment.OnLogoutListener {

    private lateinit var postFragment: NewPostFragment
    private lateinit var homeFragment: HomeFragment
    private lateinit var profileFragment: ProfileFragment

    private val binding: ActivityUserBinding by lazy { ActivityUserBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        postFragment = NewPostFragment.newInstance()
        homeFragment = HomeFragment.newInstance()
        profileFragment = ProfileFragment.newInstance()
        profileFragment.logoutListener = this
        showFragment(homeFragment) // Default fragment

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.addMenu -> {showFragment(postFragment)}
                R.id.profileMenu -> {showFragment(profileFragment)}
                R.id.homeMenu -> {showFragment(homeFragment)}
            }
            true
        }
    }

    private fun showFragment (fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.userFragmentContainer.id,fragment)
        transaction.commit()
    }

    //METODOS DE INTERFACES
    override fun onLogoutListener() {
        DataBase.endSession()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    //----------------------------------------------------------------------------------------------
    override fun onBackPressed() {}

    override fun onResume() {
        //Shared Preferences
        val sharedPref = getSharedPreferences("Preference",Context.MODE_PRIVATE)
        val json = sharedPref.getString("state","NO_STATE")
        Log.e(">>>","onResumeUserActivity: ${json.toString()}")
        if(json != "NO_STATE"){
            //Deserializacion
            val jsonDes = Gson().fromJson(json, State::class.java)
            DataBase.setPost(jsonDes.posts)
            DataBase.setSession(jsonDes.session)
            DataBase.setUsers(jsonDes.users)
        }
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        //Serializacion
        val json = Gson().toJson(State(DataBase.getUsers(),DataBase.getPost(),DataBase.getSession()))
        //Shared Preferences
        val sharedPref = getSharedPreferences("Preference",Context.MODE_PRIVATE)
        sharedPref.edit().putString("state",json).apply()
    }
}