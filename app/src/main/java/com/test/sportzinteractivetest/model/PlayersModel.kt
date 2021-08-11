package com.test.sportzinteractivetest.model

data class PlayersModel(
    val Batting: Batting,
    val Bowling: Bowling,
    val Name_Full: String,
    val Iscaptain : Boolean = false,
    val Iskeeper : Boolean = false,
    val Position: String
)