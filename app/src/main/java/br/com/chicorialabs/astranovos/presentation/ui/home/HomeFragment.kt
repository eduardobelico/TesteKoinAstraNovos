package br.com.chicorialabs.astranovos.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.com.chicorialabs.astranovos.R
import br.com.chicorialabs.astranovos.core.State
import br.com.chicorialabs.astranovos.data.SpaceFlightNewsCategory
import br.com.chicorialabs.astranovos.databinding.HomeFragmentBinding
import br.com.chicorialabs.astranovos.presentation.adapter.PostListAdapter
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Essa classe representa o fragmento da tela Home.
 */
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private val binding: HomeFragmentBinding by lazy {
        HomeFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initBinding()
        initSnackBar()
        initRecyclerView()
        initOptionMenu()

        return binding.root
    }

    private fun initOptionMenu() {
        with(binding.homeToolbar) {

            inflateMenu(R.menu.options_menu)

            menu.findItem(R.id.action_get_articles).setOnMenuItemClickListener {
                viewModel.fetchLatest(SpaceFlightNewsCategory.ARTICLES)
                true
            }

            menu.findItem(R.id.action_get_blogs).setOnMenuItemClickListener {
                viewModel.fetchLatest(SpaceFlightNewsCategory.BLOGS)
                true
            }

            menu.findItem(R.id.action_get_reports).setOnMenuItemClickListener {
                viewModel.fetchLatest(SpaceFlightNewsCategory.REPORTS)
                true
            }
        }
    }

    private fun initSnackBar() {
        viewModel.snackbar.observe(viewLifecycleOwner) {
            it?.let { errorMessage ->
                Snackbar.make(
                    binding.root,
                    errorMessage,
                    Snackbar.LENGTH_LONG
                ).show()
                viewModel.onSnackBarShown()
            }
        }
    }

    private fun initRecyclerView() {

        val adapter = PostListAdapter()
        binding.homeRv.adapter = adapter

        viewModel.listPost.observe(viewLifecycleOwner) { state ->
            when(state){
                State.Loading -> {
                    viewModel.showProgressBar()
                }
                is State.Error -> {
                    viewModel.hideProgressBar()
                }
                is State.Success -> {
                    viewModel.hideProgressBar()
                    adapter.submitList(state.result)
                }
            }
        }
    }

    /**
     * Esse método faz a inicialização do DataBinding.
     * O arquivo XML possui uma variável viewModel, que precisa
     * ser vinculada ao ViewModel instanciado. Também é preciso
     * atribuir um LifeCycleOwner para que os bindings de live data
     * funcionem.
     */
    private fun initBinding() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }


    /**
     * Esse companion object é código boilerplate que provavelmente
     * não será usado.
     */
    companion object {
        fun newInstance() = HomeFragment()
    }

}