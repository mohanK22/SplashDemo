package com.example.splashdemo

import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.splashdemo.databinding.FragmentDialogAppUpdateBinding

class AppUpdateDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentDialogAppUpdateBinding

    companion object {
        const val TAG = "AppUpdateDialog"
        private const val KEY_CANCELABLE = "KEY_CANCELABLE"
        private var dialogButtonClickListener: DialogButtonClickListener? = null

        fun newInstance(
            isCancelable: Boolean = true,
            mDialogButtonClickListener: DialogButtonClickListener
        ): AppUpdateDialogFragment {
            val args = Bundle()
            args.putBoolean(KEY_CANCELABLE, isCancelable)
            val fragment = AppUpdateDialogFragment()
            fragment.arguments = args
            dialogButtonClickListener = mDialogButtonClickListener
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogAppUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    override fun onStart() {
        super.onStart()

        arguments?.getBoolean(KEY_CANCELABLE)?.let { mCancelable ->
            dialog?.setCancelable(mCancelable)
        }

        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

        dialog?.setOnKeyListener { dialog, keyCode, event ->
            dialogButtonClickListener?.onBackPress(dialog, keyCode, event)
            true
        }
    }

    private fun setupClickListeners() {
        binding.btnUpdateNow.setOnClickListener {
            dismiss()
            dialogButtonClickListener?.onUpdateClick()
        }
        binding.tvExitApp.setOnClickListener {
            dismiss()
            dialogButtonClickListener?.onExitAppClick()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        dialogButtonClickListener?.onDialogDismiss()
        super.onDismiss(dialog)
    }

    /**
     * --------------------------------------------------------------
     * Interface used to attach listener
     * --------------------------------------------------------------
     */
    interface DialogButtonClickListener {
        fun onUpdateClick()
        fun onExitAppClick()
        fun onBackPress(dialog: DialogInterface?, keyCode: Int, event: KeyEvent?)
        fun onDialogDismiss()
    }
}