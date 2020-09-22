package bivano.apps.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import bivano.apps.common.*
import bivano.apps.common.model.Article
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlin.Result

class ArticlePagedListAdapter : PagedListAdapter<Article, RecyclerView.ViewHolder>(ArticleDiffCallback) {

    var onItemClick: ((Article) -> Unit)? = null

    var onItemLongClick: ((Article) -> Unit)? = null

    private var networkState : bivano.apps.common.Result<List<Article>>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context)
                .inflate(bivano.apps.common.R.layout.view_news_item, parent, false)
            ArticleViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(bivano.apps.common.R.layout.view_news_footer_item, parent, false)
            LoaderViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ArticleViewHolder) {
            holder.bindItems(getItem(position))
        } else {
            networkState?.run {
                (holder as LoaderViewHolder).bindState(this)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1 && hasLoadMore()) {
            TYPE_PROGRESS
        } else {
            TYPE_ITEM
        }
    }

    private fun hasLoadMore(): Boolean {
        return networkState != null && networkState !is bivano.apps.common.Result.Success
    }

    fun setNetworkState(state: bivano.apps.common.Result<List<Article>>) {
        val prevState = this.networkState
        val prevExtraRow = hasLoadMore()
        this.networkState = state
        val newExtraRow = hasLoadMore()
        if (prevExtraRow != newExtraRow) {
            if (prevExtraRow) {
                notifyItemRemoved(itemCount)
            } else {
                notifyItemInserted(itemCount)
            }
        } else if (newExtraRow && prevState != state) {
            notifyItemChanged(itemCount - 1)
        }

    }

    inner class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItems(item: Article?) {
            itemView.exportedTextViewTitle.text = item?.title
            itemView.exportedTextViewDesc.text = item?.description
            itemView.exportedTextViewTime.text = item?.publishedAt.toString()

            Glide.with(itemView.context)
                .load(item?.urlToImage)
                .transform(CenterCrop(), RoundedCorners(12))
                .into(itemView.exportedImageNews)

            itemView.setOnClickListener {
                onItemClick?.invoke(item!!)
            }

            itemView.setOnLongClickListener {
                onItemLongClick?.invoke(item!!)
                true
            }
        }

    }

    inner class LoaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindState(result: bivano.apps.common.Result<List<Article>>) {
            if (result == bivano.apps.common.Result.Loading) {
                itemView.exportedProgress.visibility = View.VISIBLE
            } else {
                itemView.exportedProgress.visibility = View.GONE
            }
        }
    }

    companion object {
        val ArticleDiffCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }

        }
        private const val TYPE_PROGRESS = 0
        private const val TYPE_ITEM = 1
    }

}