package com.example.ahmednaeemfetch.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ahmednaeemfetch.R
import com.example.ahmednaeemfetch.viewmodel.ItemViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tabLayout: TabLayout
    private lateinit var errorMsg: TextView
    private lateinit var tryAgainBtn: Button
    private lateinit var spinner: ProgressBar

    private val viewModel: ItemViewModel by viewModels()
    private val adapter = ItemsAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initLayouts()
        initObserver()
    }

    private fun initLayouts() {
        errorMsg = findViewById(R.id.errorMsg)
        spinner = findViewById(R.id.spinner)
        tryAgainBtn = findViewById(R.id.tryAgainBtn)
        tryAgainBtn.setOnClickListener {
            updateUi(UiEvent.LOADING)
            viewModel.getItems()
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        tabLayout = findViewById(R.id.tabLayout)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: Tab?) {
                // Use safe calls to prevent potential null pointer exceptions.
                tab?.let {
                    viewModel.items.value?.let { map ->
                        map[tab.id]?.let { items ->
                            adapter.updatedItems(items)
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: Tab?) {
                // do nothing
            }

            override fun onTabReselected(tab: Tab?) {
                // do nothing
            }
        })
    }

    private fun initObserver() {
        // Observe changes to the items map from the ViewModel
        viewModel.items.observe(this) { items ->
            items.forEach { (k, _) ->
                // Dynamically create tabs for each listId from the API response
                val newTab = tabLayout.newTab()
                newTab.id = k
                newTab.text = k.toString()
                tabLayout.addTab(newTab)
            }

            items[items.keys.first()]?.let {
                adapter.updatedItems(it)
            }
            updateUi(UiEvent.SHOW_RESULTS)
        }

        // Observe errors from the ViewModel and update the UI to display an error message
        viewModel.error.observe(this) { msg ->
            errorMsg.text = msg
            updateUi(UiEvent.ERROR)
        }
    }

    private fun updateUi(event: UiEvent) {
        when(event) {
            UiEvent.SHOW_RESULTS -> {
                spinner.visibility = View.GONE
                tabLayout.visibility = View.VISIBLE
                recyclerView.visibility = View.VISIBLE
            }
            UiEvent.ERROR -> {
                spinner.visibility = View.GONE
                errorMsg.visibility = View.VISIBLE
                tryAgainBtn.visibility = View.VISIBLE
            }
            UiEvent.LOADING -> {
                spinner.visibility = View.VISIBLE
                errorMsg.visibility = View.GONE
                tryAgainBtn.visibility = View.GONE
            }
        }
    }
}
