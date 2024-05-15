package com.flynn.data.repository

import com.flynn.data.local.room.LocalDatabaseResource
import com.flynn.data.remote.NetworkDataSource
import com.flynn.domain.resource.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response


@ExperimentalCoroutinesApi
class RepositoryImplTest {

    private val localDatabaseResource: LocalDatabaseResource = mock()
    private val networkDataSource: NetworkDataSource = mock()
    private lateinit var repository: RepositoryImpl

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = RepositoryImpl(localDatabaseResource, networkDataSource)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test getContent with local data and different remote data`() = testScope.runTest {
        val localContent = "Local Content"
        val remoteContent = "Remote Content"
        val response = Response.success(remoteContent)

        whenever(localDatabaseResource.getContent()).thenReturn(localContent)
        whenever(networkDataSource.getContent()).thenReturn(response)

        val result = repository.getContent().toList()

        assertEquals(3, result.size)
        assert(result[0] is Resource.Loading)

        val successResources = result.filterIsInstance<Resource.Success<String>>().map { it.data }

        assert(successResources.contains(localContent))
        assert(successResources.contains(remoteContent))

        verify(localDatabaseResource).saveContent(remoteContent)
    }

    @Test
    fun `test getContent same content on local data and remote data`() = testScope.runTest {
        val localContent = "Some Content"
        val remoteContent = "Some Content"
        val response = Response.success(remoteContent)

        whenever(localDatabaseResource.getContent()).thenReturn(localContent)
        whenever(networkDataSource.getContent()).thenReturn(response)

        val result = repository.getContent().toList()

        assertEquals(2, result.size)
        assert(result[0] is Resource.Loading)

        val successResources = result.filterIsInstance<Resource.Success<String>>().map { it.data }

        assert(successResources.size == 1)
        assert(successResources.contains(localContent))
        assert(successResources.contains(remoteContent))
    }

    @Test
    fun `test getContent locally with remote failure`() = testScope.runTest {
        val localContent = "Local Content"
        val exception = RuntimeException("Network Error")

        whenever(localDatabaseResource.getContent()).thenReturn(localContent)
        whenever(networkDataSource.getContent()).thenThrow(exception)

        val result = repository.getContent().toList()

        assertEquals(3, result.size)
        assert(result[0] is Resource.Loading)

        val successResources = result.filterIsInstance<Resource.Success<String>>().map { it.data }
        val failedResources = result.filterIsInstance<Resource.Failed>()

        assert(successResources.contains(localContent))
        assert(failedResources.isNotEmpty())

        verify(localDatabaseResource, never()).saveContent(any())
    }
}