package bivano.apps.homefeature

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bivano.apps.common.exportedImageNews
import bivano.apps.common.exportedTextViewDesc
import bivano.apps.common.exportedTextViewTime
import bivano.apps.common.exportedTextViewTitle
import bivano.apps.common.model.Article
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlin.math.min

class HomeAdapter(private val listArticle: List<Article>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    var itemClick: ((Article)-> Unit)? = null

    var itemLongClick: ((Article)-> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(bivano.apps.common.R.layout.view_news_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return min(10, listArticle.size)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listArticle[position]
        holder.bindView(item)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(item: Article) {
            itemView.exportedTextViewTitle.text = item.title
            itemView.exportedTextViewDesc.text = item.description
            itemView.exportedTextViewTime.text = item.publishedAt.toString()

            Glide.with(itemView.context)
                .load(item.urlToImage)
                .transform(CenterCrop(), RoundedCorners(12))
                .into(itemView.exportedImageNews)

            itemView.setOnClickListener {
                itemClick?.invoke(item)
            }

            itemView.setOnLongClickListener {
                itemLongClick?.invoke(item)
                true
            }
        }

    }
}