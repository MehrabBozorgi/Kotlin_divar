package Fragment

import Adapter.CatsAdapter
import Models.Ads
import Models.Cat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydivarkotlin.R
import kotlinx.android.synthetic.main.fragment_cat.*
import retrofit.ApiClient
import retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class Fragment_Cat : Fragment() {


    lateinit var cat: List<Cat>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_cat, container, false)

        getCatList()

        return view
    }


    fun getCatList() {

        var apiClient = ApiClient()

        var call: Call<List<Cat>> = apiClient.getClient().create(ApiService::class.java).getCats()

        call.enqueue(object : Callback<List<Cat>> {
            override fun onResponse(call: Call<List<Cat>>?, response: Response<List<Cat>>?) {

                cat = ArrayList<Cat>()
                cat = response!!.body()!!
                dataonRecycler()

            }

            override fun onFailure(call: Call<List<Cat>>?, t: Throwable?) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show()
            }

        })

    }

    fun dataonRecycler() {
        rv_fragmentCat_recycler.layoutManager = LinearLayoutManager(context)
        rv_fragmentCat_recycler.adapter = CatsAdapter(requireContext(), cat)


    }

}