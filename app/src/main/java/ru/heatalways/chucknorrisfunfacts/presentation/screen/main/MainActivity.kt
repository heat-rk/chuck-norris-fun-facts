package ru.heatalways.chucknorrisfunfacts.presentation.screen.main

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import net.yslibrary.android.keyboardvisibilityevent.Unregistrar
import ru.heatalways.chucknorrisfunfacts.App
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.databinding.ActivityMainBinding
import ru.heatalways.chucknorrisfunfacts.extensions.setVisibleOrGone
import ru.heatalways.chucknorrisfunfacts.extensions.showSmoothly
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseActivity
import ru.heatalways.chucknorrisfunfacts.presentation.base.KeyboardChangeListener

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val mainViewModel: MainViewModel by viewModels()

    private var keyboardListenerUnregister: Unregistrar? = null
    private val keyboardChangeListeners = mutableListOf<KeyboardChangeListener>()

    override fun getBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }

    override fun getFragmentContainerId() = R.id.fragmentContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.bottomNavigationBar.setOnNavigationItemSelectedListener {
            mainViewModel.onMenuItemSelect(it.itemId)
            return@setOnNavigationItemSelectedListener true
        }

        mainViewModel.currentScreen.observe(this, {
            App.router.replaceScreen(it)
        })

        keyboardListenerUnregister = KeyboardVisibilityEvent.registerEventListener(
            activity = this,
            listener = object: KeyboardVisibilityEventListener {
                override fun onVisibilityChanged(isOpen: Boolean) {
                    keyboardChangeListeners.forEach { callback ->
                        callback.onKeyboardChanged(isOpen)
                    }
                }
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        keyboardListenerUnregister?.unregister()
    }

    fun setBottomMenuVisibility(isVisible: Boolean) {
        binding.bottomNavigationBar.apply {
            if (isVisible) showSmoothly()
            else setVisibleOrGone(false)
        }
    }

    fun addKeyboardListener(listener: KeyboardChangeListener) {
        keyboardChangeListeners.add(listener)
    }

    fun removeKeyboardListener(listener: KeyboardChangeListener) {
        keyboardChangeListeners.remove(listener)
    }
}