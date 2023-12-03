package com.example.homework_14

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.homework_14.databinding.FragmentMainBinding
import kotlinx.coroutines.launch

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private lateinit var itemAdapter: ItemRecyclerAdapter
    private val viewModel: ItemViewModel by viewModels()

    override fun setUp() {
        //setting up the adapter
        itemAdapter = ItemRecyclerAdapter()
        with(binding) {
            itemRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
            itemRecycler.adapter = itemAdapter
            //setting up the listener for swiperefreshlayout
            swipeRefreshLayout.setOnRefreshListener {
                viewModel.refreshItems()
            }
        }
    }

    override fun onClickListeners() {
        //adding item one to the list
        binding.addItemOne.setOnClickListener {
            viewModel.addNewItem(ItemViewModel.ItemType.ITEM_ONE)
        }
        //adding item two to the list
        binding.addItemTwo.setOnClickListener {
            viewModel.addNewItem(ItemViewModel.ItemType.ITEM_TWO)
        }
        //listener for the delete button for the adapter
        itemAdapter.setOnDeleteItemClickListener { item ->
            viewModel.removeItem(item)
        }

    }
    //observing changes in the itemFlow using the fragment's lifecycleScope
    override fun bindObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.itemFlow.collect {
                    itemAdapter.submitList(it)
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        }
    }
}