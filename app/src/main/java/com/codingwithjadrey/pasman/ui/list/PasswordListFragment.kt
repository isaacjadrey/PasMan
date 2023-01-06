package com.codingwithjadrey.pasman.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingwithjadrey.pasman.R
import com.codingwithjadrey.pasman.data.entity.Pas
import com.codingwithjadrey.pasman.databinding.FragmentPasswordListBinding
import com.codingwithjadrey.pasman.ui.viewmodel.PasViewModel
import com.codingwithjadrey.pasman.util.AlertHelper
import com.codingwithjadrey.pasman.util.makeToast
import com.codingwithjadrey.pasman.util.searchItems
import com.codingwithjadrey.pasman.util.swipeToDeleteItem
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import java.util.concurrent.Executor
import javax.inject.Inject

/**
 * MIT License
 *
 * Copyright (c) 2022 Isaac Jadrey Ongwara Jr
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

@AndroidEntryPoint
class PasswordListFragment : Fragment() {

    private var _binding: FragmentPasswordListBinding? = null
    private val binding get() = _binding!!
    private val passwordViewModel: PasViewModel by viewModels()
    private val adapter: PasswordAdapter by lazy { PasswordAdapter() }
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    @Inject
    lateinit var alertHelper: AlertHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = passwordViewModel
            addButton.setOnClickListener {
                findNavController().navigate(PasswordListFragmentDirections.actionPasswordListFragmentToAddPasswordFragment())
            }
        }
        setMenuItems()
        getPasswords()
        searchPassword()
    }

    /** Sets up the options menu and its actions */
    private fun setMenuItems() {
        binding.apply {
            toolbarPasList.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_email_sort -> {
                        passwordViewModel.sortByEmail.observe(viewLifecycleOwner) {
                            adapter.submitList(it)
                        }
                        true
                    }
                    R.id.action_social_sort -> {
                        passwordViewModel.sortBySocial.observe(viewLifecycleOwner) {
                            adapter.submitList(it)
                        }
                        true
                    }
                    R.id.action_wallet_sort -> {
                        passwordViewModel.sortByWallet.observe(viewLifecycleOwner) {
                            adapter.submitList(it)
                        }
                        true
                    }
                    R.id.action_app_sort -> {
                        passwordViewModel.sortByApp.observe(viewLifecycleOwner) {
                            adapter.submitList(it)
                        }
                        true
                    }
                    R.id.action_website_sort -> {
                        passwordViewModel.sortByWebsite.observe(viewLifecycleOwner) {
                            adapter.submitList(it)
                        }
                        true
                    }
                    R.id.action_delete_all -> {
                        alertHelper.createAlertToDelete(getString(R.string.delete_all_passwords)) {
                            confirmToDelete()
                        }
                        true
                    }
                    R.id.action_settings -> {
                        findNavController().navigate(PasswordListFragmentDirections.actionPasswordListFragmentToSettingsFragment())
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun confirmToDelete() {
        executor = ContextCompat.getMainExecutor(requireContext())
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    makeToast(requireContext(), "Failed to delete: $errString")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    passwordViewModel.deleteAllPasswords()
                    makeToast(requireContext(), getString(R.string.all_passwords_deleted))
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    makeToast(requireContext(), "Authentication Failed, can't delete passwords")
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Authenticate with fingerprint")
            .setSubtitle("There is no going back after this!")
            .setNegativeButtonText("Cancel")
            .build()
        biometricPrompt.authenticate(promptInfo)
    }

    /** lists the items from the database into the recycler view */
    private fun getPasswords() {
        binding.apply {
            passwordRecycler.adapter = adapter
            passwordRecycler.layoutManager = LinearLayoutManager(requireContext())
            passwordRecycler.itemAnimator = SlideInDownAnimator().apply { addDuration = 550 }
            swipeToDelete(passwordRecycler)
        }
        passwordViewModel.allPasswords.observe(viewLifecycleOwner) { passwords ->
            passwordViewModel.checkPasswordsIfEmpty(passwords)
            adapter.submitList(passwords)
        }
    }

    /** action to delete an item from its position
     * from the recyclerView by swiping left or right to delete */
    private fun swipeToDelete(passwordRecycler: RecyclerView) {
        passwordRecycler.swipeToDeleteItem { passwordItem ->
            val pasItemToDelete = adapter.currentList[passwordItem.adapterPosition]
            passwordViewModel.deletePassword(pasItemToDelete)
            adapter.notifyItemRemoved(passwordItem.adapterPosition)
            restoreDeletedPasItem(passwordItem.itemView, pasItemToDelete)
        }
    }

    /** action invoked to restore a deleted item back into the database */
    private fun restoreDeletedPasItem(adapterPosition: View, pasItemToRestore: Pas) {
        alertHelper.restoreSnackBar(adapterPosition, pasItemToRestore.account) {
            passwordViewModel.insertPassword(pasItemToRestore)
        }
    }

    /** method that performs search in the database */
    private fun searchPassword() {
        binding.searchBar.searchItems {
            searchQuery(it)
        }
    }

    /** the actual search functionality method using the search query */
    private fun searchQuery(query: String) {
        val searchQuery = "%$query%"
        passwordViewModel.searchPassword(searchQuery).observe(viewLifecycleOwner) { list ->
            list.let {
                passwordViewModel.checkIfPasswordExists(list)
                adapter.submitList(list)
            }
        }
    }

    /** called when the view is destroyed */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}