package ru.heatalways.chucknorrisfunfacts.presentation.base

abstract class MviReducer<State: MviState, PartialState>(
    val reduce: State.(partialState: PartialState) -> State
)