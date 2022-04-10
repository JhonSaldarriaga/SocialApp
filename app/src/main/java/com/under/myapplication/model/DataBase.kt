package com.under.myapplication.model

import android.util.Log

object DataBase {
    const val NO_USERS:String="NO_USERS"
    const val INVALID_USER:String="INVALID_USER"
    const val VALID_USER:String="VALID_USER"

    private var users:ArrayList<User> = ArrayList()
    private var posts:ArrayList<Post> = ArrayList()
    private var session: Session? = null

    init {
        users.add(User("alfa@gmail.com","aplicacionesmoviles","Jhon Saldarriaga",""))
        users.add(User("beta@gmail.com","aplicacionesmoviles","Alejandro Magno",""))
    }

    //SESSION
    fun startSession(email:String, password:String):String{
        if(users.isEmpty()){
            return NO_USERS
        }else{
            for (i in users){
                if (i.verifyCredential(email,password)) {
                    session = Session(i)
                    return VALID_USER
                }
            }
            return INVALID_USER
        }
    }
    fun getUserSession(email:String, password:String):User?{
        if(session==null)
            return null
        else if(session?.user?.verifyCredential(email,password)!!)
            return session?.user
        else
            return null
    }
    fun endSession(){session = null}
    fun isOnSession():Boolean{return session!=null}

    //PROFILE
    fun changeProfilePic(user:User, path:String){
        user.setImageProfilePath(path)
    }
    fun changeProfileName(user:User, name:String){
        user.setName(name)
    }

    //POST
    fun addPost(updateText:String, location:String, date:String, user:User){
        posts.add(Post(updateText,location,date,user))
    }
    fun addPost(updateText:String, location:String, date:String, user:User, uip:String){
        val p = Post(updateText,location,date,user)
        p.updateImagePath = uip
        posts.add(p)
    }
}