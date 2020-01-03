package com.kardusinfo.amikomup.view.bimbingan.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.kardusinfo.amikomup.R
import com.kardusinfo.amikomup.model.Bimbingan
import com.kardusinfo.amikomup.viewholder.BimbinganViewHolder
import kotlinx.android.synthetic.main.fragment_bimbingan_status.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * A simple [Fragment] subclass.
 */
class BimbinganStatus : Fragment(), AnkoLogger {

    private lateinit var mAdapter: FirestorePagingAdapter<Bimbingan, BimbinganViewHolder>
    private val mFirestore = FirebaseFirestore.getInstance()
    private val mUserId = FirebaseAuth.getInstance().currentUser!!.uid
    private val mBimbinganCollection =
            mFirestore.collection("users").document(mUserId).collection("bimbingan")
    private val mQuery = mBimbinganCollection.orderBy("tanggal", Query.Direction.DESCENDING)


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? =
            inflater.inflate(R.layout.fragment_bimbingan_status, container, false)

    override fun onStart() {
        super.onStart()
        mAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        mAdapter.stopListening()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        setupAdapter()


        srlBimbingan.setColorSchemeResources(R.color.colorPrimary)
        srlBimbingan.setOnRefreshListener {
            mAdapter.refresh()
        }
    }

    private fun setupAdapter() {

        // Init Paging Configuration
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPrefetchDistance(0)
                .setPageSize(3)
                .build()

        // Init Adapter Configuration
        val options = FirestorePagingOptions.Builder<Bimbingan>()
                .setLifecycleOwner(this)
                .setQuery(mQuery, config, Bimbingan::class.java)
                .build()

        // Instantiate Paging Adapter
        mAdapter = object : FirestorePagingAdapter<Bimbingan, BimbinganViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BimbinganViewHolder =
                    BimbinganViewHolder(layoutInflater.inflate(R.layout.item_bimbingan, parent, false))

            override fun onBindViewHolder(viewHolder: BimbinganViewHolder, position: Int, bimbingan: Bimbingan) {
                // Bind to ViewHolder
                viewHolder.bind(bimbingan)


            }

            override fun onLoadStateChanged(type: PagedList.LoadType, state: PagedList.LoadState, error: Throwable?) {
                super.onLoadStateChanged(type, state, error)
                info("LOADING STATE: ${type.name} ${state.name}")
                when (type) {
                    PagedList.LoadType.START -> srlBimbingan.isRefreshing = true
                    PagedList.LoadType.REFRESH -> srlBimbingan.isRefreshing = true
                    PagedList.LoadType.END -> srlBimbingan.isRefreshing = false
                }
            }
        }

        recyclerView.adapter = mAdapter
    }

}
