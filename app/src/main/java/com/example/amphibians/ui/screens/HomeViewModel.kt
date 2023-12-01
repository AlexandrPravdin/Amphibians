package com.example.amphibians.ui.screens

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibians.AmphibiansApplication
import com.example.amphibians.data.AmphibianInfoRepository
import com.example.amphibians.model.AmphibianInfo
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface HomeUiState {
    data class Success(
        val amphibiansCard: List<AmphibianInfo>
    ) : HomeUiState

    object Error : HomeUiState
    object Loading : HomeUiState
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class HomeViewModel(
    private val amphibianRepository: AmphibianInfoRepository
) : ViewModel() {

    var uiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getAmphibians()
    }

    fun getAmphibians() {
        viewModelScope.launch {
            uiState = HomeUiState.Loading
            uiState = try {
                HomeUiState.Success(
                    amphibianRepository.getAmphibians()
                )
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AmphibiansApplication)
                val amphibianRepository = application.container.amphibianInfoRepository
                HomeViewModel(amphibianRepository = amphibianRepository)
            }
        }
    }

}