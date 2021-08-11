package com.test.sportzinteractivetest.view.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.test.sportzinteractivetest.R
import com.test.sportzinteractivetest.di.Injection
import com.test.sportzinteractivetest.model.PlayersModel
import com.test.sportzinteractivetest.utils.AppStrings.KEY_FULL_NAME
import com.test.sportzinteractivetest.utils.AppStrings.KEY_MATCH_DETAILS
import com.test.sportzinteractivetest.utils.AppStrings.KEY_SHORT_NAME
import com.test.sportzinteractivetest.utils.AppStrings.KEY_TEAMS
import com.test.sportzinteractivetest.utils.AppStrings.KEY_TEAM_AWAY
import com.test.sportzinteractivetest.utils.AppStrings.KEY_TEAM_HOME
import com.test.sportzinteractivetest.utils.AppStrings.KEY_TEAM_PLAYERS
import com.test.sportzinteractivetest.view.adapter.TeamsAdapter
import com.test.sportzinteractivetest.viewmodel.APIViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_custom_toolbar.*


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: APIViewModel
    var teamHomeFullName: String = ""
    var teamAwayFullName: String = ""
    var teamHomeShortName: String = ""
    var teamAwayShortName: String = ""
    var homeTeamList: ArrayList<PlayersModel> = ArrayList()
    var awayTeamList: ArrayList<PlayersModel> = ArrayList()
    private lateinit var adapter: TeamsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()
    }

    private fun setupUI() {
        adapter = TeamsAdapter(homeTeamList ?: emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        layoutHome.setOnClickListener {
            teamHomeView.setBackgroundColor(Color.parseColor("#98CA03"))
            teamAwayView.setBackgroundColor(Color.parseColor("#FFFFFF"))
            adapter.update(homeTeamList)
            recyclerView.scrollToPosition(0)
        }

        layoutAway.setOnClickListener {
            teamAwayView.setBackgroundColor(Color.parseColor("#98CA03"))
            teamHomeView.setBackgroundColor(Color.parseColor("#FFFFFF"))
            adapter.update(awayTeamList)
            recyclerView.scrollToPosition(0)
        }

    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory()
        ).get(APIViewModel::class.java)

        viewModel.teams.observe(this, renderVersions)
        viewModel.isViewLoading.observe(this, isViewLoadingObserver)
        viewModel.onMessageError.observe(this, onMessageErrorObserver)
        viewModel.isEmptyList.observe(this, emptyListObserver)
    }

    //observers
    private val renderVersions = Observer<JsonObject> {
        it.let { main ->
            val mainObject: JsonObject = main
            val matchDetailsObject: JsonObject = mainObject.getAsJsonObject(KEY_MATCH_DETAILS)
            val teamObject: JsonObject = mainObject.getAsJsonObject(KEY_TEAMS)

            matchDetailsObject.let { matchDetails ->
                val homeTeamId: String =
                    matchDetails.get(KEY_TEAM_HOME).toString().replace("\"", "")
                val awayTeamId: String =
                    matchDetails.get(KEY_TEAM_AWAY).toString().replace("\"", "")

                teamObject.let { teams ->
                    val homeTeamObject: JsonObject = teams.getAsJsonObject(homeTeamId)
                    val awayTeamObject: JsonObject = teams.getAsJsonObject(awayTeamId)
                    layoutTeamNames.visibility = View.VISIBLE
                    homeTeamObject.let { home ->
                        addPlayersInList(home, true)
                    }

                    awayTeamObject.let { away ->
                        addPlayersInList(away, false)
                    }
                }
            }
        }
    }

    private fun addPlayersInList(json: JsonObject, isHomeTeam: Boolean) {
        val teamFullName: String = json.get(KEY_FULL_NAME).toString().replace("\"", "")
        val teamShortName: String = json.get(KEY_SHORT_NAME).toString().replace("\"", "")
        val playersObject: JsonObject = json.getAsJsonObject(KEY_TEAM_PLAYERS)
        val playersList: ArrayList<PlayersModel> = ArrayList()
        playersObject.let { players ->
            for (entry in players.entrySet()) {
                val playersDetails = Gson().fromJson(entry.value, PlayersModel::class.java)
                playersList.add(playersDetails)
            }
        }
        if (isHomeTeam) {
            teamHomeFullName = teamFullName
            teamHomeShortName = teamShortName
            teamHomeTV.text = teamShortName
            homeTeamList.addAll(playersList)
            adapter.notifyDataSetChanged()
        } else {
            teamAwayFullName = teamFullName
            teamAwayShortName = teamShortName
            teamAwayTV.text = teamAwayShortName
            awayTeamList.addAll(playersList)
        }

        toolBarTitle.text = "$teamHomeFullName vs $teamAwayFullName"
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        val visibility = if (it) View.VISIBLE else View.GONE
        progressBar.visibility = visibility
    }

    private val onMessageErrorObserver = Observer<Any> {
        layoutEmpty.visibility = View.GONE
    }

    private val emptyListObserver = Observer<Boolean> {
        layoutEmpty.visibility = View.VISIBLE
    }
}