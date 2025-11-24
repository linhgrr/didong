package com.example.gmail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val emails = arrayListOf<EmailModel>()
        
        emails.add(EmailModel(
            "Edurila.com",
            "$19 Only (First 10 spots) - Bestselling... - Are you looking to Learn Web Designing..."
        ))

        emails.add(EmailModel(
            "Chris Abad",
            "Help make Campaign Monitor better - Let us know your thoughts! No Images..."
        ))

        emails.add(EmailModel(
            "Tuto.com",
            "8h de formation gratuite et les nouvea... - Photoshop, SEO, Blender, CSS, WordPre..."
        ))

        emails.add(EmailModel(
            "support",
            "Société Ovh : suivi de vos services - hp... - SAS OVH - http://www.ovh.com 2 rue K..."
        ))

        emails.add(EmailModel(
            "Matt from Ionic",
            "The New Ionic Creator Is Here! - Announcing the all-new Creator, build..."
        ))

        for (i in 1..15) {
            emails.add(EmailModel(
                "Sender $i",
                "Subject Email $i - This is the content of email $i. Just a demo text for the simplified layout."
            ))
        }

        val adapter = EmailAdapter(emails)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}
