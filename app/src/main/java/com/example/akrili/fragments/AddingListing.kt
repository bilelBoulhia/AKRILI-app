package com.example.akrili.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton

import com.bumptech.glide.Glide
import com.example.akrili.ProfilePage
import com.example.akrili.R
import com.example.akrili.homepage
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID




class AddingListing : Fragment() {






    private val PICK_IMAGE_REQUEST = 1
    private lateinit var galleryButton: FloatingActionButton
    private lateinit var firstImage: ImageView
    private lateinit var secondImage: ImageView
    private lateinit var thirdImage: ImageView



    private var imageCounter = 0

    private var imageUrls = mutableListOf<String>() // To store image URLs

    private var db = Firebase.firestore
    private lateinit var titleInput: EditText
    private lateinit var PhoneInput: EditText
    private lateinit var Discreption: EditText
    private lateinit var City: EditText
    private lateinit var Wilaya: Spinner

    private lateinit var UserId: String

    private lateinit var ADDbtn: Button
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference

    private var backPressedListener: OnBackPressedListener? = null






    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnBackPressedListener) {
            backPressedListener = context
        }
    }







    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_adding_listing, container, false)








        UserId = FirebaseAuth.getInstance().currentUser!!.uid
        titleInput = view.findViewById(R.id.titleInput)
        Wilaya = view.findViewById(R.id.Wilaya)
        City = view.findViewById(R.id.CIty)
        PhoneInput = view.findViewById(R.id.PhoneInput)
        Discreption = view.findViewById(R.id.Discreption)
        ADDbtn = view.findViewById(R.id.add_btn)


        // Button to add a new listing
        ADDbtn.setOnClickListener {

            val user = FirebaseAuth.getInstance().currentUser
            val UserIdRef = db.collection("Users").document(UserId)
            val UserUnifiedID = mapOf(

                "UnifiedId" to "s"
            )

            UserIdRef.set(UserUnifiedID)


            val userPostsCollection = db.collection("Users").document(UserId).collection("UserPosts")

            val userName = user?.displayName ?: "Unknown User"
            val userpdp = user?.photoUrl

            val data = mapOf(
                "title" to titleInput.text.toString(),
                "username" to userName,
                "phoneinput" to PhoneInput.text.toString(),
                "Wilaya" to Wilaya.selectedItem.toString(),
                "city" to City.text.toString(),
                "desc" to Discreption.text.toString(),
                "Images" to imageUrls,
                "userpfp" to userpdp
                 )
            // Add data to Firestore
            val isAnyValueNullOrEmpty = data.values.any {
                when (it) {
                    is String -> it.isNullOrEmpty()
                    is List<*> -> it.isNullOrEmpty()

                    else -> false
                }
            }
            if (isAnyValueNullOrEmpty) {  Toast.makeText(requireContext(), "please fill all data", Toast.LENGTH_SHORT).show() }else {
                userPostsCollection
                    .add(data)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(requireContext(), "Post has been added", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(requireContext(), homepage::class.java)
                        startActivity(intent)

                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(requireContext(), "Failed posting", Toast.LENGTH_SHORT)
                            .show()
                    }

            }

        }

        // Gallery button
        galleryButton = view.findViewById(R.id.galleryButton)
        firstImage = view.findViewById(R.id.firstImage)
        secondImage = view.findViewById(R.id.SecondImage)
        thirdImage = view.findViewById(R.id.thirdImage)

        galleryButton.setOnClickListener {
            openGallery()
        }


        val goBackButton = view.findViewById<AppCompatButton>(R.id.goBack)
        goBackButton.setOnClickListener {
//            val slideDown = AnimationUtils.loadAnimation(requireContext(), R.anim.transitionback)
//
//
//            view.startAnimation(slideDown)


            fragmentManager?.beginTransaction()?.remove(this)?.commit()

        }


        view.setOnTouchListener { _, event ->

            true
        }


        return view
    }




    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryIntent.type = "image/*"
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val selectedImages = mutableListOf<Uri>()

            if (data?.clipData != null) {

                val clipData = data.clipData
                for (i in 0 until clipData!!.itemCount) {
                    val imageUri = clipData.getItemAt(i).uri
                    selectedImages.add(imageUri)
                }
            } else if (data?.data != null) {
                // Single image selected
                val imageUri = data.data
                selectedImages.add(imageUri!!)
            }
            imageCounter = 0

            for (i in 0 until selectedImages.size) {
                val imageUri = selectedImages[i]
                Glide.with(this).load(selectedImages[0]).into(firstImage)
                Glide.with(this).load(selectedImages[1]).into(secondImage)
                Glide.with(this).load(selectedImages[2]).into(thirdImage)
                val imageFileName = "image_${UUID.randomUUID()}_${UserId}"
                val imageRef = storageRef.child("users/$UserId/images/$imageFileName")

                // Upload the image to Firebase Storage
                imageRef.putFile(imageUri)
                    .addOnSuccessListener { _ ->
                        // Get the download URL of the uploaded image
                        imageRef.downloadUrl.addOnSuccessListener { downloadUri ->



                            // Store the download URL in the imageUrls list
                            imageUrls.add(downloadUri.toString())
                            imageCounter++
                        }
                    }



            }
        }
    }










    override fun onDestroyView() {
        super.onDestroyView()
        // Remove the OnTouchListener to re-enable interaction with the activity
        view?.setOnTouchListener(null)
    }

}


