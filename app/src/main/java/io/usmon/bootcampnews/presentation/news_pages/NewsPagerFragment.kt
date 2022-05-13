package io.usmon.bootcampnews.presentation.news_pages

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import io.usmon.bootcampnews.R
import io.usmon.bootcampnews.databinding.DialogAddNewsBinding
import io.usmon.bootcampnews.databinding.FragmentNewsPagerBinding
import io.usmon.bootcampnews.domain.model.local.entitiy.News
import io.usmon.bootcampnews.presentation.base.BaseFragment
import io.usmon.bootcampnews.presentation.news_pages.adapter.NewsPagerAdapter

@AndroidEntryPoint
class NewsPagerFragment : BaseFragment<FragmentNewsPagerBinding>(FragmentNewsPagerBinding::inflate) {

    private val viewModel: NewsPagerViewModel by viewModels()

    override fun myCreateView(savedInstanceState: Bundle?) {

        NewsPagerAdapter(activity as AppCompatActivity).also { adapter ->
            binding.newsPagerAdapter.adapter = adapter
        }

        TabLayoutMediator(binding.newsTabLayout, binding.newsPagerAdapter) { tab, position ->
            tab.text = resources.getStringArray(R.array.degree)[position]
        }.attach()

        var dialog: AlertDialog? = null

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is NewsPagerViewModel.UiEvent.ShowAddDialog -> {
                        dialog = AlertDialog.Builder(requireContext()).create().apply {
                            val alert = DialogAddNewsBinding.inflate(layoutInflater).apply {
                                close.setOnClickListener {
                                    viewModel.onEvent(NewsPagerViewModel.UiEvent.CloseAddDialog)
                                }
                                save.setOnClickListener {
                                    viewModel.onEvent(
                                        NewsPagerViewModel.UiEvent.SaveNews(
                                            News(
                                                title = title.text.toString(),
                                                description = desc.text.toString(),
                                                degree = degree.selectedItemPosition
                                            )
                                        )
                                    )
                                }
                            }
                            setView(alert.root)
                            setCancelable(false)
                            show()
                        }
                    }
                    is NewsPagerViewModel.UiEvent.CloseAddDialog -> {
                        dialog?.dismiss()
                        dialog = null
                    }
                    is NewsPagerViewModel.UiEvent.ShowToast -> {
                        Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
            }
            R.id.add_news -> {
                viewModel.onEvent(NewsPagerViewModel.UiEvent.ShowAddDialog)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.news_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}