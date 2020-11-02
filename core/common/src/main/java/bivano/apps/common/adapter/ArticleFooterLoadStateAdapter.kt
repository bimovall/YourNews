package bivano.apps.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import bivano.apps.common.R
import kotlinx.android.synthetic.main.view_news_footer_item.view.*

class ArticleFooterLoadStateAdapter(
    private val retry : () -> Unit
) : LoadStateAdapter<ArticleFooterLoadStateAdapter.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) = holder.bind(loadState)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder = ViewHolder(parent, retry)

    class ViewHolder(
        parent: ViewGroup,
        val retry: () -> Unit
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.view_news_footer_item, parent, false)
    ){
        fun bind(state: LoadState) {
            if (state is LoadState.Error) {
                itemView.error_msg.text = state.error.localizedMessage
            }
            itemView.btn_retry.setOnClickListener {
                retry()
            }
            itemView.progress_circular.isVisible = state is LoadState.Loading
            itemView.btn_retry.isVisible = state is LoadState.Error
            itemView.error_msg.isVisible = state is LoadState.Error
        }

    }
}