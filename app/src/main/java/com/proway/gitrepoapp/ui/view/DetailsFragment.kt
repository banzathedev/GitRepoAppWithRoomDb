package com.proway.gitrepoapp.ui.view

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.proway.gitrepoapp.R
import com.proway.gitrepoapp.ViewModel.DetailsViewModel
import com.proway.gitrepoapp.adapter.AdapterRepoPrs
import com.proway.gitrepoapp.databinding.DetailsFragmentBinding
import com.proway.gitrepoapp.singletons.SingletonRepoPrs

class DetailsFragment : Fragment(R.layout.details_fragment) {

    companion object {
        fun newInstance() = DetailsFragment()
    }

    private lateinit var viewModel: DetailsViewModel
    private lateinit var recycler: RecyclerView
    private var adapter = AdapterRepoPrs() { repoPr ->
        val browser = Intent(Intent.ACTION_VIEW, Uri.parse(repoPr.htmlUrl))
        startActivity(browser)

    }
    private lateinit var binding: DetailsFragmentBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DetailsFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)

        recycler = binding.recyclerViewNoXml
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter
        SingletonRepoPrs.resp?.let { adapter.refresh(it) }
    }

}