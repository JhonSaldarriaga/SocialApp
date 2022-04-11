package com.under.myapplication

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.under.myapplication.databinding.ActivityMainBinding
import com.under.myapplication.model.DataBase
import com.under.myapplication.model.Session
import com.under.myapplication.model.State

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        requestPermissions(arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE),1)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var allGrant = true
        for(result in grantResults){
            if(result == PackageManager.PERMISSION_DENIED) allGrant = false
        }
        if(allGrant){
            loadData()
            if(DataBase.isOnSession()){
                startActivity(Intent(this, UserActivity::class.java))
            }else{
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }else{
            Toast.makeText(
                this,
                "Tienes que aceptar todos los permisos antes de continuar",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadData() {
        //Shared Preferences
        val sharedPref = getSharedPreferences("Preference",Context.MODE_PRIVATE)
        val json = sharedPref.getString("state","NO_STATE")
        Log.e(">>>","loadDataMainActivity: ${json.toString()}")
        if(json != "NO_STATE"){
            //Deserializacion
            val jsonDes = Gson().fromJson(json, State::class.java)
            DataBase.setPost(jsonDes.posts)
            DataBase.setSession(jsonDes.session)
            DataBase.setUsers(jsonDes.users)
        }
    }
}