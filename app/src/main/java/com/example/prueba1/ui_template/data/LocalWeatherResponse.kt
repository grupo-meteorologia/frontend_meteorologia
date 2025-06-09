package com.example.prueba1.ui_template.data

import com.google.gson.annotations.SerializedName

data class LocalWeatherResponse(
    val pp: Double? = null,
    @SerializedName("t2")
    val t2: Double? = null,
    val psfc: Double? = null,
    val tslb: Double? = null,
    val smois: Double? = null,
    val aclwdnb: Double? = null,
    val aclwupb: Double? = null,
    val acswdnb: Double? = null,
    @SerializedName("hr2")
    val hr2: Double? = null,
    @SerializedName("freezing_level")
    val freezingLevel: Double? = null,
    @SerializedName("dirviento10")
    val dirViento10: Double? = null,
    @SerializedName("magviento10")
    val magViento10: Double? = null,
    val tmin: Double? = null,
    val tmax: Double? = null
)
