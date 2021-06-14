package Fragment

import Adapter.AdsAdapter
import Models.Ads
import Sqlite.MyDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mydivarkotlin.R

class FavFragment : Fragment() {

    //کلاس دیتا بیس رو نمونه سازی می کنیم
    lateinit var database: MyDatabase
    lateinit var recyclerViewFav:RecyclerView

    //لیستی که Ads می گیریم و نمونه سازی می کنیمش
    lateinit var adsList: ArrayList<Ads>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = LayoutInflater.from(context).inflate(R.layout.fav_fragment, container, false)


        //ریسایکلر و دیتابیس رو کست میکنیم
        adsList = ArrayList()
        database = MyDatabase(requireContext())
        recyclerViewFav = view.findViewById(R.id.rv_favFragment_favRecycler)
        recyclerViewFav.layoutManager = LinearLayoutManager(context)


        //اطلاعاتی که از دیتابیس می خواهیم رو اینجا پیداسازی می کنیم
        //این شرط باید حتما باشد همیشه...
        var cursor = database.getAllData()
        if (cursor.moveToFirst()) {

            while (!cursor.isAfterLast) {

                var id = cursor.getString(1)
                var title = cursor.getString(2)
                var image = cursor.getString(3)
                var date = cursor.getString(5)
                var price = cursor.getString(8)

                var ads = Ads(id.toInt(), title, "", "", date, "", "", price.toInt(), "", image)

                adsList.add(ads)
                cursor.moveToNext()
            }
        }

        recyclerViewFav.adapter = context?.let { AdsAdapter(it, adsList) }

        return view

    }

}