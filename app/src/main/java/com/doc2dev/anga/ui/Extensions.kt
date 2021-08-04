package com.doc2dev.anga.ui

import kotlin.math.round

fun Double.formatAsTemperature(): String {
    val roundedTemp = round(this).toInt().toString()
    return "$roundedTemp°"
}

fun String.toTitleCase(): String {
    val first = this.substring(0, 1).toUpperCase()
    val others = this.substring(1, this.length).toLowerCase()
    return first + others
}