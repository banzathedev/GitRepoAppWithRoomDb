package com.proway.gitrepoapp.ui.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.proway.gitrepoapp.R
import com.proway.gitrepoapp.ViewModel.ListViewModel
import com.proway.gitrepoapp.adapter.AdapterRepositorios
import com.proway.gitrepoapp.databinding.ListFragmentBinding
import com.proway.gitrepoapp.model.GithubModel
import com.proway.gitrepoapp.model.LanguagesResponse
import com.proway.gitrepoapp.singletons.SingletonLangs

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.list_fragment) {

    companion object {
        fun newInstance() = ListFragment()
    }

    private lateinit var viewModel: ListViewModel
    private lateinit var recycler: RecyclerView
    private var selectedLanguage = "Kotlin"
    private var adapter = AdapterRepositorios() { repoResp ->
       //TODO
    }
    private lateinit var binding: ListFragmentBinding
//    private var observerResp = Observer<Boolean> {
//        if (it == true) {
//            requireActivity().replaceView(DetailsFragment())
//        } else {
//            Snackbar.make(
//                requireView(),
//                "Ah.. parece que esse reposiotorio nao teve pull requests.",
//                Snackbar.LENGTH_LONG
//            ).show()
//        }
//    }
//    private var observerLang = Observer<Boolean> {
//        if (it == true) {
//         //   SingletonRepoResponse.resp?.let { adapter.refresh(it) }
//        } else {
//            Snackbar.make(requireView(), "Ocorreu um erro na pesquisa", Snackbar.LENGTH_LONG).show()
//        }
//    }
    private val observePage = Observer<Int> { page ->
        viewModel.fetchRepositories(selectedLanguage, page)
    }

    private val observeRepositories = Observer<List<GithubModel>> { list ->
        /**
         * Escondemos o label de Loading quando a lista foi carregada.
         */
        adapter.refresh(list)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ListFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        //desativados
       // viewModel.changes.observe(viewLifecycleOwner, observerResp)
        //viewModel.refresh.observe(viewLifecycleOwner, observerLang)
        viewModel.repositories.observe(viewLifecycleOwner, observeRepositories)
        viewModel.page.observe(viewLifecycleOwner, observePage)
        recycler = binding.recyclerViewNoXml
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter
        SingletonLangs.resp?.let { setupAutoComplete(it) }
      //  SingletonRepoResponse.resp?.let { adapter.refresh(it) }
    binding.include.buttonSearch.setOnClickListener {
        setEventsForButtons()

        callForMoreItems()
    }

    }
    private fun setEventsForButtons() {

        /**
         * Add um listener no scroll do recycler view para verificar quando ele chega no
         * final da lista e chamamos a próxima página
         *
         */
        binding.recyclerViewNoXml.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                /**
                 * Verificar se o scroll chegou ao final da lista, se sim chama o nextPage()
                 *
                 */
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    callForMoreItems()
                }
            }

        })
    }

    /**
     * Sempre que for chamar a página mostra o label de Loading para o user. Este campo será escondido
     * no observer da lista de repositorios.
     */
    fun callForMoreItems() {

        viewModel.nextPage()
    }





    private fun setupAutoComplete(languages: List<LanguagesResponse>) {

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line, languages
        )

        val autoCompleteTextView = binding.include.autoComplete
        autoCompleteTextView.setAdapter(adapter)

        autoCompleteTextView.setOnItemClickListener { adapterView, p1, position, p3 ->
            selectedLanguage = adapterView.getItemAtPosition(position).toString()

        }
    }
}

