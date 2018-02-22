package com.gitzblitz.introtokotlinfirebase

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    var firebaseDatabase = FirebaseDatabase.getInstance()
    var databaseRef = firebaseDatabase.getReference("messages")
    databaseRef.setValue("Hello There this is live from the Field Office")


    //read from database
    databaseRef.addValueEventListener(object : ValueEventListener {
      override fun onCancelled(error: DatabaseError?) {
        Log.d("ERROR", error!!.message)
      }

      override fun onDataChange(dataSnapshot: DataSnapshot) {
        var value = dataSnapshot.value

        Log.d("VALUE", value.toString())
        helloTextView.text = value.toString()
      }
    })
  }
}
