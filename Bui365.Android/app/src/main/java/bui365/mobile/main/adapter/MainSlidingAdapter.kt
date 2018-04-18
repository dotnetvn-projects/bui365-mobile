package bui365.mobile.main.adapter


import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bui365.mobile.main.R
import bui365.mobile.main.impl.MainItemListener
import bui365.mobile.main.model.pojo.Article
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.item_article.view.*
import java.util.*

class MainSlidingAdapter(private val context: Context, private val articles: ArrayList<Article>, private val mainItemListener: MainItemListener) : PagerAdapter() {

    override fun getCount(): Int {
        return articles.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageLayout = LayoutInflater.from(context).inflate(R.layout.item_article, container, false)

        val article = articles[position]
        assert(imageLayout != null)

        Glide.with(context).load(article.image)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                        imageLayout.progressCircle.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        imageLayout.progressCircle.visibility = View.GONE
                        return false
                    }
                }).into(imageLayout.imgRandom)

        imageLayout.txtRandom.text = article.title

        imageLayout.imgRandom.setOnClickListener { mainItemListener.onImageClick(position) }

        container.addView(imageLayout, 0)

        return imageLayout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
