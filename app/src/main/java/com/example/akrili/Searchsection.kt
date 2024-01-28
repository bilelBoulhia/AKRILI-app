package com.example.akrili

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.akrili.Adapter.AdapterSearchResults
import com.example.akrili.Data.imageslist
import com.example.akrili.Data.searchpost
import com.example.akrili.fragments.Searchpostfragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore

class Searchsection : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var searchbar: androidx.appcompat.widget.SearchView
    private lateinit var searcharrayList: ArrayList<searchpost>
    private lateinit var ImagesList: ArrayList<imageslist>
    private lateinit var resultsearchrv: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchsection)

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        searchbar = findViewById(R.id.searchtext)


        resultsearchrv = findViewById(R.id.recyclerViewforsearch)
        resultsearchrv.layoutManager = LinearLayoutManager(this)
        resultsearchrv.setHasFixedSize(true)
        searcharrayList = arrayListOf<searchpost>()
        ImagesList = arrayListOf<imageslist>()


        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
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

                R.id.home -> {
                    val intent = Intent(this, homepage::class.java)
                    startActivity(intent)
                    true
                }

                else -> false } }



        searchbar.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    searcharrayList.clear()
                   fetchUserSearchPosts(query)


                }else searcharrayList.clear() ;
                return true ;
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    searcharrayList.clear()
                    fetchUserSearchPosts(newText)


                }
                return true

            }
        })


    }

    override fun onStart() {
        super.onStart()



    }

    fun onItemClick(position: Int) {
        val restofdata = searcharrayList[position]
        val imagesForSelectedPost = ImagesList[position]
        val fragment = Searchpostfragment(imagesForSelectedPost,restofdata)


        val bottomNavigationbar = findViewById<BottomNavigationView>(R.id.bottom_navigation)



        bottomNavigationbar.visibility = View.GONE


        supportFragmentManager.beginTransaction()
            .replace(R.id.containerSEARCH, fragment)
            .addToBackStack(null)
            .commit()


    }




    @SuppressLint("SuspiciousIndentation")
    private fun fetchUserSearchPosts(query: String) {
        val db = FirebaseFirestore.getInstance()

        searcharrayList.clear()

        db.collection("Users")
            .whereEqualTo("UnifiedId", "s")
            .get()
            .addOnSuccessListener { userQuerySnapshot ->
                for (userDocument in userQuerySnapshot) {
                    val userId = userDocument.id



                    val userPostsCollection = db.collection("Users/$userId/UserPosts")




                    userPostsCollection.get()
                        .addOnSuccessListener { postQuerySnapshot ->
                            for (postDocument in postQuerySnapshot) {

                                val title = postDocument.getString("title")
                                val location = postDocument.getString("Wilaya")
                                val images = postDocument.get("Images") as ArrayList<String>
                                val city = postDocument.getString("city")
                                val des = postDocument.getString("desc")
                                val phoneinput = postDocument.getString("phoneinput")
                                val username = postDocument.getString("username")

                                val userpfp = postDocument.getString("userpfp")


                                if (title!!.contains(query) && location != null && images.isNotEmpty()) {
                                    val searchimageUrl = images[0]
                                    val image0 = images[0]
                                    val image1 = images[1]
                                    val image2 = images[2]


                                    val searchpost = searchpost(
                                        title,
                                        location,
                                        searchimageUrl,
                                        city,
                                        des,
                                        phoneinput,
                                        username,
                                        userpfp

                                    )

                                    val searchimageposting = imageslist(
                                        image0,
                                        image1,
                                        image2


                                    )


                                        searcharrayList.add(searchpost)
                                        ImagesList.add(searchimageposting)

                                }

                            }


                               resultsearchrv.adapter = AdapterSearchResults(searcharrayList, this)


                        }

                }
            }

    }






}

