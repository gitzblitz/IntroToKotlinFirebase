package com.gitzblitz.introtokotlinfirebase

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
  private var mAuth: FirebaseAuth? = null
  var currentUser: FirebaseUser? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    var firebaseDatabase = FirebaseDatabase.getInstance()
    var databaseRef = firebaseDatabase.getReference("messages").push()

    mAuth = FirebaseAuth.getInstance()
    //create user account


    createActId.setOnClickListener {
      var email = emailID?.text.toString().trim()
      var pwd = passwordId?.text.toString().trim()
      Log.d("VALUES", "email= $email password= $pwd")
      mAuth!!.createUserWithEmailAndPassword(email, pwd)
          .addOnCompleteListener(this, { task: Task<AuthResult> ->
            if (task.isSuccessful) {
              var user: FirebaseUser = mAuth!!.currentUser!!
              Log.d("FIREBASE CREATE", "createUserWithEmail:success. User = " + user.email.toString())
            } else {
              Log.d("FIREBASE CREATE", "createUserWithEmail:failure" + task.exception.toString())
            }
          })

    }


    //sign existing users in
    mAuth!!.signInWithEmailAndPassword("dev.gitzblitz@gmail.com", "devondeadly123")
        .addOnCompleteListener { task: Task<AuthResult> ->
          if (task.isSuccessful) {
            //successful sign in
            Toast.makeText(this, "Login successful", Toast.LENGTH_LONG).show()
          } else {
            //sign in failed
            Toast.makeText(this, "Login failed", Toast.LENGTH_LONG).show()
          }
        }

////    var employee = Employee("Edwin Bond", 30, "Professor", "1 Main ave")
//    databaseRef.setValue("Hello There this is live from the Field Office")
////    databaseRef.setValue(employee)
//
//    //read from database
//    databaseRef.addValueEventListener(object : ValueEventListener {
//      override fun onCancelled(error: DatabaseError?) {
////        Log.d("ERROR", error!!.message)
//      }
//
//      override fun onDataChange(dataSnapshot: DataSnapshot) {
////        var value = dataSnapshot.value as HashMap<String, Any>
//
////        Log.d("VALUE", value.get("name").toString())
//      }
//    })
  }

  override fun onStart() {
    super.onStart()
    currentUser = mAuth!!.currentUser
    if (currentUser != null) {
      Toast.makeText(this, "User is logged in", Toast.LENGTH_LONG).show()
    } else {
      Toast.makeText(this, "User is logged out", Toast.LENGTH_LONG).show()
    }
    // update UI
  }


  //  data class Employee(var name: String, var age: Int, var position: String, var homeAddress: String)

}
