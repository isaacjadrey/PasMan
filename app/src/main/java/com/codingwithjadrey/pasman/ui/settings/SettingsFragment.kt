package com.codingwithjadrey.pasman.ui.settings

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.codingwithjadrey.pasman.databinding.FragmentSettingsBinding

class SettingsFragment: Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog = Dialog(requireContext())
        binding.apply {
            toolbarSettings.setOnClickListener {
                findNavController().popBackStack()
            }
            createPassword.setOnClickListener { createPasswordDialog() }
            changePassword.setOnClickListener { changePasswordDialog() }
            resetPassword.setOnClickListener { resetPasswordDialog() }
            info.setOnClickListener { infoDialog() }
        }
    }

    private fun createPasswordDialog() {

    }

    private fun changePasswordDialog() {

    }

    private fun resetPasswordDialog() {

    }

    private fun infoDialog() {
    }

    /** called when the view is destroyed */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}