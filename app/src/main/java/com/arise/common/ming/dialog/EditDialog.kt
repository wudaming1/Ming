package com.arise.common.ming.dialog

import android.app.DialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arise.common.ming.R
import com.arise.common.sdk.utils.ToastUtil
import kotlinx.android.synthetic.main.dialog_edit.*

/**
 * 带输入框的dialog
 */
class EditDialog : DialogFragment() {

    companion object {
        private val TITLE = "title"
        private val HINT = "hint"

        fun newInstance(title: String = "", hint: String = ""): EditDialog {
            val dialog = EditDialog()
            val bundle = Bundle()
            bundle.putString(TITLE, title)
            bundle.putString(HINT, hint)
            dialog.arguments = bundle
            return dialog
        }
    }

    var cancel: (() -> Unit)? = null
    var confirm: ((s:String) -> Unit)? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.dialog_edit, container, false)
        if (arguments.getString(TITLE, "").isBlank()) {
            title.visibility = View.GONE
        } else {
            title.visibility = View.VISIBLE
            title.text = arguments.getString(TITLE, "")
        }
        if (!arguments.getString(HINT, "").isBlank())
            content.hint = arguments.getString(HINT, "")
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        cancel_btn.setOnClickListener {
            cancel?.invoke()
            dismiss()
        }
        confirm_btn.setOnClickListener {
            if (content.text.isNullOrBlank()){
                ToastUtil.showToast(getString(R.string.input_empty))
            }else {
                confirm?.invoke(content.text.toString())
                dismiss()
            }
        }
    }
}