package Adapter

import Fragment.AdsFragment
import Models.Cat
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
import java.util.zip.Inflater

class CatsAdapter(var context: Context, var catList: List<Cat>) :
    RecyclerView.Adapter<CatsAdapter.CatsViewHolder>() {

    inner class CatsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtCatTitle = itemView.findViewById<TextView>(R.id.txt_catRow_title)

        //        var ImgArrow = itemView.findViewById<ImageView>(R.id.img_catRow_arrow)
        var relParent = itemView.findViewById<CardView>(R.id.rel_catRow_parent)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatsViewHolder {

        var view = LayoutInflater.from(context).inflate(R.layout.cat_row, parent, false)

        return CatsViewHolder(view)

    }

    override fun onBindViewHolder(holder: CatsViewHolder, position: Int) {

        var cat = catList.get(position)

        holder.txtCatTitle.setText(cat.cattitle)

        holder.relParent.setOnClickListener {

            //با ااستفاده از متد باندل میتونیم اطلاعاتی رو که میهواهیم رو به یک فرگمنت دیگه بفرستیم
            var bundle = Bundle()
            bundle.putString("id", cat.id)


            var newContext = context as AppCompatActivity
            var manager = newContext.supportFragmentManager
            var transaction = manager.beginTransaction()
            var newAdsFragment = AdsFragment()
            newAdsFragment.arguments = bundle


            transaction.replace(R.id.rel_catFragment_parent, newAdsFragment)
            transaction.addToBackStack(null)
            transaction.commit()

        }
    }

    override fun getItemCount(): Int {

        return catList.size

    }

}