package bivano.apps.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import bivano.apps.common.*
import bivano.apps.common.model.Article
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class ArticlePagingDataAdapter : PagingDataAdapter<Article, ArticlePagingDataAdapter.ViewHolder>(
    ArticleDiffCallback) {

    var onItemClick: ((Article) -> Unit)? = null

    var onItemLongClick: ((Article) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_news_item, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(getItem(position))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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

    companion object {
        val ArticleDiffCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }

        }
    }
}