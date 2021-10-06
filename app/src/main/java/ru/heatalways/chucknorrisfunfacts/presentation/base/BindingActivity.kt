package ru.heatalways.chucknorrisfunfacts.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BindingActivity<Binding: ViewBinding>(
    private val bindingInflater: (LayoutInflater) -> Binding
): AppCompatActivity() {
    protected lateinit var binding: Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingInflater(layoutInflater)
        setContentView(binding.root)
    }
}