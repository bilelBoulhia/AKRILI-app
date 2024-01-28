package com.example.akrili.fragments

import android.annotation.SuppressLint
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
import com.example.akrili.Adapter.MyAdapterImagePost
import com.example.akrili.Data.imageslist
import com.example.akrili.Data.searchpost
import com.example.akrili.R
import com.example.akrili.Searchsection
import com.example.akrili.homepage


class Searchpostfragment(private val imagearraylist : imageslist ,private val restofdata : searchpost) : Fragment() {

    lateinit var ImagePostRecylerView: RecyclerView


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_searchpostfragment, container, false)



        ImagePostRecylerView = view.findViewById(R.id.showingPostimagessearch)
        ImagePostRecylerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        ImagePostRecylerView.adapter = MyAdapterImagePost(imagearraylist)


        val titletextview : TextView = view.findViewById(R.id.titleinpagesearch)
        titletextview.text = restofdata.searchtitle


        val locationtextview : TextView = view.findViewById(R.id.loactioninpagesearch)
        locationtextview.text = restofdata.searchlocation


        val citytextview : TextView = view.findViewById(R.id.cityinpagesearch)
        citytextview.text = restofdata.searchcity
        val phonetextview : TextView = view.findViewById(R.id.phoneinpagesearch)
        phonetextview.text = restofdata.searchphone

        val desctextview : TextView = view.findViewById(R.id.descinpagesearch)
        desctextview.text = restofdata.searchdes


        val usernametextview : TextView = view.findViewById(R.id.usernameinpagesearch)
        usernametextview.text = restofdata.searchusername

        val pfpimage : ImageView = view.findViewById(R.id.pdpinpagesearch)

        Glide.with(requireContext())
            .load(restofdata.searchpfp)
            .into(pfpimage)


        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val goBackButton = view.findViewById<AppCompatButton>(R.id.goBacksearch)

        goBackButton.setOnClickListener {
            val intent = Intent(requireContext(), Searchsection::class.java)
            startActivity(intent)
//            val slideDown = AnimationUtils.loadAnimation(requireContext(), R.anim.transitionback)
//
//            // Apply the animation to the fragment's view
//            view.startAnimation(slideDown)
        }

    }
}