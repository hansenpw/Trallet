package com.microlabs.trallet.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class BaseActivity<A : ViewDataBinding, B : ViewModel> : AppCompatActivity() {

    abstract val layout: Int
    protected lateinit var viewModel: B
        private set
    protected lateinit var binding: A
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layout)
        viewModel = initViewModel()
    }

    abstract fun initViewModel(): B
}