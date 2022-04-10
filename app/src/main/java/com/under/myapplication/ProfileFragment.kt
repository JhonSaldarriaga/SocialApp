package com.under.myapplication

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.under.myapplication.databinding.FragmentProfileBinding
import com.under.myapplication.model.DataBase
import java.io.File

class ProfileFragment : Fragment() {

    //INTERFACE
    var profileListener:ProfileListener? = null

    //BINDING
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater,container,false)

        updateInfo()
        binding.logoutBTN.setOnClickListener{
            profileListener?.onLogoutListener()
        }

        binding.profileFragmentEditProfileBTN.setOnClickListener{
            profileListener?.onEditProfileListener()
        }

        return binding.root
    }

    fun updateInfo(){
        var actualUser = DataBase.getUserByID(DataBase.getSession()!!.userID)
        var profilePic = actualUser?.getImageProfilePaht()
        val profileName = actualUser?.getName()

        if(profilePic!=""){
            val bitmap = BitmapFactory.decodeFile(profilePic)
            binding.profileFragmentProfilePicIV.setImageBitmap(bitmap)
        }else{ binding.profileFragmentProfilePicIV.setImageResource(R.drawable.default_profile_pic) }

        binding.profileFragmentNameTV.text = profileName
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }

    interface ProfileListener{
        fun onLogoutListener()
        fun onEditProfileListener()
    }
}