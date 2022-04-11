package com.under.myapplication.model

import android.util.Log
import java.io.StringReader
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

object DataBase {
    const val NO_USERS:String="NO_USERS"
    const val INVALID_USER:String="INVALID_USER"
    const val VALID_USER:String="VALID_USER"

    private var users:HashMap<String,User> = HashMap()
    private var posts:ArrayList<Post> = ArrayList()
    private var session: Session? = null

    init {
        users["alfa@gmail.com"] = User("alfa@gmail.com","aplicacionesmoviles","Jhon Saldarriaga","")
        users["beta@gmail.com"] = User("beta@gmail.com","aplicacionesmoviles","Alejandro Magno","")
    }

    fun getUserByID(id:String):User?{
        return users[id]
    }

    //SESSION
    fun startSession(email:String, password:String):String{
        if(users.isEmpty()){
            return NO_USERS
        }else{
            if (users[email]!=null){
                if(users[email]!!.verifyCredential(email,password)) {
                    session = Session(email)
                    return VALID_USER
                }
            }
            return INVALID_USER
        }
    }
    fun endSession(){session = null}
    fun isOnSession():Boolean{return session!=null}

    //PROFILE
    fun changeProfilePic(path:String){
        if(getUserByID(session!!.userID)!=null){
            getUserByID(session!!.userID)?.setImageProfilePath(path)
        }else{
            Log.e(">>>","userId not found")
        }
    }
    fun changeProfileName(name:String){
        if(getUserByID(session!!.userID)!=null){
            getUserByID(session!!.userID)?.setName(name)
        }
    }

    //POST
    fun addPost(updateText:String, location:String, date:String){
        posts.add(Post(updateText,location,date, session!!.userID))
    }
    fun addPost(updateText:String, location:String, date:String, uip:String){
        val p = Post(updateText,location,date,session!!.userID)
        p.updateImagePath = uip
        posts.add(p)
    }

    //DATE
    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    //SERI
    fun getUsers():HashMap<String,User>{return users}
    fun setUsers(u:HashMap<String,User>){users = u}
    fun getSession():Session?{return session}
    fun setSession(s:Session?){session = s}
    fun getPost():ArrayList<Post>{return posts}
    fun setPost(p:ArrayList<Post>){posts = p}
}