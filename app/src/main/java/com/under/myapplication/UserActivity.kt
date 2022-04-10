package com.under.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.under.myapplication.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {

    private val binding: ActivityUserBinding by lazy {
        ActivityUserBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}