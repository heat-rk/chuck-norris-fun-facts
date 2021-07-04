package ru.heatalways.chucknorrisfunfacts.tests

import android.Manifest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.presentation.screen.main.MainActivity
import ru.heatalways.chucknorrisfunfacts.recycler_items.CategoryItem
import ru.heatalways.chucknorrisfunfacts.screens.CategorySelectionScreen
import ru.heatalways.chucknorrisfunfacts.screens.RandomJokeScreen
import ru.heatalways.chucknorrisfunfacts.screens.SearchJokeScreen

class RandomJokeScreenTest: TestCase() {
    @get:Rule
    val runtimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.INTERNET
    )

    @get:Rule
    var rule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testCategorySelection_shouldSelectAnimal() {
        run {
            step("1. Open random joke screen") {
                SearchJokeScreen.bottomNavigation {
                    flakySafely {
                        setSelectedItem(R.id.navJokeRandom)
                    }
                }
            }

            step("2. Select category button click (navigate to categories screen)") {
                RandomJokeScreen.selectCategoryButton {
                    flakySafely {
                        isVisible()
                        click()
                    }
                }
            }

            step("3. Type search query") {
                CategorySelectionScreen.searchQueryView.editText {
                    flakySafely {
                        isVisible()
                        typeText("animal")
                    }
                }
            }

            step("4. Click search button") {
                CategorySelectionScreen.searchQueryView.searchButton {
                    flakySafely {
                        isVisible()
                        click()
                    }
                }
            }

            step("5. Select animal category") {
                CategorySelectionScreen.categories {
                    childWith<CategoryItem> { withText("animal") } perform {
                        button {
                            flakySafely {
                                isVisible()
                                click()
                            }
                        }
                    }
                }
            }

            step("6. Check selected category") {
                RandomJokeScreen.selectCategoryButton {
                    flakySafely {
                        isVisible()
                        hasText("animal")
                    }
                }
            }
        }
    }
}