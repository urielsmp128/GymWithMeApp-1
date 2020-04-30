package com.itesm.gymwithme.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.itesm.gymwithme.CameraXActivity
import com.itesm.gymwithme.R
import com.itesm.gymwithme.databinding.FragmentQrScanWorkoutBinding

class QRScanWorkoutFragment : Fragment() {


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentQrScanWorkoutBinding>(inflater,
                R.layout.fragment_qr_scan_workout, container, false)

        binding.scanButton.setOnClickListener {
            val intent = Intent(requireActivity(), CameraXActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}
