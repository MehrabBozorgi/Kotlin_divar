package Fragment

import Adapter.AdsAdapter
import Models.Ads
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mydivarkotlin.R
import kotlinx.android.synthetic.main.fragment_adv.*
import kotlinx.android.synthetic.main.fragment_home.*

import retrofit.ApiClient
import retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class Fragment_Home : Fragment() {

    lateinit var ads: List<Ads>
    lateinit var edtSearch: EditText
    lateinit var searchRecycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_home, container, false)

        getAllAds()
        setupViews(view)
        return view

    }

    fun getAllAds() {

        var apiClient = ApiClient()
        var call: Call<List<Ads>> = apiClient.getClient().create(ApiService::class.java).getAllAds()
        call.enqueue(object : retrofit2.Callback<List<Ads>> {


            override fun onFailure(call: Call<List<Ads>>?, t: Throwable?) {

                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show()

            }

            override fun onResponse(call: Call<List<Ads>>?, response: Response<List<Ads>>?) {

                ads = ArrayList<Ads>()
                ads = response!!.body()!!
                setDataOnRecycler()

            }
        })
    }

    private fun setDataOnRecycler() {

        var recyclerAds = rv_fragmentHome_recyclerAds
        recyclerAds.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerAds.adapter = AdsAdapter(requireContext(), ads)
    }

    //متدی که درونش خاصیت سرچ رو ایجاد کردیم
    fun setupViews(view: View) {

        searchRecycler = view.findViewById(R.id.rv_fragmentHome_recyclerAds)
        searchRecycler.layoutManager = LinearLayoutManager(context)

        edtSearch = view.findViewById(R.id.edt_fragmentHome_search)

        //خاصیت سرچ پذیری
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                search(s.toString())

            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

    }

    //متدی برای سرچ به سمت سرور درخواست میزنیم
    fun search(search: String) {

        var apiClient = ApiClient()
        var call: Call<List<Ads>> =
            apiClient.getClient().create(ApiService::class.java).getSearch(search)
        call.enqueue(object : Callback<List<Ads>> {
            override fun onResponse(call: Call<List<Ads>>?, response: Response<List<Ads>>?) {
                var list = response!!.body()
                //شرط میذاریم که چه وقت پیام موردی نیست پاپ بشه
                if (!list!!.isEmpty()) {
                    txt_fragmentHome_warning.visibility = View.GONE
                    searchRecycler.visibility = View.VISIBLE
                    //از ادپتر نمونه  سازی می کنیم
                    var adsAdapter = AdsAdapter(context!!, list)
                    searchRecycler.adapter = adsAdapter
                    adsAdapter.notifyDataSetChanged()
                } else {
                    txt_fragmentHome_warning.visibility = View.VISIBLE
                    searchRecycler.visibility = View.GONE
                }

            }
            override fun onFailure(call: Call<List<Ads>>?, t: Throwable?) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show()
            }

        })

    }

}



