package com.example.clipy.ui.home

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.clipy.base.MainViewModel
import com.example.clipy.databinding.FragmentHomeBinding
import com.example.clipy.di.AppModule
import com.example.clipy.item.HomeItem
import com.example.clipy.ui.login.LoginFragment
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()

    private val adapter by lazy {
        ImagePagerAdapter(
            moveCreatePage = {
                try {
                    if (mainViewModel.loginState) {
                        Toast.makeText(requireContext(), "명함 만들기 페이지로 이동", Toast.LENGTH_SHORT).show()
//                        makeQRCode()
                    } else {
                        val dialog = LoginFragment.newInstance()
                        dialog.show(requireActivity().supportFragmentManager, "Login")
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "QR 코드 생성 오류", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun makeQRCode() {
        val barcodeEncoder = BarcodeEncoder()
        val bitmap =
            barcodeEncoder.encodeBitmap("https://www.naver.com/", BarcodeFormat.QR_CODE, 400, 400)
        mainViewModel.updateQRCode(bitmap)
        adapter.submitList(
            listOf(
                HomeItem(
                    true,
                    null,
                    null,
                ),
                HomeItem(
                    false,
                    null,
                    bitmap,
                )
            )
        )
    }

    val dummyData = listOf(
        HomeItem(
            true,
            null,
            null,
        ),
        HomeItem(
            false,
            null,
            null,
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vpHome.adapter = adapter
        adapter.submitList(dummyData)

        binding.ci3Home.setViewPager(binding.vpHome)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}