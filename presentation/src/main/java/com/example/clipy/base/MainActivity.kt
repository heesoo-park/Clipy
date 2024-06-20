package com.example.clipy.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.clipy.ui.home.HomeFragment
import com.example.clipy.type.PageType
import com.example.clipy.ui.scan.QRFragment
import com.example.clipy.R
import com.example.clipy.ui.storage.StorageFragment
import com.example.clipy.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initViewModel()

        binding.bnvMain.setOnItemSelectedListener {
            viewModel.setCurrentPage(it.itemId)
            true
        }
    }

    private fun initView() = with(binding) {
        viewModel.setCurrentPage(R.id.navigation_home)
        binding.bnvMain.selectedItemId = R.id.navigation_home

        tbMain.setOnClickListener {
            viewModel.logout()
            Toast.makeText(this@MainActivity, "로그아웃", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initViewModel() = with(viewModel) {
        curPageType.observe(this@MainActivity) {
            changeFragment(it)
        }
    }

    private fun changeFragment(pageType: PageType) {
        val transaction = supportFragmentManager.beginTransaction()

        var targetFragment = supportFragmentManager.findFragmentByTag(pageType.tag)

        if (targetFragment == null) {
            targetFragment = getFragment(pageType)
            transaction.add(R.id.fl_main, targetFragment, pageType.tag)
        }

        transaction.show(targetFragment)

        PageType.entries
            .filterNot { it == pageType }
            .forEach { type ->
                supportFragmentManager.findFragmentByTag(type.tag)?.let {
                    transaction.hide(it)
                }
            }

        transaction.commitAllowingStateLoss()
    }

    private fun getFragment(pageType: PageType): Fragment {
        return when (pageType) {
            PageType.PAGE1 -> QRFragment.newInstance()
            PageType.PAGE2 -> HomeFragment.newInstance()
            PageType.PAGE3 -> StorageFragment.newInstance()
        }
    }
}