package com.example.akrili

import android.content.Intent
import android.os.Bundle
import android.view.View

import android.widget.Button
import com.facebook.shimmer.ShimmerFrameLayout

import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.akrili.Adapter.CityClickListener
import com.example.akrili.Adapter.Horizontal_RecyclerView_Adapter
import com.example.akrili.Adapter.MyAdapter

import com.example.akrili.Data.Posts
import com.example.akrili.Data.imageslist



import com.example.akrili.databinding.HomePageBinding
import com.example.akrili.fragments.AddingListing
import com.example.akrili.fragments.postsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.firestore.FirebaseFirestore


class homepage : AppCompatActivity(), MyAdapter.OnItemClickListener , CityClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var PostRecylerView: RecyclerView

    private lateinit var newArrayList: ArrayList<Posts>
    private lateinit var ImagesList: ArrayList<imageslist>
    private lateinit var auth : FirebaseAuth
    private lateinit var Adapter: Horizontal_RecyclerView_Adapter

    private lateinit var binding: HomePageBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var shimmerViewContainer: ShimmerFrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)
        binding = HomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
         auth = FirebaseAuth.getInstance()
         val currentUser = auth.currentUser
         if (!currentUser!!.isEmailVerified) {
                auth.signOut()

                startActivity(Intent(this, loginpage::class.java))
                finish()
                return
            }




        bottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.profile -> {
                    val intent = Intent(this, ProfilePage::class.java)
                    startActivity(intent)
                    true


                }

                R.id.searchBar-> {

                    val intent = Intent(this , Searchsection::class.java)
                    startActivity(intent)
                    true


                }
                R.id.home->{
                    val intent = Intent(this , homepage::class.java)
                    startActivity(intent)
                    true


                }


                else -> false
            }
        }

        PostRecylerView = findViewById(R.id.ShowingListingRV)
        PostRecylerView.layoutManager = LinearLayoutManager(this)
        PostRecylerView.setHasFixedSize(true)

        newArrayList = arrayListOf<Posts>()
        ImagesList = arrayListOf<imageslist>()



        /* */
        //horizantal
        recyclerView = findViewById(R.id.recyclerView)
        Adapter = Horizontal_RecyclerView_Adapter(this,this)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = Adapter

    //    val shimmerViewContainer: ShimmerFrameLayout = findViewById(R.id.shimmer_view_container)





        fetchUserPosts()


        shimmerViewContainer.stopShimmer()



        val add_listingbtn = findViewById<Button>(R.id.AddListingsbtn)

        add_listingbtn.setOnClickListener {

            val fragment = AddingListing()

            val fragmentManager = supportFragmentManager
            val transaction = fragmentManager.beginTransaction()


        transaction.add(R.id.addLisngFrame, fragment)

            transaction.addToBackStack(true.toString())


            transaction.commit();







        }







    }


    override fun onCityClick(cityName: String) {
        fetchspeficdata(cityName)
    }


    override fun onCityunclik(cityName: String) {
        fetchUserPosts()
    }

  private fun fetchspeficdata(s : String){

      val db = FirebaseFirestore.getInstance()

      newArrayList.clear()

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


                              if (location!!.contains(s) && location != null && images.isNotEmpty()) {
                                  val imageUrl = images[0]
                                  val image0 = images[0]
                                  val image1 = images[1]
                                  val image2 = images[2]


                                  val post = Posts(
                                      title.toString(),
                                      username,
                                      phoneinput,
                                      location.toString(),
                                      city,
                                      des,
                                      imageUrl,
                                      userpfp
                                  )

                                  val searchimageposting = imageslist(
                                      image0,
                                      image1,
                                      image2


                                  )


                                  newArrayList.add(post)
                                  ImagesList.add(searchimageposting)

                              }

                          }


                          PostRecylerView.adapter = MyAdapter(newArrayList, this)



                      }

              }
          }




  }





    private fun fetchUserPosts() {
        val db = FirebaseFirestore.getInstance()


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




                                    val imageUrl = images.getOrNull(0) ?: ""
                                    val image0 = images.getOrNull(0) ?: ""
                                    val image1 = images.getOrNull(1) ?: ""
                                    val image2 = images.getOrNull(2) ?: ""



                                    val post = Posts(
                                        title.toString(),
                                        username,
                                        phoneinput,
                                        location.toString(),
                                        city,
                                        des,
                                        imageUrl,
                                        userpfp
                                    )

                                    val imageposting = imageslist(
                                        image0,
                                        image1,
                                        image2


                                    )

                                    //
                                    newArrayList.add(post)
                                    ImagesList.add(imageposting)



                            }

                            // Update the RecyclerView after fetching data (outside of the loops)
                            PostRecylerView.adapter = MyAdapter(newArrayList, this)


                        }

                }
            }

    }




    override fun onItemClick(position: Int) {
        val restofdataforPost = newArrayList[position]
        val imagesForSelectedPost = ImagesList[position]





           val fragment = postsFragment(imagesForSelectedPost, restofdataforPost)



            supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.transition,R.anim.transitionback)
            //.replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()



    }






//    override fun onBackPressed() {
//        // Handle the back button press in the hosting activity
//        val intent = Intent(this, homepage::class.java)
//        startActivity(intent)
//        val slideDown = AnimationUtils.loadAnimation(this, R.anim.transitionback)
//
//        // Apply the animation to the activity's view
//        findViewById<View>(android.R.id.content).startAnimation(slideDown)
//    }





    fun onItemClickfilter(cityname : String){

        fetchspeficdata(cityname)


    }
    override fun onDestroy() {
     //   val shimmerViewContainer: ShimmerFrameLayout = findViewById(R.id.shimmer_view_container)
        shimmerViewContainer.stopShimmer()
        super.onDestroy()
    }




}





















