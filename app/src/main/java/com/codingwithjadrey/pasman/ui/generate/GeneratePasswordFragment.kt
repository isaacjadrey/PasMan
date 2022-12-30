package com.codingwithjadrey.pasman.ui.generate

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codingwithjadrey.pasman.databinding.FragmentGeneratePasswordBinding
import com.codingwithjadrey.pasman.util.makeToast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

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

class GeneratePasswordFragment: BottomSheetDialogFragment() {

    private var _binding: FragmentGeneratePasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGeneratePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            generatePasswordBtn.setOnClickListener { generatePassword(12) }
            copyToClipboardBtn.setOnClickListener { copyToClipboard() }
        }
    }

    /** method that generates a random password with a specified character
     * length when invoking the method */
    private fun generatePassword(i: Int) {
        val characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#\$%&^*/()|?"
        val stringBuilder = StringBuilder(i)

        repeat(i) {
            val generatedString = (characters.indices).random()
            stringBuilder.append(characters[generatedString])
        }

        stringBuilder.insert((0 until i).random(), "")
        binding.apply {
            generatedPassword.text = stringBuilder.toString()
            copyToClipboardBtn.visibility = View.VISIBLE
        }
    }

    /** method to copy the generated password string into the system clipboard
     * so that it can be copied into the password field */
    private fun copyToClipboard() {
        val clipboard = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipboardContent  = ClipData.newPlainText("", binding.generatedPassword.text.toString())
        clipboard.setPrimaryClip(clipboardContent)
        makeToast(requireContext(), "Copied to clipboard")
        dialog?.dismiss()
    }

    /** called when the view is destroyed */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}