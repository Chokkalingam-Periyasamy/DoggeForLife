package com.example.doggeforlife

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class AdapterClass(var songs:MutableList<PetsData>): RecyclerView.Adapter<AdapterClass.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater:LayoutInflater= LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.petlist,parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dogName.text=songs[position].name
//        holder.dishType.text=songs[position].type
//        holder.dishPrice.text="${songs[position].price}$ per stock"
        Picasso.get().load(songs[position].url).into(holder.dogImage)
    }

    override fun getItemCount(): Int {
        return songs.size
    }
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val dogImage=itemView.findViewById<ImageView>(R.id.dogimage)
        val dogName=itemView.findViewById<TextView>(R.id.dogname)
//        val dishType=itemView.findViewById<TextView>(R.id.dishtype)
//        val dishPrice=itemView.findViewById<TextView>(R.id.dishprice)
    }

}