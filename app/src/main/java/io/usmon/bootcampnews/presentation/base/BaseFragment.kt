package io.usmon.bootcampnews.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

// Created by Usmon Abdurakhmanv on 5/6/2022.

abstract class BaseFragment<VB : ViewBinding>(val bindingInflater: (LayoutInflater) -> VB) : Fragment() {

    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!

    protected abstract fun myCreateView(savedInstanceState: Bundle?)

    protected open fun myCreate() = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myCreate()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = bindingInflater.invoke(layoutInflater)
        if (_binding == null)
            throw IllegalArgumentException("View binding couldn't be null")
        myCreateView(savedInstanceState)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    open fun setupActionBar(title: String?, showBack: Boolean = false) {
        (activity as AppCompatActivity).supportActionBar?.apply {
            show()
            this.title = title
            setDisplayHomeAsUpEnabled(showBack)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}