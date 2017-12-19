package com.arise.common.ming.dialog

import android.app.DialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arise.common.ming.R
import kotlinx.android.synthetic.main.dialog_simple.*

/**
 * 官方推荐DialogFragment，而不是直接使用Dialog创建对话框。
 * 实际上DialogFragment内部也是使用的Dialog，使用DialogFragment来管理对话框
 * ，当旋转屏幕fragmentManger负责恢复现场(dialog)，按下后退键时可以更好的管理其声明周期。
 * 所以我理解DialogFragment是对dialog的封装，并给dialog提供一个稳定的上下文环境。
 */
class CommonDialog : DialogFragment() {

    companion object {
        private val TITLE = "title"
        private val DESC = "desc"

        fun newInstance(title: String = "", description: String = ""): CommonDialog {
            val dialog = CommonDialog()
            val bundle = Bundle()
            bundle.putString(TITLE, title)
            bundle.putString(DESC, description)
            dialog.arguments = bundle
            return dialog
        }
    }

    var cancel: (() -> Unit)? = null
    var confirm: (() -> Unit)? = null

    /**
     * @param container 只有restore的情况可能为空，否则一定不为空，代表Fragment界面的容器
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle): View {
        val view = inflater.inflate(R.layout.dialog_simple, container, false)
        if (arguments.getString(TITLE, "").isBlank()) {
            title.visibility = View.GONE
        } else {
            title.visibility = View.VISIBLE
            title.text = arguments.getString(TITLE, "")
        }
        content.text = arguments.getString(DESC, "")
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        cancel_btn.setOnClickListener {
            cancel?.invoke()
            dismiss()
        }
        confirm_btn.setOnClickListener {
            confirm?.invoke()
            dismiss()
        }
    }
}