package com.proway.gitrepoapp.ui.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.proway.gitrepoapp.R
import com.proway.gitrepoapp.ViewModel.ListViewModel
import com.proway.gitrepoapp.adapter.AdapterRepositorios
import com.proway.gitrepoapp.databinding.ListFragmentBinding
import com.proway.gitrepoapp.model.ItemRepoList
import com.proway.gitrepoapp.model.LanguagesResponse
import com.proway.gitrepoapp.model.RepositoriesResponse
import com.proway.gitrepoapp.singletons.SingletonLangs
import com.proway.gitrepoapp.singletons.SingletonRepoResponse
import com.proway.gitrepoapp.utils.replaceView
import dagger.hilt.EntryPoint
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
        viewModel.callGetRepoPrs(repoResp.ownerInfo.login, repoResp.repoName)
    }
    private lateinit var binding: ListFragmentBinding
    private var observerResp = Observer<Boolean> {
        if (it == true) {
            requireActivity().replaceView(DetailsFragment())
        } else {
            Snackbar.make(
                requireView(),
                "Ah.. parece que esse reposiotorio nao teve pull requests.",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
    private var observerLang = Observer<Boolean> {
        if (it == true) {
            SingletonRepoResponse.resp?.let { adapter.refresh(it) }
        } else {
            Snackbar.make(requireView(), "Ocorreu um erro na pesquisa", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ListFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.changes.observe(viewLifecycleOwner, observerResp)
        viewModel.refresh.observe(viewLifecycleOwner, observerLang)
        recycler = binding.recyclerViewNoXml
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter
        SingletonLangs.resp?.let { setupAutoComplete(it) }
        SingletonRepoResponse.resp?.let { adapter.refresh(it) }
    binding.include.buttonSearch.setOnClickListener {
        viewModel.callRepoByLangs(selectedLanguage)
    }

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

