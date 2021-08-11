package com.test.sportzinteractivetest.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.sportzinteractivetest.R
import com.test.sportzinteractivetest.model.PlayersModel
import kotlinx.android.synthetic.main.layout_version_list_items.view.*

class TeamsAdapter(private var players: List<PlayersModel>) :
    RecyclerView.Adapter<TeamsAdapter.MViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_version_list_items, parent, false)
        return MViewHolder(view)
    }

    override fun onBindViewHolder(vh: MViewHolder, position: Int) {
        vh.bind(players[position])
    }

    override fun getItemCount(): Int {
        return players.size
    }

    fun update(data: List<PlayersModel>) {
        players = data
        notifyDataSetChanged()
    }

    class MViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val playerNameTV: TextView = view.playerNameTV
        private val captainTV: TextView = view.captainTV
        private val keeperTV: TextView = view.keeperTV

        fun bind(players: PlayersModel) {
            playerNameTV.text = players.Name_Full
            if(players.Iscaptain){
                captainTV.visibility = View.VISIBLE
            }else {
                captainTV.visibility = View.GONE
            }

            if(players.Iskeeper){
                keeperTV.visibility = View.VISIBLE
            }else {
                keeperTV.visibility = View.GONE
            }
        }
    }
}