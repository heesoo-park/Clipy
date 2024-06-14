package com.example.clipy.ui.storage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.example.clipy.R
import com.example.clipy.databinding.FragmentStorageBinding
import com.example.clipy.item.StorageItem

class StorageFragment : Fragment() {

    private var _binding: FragmentStorageBinding? = null
    private val binding get() = _binding!!

    private val adapter: StorageListAdapter by lazy {
        StorageListAdapter(
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

        val dummyData = listOf(
            StorageItem(
                "dkdk",
                "dkfjsdlkfj",
                "dkfsvnmcls",
                null,
                false
            ),
            StorageItem(
                "dkdk",
                "dkfjsdlkfj",
                "dkfsvnmcls",
                null,
                false
            ),
            StorageItem(
                "dkdk",
                "dkfjsdlkfj",
                "dkfsvnmcls",
                null,
                false
            ),
        )

        binding.rvListStorage.adapter = adapter
        adapter.submitList(dummyData)
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