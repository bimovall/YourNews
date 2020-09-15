package bivano.apps.homefeature

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import bivano.apps.common.model.Article
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.view_home_featured_item.view.*
import kotlin.math.min

class HomeFeaturedAdapter(private val listArticle: List<Article>) :
    RecyclerView.Adapter<HomeFeaturedAdapter.ViewHolder>() {

    var featuredItemClick : ((Article) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_home_featured_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return min(listArticle.size, 5)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(listArticle[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(article: Article) {
            itemView.tv_featured_title.text = article.title

            Glide.with(itemView.context)
                .load(article.urlToImage)
                .transform(CenterCrop(), RoundedCorners(12))
                .into(itemView.img_cover)

            itemView.setOnClickListener {
                featuredItemClick?.invoke(article)
            }
        }

    }
}