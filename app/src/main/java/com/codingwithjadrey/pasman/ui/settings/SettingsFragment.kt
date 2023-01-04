package com.codingwithjadrey.pasman.ui.settings

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.codingwithjadrey.pasman.BuildConfig
import com.codingwithjadrey.pasman.data.entity.User
import com.codingwithjadrey.pasman.databinding.ChangePasswordLayoutBinding
import com.codingwithjadrey.pasman.databinding.CreatePasswordLayoutBinding
import com.codingwithjadrey.pasman.databinding.FragmentSettingsBinding
import com.codingwithjadrey.pasman.ui.viewmodel.UserViewModel
import com.codingwithjadrey.pasman.util.makeToast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var dialog: Dialog
    private val userViewModel: UserViewModel by viewModels()

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

    /** method to create app login password */
    private fun createPasswordDialog() {
        val dialogBinding = CreatePasswordLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(false)
        dialogBinding.apply {
            cancelButton.setOnClickListener { dialog.dismiss() }
            createPasswordBtn.setOnClickListener {
                val passwordText = createPasswordTxt.text.toString()
                // validates the text field using the userViewModel
                val validate = userViewModel.validatePasswords(passwordText)
                if (validate) {
                    val user = User(1, passwordText)
                    // creates a login password
                    userViewModel.insertUser(user)
                    dialog.dismiss()
                    makeToast(requireContext(), "Login password created successfully.")
                } else makeToast(requireContext(), "Password is required")
            }
        }
        dialog.show()
    }

    /** method to change app login password */
    private fun changePasswordDialog() {
        val dialogBinding = ChangePasswordLayoutBinding.inflate(layoutInflater)
        dialogBinding.viewModel = userViewModel
        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(false)
        dialogBinding.apply {
            cancelButton.setOnClickListener { dialog.dismiss() }
            updatePasswordBtn.setOnClickListener {
                val newPassword = newPassword.text.toString()
                // validates the text field using the userViewModel
                val validate = userViewModel.validatePasswords(newPassword)
                if (validate) {
                    val user = User(1, newPassword)
                    // updates the login password
                    userViewModel.updateUser(user)
                    dialog.dismiss()
                    makeToast(requireContext(), "Password updated successfully")
                } else makeToast(requireContext(), "Please enter new password!")
            }
        }
        dialog.show()
    }

    /** method to delete and reset app login password */
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