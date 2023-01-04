package com.codingwithjadrey.pasman.ui.settings

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.codingwithjadrey.pasman.BuildConfig
import com.codingwithjadrey.pasman.databinding.FragmentSettingsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SettingsFragment : Fragment() {

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
        val url = "https://github.com/isaacjadrey/PasMan"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)

        val versionNumber = BuildConfig.VERSION_NAME
        MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle("About")
            setMessage(
                "Pas-Man is your personal password manager, " +
                        "save all your passwords right on the move." +
                        "\nNo need to forget your passwords anymore." +
                        "\n\nVersion: $versionNumber" +
                        "\nPowered by COJ"
            )
            setNeutralButton("Visit Site") { _, _ ->
                startActivity(intent)
            }
        }.show()
    }

    /** called when the view is destroyed */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}