package com.example.bottomnavigationview30.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import com.example.bottomnavigationview30.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.editBTN?.setOnClickListener {
            showInputDialog()
        }
    }

    private fun showInputDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Введите личный данные")

        val nameInput = EditText(requireContext())
        nameInput.inputType = InputType.TYPE_CLASS_TEXT
        nameInput.hint = "Имя"

        val emailInput = EditText(requireContext())
        emailInput.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        emailInput.hint = "Email"

        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            addView(nameInput)
            addView(emailInput)
        }
        builder.setView(layout)

        builder.setPositiveButton("Сохранить"){dialog: DialogInterface, which: Int->
            val name = nameInput.text.toString()
            val email = emailInput.text.toString()
            binding?.nameTV?.text = "Имя: $name"
            binding?.emailTV?.text = "Email: $email"
        }

        builder.setNegativeButton("Отмена"){dialog:DialogInterface, which:Int ->
            dialog.cancel()
        }

        builder.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}