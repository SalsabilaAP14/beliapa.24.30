package com.app.beliapa

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.FirebaseDatabase

class Adapter(val mCtx: Context, val layoutResId: Int, val list: List<Items> )
    : ArrayAdapter<Items>(mCtx,layoutResId,list){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)

        val textNama = view.findViewById<TextView>(R.id.textNama)
        val edit = view.findViewById<ImageView>(R.id.icon_edit)
        val delete = view.findViewById<ImageView>(R.id.icon_delete)


        val item = list[position]

        textNama.text = item.nama
        delete.setOnClickListener {
            deleteItem(item)
        }
        edit.setOnClickListener { 
            updateItem(item)
        }
        
        return view

    }

    private fun updateItem(item: Items) {
        val builder = AlertDialog.Builder(mCtx)

        builder.setTitle("Update")

        val inflater = LayoutInflater.from(mCtx)

        val view = inflater.inflate(R.layout.update, null)

        val textItem = view.findViewById<EditText>(R.id.inputItem)

        textItem.setText(item.nama)


        builder.setView(view)

        builder.setPositiveButton("Update") { dialog, which ->

            val dbUsers = FirebaseDatabase.getInstance().getReference("item")

            val nama = textItem.text.toString().trim()


            if (nama.isEmpty()){
                textItem.error = "please enter item"
                textItem.requestFocus()
                return@setPositiveButton
            }



            val item = Items(item.id,nama)

            dbUsers.child(item.id).setValue(item).addOnCompleteListener {
                Toast.makeText(mCtx,"Updated",Toast.LENGTH_SHORT).show()
            }

        }

        builder.setNegativeButton("No") { dialog, which ->

        }

        val alert = builder.create()
        alert.show()
    }

    private fun deleteItem(item: Items) {
        val mydatabase = FirebaseDatabase.getInstance().getReference("item")
        mydatabase.child(item.id).removeValue()
        Toast.makeText(mCtx,"Deleted",Toast.LENGTH_SHORT).show()
        val intent = Intent(context, HomeActivity::class.java)
        context.startActivity(intent)
    }
}