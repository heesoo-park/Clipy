package com.peacepark.clipy.ui.storage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.peacepark.clipy.R
import com.peacepark.clipy.databinding.FragmentStorageBinding
import com.peacepark.domain.model.Card
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.UUID

@AndroidEntryPoint
class StorageFragment : Fragment() {

    private var _binding: FragmentStorageBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StorageViewModel by viewModels()

    private val adapter: StoragePagingAdapter by lazy {
        StoragePagingAdapter(
            moveDetailPage = {
                Toast.makeText(requireContext(), "상세 페이지로 이동", Toast.LENGTH_SHORT).show()
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStorageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = listOf("전체","즐겨찾기")

        binding.spinnerCategoryStorage.adapter = OptionSpinnerAdapter(requireContext(), R.layout.item_spinner_option_storage,list)
        binding.spinnerCategoryStorage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val value = binding.spinnerCategoryStorage.getItemAtPosition(p2).toString()
                Toast.makeText(requireContext(), value, Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                // 선택되지 않은 경우
            }
        }

        binding.tvEditStorage.setOnClickListener {
            viewModel.save(Card(
                cardCode = UUID.randomUUID().toString(),
                name = "누구신",
                rank = "대리",
                company = "채움",
                addedDate = "2022.02.04",
                cardImage = "www.dkfjsl.doivjn/download",
                badge = false
            ))
        }

        binding.rvListStorage.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pagingCards.collectLatest {
                    adapter.submitData(it)
                }
            }
        }
//        adapter.submitList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() =
            StorageFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}