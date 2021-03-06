package com.jjf.template.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.jjf.template.R
import com.jjf.template.result.Status
import com.jjf.template.util.viewModelProvider
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_category.*
import javax.inject.Inject

/**
 * @author xj
 * date: 19-3-14
 * description :
 */
class CategoryFragment : DaggerFragment() {

    companion object {
        private const val ARG_PROJECT_CID = "arg.project_cid"
        fun newInstance(cid: Int): CategoryFragment {
            val args = Bundle().apply {
                putInt(ARG_PROJECT_CID, cid)
            }
            return CategoryFragment().apply { arguments = args }
        }
    }
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var homeViewModel: CategoryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout.setColorSchemeColors(*(resources.getIntArray(R.array.swipe_refresh)))
        swipeRefreshLayout.setOnRefreshListener {
            homeViewModel.refresh()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeViewModel = viewModelProvider(viewModelFactory)
        val cId = arguments?.getInt(ARG_PROJECT_CID)
        cId?.let { homeViewModel.setCategoryId(it) }
        val articleAdapter = HomeArticleAdapter()
        rvArticle.adapter = articleAdapter
        rvArticle.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        homeViewModel.projects.observe(viewLifecycleOwner, Observer {
            swipeRefreshLayout.isRefreshing = it.status == Status.LOADING
            if(it.status == Status.SUCCESS){
                articleAdapter.submitList(it.data)
            }
        })
    }
}