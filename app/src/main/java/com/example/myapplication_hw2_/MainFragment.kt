package com.example.myapplication_hw2_

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.ProgressBar
import android.widget.Button
import android.widget.LinearLayout
import androidx.lifecycle.LiveData

class MainFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var foxAdapter: FoxAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var errorLayout: LinearLayout
    private lateinit var retryButton: Button

    private var isLoading = false
    private var hasError = false
    private var foxImages: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        progressBar = view.findViewById(R.id.progressBar)
        errorLayout = view.findViewById(R.id.errorLayout)
        retryButton = view.findViewById(R.id.retryButton)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        foxAdapter = FoxAdapter(foxImages)
        recyclerView.adapter = foxAdapter

        savedInstanceState?.let {
            foxImages = it.getStringArrayList("foxImages")?.toMutableList() ?: mutableListOf()
            foxAdapter.updateFoxes(foxImages)
        }

        retryButton.setOnClickListener {
            loadFoxImages()
        }

        if (foxImages.isEmpty()) {
            loadFoxImages()
        } else {
            foxAdapter.updateFoxes(foxImages)
        }

        return view
    }

    private fun loadFoxImages() {
        if (isLoading || hasError) return

        isLoading = true
        showLoading()

        val api = FoxRepository(ApiFox.apiService)
        val requests = mutableListOf<LiveData<FoxDataClass?>>()

        for (i in 1..10) {
            val liveData = api.getRandomFoxImage()
            requests.add(liveData)

            liveData.observe(viewLifecycleOwner) { foxData ->
                if (foxData != null) {
                    foxImages.add(foxData.image)
                } else {
                    hasError = true
                    showError()
                }
                if (foxImages.size == requests.size) {
                    isLoading = false
                    hideLoading()
                    if (!hasError) {
                        foxAdapter.updateFoxes(foxImages)
                    }
                }
            }
        }
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
        errorLayout.visibility = View.GONE
        recyclerView.visibility = View.GONE
    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    private fun showError() {
        errorLayout.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("foxImages", ArrayList(foxImages))
    }
}