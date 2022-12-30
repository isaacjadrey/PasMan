package com.codingwithjadrey.pasman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codingwithjadrey.pasman.databinding.FragmentGeneratePasswordBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class GeneratePasswordFragment: BottomSheetDialogFragment() {

    private var _binding: FragmentGeneratePasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGeneratePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}