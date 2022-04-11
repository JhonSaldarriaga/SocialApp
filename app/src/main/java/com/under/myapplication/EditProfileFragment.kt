package com.under.myapplication

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.under.myapplication.databinding.FragmentEditProfileBinding
import com.under.myapplication.model.DataBase
import java.io.File

class EditProfileFragment : DialogFragment() {

    var editProfileListener:EditProfileListener?=null

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    var tempPath: String? = null

    //ACOMODA LA VISTA
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditProfileBinding.inflate(inflater,container,false)

        Log.e(">>>","createview")
        loadInfo()

        binding.editProfileChangeImageIV.setOnClickListener {
            editProfileListener?.onChangeProfilePicListener()
        }

        binding.editProfileCancelIV.setOnClickListener {
            binding.editProfileNameET.setText("")
            dismiss()
        }

        binding.editProfileApplyBTN.setOnClickListener {
            if (tempPath != null){
                DataBase.changeProfilePic(tempPath!!)
            }
            if(binding.editProfileNameET.text.toString()!=""){
                DataBase.changeProfileName(binding.editProfileNameET.text.toString())
            }
            editProfileListener?.onEditUserValuesApplyListener()
            binding.editProfileNameET.setText("")
            dismiss()
        }

        return binding.root
    }

    fun loadInfo(){
        var actualUser = DataBase.getUserByID(DataBase.getSession()!!.userID)
        var profilePic = actualUser?.getImageProfilePaht()
        val profileName = actualUser?.getName()

        if(profilePic!=""){
            binding.editProfileProfilePicIV.setImageURI(Uri.fromFile(File(profilePic)))
        }else{ binding.editProfileProfilePicIV.setImageResource(R.drawable.default_profile_pic) }

        binding.editProfileNameET.hint = profileName
    }

    fun onImageCharge(){
        binding.editProfileProfilePicIV.setImageURI(Uri.fromFile(File(tempPath)))
    }

    override fun onStop() {
        super.onStop()
        tempPath = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = EditProfileFragment()
    }

    interface EditProfileListener{
        fun onChangeProfilePicListener()
        fun onEditUserValuesApplyListener()
    }
}