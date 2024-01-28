package com.example.akrili

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

import com.bumptech.glide.Glide
import com.example.akrili.databinding.ActivityRegisterBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class Register : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding
    private val PICK_IMAGE_REQUEST = 1
   lateinit var selectedImageUri: Uri
    private lateinit var selectedImageView: ImageView





    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)



       val galleryButton = findViewById<FloatingActionButton>(R.id.galleryButtonregister)
        galleryButton.setOnClickListener {
            openGallery()
        }






        auth = FirebaseAuth.getInstance()


        binding.backtosignin.setOnClickListener {
            startActivity(Intent(this, loginpage::class.java))
            finish()

        }




    }

    private fun updateProfileWithImage(usernamesignup : String,selectedImageUri : Uri) {
        val profileupdate = UserProfileChangeRequest.Builder()
            .setDisplayName(usernamesignup)
            .setPhotoUri(selectedImageUri)
            .build()

        auth.currentUser?.updateProfile(profileupdate)?.addOnCompleteListener {
            if (it.isSuccessful) {
                auth.currentUser?.sendEmailVerification()
                Toast.makeText(this, "Registration is successful, please verify your email", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, loginpage::class.java))
                finish()
            } else {
                Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show()
            }
        }
    }



    @SuppressLint("IntentReset")
    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
    }



    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        selectedImageView = findViewById(R.id.pfpforrgs)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
             selectedImageUri = data.data!!
            Glide.with(this).load(selectedImageUri).into(selectedImageView)
        }





        binding.appCompatButton.setOnClickListener {
            val emailreg = binding.emailsignup.text.toString()
            val passwordreg = binding.passwordsignup.text.toString()
            val confpass = binding.confirmpasswordsignup.text.toString()
            val usernamesignup = findViewById<EditText>(R.id.usernamesignup).text

            if (passwordreg.isNotEmpty() && confpass.isNotEmpty() && passwordreg == confpass && emailreg.isNotEmpty() ) {



                auth.createUserWithEmailAndPassword(emailreg, passwordreg)
                    .addOnCompleteListener {

                  updateProfileWithImage(usernamesignup.toString(),selectedImageUri)


                    }







            } else { Toast.makeText(this, "Invalid credentials ", Toast.LENGTH_SHORT).show()
            }



        }







    }







}

