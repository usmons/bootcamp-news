package io.usmon.bootcampnews.presentation.news_list

import android.app.AlertDialog
import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.usmon.bootcampnews.R
import io.usmon.bootcampnews.common.Constants.NEWS_ID
import io.usmon.bootcampnews.common.Constants.POSITION
import io.usmon.bootcampnews.databinding.DialogAddNewsBinding
import io.usmon.bootcampnews.databinding.FragmentNewsListBinding
import io.usmon.bootcampnews.presentation.base.BaseFragment
import io.usmon.bootcampnews.presentation.news_list.adapter.NewsListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlin.text.Typography.degree

@AndroidEntryPoint
class NewsListFragment : BaseFragment<FragmentNewsListBinding>(FragmentNewsListBinding::inflate) {

    private val viewModel: NewsListViewModel by viewModels()

    private var position: Int? = null

    override fun myCreate() {
        arguments?.let {
            position = it.getInt(POSITION)
        }
        viewModel.followNewsByDegree(position ?: return)
    }

    override fun myCreateView(savedInstanceState: Bundle?) {
        val adapter = NewsListAdapter()
        binding.newsListView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.news.collectLatest { news ->
                adapter.differ.submitList(news)
                binding.notAvailableYet.isVisible = news.isEmpty()
            }
        }

        adapter.onItemClickListener { news ->
            findNavController().navigate(
                R.id.newsDetailFragment,
                Bundle().apply { putInt(NEWS_ID, news.newsId ?: return@onItemClickListener) }
            )
        }

        adapter.onMoreClickListener { news, view ->
            val popupMenu = PopupMenu(requireContext(), view)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_edit -> {

                        AlertDialog.Builder(requireContext()).create().apply {
                            val alert = DialogAddNewsBinding.inflate(layoutInflater).apply {

                                title.setText(news.title)
                                desc.setText(news.description)
                                degree.setSelection(news.degree)

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
                                        dismiss()
                                    }
                                }
                            }
                            setView(alert.root)
                            setCancelable(false)
                            show()
                        }

                    }
                    R.id.action_delete -> {
                        AlertDialog.Builder(requireContext()).apply {
                            setTitle("Xabarni o’chirmoqchimisiz?")
                            setNegativeButton("Bekor qilish") { dialog, _ ->
                                dialog.dismiss()
                            }

                            setPositiveButton("O’chirish") { dialog, itemId ->
                                viewModel.deleteNews(news)
                                dialog.dismiss()
                            }
                            show()
                        }
                    }
                }
                true
            }
            popupMenu.show()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(position: Int): NewsListFragment {
            return NewsListFragment().apply {
                arguments = Bundle().apply {
                    putInt(POSITION, position)
                }
            }
        }
    }
}
