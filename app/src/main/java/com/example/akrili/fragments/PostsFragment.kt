  package com.example.akrili.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.akrili.Adapter.MyAdapter
import com.example.akrili.Adapter.MyAdapterImagePost
import com.example.akrili.Data.Posts
import com.example.akrili.Data.imageslist


import com.example.akrili.R
import com.example.akrili.homepage

interface OnBackPressedListener {
    fun onBackPressed()
}

class postsFragment(private val imageslist: imageslist , private val restofdata : Posts ) : Fragment() {


    private var backPressedListener: OnBackPressedListener? = null



    lateinit var ImagePostRecylerView: RecyclerView



    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnBackPressedListener) {
            backPressedListener = context
        }
    }


  override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)





    }

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_posts, container, false)
        //recyclerview
        ImagePostRecylerView = view.findViewById(R.id.showingPostimages)
        ImagePostRecylerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        ImagePostRecylerView.adapter = MyAdapterImagePost(imageslist)
        //


        val titletextview : TextView = view.findViewById(R.id.titleinpage)
        titletextview.text = restofdata.title

        val usernametextview : TextView = view.findViewById(R.id.usernameinpage)
            usernametextview.text = restofdata.username

        val phonetextview : TextView = view.findViewById(R.id.phoneinpage)
            phonetextview.text = restofdata.phone



        val locationtextview : TextView = view.findViewById(R.id.loactioninpage)
            locationtextview.text = restofdata.location


        val citytextview : TextView = view.findViewById(R.id.cityinpage)
        citytextview.text = restofdata.city

        val desctextview : TextView = view.findViewById(R.id.descinpage)
            desctextview.text = restofdata.des


        val pfpimage : ImageView = view.findViewById(R.id.pdpinpage)

            Glide.with(requireContext())
            .load(restofdata.userpfp)
            .into(pfpimage)









            return view
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val goBackButton = view.findViewById<AppCompatButton>(R.id.goBack)
        goBackButton.setOnClickListener {


            fragmentManager?.beginTransaction()?.remove(this)?.commit()
            // Apply the animation to the fragment's view

        }

    }

    override fun onResume() {
        super.onResume()
        // Disable interactions with the underlying activity
        activity?.window?.decorView?.apply {
            isClickable = false
            isFocusable = false
        }
    }

    override fun onPause() {
        super.onPause()
        // Re-enable interactions with the underlying activity
        activity?.window?.decorView?.apply {
            isClickable = true
            isFocusable = true
        }
    }
}




