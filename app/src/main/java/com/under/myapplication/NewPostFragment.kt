package com.under.myapplication

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.under.myapplication.databinding.FragmentNewPostBinding
import com.under.myapplication.model.DataBase
import com.under.myapplication.model.DataBase.toString
import java.text.SimpleDateFormat
import java.util.*

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

        binding.newPostFragmenCamBTN.setOnClickListener {newPostListener?.onCameraButtonListener()}
        binding.newPostFragmentAddImageBTN.setOnClickListener {newPostListener?.onImageButtonListener()}
        binding.postBTN.setOnClickListener {
            val text = binding.newPostFragmentUpdateET.text.toString()
            if(text != "") {
                val location = binding.spinnerLocality.selectedItem.toString()
                val date = DataBase.getCurrentDateTime()
                val formatter = SimpleDateFormat("d/MM/yyyy")
                val dateString = formatter.format(date)

                if (tempPath != null) {
                    DataBase.addPost(text, location, dateString, tempPath!!)
                } else {
                    DataBase.addPost(text, location, dateString)
                }
                newPostListener?.onPostListener()
                Toast.makeText(requireContext(),R.string.new_post_success, Toast.LENGTH_SHORT).show()
                reset()
            }else{Toast.makeText(requireContext(),R.string.new_post_empty_update, Toast.LENGTH_SHORT).show()}
        }
        return binding.root
    }

    fun reset(){
        binding.newPostFragmentPostImageIV.isVisible = false
        binding.newPostFragmentCancelImageIV.isVisible = false
        binding.newPostFragmentPostImageIV.isGone = true
        binding.newPostFragmentCancelImageIV.isGone  = true
        binding.newPostFragmentUpdateET.setText("")
        tempPath = null
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