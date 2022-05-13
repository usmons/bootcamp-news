package io.usmon.bootcampnews.presentation.news_detail

import android.app.AlertDialog
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.usmon.bootcampnews.common.Constants.NEWS_ID
import io.usmon.bootcampnews.databinding.DialogAddNewsBinding
import io.usmon.bootcampnews.databinding.FragmentNewsDetailBinding
import io.usmon.bootcampnews.presentation.base.BaseFragment
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class NewsDetailFragment : BaseFragment<FragmentNewsDetailBinding>(FragmentNewsDetailBinding::inflate) {

    private var newsId: Int? = null

    private val viewModel: NewsDetailViewModel by viewModels()

    override fun myCreate() {
        arguments?.let { newsId = it.getInt(NEWS_ID) }
        viewModel.followNewsById(newsId)
        setHasOptionsMenu(true)
    }

    override fun myCreateView(savedInstanceState: Bundle?) {

        (activity as AppCompatActivity).supportActionBar?.apply {
            show()
            this.title = "Bootcamp news"
            setDisplayHomeAsUpEnabled(true)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.news.collectLatest { news ->
                binding.title.text = news?.title
                binding.desc.text = news?.description

                binding.edit.setOnClickListener {
                    AlertDialog.Builder(requireContext()).create().apply {
                        val alert = DialogAddNewsBinding.inflate(layoutInflater).apply {

                            title.setText(news?.title)
                            desc.setText(news?.description)
                            degree.setSelection(news?.degree!!)

                            close.setOnClickListener {
                                dismiss()
                            }
                            save.setOnClickListener {
                                viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                                    viewModel.updateNews(
                                        news.copy(
                                            title = title.text.toString(),
                                            description = desc.text.toString(),
                                            degree = degree.selectedItemPosition
                                        )
                                    )
                                    Toast.makeText(requireContext(), "Successfully updated", Toast.LENGTH_SHORT).show()
                                    viewModel.followNewsById(newsId)
                                    dismiss()
                                }
                            }
                        }
                        setView(alert.root)
                        setCancelable(false)
                        show()
                    }
                }
            }
        }

        binding.edit.setOnClickListener {
            Toast.makeText(requireContext(), "Hello", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}