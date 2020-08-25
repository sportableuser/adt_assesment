package com.example.adt.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adt.R
import com.example.adt.ui.main.ui.ArticlesAdapter
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private val adapter = ArticlesAdapter(::onItemClick)
    lateinit var layoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initViews()
        observeData()
        viewModel.getArticles()
    }

    private fun initViews() {
        swiperefresh.setOnRefreshListener {
            viewModel.getArticles()
        }

        //to display grid layout
        //layoutManager = GridLayoutManager(activity, 2)

        article_list.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )

        article_list.setHasFixedSize(true)
        article_list.adapter = adapter
    }

    private fun observeData() {
        viewModel.stateResponse.observe(viewLifecycleOwner, Observer {
            swiperefresh.isRefreshing = false
            when (it) {
                is StateResponse.Success -> {
                    hideProgressBar()
                    adapter.submitList(it.data)
                }
                is StateResponse.Loading -> showProgressBar()
                is StateResponse.Error -> hideProgressBar()
            }
        })
    }

    private fun showProgressBar() {
        loading.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        loading.visibility = View.GONE
    }

    private fun onItemClick(position: Int) {
        val article = adapter.getItem(position)
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
        startActivity(browserIntent)
    }
}