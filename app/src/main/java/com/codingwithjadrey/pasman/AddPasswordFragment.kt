package com.codingwithjadrey.pasman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.codingwithjadrey.pasman.databinding.FragmentAddPasswordBinding

class AddPasswordFragment: Fragment() {

    private var _binding: FragmentAddPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            generatePassword.setOnClickListener {
                findNavController().navigate(R.id.action_addPasswordFragment_to_generatePasswordFragment)
            }
        }
        setMenuItem()
    }

    private fun setMenuItem() {
        binding.apply {
            toolbarAddPas.setOnClickListener {
                findNavController().popBackStack()
            }
            toolbarAddPas.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_add -> {true}
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