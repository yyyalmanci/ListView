package com.yyy.listview.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.yyy.listview.R
import com.yyy.listview.databinding.FragmentPostListBinding
import com.yyy.listview.presentation.adapter.PostsAdapter
import com.yyy.listview.presentation.model.PostListUiState
import com.yyy.listview.presentation.viewmodel.PostListViewModel
import com.yyy.listview.utils.DividerItemDecorator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PostListFragment() : Fragment() {

    private var _binding: FragmentPostListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PostListViewModel by viewModels()

    private val itemTouchHelper by lazy {
        ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.RIGHT,
                ItemTouchHelper.LEFT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: ViewHolder, target: ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                    binding.postList.adapter?.notifyItemRemoved(viewHolder.adapterPosition);
                }
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchPosts()
        viewModel.getPosts()
        initialize()
        subscribeEvents()
        binding.toolbar.title = getString(R.string.app_name)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initialize() {
        binding.postList.apply {
            val divider =
                DividerItemDecorator(ContextCompat.getDrawable(context, R.drawable.list_divider))
            layoutManager =
                LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(divider)
            adapter = PostsAdapter(::navigateToDetailFragment)
            itemTouchHelper.attachToRecyclerView(this)
        }
    }

    private fun subscribeEvents() {
      viewModel.apply {
          lifecycleScope.launch {
              viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                  launch {
                      postListFlow.collect {
                          when (it) {
                              is PostListUiState.Success -> {
                                  (binding.postList.adapter as PostsAdapter).submitList(it.postList)
                              }
                              is PostListUiState.Loading -> {
                                  //TODO show loading ui
                              }
                              else -> {}
                          }
                      }
                  }
                  launch {
                      connectionError.collect {
                          if (it) {
                              Toast.makeText(
                                  context,
                                  getString(R.string.error_connection_list),
                                  Toast.LENGTH_SHORT
                              ).show()
                          }
                      }
                  }
              }
          }
      }
    }

    private fun navigateToDetailFragment(id: Int, title: String, desc: String) {

    }

}