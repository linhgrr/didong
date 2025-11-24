package com.example.ggplay

data class AppModel(
    val name: String,
    val rating: String,
    val imageRes: Int
)

data class SectionModel(
    val title: String,
    val apps: List<AppModel>
)
