package com.codingwithjadrey.pasman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codingwithjadrey.pasman.databinding.FragmentUpdatePasswordBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UpdatePasswordFragment: BottomSheetDialogFragment() {

    private var _binding: FragmentUpdatePasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdatePasswordBinding.inflate(inflater, container, false)
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