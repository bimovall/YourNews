package bivano.apps.articlefeature

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import bivano.apps.common.exportedImageNews
import bivano.apps.common.exportedTextViewDesc
import bivano.apps.common.exportedTextViewTime
import bivano.apps.common.exportedTextViewTitle
import bivano.apps.common.model.Article
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class ArticleAdapter : PagedListAdapter<Article, ArticleAdapter.ViewHolder>(ArticleDiffCallback) {

    var onItemClick: ((Article) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //TODO type "progress" and "article"
        val view = LayoutInflater.from(parent.context)
            .inflate(bivano.apps.common.R.layout.view_news_item, parent, false)
        return ViewHolder(view)
    }

    /*override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ArticleViewHolder).bindItems(getItem(position))
    }*/

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(getItem(position))
        println("CHeck onBindViewHolder")
    }

    //unused for now
    inner class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItems(item: Article?) {
            itemView.exportedTextViewTitle.text = item?.title
            itemView.exportedTextViewDesc.text = item?.description
            itemView.exportedTextViewTime.text = item?.publishedAt.toString()

            Glide.with(itemView.context)
                .load(item?.urlToImage)
                .transform(CenterCrop(), RoundedCorners(12))
                .into(itemView.exportedImageNews)
        }

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItems(item: Article?) {
            item?.apply {
                itemView.exportedTextViewTitle.text = item.title
                itemView.exportedTextViewDesc.text = item.description
                itemView.exportedTextViewTime.text = item.publishedAt.toString()

                Glide.with(itemView.context)
                    .load(item.urlToImage)
                    .transform(CenterCrop(), RoundedCorners(12))
                    .into(itemView.exportedImageNews)

                itemView.setOnClickListener {
                    onItemClick?.invoke(item)
                }
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