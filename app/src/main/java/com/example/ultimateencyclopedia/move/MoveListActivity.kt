package com.example.ultimateencyclopedia.move

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.ultimateencyclopedia.R
import com.example.ultimateencyclopedia.database.fighter.FighterEntity
import com.example.ultimateencyclopedia.utils.UltimateEnums.Companion.ENUM_FIGHTER
import com.example.ultimateencyclopedia.database.move.MoveEntity
import com.example.ultimateencyclopedia.home.BaseActivity
import com.example.ultimateencyclopedia.utils.UltimateEnums
import com.example.ultimateencyclopedia.utils.Utils

class MoveListActivity : BaseActivity(), MoveListAdapter.MoveListAdapterListener {
    private lateinit var ivFighterImage: ImageView
    private lateinit var tvFighterName: TextView
    private lateinit var tvFighterSeries: TextView
    private lateinit var rvMoveList: RecyclerView
    private lateinit var ibFavorite: ImageButton
    private lateinit var fighterName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fighter_detail)
        hideStatusBar()
        fighterName = intent.getStringExtra(ENUM_FIGHTER)!!
        val fighter = grabFighterByName(fighterName)
        if (fighter != null) {
            val moveList = grabMovesForFighter(fighter.name!!)
            bindViews()
            setListeners()
            setData(fighter)
            setMoveList(ArrayList(moveList), fighter)
        }
        startMarquees()
        startBackgroundAnimation()
    }

    private fun bindViews() {
        ivFighterImage = findViewById(R.id.iv_fighter_image_detail)
        tvFighterName = findViewById(R.id.tv_detail_fighter_name)
        tvFighterSeries = findViewById(R.id.tv_detail_fighter_series)
        rvMoveList = findViewById(R.id.rv_move_list)
        ibFavorite = findViewById(R.id.ib_favorite)
    }

    private fun setListeners() {
        ibFavorite.setOnClickListener {
            it.isSelected = !it.isSelected
            Utils.setIsFavorite(fighterName, it.isSelected, applicationContext)
        }
    }

    private fun setData(fighter: FighterEntity) {
        tvFighterName.text = fighter.name
        tvFighterSeries.text = fighter.series
        ibFavorite.isSelected = fighter.isFavorite

        Glide.with(this)
            .load(fighter.image)
            .apply(RequestOptions.fitCenterTransform())
            .into(ivFighterImage)
    }

    private fun startMarquees() {
        tvFighterName.postDelayed({ tvFighterName.isSelected = true }, 2000L)
        tvFighterSeries.postDelayed({ tvFighterSeries.isSelected = true }, 2000L)
    }

    private fun setMoveList(moveList: ArrayList<MoveEntity>, fighter: FighterEntity) {
        val adapter = MoveListAdapter(moveList, this, fighter)
        val layoutManager = LinearLayoutManager(applicationContext)
        rvMoveList.addItemDecoration(
            DividerItemDecoration(
                applicationContext,
                layoutManager.orientation
            )
        )
        rvMoveList.layoutManager = layoutManager
        rvMoveList.itemAnimator = DefaultItemAnimator()
        rvMoveList.adapter = adapter
    }

    override fun onMoveSelected(move: MoveEntity, fighter: FighterEntity) {
        val intent = Intent(this, MoveDetailActivity::class.java).apply {
            putExtra(UltimateEnums.ENUM_FIGHTER, fighter.name)
            putExtra(UltimateEnums.ENUM_MOVE, move.id)
        }
        startActivity(intent)
    }

    private fun grabMovesForFighter(fighterName: String): List<MoveEntity> {
        return Utils.retrieveMovesForFighter(fighterName, applicationContext)
    }

    private fun grabFighterByName(name: String): FighterEntity? {
        return Utils.retrieveFighterByName(name, applicationContext)
    }
}