package com.example.ultimateencyclopedia.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.ultimateencyclopedia.database.UltimateDatabase
import com.example.ultimateencyclopedia.database.fighter.FighterEntity

class SearchActivityViewModel(app: Application) : AndroidViewModel(app) {
    lateinit var allFighters : MutableLiveData<List<FighterEntity>>

    init {
        allFighters = MutableLiveData()
    }

    fun getAllFightersObserver(): MutableLiveData<List<FighterEntity>> {
        return allFighters
    }

    fun getAllFighters() {
        val fighterDao = UltimateDatabase.getUltimateDatabase(getApplication())?.fighterDao()
        val fighterList = fighterDao?.getAllFighters()
        allFighters.postValue(fighterList)
    }


}