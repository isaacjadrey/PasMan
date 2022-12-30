package com.codingwithjadrey.pasman.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.codingwithjadrey.pasman.R
import com.codingwithjadrey.pasman.databinding.FragmentPasswordListBinding

class PasswordListFragment: Fragment() {

    private var _binding: FragmentPasswordListBinding? = null
    private val binding get() = _binding!!

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
            addButton.setOnClickListener {
                findNavController().navigate(R.id.action_passwordListFragment_to_addPasswordFragment)
            }
        }
        setMenuItems()
    }

    private fun setMenuItems() {
        binding.apply {
            toolbarPasList.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_email_sort -> {true}
                    R.id.action_social_sort -> {true}
                    R.id.action_wallet_sort -> {true}
                    R.id.action_app_sort -> {true}
                    R.id.action_website_sort -> {true}
                    R.id.action_delete_all -> {true}
                    else -> false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}