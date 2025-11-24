package com.example.ggplay

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AppAdapter(private val appList: List<AppModel>) :
    RecyclerView.Adapter<AppAdapter.AppViewHolder>() {

    class AppViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgIcon: ImageView = view.findViewById(R.id.imgAppIcon)
        val tvName: TextView = view.findViewById(R.id.tvAppName)
        val tvRating: TextView = view.findViewById(R.id.tvAppRating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_app, parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val app = appList[position]
        holder.tvName.text = app.name
        holder.tvRating.text = app.rating
        holder.imgIcon.setImageResource(app.imageRes)
    }

    override fun getItemCount(): Int = appList.size
}

class SectionAdapter(private val sectionList: List<SectionModel>) :
    RecyclerView.Adapter<SectionAdapter.SectionViewHolder>() {

    class SectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvSectionTitle)
        val rvHorizontal: RecyclerView = view.findViewById(R.id.rvHorizontalApps)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_section, parent, false)
        return SectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        val section = sectionList[position]
        
        holder.tvTitle.text = section.title

        val appAdapter = AppAdapter(section.apps)
        holder.rvHorizontal.layoutManager = LinearLayoutManager(
            holder.itemView.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        holder.rvHorizontal.adapter = appAdapter
    }

    override fun getItemCount(): Int = sectionList.size
}
