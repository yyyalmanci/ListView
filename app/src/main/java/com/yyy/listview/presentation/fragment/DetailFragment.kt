package com.yyy.listview.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.yyy.listview.R
import com.yyy.listview.databinding.FragmentDetailBinding
import com.yyy.listview.domain.model.PostDomainModel
import com.yyy.listview.presentation.viewmodel.PostsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PostsViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize(args.postDetail, args.imageUrl)
        binding.toolbar.title = getString(R.string.details)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initialize(model: PostDomainModel, imageUrl: String) {
        binding.apply {
            title.setText(
                if (model.isTitleUpdated) {
                    model.updatedTitle
                } else {
                    model.title
                }
            )
            description.setText(
                if (model.isDescriptionUpdated) {
                    model.updatedDescription
                } else {
                    model.description
                }
            )
            image.load(imageUrl)
            save.setOnClickListener {
                if (title.text.toString() != model.title) {
                    viewModel.updateTitle(title.text.toString(), model.id)
                }
                if (description.text.toString() != model.description) {
                    viewModel.updateDescription(description.text.toString(), model.id)
                }
                findNavController().popBackStack()
            }
        }
    }

}