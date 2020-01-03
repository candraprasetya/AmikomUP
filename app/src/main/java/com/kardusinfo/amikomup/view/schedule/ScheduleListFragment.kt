package com.kardusinfo.amikomup.view.schedule


import android.os.Bundle
import android.util.Log
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
import com.kardusinfo.amikomup.model.Schedule
import com.kardusinfo.amikomup.viewholder.BimbinganViewHolder
import com.kardusinfo.amikomup.viewholder.ScheduleViewHolder
import kotlinx.android.synthetic.main.fragment_bimbingan_status.*
import kotlinx.android.synthetic.main.fragment_schedule_list.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * A simple [Fragment] subclass.
 */
class ScheduleListFragment : Fragment(), AnkoLogger {
    private lateinit var mAdapter: FirestorePagingAdapter<Schedule, ScheduleViewHolder>
    private val mFirestore = FirebaseFirestore.getInstance()
    private val mUserId = FirebaseAuth.getInstance().currentUser!!.uid
    private val mScheduleCollection = mFirestore.collection("users").document(mUserId).collection("jadwal")
    private var mQuery = mScheduleCollection.orderBy("time", Query.Direction.ASCENDING)
    private val mQuerySenin = mScheduleCollection.whereEqualTo("day", "Senin")
    private val mQuerySelasa = mScheduleCollection.whereEqualTo("day", "Selasa")
    private val mQueryRabu = mScheduleCollection.whereEqualTo("day", "Rabu")
    private val mQueryKamis = mScheduleCollection.whereEqualTo("day", "Kamis")
    private val mQueryJumat = mScheduleCollection.whereEqualTo("day", "Jumat")
    private val mQueryJum_at = mScheduleCollection.whereEqualTo("day", "Jum'at")
    private val mQuerySabtu = mScheduleCollection.whereEqualTo("day", "Sabtu")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_list, container, false)
    }

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

        rvSchedule.setHasFixedSize(true)
        rvSchedule.layoutManager = LinearLayoutManager(requireContext())

        setupAdapter(mQuery)


        srlSchedule.setColorSchemeResources(R.color.colorPrimary)
        srlSchedule.setOnRefreshListener {
            mAdapter.refresh()
        }
        btnAll.setOnClickListener {
            setupAdapter(mQuery)
            mAdapter.refresh()
        }
        btnSenin.setOnClickListener {
            setupAdapter(mQuerySenin)
            mAdapter.refresh()
        }
        btnSelasa.setOnClickListener {
            setupAdapter(mQuerySelasa)
            mAdapter.refresh()
        }
        btnRabu.setOnClickListener {
            setupAdapter(mQueryRabu)
            mAdapter.refresh()
        }
        btnKamis.setOnClickListener {
            setupAdapter(mQueryKamis)
            mAdapter.refresh()
        }
        btnJumat.setOnClickListener {
            setupAdapter(mQueryJumat)
            mAdapter.refresh()
        }
        btnJumat.setOnClickListener {
            setupAdapter(mQueryJum_at)
            mAdapter.refresh()
        }
        btnSabtu.setOnClickListener {
            setupAdapter(mQuerySabtu)
            mAdapter.refresh()
        }
    }

    private fun setupAdapter(Querynya: Query) {

        // Init Paging Configuration
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPrefetchDistance(0)
            .setPageSize(3)
            .build()

        // Init Adapter Configuration
        val options = FirestorePagingOptions.Builder<Schedule>()
            .setLifecycleOwner(this)
            .setQuery(Querynya, config, Schedule::class.java)
            .build()


        // Instantiate Paging Adapter
        mAdapter = object : FirestorePagingAdapter<Schedule, ScheduleViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder =
                ScheduleViewHolder(layoutInflater.inflate(R.layout.item_schedule, parent, false))

            override fun onBindViewHolder(
                viewHolder: ScheduleViewHolder,
                position: Int,
                schedule: Schedule
            ) {
                // Bind to ViewHolder
                viewHolder.bind(schedule)
            }

            override fun onLoadStateChanged(
                type: PagedList.LoadType,
                state: PagedList.LoadState,
                error: Throwable?
            ) {
                super.onLoadStateChanged(type, state, error)
                info("LOADING STATE: ${type.name} ${state.name}")
                when (type) {
                    PagedList.LoadType.START -> srlSchedule.isRefreshing = true
                    PagedList.LoadType.REFRESH -> srlSchedule.isRefreshing = true
                    PagedList.LoadType.END -> srlSchedule.isRefreshing = false
                }
            }
        }

        rvSchedule.adapter = mAdapter
    }

}
