package ru.heatalways.chucknorrisfunfacts.tests

import android.Manifest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test
import ru.heatalways.chucknorrisfunfacts.presentation.screen.main.MainActivity
import ru.heatalways.chucknorrisfunfacts.screens.SearchJokeScreen


class SearchJokeScreenTest: TestCase() {
    @get:Rule
    val runtimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.INTERNET
    )

    @get:Rule
    var rule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testShortSearchQuery_shouldHideButton() {
        run {
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

        step("3. Check recycler view size") {
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