package com.under.myapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.under.myapplication.databinding.ActivityLoginBinding
import com.under.myapplication.model.DataBase
import com.under.myapplication.model.State

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loginBTN.setOnClickListener{onClickListenerLoginBTN()}
        binding.loginForgotPasswordTV.setOnClickListener{Toast.makeText(this,R.string.soon,Toast.LENGTH_SHORT).show()}
    }

    private fun onClickListenerLoginBTN(){
        Log.e(">>>",binding.loginEmailET.text.toString())
        Log.e(">>>",binding.loginPasswordET.text.toString())
        var sessionResult = DataBase.startSession(binding.loginEmailET.text.toString(),binding.loginPasswordET.text.toString())

        when(sessionResult){
            DataBase.VALID_USER ->{startActivity(Intent(this, UserActivity::class.java))}
            DataBase.INVALID_USER ->{Toast.makeText(this, R.string.login_invalid, Toast.LENGTH_SHORT).show()}
            DataBase.NO_USERS ->{Toast.makeText(this, "No hay usuarios registrados", Toast.LENGTH_LONG).show()}
        }
    }

    override fun onPause() {
        super.onPause()
        //Serializacion
        val json = Gson().toJson(
            State(
                DataBase.getUsers(),
                DataBase.getPost(),
                DataBase.getSession())
        )
        //Shared Preferences
        val sharedPref = getSharedPreferences("Preference", Context.MODE_PRIVATE)
        sharedPref.edit().putString("state",json).apply()
    }

    override fun onResume() {
        //Shared Preferences
        val sharedPref = getSharedPreferences("Preference", Context.MODE_PRIVATE)
        val json = sharedPref.getString("state","NO_STATE")
        Log.e(">>>","onResumeMainActivity: ${json.toString()}")
        if(json != "NO_STATE"){
            //Deserializacion
            val jsonDes = Gson().fromJson(json, State::class.java)
            DataBase.setPost(jsonDes.posts)
            DataBase.setSession(jsonDes.session)
            DataBase.setUsers(jsonDes.users)
        }
        super.onResume()
    }
}