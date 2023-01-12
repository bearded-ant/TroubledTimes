package com.ant.troubledtimes

data class Location(
    val name: String = "",
    val nextRun: String? = null,
    val status: String = "",
    val underAttackFlag: Boolean = false,
    val timeToAttack: Int = 0
)