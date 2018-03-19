package bui365.mobile.main.adapter


import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

import java.util.ArrayList

import bui365.mobile.main.R
import bui365.mobile.main.impl.MainItemListener
import bui365.mobile.main.model.pojo.Article

class MainSlidingAdapter(private val context: Context, private val mArticles: ArrayList<Article>, private val mMainItemListener: MainItemListener) : PagerAdapter() {

    override fun getCount(): Int {
        return mArticles.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageLayout = LayoutInflater.from(context).inflate(R.layout.item_article, container, false)

        val article = mArticles[position]
        assert(imageLayout != null)
        val imgRandom = imageLayout!!.findViewById<ImageView>(R.id.imgRandom)
        val progressCircle = imageLayout.findViewById<ProgressBar>(R.id.progressCircle)
        val txtRandom = imageLayout.findViewById<TextView>(R.id.txtRandom)

        Glide.with(context).load(article.image)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                        progressCircle.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        progressCircle.visibility = View.GONE
                        return false
                    }
                }).into(imgRandom)

        txtRandom.text = article.title

        imgRandom.setOnClickListener { mMainItemListener.onImageClick(position) }

        container.addView(imageLayout, 0)

        return imageLayout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
