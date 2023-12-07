package com.example.homework_15

import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_15.databinding.FragmentMainBinding
import kotlinx.coroutines.launch

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val viewModel: MainViewModel by activityViewModels()
    private val chatListAdapter = ChatListAdapter()

    //setting up initial configuration and fetching chat messages
    override fun setUp() {
        //setup for RecyclerView
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = chatListAdapter
        }
        //fetching chat messages from the API
        viewModel.fetchChatMessages()
    }
    //setting up click listeners for UI elements
    override fun onClickListeners() {
        //listener for button to start search
        binding.searchButton.setOnClickListener {
            val searchWord = binding.searchField.text.toString()
            chatListAdapter.search(searchWord)
        }
        //listener for search field to display original list when the edittext is empty
        binding.searchField.addTextChangedListener{
            val searchWord = it.toString()
            if (searchWord.isEmpty()) {
                chatListAdapter.search("")
            }
        }
    }
    //observing chatMessages using the fragment's lifecycleScope
    override fun bindObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.chatMessages.collect {
                    Log.d("UI_UPDATE", "Chat messages updated: $it")
                    chatListAdapter.setOriginalList(it)
                }
            }
        }
    }
}
