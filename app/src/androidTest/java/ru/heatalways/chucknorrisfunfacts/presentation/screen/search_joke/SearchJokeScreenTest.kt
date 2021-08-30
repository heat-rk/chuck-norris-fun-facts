package ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke

import android.Manifest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import ru.heatalways.chucknorrisfunfacts.presentation.screen.main.MainActivity

@HiltAndroidTest
class SearchJokeScreenTest: TestCase() {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val runtimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.INTERNET
    )

    @get:Rule(order = 2)
    var rule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testShortSearchQuery_shouldHideButton() = run {
        step("1. Enter short search query") {
            SearchJokeScreen.searchQueryView.editText {
                flakySafely {
                    isVisible()
                    typeText("he")
                }
            }
        }

        step("2. Check search button visibility (should be GONE)") {
            SearchJokeScreen.searchQueryView.searchButton {
                flakySafely { isGone() }
            }
        }
    }


    @Test
    fun testValidSearchQueryResults_shouldShowResults() = run {
        step("1. Enter valid search query") {
            SearchJokeScreen.searchQueryView.editText {
                flakySafely {
                    isVisible()
                    typeText("hey")
                }
            }
        }

        step("2. Search button click") {
            SearchJokeScreen.searchQueryView.searchButton {
                flakySafely {
                    isVisible()
                    click()
                }
            }
        }

        step("3. Check if progress bar is visible") {
            SearchJokeScreen.progressBar {
                flakySafely {
                    isVisible()
                }
            }
        }

        step("4. Check recycler view size") {
            SearchJokeScreen.recyclerView {
                flakySafely {
                    isVisible()
                    assert(getSize() > 0)
                }
            }
        }
    }

    @Test
    fun testInvalidSearchQuery_shouldShowError() = run {
        step("1. Enter invalid search query") {
            SearchJokeScreen.searchQueryView.editText {
                flakySafely {
                    isVisible()
                    typeText("heydkngkdognskmfkbnkfg")
                }
            }
        }

        step("2. Search button click") {
            SearchJokeScreen.searchQueryView.searchButton {
                flakySafely {
                    isVisible()
                    click()
                }
            }
        }

        step("3. Check error visibility (should be VISIBLE)") {
            SearchJokeScreen.errorTextView {
                flakySafely {
                    isVisible()
                }
            }
        }
    }
}