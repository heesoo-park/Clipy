package com.example.clipy.ui.scan

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.clipy.databinding.FragmentQrBinding
import com.journeyapps.barcodescanner.BarcodeCallback

class QRFragment : Fragment() {

    private var _binding: FragmentQrBinding? = null
    private val binding get() = _binding!!

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            binding.dbvQrScanner.decodeContinuous(callback)
        } else {
            // 권한이 거부된 경우 처리

        }
    }

    private val callback = BarcodeCallback { result ->
        if (result?.text != null) {
            val qrContents = result.text
            val extractedData = parseVCard(qrContents)
            binding.dbvQrScanner.pause()
            Toast.makeText(requireContext(), "${extractedData["NAME"]}", Toast.LENGTH_SHORT).show()
            binding.dbvQrScanner.resume()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQrBinding.inflate(inflater, container, false)

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        } else {
            binding.dbvQrScanner.decodeContinuous(callback)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        binding.dbvQrScanner.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.dbvQrScanner.pause()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            binding.dbvQrScanner.decodeContinuous(callback)
        } else {
            // 권한이 거부된 경우 처리
        }
    }

    private fun parseVCard(vCard: String): Map<String, String> {
        val data = mutableMapOf<String, String>()

        vCard.lines().forEach { line ->
            when {
                line.startsWith("FN:") -> data["NAME"] = line.removePrefix("FN:")
                line.startsWith("TEL;CELL:") -> data["TEL"] = line.removePrefix("TEL:")
                line.startsWith("ORG:") -> data["ORG"] = line.removePrefix("ORG:")
                line.startsWith("TITLE:") -> data["TITLE"] = line.removePrefix("TITLE:")
            }
        }

        return data
    }

    companion object {
        fun newInstance() =
            QRFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}