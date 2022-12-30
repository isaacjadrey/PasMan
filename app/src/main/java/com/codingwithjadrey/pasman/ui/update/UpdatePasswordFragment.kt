package com.codingwithjadrey.pasman.ui.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.codingwithjadrey.pasman.R
import com.codingwithjadrey.pasman.databinding.FragmentUpdatePasswordBinding
import com.codingwithjadrey.pasman.ui.viewmodel.PasViewModel
import com.codingwithjadrey.pasman.util.makeToast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
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
class UpdatePasswordFragment : BottomSheetDialogFragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentUpdatePasswordBinding? = null
    private val binding get() = _binding!!
    private val pasViewModel: PasViewModel by viewModels()
    private val args by navArgs<UpdatePasswordFragmentArgs>()
    private lateinit var accountType: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdatePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            pas = args.currentPassword
            generatePassword.setOnClickListener {
                findNavController()
                    .navigate(UpdatePasswordFragmentDirections.actionUpdatePasswordFragmentToGeneratePasswordFragment())
            }
            updatePassword.setOnClickListener { updatePassword() }
        }
        popupSpinner()
    }

    /** method that carries out the updatePassword functionality from
     * the pasViewModel */
    private fun updatePassword() {
        binding.apply {
            val account = accountUpdate.text.toString()
            val password = accountPasswordUpdate.text.toString()
            val name = accountNameUpdate.text.toString()
            val validate = pasViewModel.validateInputs(account, password, name)

            if (validate) {
                val pas = args.currentPassword.copy(
                    account = account,
                    accountPassword = password,
                    accountName = name,
                    accountType = accountType
                )
                pasViewModel.updatePassword(pas)
                dialog?.dismiss()
                makeToast(requireContext(), "Password updated successfully")
            }
        }
    }

    /** method that populates a spinner item that sets the type of password account
     * you are creating */
    private fun popupSpinner() {
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

    /** called when the view is destroyed */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /** sets the accountType variable with the string at the spinner position */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        accountType = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}