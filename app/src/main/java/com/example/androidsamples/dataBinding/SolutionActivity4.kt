package com.example.androidsamples.dataBinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.androidsamples.R
import com.example.androidsamples.dataBinding.data.SimpleViewModelSolution
import com.example.androidsamples.databinding.ActivitySolution3Binding
import com.example.androidsamples.databinding.ActivitySolution4Binding

class SolutionActivity4 : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(SimpleViewModelSolution::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySolution4Binding =
            DataBindingUtil.setContentView(this, R.layout.activity_solution4)

        binding.lifecycleOwner = this

        binding.viewmodel = viewModel
    }
}