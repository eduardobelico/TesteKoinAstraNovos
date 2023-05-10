package br.com.chicorialabs.astranovos.presentation.ui.home

import androidx.lifecycle.*
import br.com.chicorialabs.astranovos.core.Query
import br.com.chicorialabs.astranovos.core.RemoteException
import br.com.chicorialabs.astranovos.core.State
import br.com.chicorialabs.astranovos.data.SpaceFlightNewsCategory
import br.com.chicorialabs.astranovos.data.model.Post
import br.com.chicorialabs.astranovos.domain.PostUseCases.GetLatestPostsTitleContainsUseCase
import br.com.chicorialabs.astranovos.domain.PostUseCases.GetLatestPostsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


/**
 * Essa classe dá suporte à tela principal (Home).
 */
class HomeViewModel(
    private val getLatestPostsUseCase: GetLatestPostsUseCase,
    private val getLatestPostsTitleContainsUseCase: GetLatestPostsTitleContainsUseCase
) : ViewModel() {

    /**
     * Esse campo controla a visibilidade da progress bar.
     */

    private val _progressBarVisible = MutableLiveData<Boolean>(false)
    val progressBarVisible: LiveData<Boolean>
        get() = _progressBarVisible

    fun showProgressBar() {
        _progressBarVisible.value = true
    }

    fun hideProgressBar() {
        _progressBarVisible.value = false
    }

    private val _category = MutableLiveData<SpaceFlightNewsCategory>().apply {
        value = SpaceFlightNewsCategory.ARTICLES
    }

    val category: LiveData<SpaceFlightNewsCategory>
        get() = _category

    /**
     * Esse campo controla a visibilidade e a mensagem da snackbar.
     */

    private val _snackbar = MutableLiveData<String?>(null)
    val snackbar: LiveData<String?>
        get() = _snackbar

    fun onSnackBarShown() {
        _snackbar.value = null
    }

    private val _listPost = MutableLiveData<State<List<Post>>>()
    val listPost: LiveData<State<List<Post>>>
        get() = _listPost

    init {
        fetchLatest(_category.value ?: SpaceFlightNewsCategory.ARTICLES)
    }

    fun fetchLatest(category: SpaceFlightNewsCategory) {
        fetchPosts(Query(category.value))
//        _category.value = category
    }

    /**
     * Esse método coleta o fluxo do repositorio e atribui
     * o seu valor ao campo _listPost
     */
    private fun fetchPosts(query: Query) {
        viewModelScope.launch {
            getLatestPostsUseCase(query)
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
                    _category.value = enumValueOf<SpaceFlightNewsCategory>(query.type.uppercase())
                }
        }
    }

    private fun fetchPostsTitleContains(query: Query) {
        viewModelScope.launch {
            getLatestPostsTitleContainsUseCase(query)
                .onStart {
                    _listPost.postValue(State.Loading)
                }
                .catch {
                    val exception = RemoteException("Unable to connect to SpaceFlightNews API")
                    _listPost.postValue(State.Error(exception))
                    _snackbar.value = exception.message
                }
                .collect { listPost ->
                    _listPost.postValue(State.Success(listPost))
                    _category.value = enumValueOf<SpaceFlightNewsCategory>(query.type.uppercase())
                }
        }
    }

    fun searchPostsTitleContains(searchString: String) {
        fetchPostsTitleContains(Query(_category.value.toString(), searchString))
    }


    val helloText = Transformations.map(listPost) { state ->
        when (state) {
            State.Loading -> {
                "🚀 Loading latest news..."
            }
            is State.Error -> {
                "Houston, we've had a problem!!"
            }
            else -> {
                ""
            }
        }
    }

}
