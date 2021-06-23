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
    fun testSearchView() {
        run {
            step("1. Enter short search query") {
                SearchJokeScreen {
                    search {
                        flakySafely {
                            isVisible()
                            typeText("he")
                        }
                    }

                    searchButton {
                        flakySafely { isGone() }
                    }
                }
            }

            step("2. Enter valid search query") {
                SearchJokeScreen {
                    search {
                        flakySafely {
                            isVisible()
                            clearText()
                            typeText("hey")
                        }
                    }

                    searchButton {
                        flakySafely { isVisible() }
                    }
                }
            }

            step("3. Search button click") {
                SearchJokeScreen {
                    searchButton {
                        flakySafely { click() }
                    }
                }
            }

            step("4. Check recycler view size") {
                SearchJokeScreen {
                    recyclerView {
                        flakySafely {
                            isVisible()
                            assert(getSize() > 0)
                        }
                    }
                }
            }
        }
    }
}