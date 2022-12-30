package com.codingwithjadrey.pasman.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingwithjadrey.pasman.R
import com.codingwithjadrey.pasman.databinding.FragmentPasswordListBinding
import com.codingwithjadrey.pasman.ui.viewmodel.PasViewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator

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
class PasswordListFragment: Fragment() {

    private var _binding: FragmentPasswordListBinding? = null
    private val binding get() = _binding!!
    private val passwordViewModel: PasViewModel by viewModels()
    private val adapter: PasswordAdapter by lazy { PasswordAdapter() }

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
                    R.id.action_delete_all -> {true}
                    else -> false
                }
            }
        }
    }

    /** lists the items from the database into the recycler view */
    private fun getPasswords() {
        binding.apply {
            passwordRecycler.adapter = adapter
            passwordRecycler.layoutManager = LinearLayoutManager(requireContext())
        }
        passwordViewModel.allPasswords.observe(viewLifecycleOwner) { passwords ->
            passwordViewModel.checkPasswordsIfEmpty(passwords)
            adapter.submitList(passwords)
        }
    }

    /** called when the view is destroyed */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}