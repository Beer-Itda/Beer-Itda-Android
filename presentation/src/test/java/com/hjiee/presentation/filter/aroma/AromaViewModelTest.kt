package com.hjiee.presentation.filter.aroma

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hjiee.data.api.BeerApi
import com.hjiee.data.repository.BeerRepositoryImpl
import com.hjiee.domain.repository.BeerRepository
import com.hjiee.domain.usecase.filter.aroma.GetAromaUseCase
import com.hjiee.domain.usecase.filter.aroma.PostAromaUseCase
import com.hjiee.presentation.ui.common.filter.FilterStringProvider
import com.hjiee.presentation.ui.filter.aroma.viewmodel.AromaViewModel
import com.hjiee.presentation.util.NetworkStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class AromaViewModelTest {

    private lateinit var viewModel: AromaViewModel
    private lateinit var repository: FakeAromaRepository
    private lateinit var aromaUseCase: GetAromaUseCase
    private lateinit var selectAromaUseCase: PostAromaUseCase
    private lateinit var stringProvider: FilterStringProvider

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val testDispatchers = TestCoroutineDispatcher()


    @Before
    fun setUp() {
        repository = FakeAromaRepository()
        stringProvider = FilterStringProvider(Mockito.mock(Context::class.java))
        aromaUseCase = GetAromaUseCase(repository)
        selectAromaUseCase = PostAromaUseCase(repository)

        viewModel = AromaViewModel(
            aromaUseCase = aromaUseCase,
            selectUseCase = selectAromaUseCase,
            stringProvider = stringProvider
        )

        Dispatchers.setMain(testDispatchers)
    }

    @Test
    fun `향 데이터를 정상적으로 가져와 network status가 success이다`() {
        viewModel.load()
        assert(viewModel.networkStatus.value == NetworkStatus.SUCCESS)
    }

    @Test
    fun `향 데이터를 실패하여 network status가 fail이다`() {
        assert(viewModel.networkStatus.value == NetworkStatus.FAILURE)
    }

    @Test
    fun `선택된 향이 max`() {

        assert(viewModel.viewState.isMaxSelected.get())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatchers.cleanupTestCoroutines()
    }
}