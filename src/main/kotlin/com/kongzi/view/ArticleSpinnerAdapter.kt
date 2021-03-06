package com.kongzi.view

import android.databinding.adapters.TextViewBindingAdapter.setText
import android.widget.TextView
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import org.intellij.lang.annotations.Language
import android.view.ViewGroup
import android.content.Context
import android.widget.ArrayAdapter
import com.kongzi.R
import com.kongzi.model.Article
import com.kongzi.model.WikiApiService.BacklinkModel.Backlink

class ArticleSpinnerAdapter(context: Context, resource: Int, objects: List<Article>) :ArrayAdapter<Article>(context, resource, objects) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView)
    }

    private fun getCustomView(position: Int, convertView: View?): View {
        val holder: ViewHolder
        var view = convertView

        if (view == null) {
            view = inflateView()
            val textView = view.findViewById<View>(android.R.id.text1) as TextView
            holder = ViewHolder(textView)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val article: Article = getItem(position)
        holder.bind(article.title)

        return view
    }

    private fun inflateView(): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return inflater.inflate(R.layout.article_item, null)
    }

    private inner class ViewHolder(private val mText: TextView) {
        fun bind(text: String) {
            mText.text = text
        }
    }
}