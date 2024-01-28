package com.example.akrili.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import android.widget.ToggleButton


import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.akrili.R





interface CityClickListener {
    fun onCityClick(cityName: String)
    fun onCityunclik(cityName: String)
}

class Horizontal_RecyclerView_Adapter(private val context: Context, private val cityClickListener: CityClickListener) : RecyclerView.Adapter<Horizontal_RecyclerView_Adapter.MyViewHolder>() {




    private val selectedCities = mutableListOf<String>()
    private var checkedPosition = RecyclerView.NO_POSITION
    private val cityList = listOf(
        "Adrar", "Chlef", "Laghouat", "OumElBouaghi", "Batna", "Béjaïa", "Biskra", "Béchar",
        "Blida", "Bouira", "Tamanrasset", "Tébessa", "Tlemcen", "Tiaret", "Tizi Ouzou",
        "Alger", "Djelfa", "Jijel", "Sétif", "Saïda", "Skikda", "SidiBelAbbès",
        "Annaba", "Guelma", "Constantine", "Médéa", "Mostaganem", "M'Sila", "Mascara", "Ouargla",
        "Oran", "El Bayadh", "Illizi", "Bordj Bou Arréridj", "Boumerdès", "Taref", "Tindouf",
        "Tissemsilt", "ElOued", "Khenchela", "SoukAhras", "Tipaza", "Mila", "AïnDefla", "Naâma",
        "AïnTémouchent", "Ghardaïa", "Relizane"
    )


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return MyViewHolder(view)

    }

    @SuppressLint("ResourceAsColor")

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val  materialButton : ToggleButton = holder.itemView.findViewById(R.id.cityNameTextView)
        val cityName = cityList[position]
        val clicked = ContextCompat.getColor(context, R.color.AFTERbtngrey)
        val notclicked = ContextCompat.getColor(context, R.color.secondary)
        materialButton.text = cityName
        materialButton.textOn = cityName
        materialButton.textOff = cityName


        val colorStateList = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(-android.R.attr.state_checked)
            ),
            intArrayOf(
                clicked,
                notclicked
            )
        )




        materialButton.backgroundTintList = colorStateList

        materialButton.isChecked = selectedCities.contains(cityName)
        materialButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedCities.add(cityName)

                cityClickListener.onCityClick(cityName)

            }  else {
                cityClickListener.onCityunclik(cityName)
                selectedCities.remove(cityName)}



        }









    }
    override fun getItemCount(): Int {


        return 48
    }

  fun  alreadychecked(btn:ToggleButton) : Boolean {

      var ischecked = false
  if(btn.isChecked) {ischecked = true} else {ischecked = false}



      return  ischecked
  }

}





