package com.example.ggplay

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dummyData = createDummyData()

        val rvMain = findViewById<RecyclerView>(R.id.rvMain)
        rvMain.layoutManager = LinearLayoutManager(this)
        rvMain.adapter = SectionAdapter(dummyData)
    }

    private fun createDummyData(): List<SectionModel> {
        val sections = mutableListOf<SectionModel>()

        fun getApps(prefix: String): List<AppModel> {
            return listOf(
                AppModel("$prefix RPG", "4.8 ★", R.mipmap.ic_launcher),
                AppModel("$prefix Action", "4.5 ★", R.mipmap.ic_launcher),
                AppModel("$prefix Puzzle", "4.2 ★", R.mipmap.ic_launcher),
                AppModel("$prefix Strategy", "4.9 ★", R.mipmap.ic_launcher),
                AppModel("$prefix Racing", "4.0 ★", R.mipmap.ic_launcher),
                AppModel("$prefix Shooter", "4.6 ★", R.mipmap.ic_launcher)
            )
        }

        sections.add(SectionModel("Suggested for you", getApps("Epic")))
        sections.add(SectionModel("New & Updated Games", getApps("Super")))
        sections.add(SectionModel("Recommended for you", getApps("Meta")))
        sections.add(SectionModel("Offline Games", getApps("Solo")))
        sections.add(SectionModel("Editors' Choice", getApps("Best")))

        return sections
    }
}