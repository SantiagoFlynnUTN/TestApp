package com.flynn.feature_home

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class HomeReducerTest {

    private lateinit var reducer: HomeReducer

    @Before
    fun setUp() {
        reducer = HomeReducer()
    }

    @Test
    fun `reduce with SetConvertedContent action updates convertedContent`() {
        val initialState = HomeState(
            convertedContent = "",
            loadingTopBox = false,
            loadingBottomBox = false,
            contentWordsCounted = emptyMap()
        )
        val action = HomeReduceAction.SetConvertedContent("Test content")

        val newState = reducer.reduce(initialState, action)

        assertEquals("Test content", newState.convertedContent)
        assertEquals(initialState.loadingTopBox, newState.loadingTopBox)
        assertEquals(initialState.loadingBottomBox, newState.loadingBottomBox)
        assertEquals(initialState.contentWordsCounted, newState.contentWordsCounted)
    }

    @Test
    fun `reduce with ShowLoadingTop action updates loadingTopBox`() {
        val initialState = HomeState(
            convertedContent = "",
            loadingTopBox = false,
            loadingBottomBox = false,
            contentWordsCounted = emptyMap()
        )
        val action = HomeReduceAction.ShowLoadingTop(true)

        val newState = reducer.reduce(initialState, action)

        assertTrue(newState.loadingTopBox)
        assertEquals(initialState.convertedContent, newState.convertedContent)
        assertEquals(initialState.loadingBottomBox, newState.loadingBottomBox)
        assertEquals(initialState.contentWordsCounted, newState.contentWordsCounted)
    }

    @Test
    fun `reduce with ShowLoadingBottom action updates loadingBottomBox`() {
        val initialState = HomeState(
            convertedContent = "",
            loadingTopBox = false,
            loadingBottomBox = false,
            contentWordsCounted = emptyMap()
        )
        val action = HomeReduceAction.ShowLoadingBottom(true)

        val newState = reducer.reduce(initialState, action)

        assertTrue(newState.loadingBottomBox)
        assertEquals(initialState.convertedContent, newState.convertedContent)
        assertEquals(initialState.loadingTopBox, newState.loadingTopBox)
        assertEquals(initialState.contentWordsCounted, newState.contentWordsCounted)
    }

    @Test
    fun `reduce with ClearProcessedContent action clears convertedContent and contentWordsCounted`() {
        val initialState = HomeState(
            convertedContent = "Test content",
            loadingTopBox = false,
            loadingBottomBox = false,
            contentWordsCounted = mapOf("Test" to 1)
        )
        val action = HomeReduceAction.ClearProcessedContent

        val newState = reducer.reduce(initialState, action)

        assertEquals("", newState.convertedContent)
        assertTrue(newState.contentWordsCounted.isEmpty())
        assertEquals(initialState.loadingTopBox, newState.loadingTopBox)
        assertEquals(initialState.loadingBottomBox, newState.loadingBottomBox)
    }

    @Test
    fun `reduce with SetWordsCounted action updates contentWordsCounted`() {
        val initialState = HomeState(
            convertedContent = "",
            loadingTopBox = false,
            loadingBottomBox = false,
            contentWordsCounted = emptyMap()
        )
        val wordCount = mapOf("Test" to 1, "content" to 1)
        val action = HomeReduceAction.SetWordsCounted(wordCount)

        val newState = reducer.reduce(initialState, action)

        assertEquals(wordCount, newState.contentWordsCounted)
        assertEquals(initialState.convertedContent, newState.convertedContent)
        assertEquals(initialState.loadingTopBox, newState.loadingTopBox)
        assertEquals(initialState.loadingBottomBox, newState.loadingBottomBox)
    }
}