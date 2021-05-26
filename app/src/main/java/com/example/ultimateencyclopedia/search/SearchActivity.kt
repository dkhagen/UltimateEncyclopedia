package com.example.ultimateencyclopedia.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ultimateencyclopedia.*
import com.example.ultimateencyclopedia.database.UltimateDatabase
import com.example.ultimateencyclopedia.database.fighter.FighterEntity
import com.example.ultimateencyclopedia.home.BaseActivity
import com.example.ultimateencyclopedia.move.MoveListActivity
import com.example.ultimateencyclopedia.utils.UltimateEnums

class SearchActivity : BaseActivity(), FighterAdapter.FighterAdapterListener {
    private lateinit var editTextSearch: EditText
    private lateinit var rvFighterList: RecyclerView
    private lateinit var fighterAdapter: FighterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_search)
        hideStatusBar()
        bindViews()
        startBackgroundAnimation()
        setUpRecyclerView()
        setUpSearchBar()
    }

    private fun bindViews() {
        editTextSearch = findViewById(R.id.et_query)
        rvFighterList = findViewById(R.id.rv_fighters)
    }

    private fun setUpRecyclerView() {
        val dao = UltimateDatabase.getUltimateDatabase(applicationContext)?.fighterDao()
        val fighterList = dao?.getAllFighters()
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
        rvFighterList.requestLayout()
    }

    private fun setUpSearchBar() {
        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                fighterAdapter.filter.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onFighterSelected(fighter: FighterEntity) {
        val intent = Intent(this, MoveListActivity::class.java).apply {
            putExtra(UltimateEnums.ENUM_FIGHTER, fighter.name)
        }
        startActivity(intent)
    }
}