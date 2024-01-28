package com.example.akrili

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import coil.load
import com.example.akrili.fragments.AddingListing
import com.example.akrili.fragments.changeemailfrag
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class ProfilePage : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth
    private lateinit var bottomNavigationView: BottomNavigationView



    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profilepage)


        bottomNavigationView = findViewById(R.id.bottomNavigation)

        // Set the listener for item clicks // make it a class later
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    val intent = Intent(this, homepage::class.java)
                    startActivity(intent)
                    true
                }
                R.id.profile -> {
                    val intent = Intent(this, ProfilePage::class.java)
                    startActivity(intent)
                    true
                }

                R.id.searchBar -> {

                    val intent = Intent(this, Searchsection::class.java)
                    startActivity(intent)
                    true

                }
                else -> false}
        }
        ///////
        mAuth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_clientId))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)



        val textView = findViewById<TextView>(R.id.name)
        val profileImageView = findViewById<ImageView>(R.id.profileImageView)
        val auth = Firebase.auth
        val user = auth.currentUser

        val gmailtextview = findViewById<TextView>(R.id.emailtextview)
        if (user != null) {

            val userName = user.displayName
            textView.text = userName

            val usergmail = user.email
            gmailtextview.text = usergmail





            val profilePicture = user.photoUrl
            profilePicture?.let { url ->
                profileImageView.load(url)
            }
        }




        val signoutbutton = findViewById<Button>(R.id.logout_button)
            signoutbutton.setOnClickListener {
            signOutAndStartSignInActivity()

            }

      val changeemailbtn = findViewById<FrameLayout>(R.id.changeEmFrame)

        changeemailbtn.setOnClickListener {

            onLinearLayoutClick()
            
        }

    }

    private fun signOutAndStartSignInActivity() {
        mAuth.signOut()

        mGoogleSignInClient.signOut().addOnCompleteListener(this) {

            val intent = Intent(this@ProfilePage, loginpage::class.java)
       //creates new task
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }


    fun onLinearLayoutClick() {


        Toast.makeText(this, "LinearLayout clicked!" , Toast.LENGTH_SHORT).show()

        val fragment = changeemailfrag()

        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()


        transaction.add(R.id.changeEmFrame, fragment)

        transaction.addToBackStack(true.toString())


        transaction.commit();


    }

    override fun onBackPressed() {
        // Disable going back to the main page after logging out
        moveTaskToBack(true)



    }



}


