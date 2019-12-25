package com.kardusinfo.amikomup.view.bimbingan.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.kardusinfo.amikomup.R
import com.kardusinfo.amikomup.model.Bimbingan
import com.kardusinfo.amikomup.viewholder.BimbinganViewHolder
import kotlinx.android.synthetic.main.fragment_bimbingan_status.*

/**
 * A simple [Fragment] subclass.
 */
class BimbinganStatus : Fragment() {

    private lateinit var mAdapter: FirestorePagingAdapter<Bimbingan, BimbinganViewHolder>
    private val mFirestore = FirebaseFirestore.getInstance()
    private val mUserId = FirebaseAuth.getInstance().currentUser!!.uid
    private val mBimbinganCollection =
        mFirestore.collection("users").document(mUserId).collection("bimbingan")
    private val mQuery = mBimbinganCollection.orderBy("tanggal", Query.Direction.DESCENDING)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bimbingan_status, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        setupAdapter()

        swipeRefreshLayout.setOnRefreshListener {
            
        }
    }

    private fun setupAdapter() {

        // Init Paging Configuration
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPrefetchDistance(2)
            .setPageSize(10)
            .build()

        // Init Adapter Configuration
        val options = FirestorePagingOptions.Builder<Bimbingan>()
            .setLifecycleOwner(this)
            .setQuery(mQuery, config, Bimbingan::class.java)
            .build()

        // Instantiate Paging Adapter
        mAdapter = object : FirestorePagingAdapter<Bimbingan, BimbinganViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BimbinganViewHolder {
                val view = layoutInflater.inflate(R.layout.item_bimbingan, parent, false)
                return BimbinganViewHolder(view)
            }

            override fun onBindViewHolder(
                viewHolder: BimbinganViewHolder,
                position: Int,
                bimbingan: Bimbingan
            ) {
                // Bind to ViewHolder

                viewHolder.bind(bimbingan)
            }

            override fun onLoadingStateChanged(state: LoadingState) {
                when (state) {
                    LoadingState.LOADING_INITIAL -> {
                        swipeRefreshLayout.isRefreshing = true
                    }

                    LoadingState.LOADING_MORE -> {
                        swipeRefreshLayout.isRefreshing = true
                    }

                    LoadingState.LOADED -> {
                        swipeRefreshLayout.isRefreshing = false
                    }

                    LoadingState.ERROR -> {
                        Toast.makeText(
                            requireContext(),
                            "Error Occurred!",
                            Toast.LENGTH_SHORT
                        ).show()
                        swipeRefreshLayout.isRefreshing = false
                    }

                    LoadingState.FINISHED -> {
                        swipeRefreshLayout.isRefreshing = false
                    }
                }
            }

        }

        recyclerView.adapter = mAdapter
    }

}
