package Fragment

import Adapter.AdsAdapter
import Models.Ads
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydivarkotlin.R
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit.ApiClient
import retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        var view = inflater.inflate(R.layout.fragment_home, container, false)

        var bundle = arguments
        var id = bundle!!.getString("id")

        if (id != null) {
            getUniqeCat(id)
        }
        return view
    }


    fun getUniqeCat( id: String) {

        var apiClient = ApiClient()
        var call: Call<List<Ads>> =
            apiClient.getClient().create(ApiService::class.java).getUniqeCat(id)
        call.enqueue(object : Callback<List<Ads>> {
            override fun onResponse(call: Call<List<Ads>>?, response: Response<List<Ads>>?) {

                var adsList = response!!.body()

                rv_fragmentHome_recyclerAds.layoutManager = LinearLayoutManager(context)
                rv_fragmentHome_recyclerAds.adapter = context?.let { AdsAdapter(it, adsList) }

            }

            override fun onFailure(call: Call<List<Ads>>?, t: Throwable?) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG)
            }

        })


    }
}