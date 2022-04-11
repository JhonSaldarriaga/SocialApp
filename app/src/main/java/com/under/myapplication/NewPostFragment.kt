package com.under.myapplication

import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.under.myapplication.databinding.FragmentNewPostBinding
import com.under.myapplication.model.DataBase

class NewPostFragment : Fragment() {

    private var _binding: FragmentNewPostBinding? = null
    private val binding get() = _binding!!
    var newPostListener:NewPostListener? = null
    var tempPath:String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewPostBinding.inflate(inflater,container,false)
        updateInfo()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, resources.getStringArray(R.array.new_post_spinner_optiones))
        binding.spinnerLocality.adapter = adapter
        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        binding.newPostFragmentCancelImageIV.setOnClickListener {
            binding.newPostFragmentPostImageIV.isVisible = false
            binding.newPostFragmentCancelImageIV.isVisible = false
            binding.newPostFragmentPostImageIV.isGone = true
            binding.newPostFragmentCancelImageIV.isGone  = true
            tempPath = null
        }

        binding.newPostFragmenCamBTN.setOnClickListener {  }
        binding.newPostFragmentAddImageBTN.setOnClickListener {newPostListener?.onImageButtonListener()}
        binding.postBTN.setOnClickListener {  }

        return binding.root
    }

    fun setImageUpdateListener(){
        val bitmap = BitmapFactory.decodeFile(tempPath)
        binding.newPostFragmentPostImageIV.isGone = false
        binding.newPostFragmentCancelImageIV.isGone = false
        binding.newPostFragmentPostImageIV.isVisible = true
        binding.newPostFragmentCancelImageIV.isVisible = true
        binding.newPostFragmentPostImageIV.setImageBitmap(bitmap)
    }

    fun updateInfo(){
        var actualUser = DataBase.getUserByID(DataBase.getSession()!!.userID)
        var profilePic = actualUser?.getImageProfilePaht()
        val profileName = actualUser?.getName()

        if(profilePic!=""){
            val bitmap = BitmapFactory.decodeFile(profilePic)
            binding.newPostFragmentProfilePicIV.setImageBitmap(bitmap)
        }else{ binding.newPostFragmentProfilePicIV.setImageResource(R.drawable.default_profile_pic) }

        binding.newPostFragmentProfileNameTV.text = profileName
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewPostFragment()
    }

    interface NewPostListener{
        fun onImageButtonListener()
        fun onCameraButtonListener()
        fun onPostListener()
    }
}