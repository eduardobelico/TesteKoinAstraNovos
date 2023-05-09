package br.com.chicorialabs.astranovos.presentation.ui.home

import androidx.lifecycle.*
import br.com.chicorialabs.astranovos.core.RemoteException
import br.com.chicorialabs.astranovos.core.State
import br.com.chicorialabs.astranovos.data.SpaceFlightNewsCategory
import br.com.chicorialabs.astranovos.data.model.Post
import br.com.chicorialabs.astranovos.data.repository.PostRepository
import br.com.chicorialabs.astranovos.domain.PostUseCases.GetLatestPostsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


/**
 * Essa classe dá suporte à tela principal (Home).
 */
class HomeViewModel(private val getLatestPostsUseCase: GetLatestPostsUseCase) : ViewModel() {

    /**
     * Esse campo controla a visibilidade da progress bar.
     */

    private val _progressBarVisible = MutableLiveData<Boolean>(false)
    val progressBarVisible : LiveData<Boolean>
        get() = _progressBarVisible

    fun showProgressBar() {
        _progressBarVisible.value = true
    }

    fun hideProgressBar() {
        _progressBarVisible.value = false
    }

    /**
     * Esse campo controla a visibilidade e a mensagem da snackbar.
     */

    private val _snackbar = MutableLiveData<String?>(null)
    val snackbar : LiveData<String?>
    get() = _snackbar

    fun onSnackBarShown() {
        _snackbar.value = null
    }

    private val _listPost = MutableLiveData<State<List<Post>>>()
    val listPost: LiveData<State<List<Post>>>
        get() = _listPost

    init {
        fetchPosts()
    }

    /**
     * Esse método coleta o fluxo do repositorio e atribui
     * o seu valor ao campo _listPost
     */
    private fun fetchPosts() {
        viewModelScope.launch {
            getLatestPostsUseCase(SpaceFlightNewsCategory.ARTICLES.value)
                .onStart {
                    //fazer algo no começo do flow
                    _listPost.postValue(State.Loading)
                    delay(800)
                }
                .catch {
                    //tratar uma exceção
                    val exception = RemoteException("Unable to connect to SpaceFlightNews API")
                    _listPost.postValue(State.Error(exception))
                    _snackbar.value = exception.message
                }
                .collect { listPost ->
                _listPost.postValue(State.Success(listPost))
            }
        }
    }

    val helloText = Transformations.map(listPost) { state ->
        when(state) {
            State.Loading -> { "🚀 Loading latest news..." }
            is State.Error -> { "Houston, we've had a problem!!" }
            else -> { "" }
        }
    }

}
