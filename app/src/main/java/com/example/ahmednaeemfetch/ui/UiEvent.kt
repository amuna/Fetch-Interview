package com.example.ahmednaeemfetch.ui

enum class UiEvent {
    /**
     * Indicates an error state, where an error message and retry option should be shown.
     */
    ERROR,

    /**
     * Indicates that results are available and should be displayed in the UI.
     */
    SHOW_RESULTS,

    /**
     * Indicates a loading state, where a spinner should be displayed.
     */
    LOADING
}