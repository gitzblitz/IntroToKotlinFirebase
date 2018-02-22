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
    var databaseRef = firebaseDatabase.getReference("messages").push()

    var employee = Employee("Edwin Bond", 30, "Professor", "1 Main ave")
//    databaseRef.setValue("Hello There this is live from the Field Office")
    databaseRef.setValue(employee)


    //read from database
    databaseRef.addValueEventListener(object : ValueEventListener {
      override fun onCancelled(error: DatabaseError?) {
//        Log.d("ERROR", error!!.message)
      }

      override fun onDataChange(dataSnapshot: DataSnapshot) {
        var value = dataSnapshot.value as HashMap<String,Any>

        Log.d("VALUE", value.get("name").toString())
      }
    })
  }

  data class Employee(var name: String, var age: Int, var position: String, var homeAddress: String)

}
