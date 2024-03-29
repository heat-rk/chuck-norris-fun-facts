package ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke

import android.Manifest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.rule.GrantPermissionRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test
import ru.heatalways.navigation.R
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.view_holders.CategoryItem
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.view_holders.JokeItem
import ru.heatalways.chucknorrisfunfacts.presentation.screen.MainActivity
import ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category.CategorySelectionScreen
import ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke.SearchJokeScreen

@LargeTest
class RandomJokeScreenTest: TestCase() {
    @get:Rule(order = 1)
    val runtimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.INTERNET
    )

    @get:Rule(order = 2)
    var rule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testCategorySelection_shouldSelectAnimal() = run {
        step("1. Open random joke screen") {
            SearchJokeScreen.bottomNavigation {
                flakySafely {
                    setSelectedItem(R.id.nav_graph_random)
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

        step("4. Select animal category") {
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

        step("5. Check selected category") {
            RandomJokeScreen.selectCategoryButton {
                flakySafely {
                    isVisible()
                    hasText("animal")
                }
            }
        }
    }

    @Test
    fun testGetRandomJoke_shouldAddOneJokeToList() = run {
        step("1. Open random joke screen") {
            SearchJokeScreen.bottomNavigation {
                flakySafely {
                    setSelectedItem(R.id.nav_graph_random)
                }
            }
        }



        step("2. Click get joke button") {
            RandomJokeScreen.geJokeButton {
                flakySafely {
                    isVisible()
                    click()
                }
            }
        }

        step("3. Check that recycler view size is increased by one") {
            RandomJokeScreen.recyclerView {
                flakySafely {
                    assert(getSize() == 1)
                }
            }
        }

        step("4. Check that new item has image and text") {
            RandomJokeScreen.recyclerView.firstChild<JokeItem> {
                image {
                    flakySafely {
                        isVisible()
                        isCompletelyDisplayed()
                    }
                }

                text {
                    flakySafely {
                        isVisible()
                        isCompletelyDisplayed()
                        hasAnyText()
                    }
                }
            }
        }
    }
}