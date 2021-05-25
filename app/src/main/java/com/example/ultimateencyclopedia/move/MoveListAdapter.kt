package com.example.ultimateencyclopedia.move

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ultimateencyclopedia.R
import com.example.ultimateencyclopedia.database.fighter.FighterEntity
import com.example.ultimateencyclopedia.database.move.MoveEntity
import com.example.ultimateencyclopedia.utils.Utils

class MoveListAdapter(
    private val moveList: List<MoveEntity>,
    private val listener: MoveListAdapterListener,
    private val fighter: FighterEntity
) : RecyclerView.Adapter<MoveListAdapter.MoveListViewHolder>() {

    inner class MoveListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvInput: TextView = itemView.findViewById(R.id.tv_move_input)
        val tvDamage: TextView = itemView.findViewById(R.id.tv_move_damage)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_move_description)

        init {
            itemView.setOnClickListener {
                this@MoveListAdapter.listener.onMoveSelected(moveList[adapterPosition], fighter)
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    interface MoveListAdapterListener {
        fun onMoveSelected(move: MoveEntity, fighter: FighterEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoveListViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.move_row_item, parent, false)
        return MoveListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MoveListViewHolder, position: Int) {
        val move = moveList[position]
        holder.tvInput.text = move.input
        holder.tvDamage.text = move.damage // Utils.getDamageStringVertical(move.damage)
        holder.tvDescription.text = move.description
    }

    override fun getItemCount(): Int {
        return moveList.size
    }
}