package bivano.apps.common

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.android.synthetic.main.view_news_footer_item.view.*
import kotlinx.android.synthetic.main.view_news_item.view.*

inline val View.exportedTextViewTitle: TextView get() = tv_title

inline val View.exportedTextViewDesc: TextView get() = tv_description

inline val View.exportedTextViewTime: TextView get() = tv_time

inline val View.exportedImageNews: ImageView get() = img_news

inline val View.exportedProgress: ProgressBar get() = progress_circular