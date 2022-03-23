package com.app.beliapa

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add.*


class AddActivity : AppCompatActivity() {

    lateinit var ref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        ref = FirebaseDatabase.getInstance().getReference("item")

        btnAdd.setOnClickListener {
            savedata()
        }
    }

    private fun savedata() {
        val nama = inputItem.text.toString()

        val itemId = ref.push().key.toString()
        val item = Items(itemId,nama)


        ref.child(itemId).setValue(item).addOnCompleteListener {
            Toast.makeText(this, "Successs",Toast.LENGTH_SHORT).show()
            inputItem.setText("")
        }
    }
}