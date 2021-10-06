package ru.heatalways.chucknorrisfunfacts.presentation.screen

import dagger.hilt.android.AndroidEntryPoint
import ru.heatalways.chucknorrisfunfacts.databinding.ActivityMainBinding
import ru.heatalways.chucknorrisfunfacts.presentation.base.BindingActivity

@AndroidEntryPoint
class MainActivity: BindingActivity<ActivityMainBinding>(
    bindingInflater = ActivityMainBinding::inflate
)