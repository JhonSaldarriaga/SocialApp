package com.under.myapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.under.myapplication.databinding.ActivityUserBinding
import com.under.myapplication.model.DataBase
import com.under.myapplication.model.State
import java.io.File

class UserActivity : AppCompatActivity(),
    ProfileFragment.ProfileListener,
    EditProfileFragment.EditProfileListener,
    NewPostFragment.NewPostListener{

    private val binding: ActivityUserBinding by lazy { ActivityUserBinding.inflate(layoutInflater) }

    private lateinit var postFragment: NewPostFragment
    private lateinit var homeFragment: HomeFragment
    private lateinit var profileFragment: ProfileFragment
    private var dialogEditProfile = EditProfileFragment()

    private val galleryLauncherTemp = registerForActivityResult(StartActivityForResult(),::onGalleryTempResult)
    private val galleryLauncherNewPost = registerForActivityResult(StartActivityForResult(),::onGalleryResultNewPost)
    private val galleryLauncherNewPostCamera = registerForActivityResult(StartActivityForResult(),::onGalleryResultNewPostCamera)
    private var tempFile: File? = null
    private var numberFile: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        postFragment = NewPostFragment.newInstance()
        postFragment.newPostListener = this
        homeFragment = HomeFragment.newInstance()
        profileFragment = ProfileFragment.newInstance()
        profileFragment.profileListener = this
        dialogEditProfile.editProfileListener = this
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

    //profileListener
    override fun onLogoutListener() {
        DataBase.endSession()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun onEditProfileListener() {
        dialogEditProfile.show(supportFragmentManager,"editProfile")
    }


    //editProfileListener
    override fun onChangeProfilePicListener() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type= "image/*"
        galleryLauncherTemp.launch(intent)
    }

    override fun onEditUserValuesApplyListener() {
        serialize()
        profileFragment.updateInfo()
    }

    private fun onGalleryTempResult(result: ActivityResult){
        if(result.resultCode==RESULT_OK){
            val uriImage = result.data?.data
            Log.e(">>>","${uriImage}")
            Log.e(">>>","${UtilDomi.getPath(this,uriImage!!)!!.toString()}")
            dialogEditProfile.tempPath = UtilDomi.getPath(this,uriImage!!)!!.toString()
            dialogEditProfile.onImageCharge()
        }
    }
    //newPostListener
    override fun onImageButtonListener() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type= "image/*"
        galleryLauncherNewPost.launch(intent)
    }

    override fun onCameraButtonListener() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        tempFile = File("${getExternalFilesDir(null)}/photo${numberFile}.png")
        val uri = FileProvider.getUriForFile(this,packageName, tempFile!!)
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri)
        galleryLauncherNewPostCamera.launch(intent)
    }

    override fun onPostListener() {
        TODO("Not yet implemented")
    }

    private fun onGalleryResultNewPost(result: ActivityResult){
        if(result.resultCode==RESULT_OK){
            val uriImage = result.data?.data
            Log.e(">>>","${uriImage}")
            Log.e(">>>","${UtilDomi.getPath(this,uriImage!!)!!.toString()}")
            postFragment.tempPath = UtilDomi.getPath(this,uriImage!!)!!.toString()
            postFragment.setImageUpdateListener()
        }
    }

    private fun onGalleryResultNewPostCamera(result: ActivityResult){
        if(result.resultCode==RESULT_OK){
            numberFile++
            val path = tempFile?.path
            postFragment.tempPath = path
            postFragment.setImageUpdateListener()
        }else if(result.resultCode==RESULT_CANCELED){
            Toast.makeText(this,"No se tomó ninguna foto", Toast.LENGTH_SHORT).show()
        }
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

    private fun serialize() {
        //Serializacion
        val json = Gson().toJson(State(DataBase.getUsers(),DataBase.getPost(),DataBase.getSession()))
        Log.e(">>>","serialize: ${json.toString()}")
        //Shared Preferences
        val sharedPref = getSharedPreferences("Preference",Context.MODE_PRIVATE)
        sharedPref.edit().putString("state",json).apply()
    }

}