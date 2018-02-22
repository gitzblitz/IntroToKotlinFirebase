package com.gitzblitz.introtokotlinfirebase

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
  private var mAuth: FirebaseAuth? = null
  var currentUser: FirebaseUser? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    var firebaseDatabase = FirebaseDatabase.getInstance()
    var databaseRef = firebaseDatabase.getReference("messages").push()

    mAuth = FirebaseAuth.getInstance()

    //sign existing users in
    mAuth!!.signInWithEmailAndPassword("dev.gitzblitz@gmail.com", "devondeadly123")
        .addOnCompleteListener {
          task: Task<AuthResult> ->
          if (task.isSuccessful){
            //successful sign in
            Toast.makeText(this,"Login successful",Toast.LENGTH_LONG).show()
          }else {
            //sign in failed
            Toast.makeText(this,"Login failed",Toast.LENGTH_LONG).show()
          }
        }

//    var employee = Employee("Edwin Bond", 30, "Professor", "1 Main ave")
    databaseRef.setValue("Hello There this is live from the Field Office")
//    databaseRef.setValue(employee)


    //read from database
    databaseRef.addValueEventListener(object : ValueEventListener {
      override fun onCancelled(error: DatabaseError?) {
//        Log.d("ERROR", error!!.message)
      }

      override fun onDataChange(dataSnapshot: DataSnapshot) {
//        var value = dataSnapshot.value as HashMap<String, Any>

//        Log.d("VALUE", value.get("name").toString())
      }
    })
  }

  override fun onStart() {
    super.onStart()
    currentUser = mAuth!!.currentUser
    if (currentUser != null){
      Toast.makeText(this, "User is logged in", Toast.LENGTH_LONG).show()
    }else{
      Toast.makeText(this, "User is logged out", Toast.LENGTH_LONG).show()
    }
    // update UI
  }


  //  data class Employee(var name: String, var age: Int, var position: String, var homeAddress: String)

}
