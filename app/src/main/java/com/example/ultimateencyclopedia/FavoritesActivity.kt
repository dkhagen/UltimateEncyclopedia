package com.example.ultimateencyclopedia

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ultimateencyclopedia.database.UltimateDatabase
import com.example.ultimateencyclopedia.database.UltimateDatabase.Companion.getUltimateDatabase
import com.example.ultimateencyclopedia.database.fighter.FighterEntity
import com.example.ultimateencyclopedia.home.BaseActivity
import com.example.ultimateencyclopedia.move.MoveListActivity
import com.example.ultimateencyclopedia.search.FighterAdapter
import com.example.ultimateencyclopedia.utils.UltimateEnums

class FavoritesActivity : BaseActivity(), FighterAdapter.FighterAdapterListener {
    private lateinit var rvFighterList: RecyclerView
    private lateinit var fighterAdapter: FighterAdapter
    private lateinit var tvNoFavorites: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_favorites)
        bindViews()
        startBackgroundAnimation()
        setUpRecyclerView()
    }

    private fun bindViews() {
        rvFighterList = findViewById(R.id.rv_favorite_fighters)
        tvNoFavorites = findViewById(R.id.tv_no_favorites_selected)
    }

    override fun onResume() {
        super.onResume()
        fighterAdapter.setListData(ArrayList(getUltimateDatabase(applicationContext)?.fighterDao()?.getFavoriteFighters()))

        rvFighterList.requestLayout()
    }

    private fun setUpRecyclerView() {
        val dao = getUltimateDatabase(applicationContext)?.fighterDao()
        val fighterList = dao?.getFavoriteFighters()
        fighterAdapter = FighterAdapter(this, fighterList!!, this)
        val layoutManager = LinearLayoutManager(applicationContext)
        rvFighterList.layoutManager = layoutManager
        rvFighterList.itemAnimator = DefaultItemAnimator()
        rvFighterList.addItemDecoration(
            DividerItemDecoration(
                applicationContext,
                layoutManager.orientation
            )
        )
        rvFighterList.adapter = fighterAdapter
        if (fighterList.isEmpty()) {
            tvNoFavorites.visibility = View.VISIBLE
            rvFighterList.visibility = View.GONE
            rvFighterList.requestLayout()
        } else {
            tvNoFavorites.visibility = View.GONE
            rvFighterList.visibility = View.VISIBLE
            rvFighterList.requestLayout()
        }
    }

    override fun onFighterSelected(fighter: FighterEntity) {
        val intent = Intent(this, MoveListActivity::class.java).apply {
            putExtra(UltimateEnums.ENUM_FIGHTER, fighter.name)
        }
        startActivity(intent)
    }
}