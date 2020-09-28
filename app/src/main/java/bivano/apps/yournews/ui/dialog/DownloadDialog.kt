package bivano.apps.yournews.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import bivano.apps.common.extension.animateProgress
import bivano.apps.yournews.R
import kotlinx.android.synthetic.main.fragment_download_dialog.*

class DownloadDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_download_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setCanceledOnTouchOutside(false)
        btn_ok.setOnClickListener {
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    fun setPreparingDialog() {
        progress_download.isIndeterminate = true
        tv_progress.text = ""
        tv_title.text = "Preparing..."
        btn_ok.visibility = View.GONE
    }

    fun setDownloadingDialog(end: Int) {
        progress_download.isIndeterminate = false
        progress_download.animateProgress(progress_download.progress, end)
        tv_progress.animateProgress(progress_download.progress, end, 300)
        tv_title.text = "Downloading..."
        btn_ok.visibility = View.GONE
    }

    fun setInstallingDialog() {
        tv_title.clearAnimation()
        progress_download.progress = 100
        progress_download.isIndeterminate = true
        tv_progress.text = ""
        tv_title.text = "Installing..."
        btn_ok.visibility = View.GONE
    }

    fun setSuccessInstallDialog() {
        progress_download.isIndeterminate = false
        tv_progress.text = ""
        tv_title.text = "Successfully installing feature"
        btn_ok.visibility = View.VISIBLE
    }

    fun setFailedDialog(message: String) {
        tv_title.text = message
        tv_progress.text = ""
        btn_ok.visibility = View.VISIBLE
    }
}