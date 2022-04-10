package com.under.myapplication

import android.content.Intent
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
    var logoutListener:OnLogoutListener? = null

    //BINDING
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        var actualUser = DataBase.getUserByID(DataBase.getSession()!!.userID)
        var profilePic = actualUser?.getImageProfilePaht()
        val profileName = actualUser?.getName()

        binding.profileFragmentNameTV.text = profileName

        if(profilePic!=""){
            binding.profileFragmentProfilePicIV.setImageURI(Uri.fromFile(File(profilePic)))
        }else{ binding.profileFragmentProfilePicIV.setImageResource(R.drawable.default_profile_pic) }

        binding.logoutBTN.setOnClickListener{
            logoutListener?.onLogoutListener()
        }

        return binding.root
    }

    interface OnLogoutListener{
        fun onLogoutListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}