package ar.com.westsoft.listening

import androidx.lifecycle.ViewModel
import ar.com.westsoft.listening.data.Reader
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val reader: Reader
) : ViewModel() {
    fun onButtonClicked() {
        reader.speakOut("Click on button!")
    }
}