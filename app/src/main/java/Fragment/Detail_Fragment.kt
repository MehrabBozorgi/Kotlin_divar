package Fragment

import Models.Detail
import Sqlite.MyDatabase
import Sqlite.colDesc
import android.app.Dialog
import android.content.ContentValues
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mydivarkotlin.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.ads_row.*
import kotlinx.android.synthetic.main.detail_fragment.*
import retrofit.ApiClient
import retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Detail_Fragment : Fragment() {

    lateinit var imgFav: ImageView
    lateinit var phone: String
    lateinit var btnPhone: Button
    lateinit var url: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.detail_fragment, container, false)

        //باندل رو از اداپتر گرفتم

        var bundle = arguments
        var id = bundle!!.getString("id")
        var url = bundle!!.getString("url")
        var phone = bundle!!.getString("phone")

        if (id != null) {
            getUniqeAds(id)
        }


        imgFav = view.findViewById(R.id.img_detailFragment_fav)
        var database = MyDatabase(requireContext())
        var cursor = database.getUniqeData(id!!.toInt())
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {

                var title = cursor.getString(3)
                if (title != null || !title.equals("")) {

                    imgFav.setImageResource(R.drawable.ic_favred)
                    imgFav.contentDescription = "black"
                    cursor.moveToNext()
                }
            }
        }
        //داریم روی قرمز و بیرنگ شدن قلب کار می کنیم
        imgFav.setOnClickListener {

            if (imgFav.contentDescription.equals("white")) {
                imgFav.setImageResource(R.drawable.ic_favred)
                imgFav.contentDescription = "black"

                //  دستورات مربوط به اینکه وقتی روی قلب کلیک کردیم
                //  اگهی مورد نظر بره توی علافه مندیها و این دستورات از دیتا بیس گرفته میشه

                var values = ContentValues()

                values.put("adsid", id)

                values.put("description", txt_detailFragment_desc.text.toString())
                values.put("image", url)
                values.put("city", "")
                values.put("date", txt_detailFragment_date.text.toString())
                values.put("cat", txt_detailFragment_catTitle.text.toString())
                values.put("phone", phone)
                values.put("price", txt_detailFragment_PriceValue.text.toString())
                values.put("option", txt_detailFragment_option.text.toString())

                var result = database.insertData(values)

                Toast.makeText(context, result.toString(), Toast.LENGTH_LONG).show()
            } else {
                imgFav.setImageResource(R.drawable.ic_favblack)
                imgFav.contentDescription = "white"

                database.delete(id.toInt())
            }

        }
        //روی اون دیالوگی که بعد از کلیک روی دکمه نشون میده کار می کنیم که شماره تلفن رو نشون میده
        btnPhone = view.findViewById<Button>(R.id.btn_detailFragment_showPhone)
        btnPhone.setOnClickListener {

            var dialog = context?.let { Dialog(it) }
            dialog!!.setContentView(R.layout.dialog)

            var txtDialog = dialog.findViewById<TextView>(R.id.txt_dialog_myNumber)

            txtDialog.setText(phone)

            dialog.show()

        }

        return view
    }

    //فرستادن اطلاعات به لیست CAt

    fun getUniqeAds(id: String) {

        var apiClient = ApiClient()
        var call: Call<Detail> =
            apiClient.getClient().create(ApiService::class.java).getUniqeAds(id)
        call.enqueue(object : Callback<Detail> {
            override fun onResponse(call: Call<Detail>?, response: Response<Detail>?) {

                var detail = response!!.body()
                Picasso.get().load(detail!!.url.get(0)).into(img_detailFragment_slide)
                txt_detailFragment_title.setText(detail.title)
                txt_detailFragment_PriceValue.setText(detail.price)
                txt_detailFragment_date.setText(detail.date)
                txt_detailFragment_desc.setText(detail.description)

                if (detail.option == null) {

                    txt_detailFragment_option.visibility = View.GONE

                } else {
                    txt_detailFragment_option.setText(detail.option)
                }

                phone = detail.phone

            }

            override fun onFailure(call: Call<Detail>?, t: Throwable?) {

                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show()

            }


        })

    }


}
