 package Adapter

import Fragment.AdsFragment
import Fragment.Detail_Fragment
import Models.Ads
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.mydivarkotlin.R
import com.squareup.picasso.Picasso

class AdsAdapter(var context: Context, var adsList: List<Ads>) :
    RecyclerView.Adapter<AdsAdapter.AdsViewHolder>() {

    inner class AdsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtTitle = itemView.findViewById<TextView>(R.id.txt_asdRow_title)
        var txtCity = itemView.findViewById<TextView>(R.id.txt_adsRow_city)
        var txtPrice = itemView.findViewById<TextView>(R.id.txt_adsRow_price)
        var txtDate = itemView.findViewById<TextView>(R.id.txt_adsRow_date)
        var imgIcon = itemView.findViewById<ImageView>(R.id.img_adsRow_icon)
        var parent = itemView.findViewById<CardView>(R.id.card_adsRow_parent)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsViewHolder {

        var view = LayoutInflater.from(context).inflate(R.layout.ads_row, parent, false)

        return AdsViewHolder(view)

    }

    override fun onBindViewHolder(holder: AdsViewHolder, position: Int) {

        var ads = adsList.get(position)
        Picasso.get().load(ads.url).into(holder.imgIcon)
        holder.txtTitle.setText(ads.title)
        holder.txtPrice.setText(ads.price.toString())
        holder.txtDate.setText(ads.date)
        holder.txtCity.setText(ads.city)
        holder.parent.setOnClickListener {

            var bundle = Bundle()
            bundle.putString("id", ads.id.toString())
            bundle.putString("url", ads.url)
            bundle.putString("phone", ads.phone)


            var newContext = context as AppCompatActivity
            var manager = newContext.supportFragmentManager
            var transaction = manager.beginTransaction()
            var newAdsFragment = Detail_Fragment()
            newAdsFragment.arguments = bundle


            transaction.replace(R.id.rel_fragmentHome_parent, newAdsFragment)
            transaction.addToBackStack(null)
            transaction.commit()


        }


    }

    override fun getItemCount(): Int {

        return adsList.size
    }

}