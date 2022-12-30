package com.codingwithjadrey.pasman.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.codingwithjadrey.pasman.R
import com.codingwithjadrey.pasman.data.entity.Pas
import com.codingwithjadrey.pasman.databinding.FragmentAddPasswordBinding
import com.codingwithjadrey.pasman.ui.viewmodel.PasViewModel
import com.codingwithjadrey.pasman.util.makeToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPasswordFragment: Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentAddPasswordBinding? = null
    private val binding get() = _binding!!
    private lateinit var accountType: String
    private val pasViewModel: PasViewModel by viewModels()

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
            lifecycleOwner = viewLifecycleOwner
            generatePassword.setOnClickListener {
                findNavController().navigate(R.id.action_addPasswordFragment_to_generatePasswordFragment)
            }
        }
        setMenuItem()
        populateSpinner()
    }

    private fun setMenuItem() {
        binding.apply {
            toolbarAddPas.setOnClickListener {
                findNavController().popBackStack()
            }
            toolbarAddPas.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_add -> {
                        insertPassword()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun insertPassword() {
        binding.apply {
            val account = account.text.toString()
            val password = accountPassword.text.toString()
            val name = accountName.text.toString()
            val validateInputs = pasViewModel.validateInputs(account, password, name)

            if (validateInputs) {
                val pasItem = Pas(0, account, password, name, accountType)
                pasViewModel.insertPassword(pasItem)
                findNavController().popBackStack()
                makeToast(requireContext(), "New password added successfully!")
            } else makeToast(requireContext(), "All fields are required")
        }
    }

    private fun populateSpinner() {
        val spinner = binding.atSpinner
        spinner.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.account_type_spinner,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        accountType = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}