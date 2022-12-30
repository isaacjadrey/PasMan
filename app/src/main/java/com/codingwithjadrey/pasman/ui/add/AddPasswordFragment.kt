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

    /** method that sets up the option menu with an item that adds items into the database */
    private fun setMenuItem() {
        binding.apply {
            toolbarAddPas.setOnClickListener {
                // navigates back to the previous screen
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

    /** method that calls the insertPassword function from the viewModel class
     * to perform insertion into the database */
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

    /** method that populates a spinner item that sets the type of password account
     * you are creating */
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

    /** Called when the view is destroyed*/
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /** sets the accountType variable with the string at the spinner position */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        accountType = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}