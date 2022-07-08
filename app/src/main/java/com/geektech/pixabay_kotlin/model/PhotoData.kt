package com.geektech.pixabay_kotlin.model

data class PhotoData(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
)