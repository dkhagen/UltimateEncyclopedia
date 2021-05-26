package com.example.ultimateencyclopedia.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.ultimateencyclopedia.R
import com.example.ultimateencyclopedia.search.FighterAdapter.FighterViewHolder
import com.example.ultimateencyclopedia.database.fighter.FighterEntity

class FighterAdapter(
    private val context: Context,
    private var fighterList: List<FighterEntity>,
    private val listener: FighterAdapterListener
) : RecyclerView.Adapter<FighterViewHolder>(), Filterable {
    private var fighterListFiltered: List<FighterEntity> = emptyList()

    inner class FighterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fighterImage: ImageView = itemView.findViewById(R.id.iv_fighter_image)
        val fighterName: TextView = itemView.findViewById(R.id.tv_fighter_name)
        val fighterSeries: TextView = itemView.findViewById(R.id.tv_fighter_series)

        init {
            itemView.setOnClickListener {
                this@FighterAdapter.listener.onFighterSelected(fighterListFiltered[adapterPosition])
            }
        }
    }

    init {
        fighterListFiltered = fighterList
    }

    fun setListData(data: ArrayList<FighterEntity>) {
        fighterList = data
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return FighterFilter()
    }

    interface FighterAdapterListener {
        fun onFighterSelected(fighter: FighterEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FighterViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fighter_row_item, parent, false)
        return FighterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FighterViewHolder, position: Int) {
        val fighter = fighterListFiltered[position]
        holder.fighterName.text = fighter.name
        holder.fighterSeries.text = fighter.series
        holder.fighterSeries.isSelected = true

        Glide.with(context)
            .load(fighter.image)
            .apply(RequestOptions.fitCenterTransform())
            .into(holder.fighterImage)
    }

    override fun getItemCount(): Int {
        return fighterListFiltered.size
    }

    inner class FighterFilter : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val charString = constraint.toString()
            fighterListFiltered = if (charString.isEmpty()) {
                fighterList
            } else {
                val filteredList = mutableListOf<FighterEntity>()
                for (fighter in fighterList) {
                    if (fighter.name!!.lowercase().contains(charString.lowercase()) ||
                        fighter.series!!.lowercase().contains(charString.lowercase())) {
                        filteredList.add(fighter)
                    }
                }
                filteredList
            }
            val filterResults = FilterResults()
            filterResults.values = fighterListFiltered
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            fighterListFiltered = results?.values as ArrayList<FighterEntity>
            notifyDataSetChanged()
        }

    }
}