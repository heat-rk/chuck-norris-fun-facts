package ru.heatalways.chucknorrisfunfacts.common.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import ru.heatalways.chucknorrisfunfacts.common.utils.MainCoroutineRule

@ExperimentalCoroutinesApi
abstract class BaseViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()
}