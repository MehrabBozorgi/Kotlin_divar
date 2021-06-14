package Fragment

import Models.Cat
import Models.myResult
import android.accounts.AccountManager.get
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.mydivarkotlin.R
import retrofit.ApiClient
import retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Fragment_adv : Fragment() {

    //اسپینر رو براش متغیر تعریف می کنیم و یه لیستی از رشته ها براش تعریف می کنیم
    lateinit var spinner: Spinner
    lateinit var list: ArrayList<String>
    lateinit var selectedCat: String
    lateinit var buttonSave: Button

    lateinit var edtTitle: EditText
    lateinit var edtPhone: EditText
    lateinit var edtPrice: EditText
    lateinit var edtDesc: EditText
    lateinit var edtCity: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_adv, container, false)

        getCats()


        buttonSave = view.findViewById(R.id.btn_fragemtAdv_save)
        buttonSave.setOnClickListener {

            //شرط میذاریم اگه هر فلیدی خالی بودش پیغام چاپ کنه
            if (edtTitle.text.equals("") || edtTitle.text.isEmpty() &&
                edtPrice.text.equals("") || edtPrice.text.isEmpty() &&
                edtPhone.text.equals("") || edtPhone.text.isEmpty() &&
                edtDesc.text.equals("") || edtDesc.text.isEmpty()
            )
                Toast.makeText(context, "همه ی فیلد را مشخص کنید", Toast.LENGTH_SHORT).show()

            addRecord()

        }
        edtTitle = view.findViewById(R.id.edt_fragmentAdv_title)
        edtPhone = view.findViewById(R.id.edt_fragmentAdv_phone)
        edtPrice = view.findViewById(R.id.edt_fragmentAdv_price)
        edtDesc = view.findViewById(R.id.edt_fragmentAdv_desc)
        edtCity = view.findViewById(R.id.edt_fragmentAdv_city)


        //لیستمون رو دوباره مقدار دهی می کنیم
        list = ArrayList()

        spinner = view!!.findViewById(R.id.sp_fragmentAdv_cat)

        //یه متدی هست در برای اسپینرها که بتونیم روش کلیک کردیم لیستمون رو نشون بده
        //وقتی که بعد از ابجکت رو نوشتیم خطارو برطرف می کنیم و دوتا متد بهش اضافه می کنیم
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {


                var item = list.get(position)
                selectedCat = item
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        return view
    }
//برای فرستادن طبقه بندی ها به اسپینر از این متد استفاده می کنیم
    fun getCats() {

        //وصل می شیم به سرور

        var apiClient = ApiClient()
        var call: Call<List<Cat>> = apiClient.getClient().create(ApiService::class.java).getAllCat()
        call.enqueue(object : Callback<List<Cat>> {
            override fun onResponse(call: Call<List<Cat>>?, response: Response<List<Cat>>?) {

                var responseList = response!!.body()

                //شرط برای اسپینر تعریف می کنیم

                for (item in responseList!!) {
                    list.add(item.cattitle)
                }
                //برای اسپینر اداپتر تعریف می کنیم
                spinner.adapter =
                    ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1, list)
            }

            override fun onFailure(call: Call<List<Cat>>?, t: Throwable?) {

                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show()

            }

        })

    }


    //برای فرستادن اطلاعات که در ادیت تکس پر کردیم از این متد استفاده میکنیم
    fun addRecord() {

        var apiClient = ApiClient()
        var call: Call<myResult> = apiClient.getClient().create(ApiService::class.java).addRecord(
            edtTitle.text.toString(),
            edtCity.text.toString(),
            edtPrice.text.toString(),
            edtDesc.text.toString(),
            edtPhone.text.toString(),
            selectedCat
        )
        call.enqueue(object : Callback<myResult> {
            override fun onResponse(call: Call<myResult>?, response: Response<myResult>?) {

                var res = response!!.body()
                //شرط میذاریم براش

                if (res!!.result.equals("ok")) {
                    Toast.makeText(context, "اگهی با موفقیت ثبت شد", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "مشکلی در ثبت اگهی", Toast.LENGTH_LONG).show()
                }


            }

            override fun onFailure(call: Call<myResult>?, t: Throwable?) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show()

            }


        })

    }



}