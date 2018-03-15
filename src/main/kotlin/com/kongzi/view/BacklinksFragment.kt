package com.kongzi.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v7.widget.LinearLayoutManager
import com.kongzi.R
import android.content.Context
import android.util.Log
import android.widget.AdapterView
import android.widget.Spinner
import com.kongzi.model.Article
import com.kongzi.model.WikiApiService.BacklinkModel.Backlink

public class BacklinksFragment : Fragment() {

    private var dataSet: MutableList<Backlink> = ArrayList()
    var callback: FragmentToActivity? = null

    private var articleSpinner: Spinner? = null
    private var articleSpinnerAdapter: ArticleSpinnerAdapter? = null

    //Should bundle this up in onSaveInstanceState so we don't have to query it many times
    private var articles: MutableList<Article> = emptyList<Article>().toMutableList()

    protected lateinit var recyclerView: RecyclerView
    public lateinit var recyclerViewAdapter: BacklinksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataset()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            callback = this.activity as FragmentToActivity
        } catch (cce: ClassCastException) {
            throw ClassCastException(activity.toString() +
                    " must implement FragmentToActivity")
        }
    }

    override fun onDetach() {
        callback = null
        super.onDetach()
    }

    public fun updateArticlesDisplay(articles: List<Article>) {
        if (articleSpinnerAdapter != null) {
            this.articles.clear()
            this.articles.addAll(articles)
            articleSpinnerAdapter?.notifyDataSetChanged()
        } else {
            Log.d("BacklinksFragment", "articleSpinnerAdapter is null.")
        }
    }

    public fun updateBacklinkDisplay(backlinks: List<Backlink>) {
        recyclerViewAdapter.refresh(backlinks)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.backlinks_frag, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        recyclerViewAdapter = BacklinksAdapter(dataSet)
        recyclerView = view!!.findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(activity)
            scrollToPosition(0)
        }

        articleSpinner = view?.findViewById(R.id.articles) as Spinner
        if (articleSpinner == null) Log.d("BacklinksAdapter", "Could not create articleSpinner")
        else {
            articleSpinnerAdapter = ArticleSpinnerAdapter(this.activity, R.layout.article_item, articles)
            if (articleSpinnerAdapter == null) Log.d("BacklinksAdapter", "Could not create articleSpinnerAdapter")
            else {
                Log.v("BacklinksFragment", "Just created mArticeSpinnerAdapter, so it should not be null from now on...")
                articleSpinner?.adapter = articleSpinnerAdapter
            }

            articleSpinner?.onItemSelectedListener = itemSelectedListener
        }
    }

    private val itemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {
            //Nothing to do here, but required to implement
        }

        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            val articleSelected = articleSpinnerAdapter?.getItem(position)
            if (articleSelected != null) {
                callback?.selectArticle(articleSelected)
            }
        }
    }

    private fun initDataset() {
        dataSet.add(Backlink(0, 0, "Hello"))

        //Sets the UI but, on network error:
        // OnErrorNotImplementedException: Unable to resolve host "en.wikipedia.org": No address associated with hostname
        // can implement interface https://stackoverflow.com/questions/33548608/prevent-onerrornotimplementedexception
        //callback?.selectArticle(Article(666, "Donald_Trump"))
    }
}