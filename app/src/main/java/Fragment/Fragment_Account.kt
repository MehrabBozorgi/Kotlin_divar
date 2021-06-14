package Fragment

import Models.myResult
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.mydivarkotlin.R
import retrofit.ApiClient
import retrofit.ApiService
import retrofit2.Response

class Fragment_Account : Fragment() {

    lateinit var edtUserName: EditText
    lateinit var edtPass: EditText
    lateinit var txtExit: TextView
    lateinit var btnEnter: Button
    lateinit var preference: SharedPreferences
    lateinit var txtFav: TextView
    lateinit var manager:FragmentManager
    lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = LayoutInflater.from(context).inflate(R.layout.fragment_account, container, false)


        //دکمه ای روش کلیک می کنیم و وارده حی=ساب کاربری میشیم
        btnEnter = view.findViewById(R.id.btn_fragmentAccount_enter)
        btnEnter.visibility=View.VISIBLE
        btnEnter.setOnClickListener {

            signUp()
        }

        txtExit = view.findViewById(R.id.txt_fragmentAccount_exit)
        edtUserName = view.findViewById(R.id.edt_fragmentAccount_userName)
        edtPass = view.findViewById(R.id.edt_fragmentAccount_pass)


        //اون تکس ویوهه که روش بزنیم میریم به اگهی هایی که نشان کردیم
        txtFav = view.findViewById(R.id.txt_fragmentAccount_favorite)
        txtFav.setOnClickListener {

            var newContext = context as AppCompatActivity
            manager = newContext.supportFragmentManager
            var transAction = manager.beginTransaction()

            var fragmentFav = FavFragment()
            btnEnter.visibility=View.GONE
            transAction.replace(R.id.rel_fragmentAccount_parent, fragmentFav)
//            transAction.addToBackStack(null)
            transAction.commit()

        }


        //تکس ویویی که روش کلیک می کنیم و از حساب کاربری خارج میشیم
        txtExit.setOnClickListener {


            //قسمت وارد کردن نام کاربری و پسورد معلوم باشه
            edtUserName.visibility = View.VISIBLE
            edtPass.visibility = View.VISIBLE


            //دکمه نوشتش نوشته باشه ورود
            btnEnter.setText("ورود")


            //ادیت تکس خارح شدن از اکانت مخفی بشه
            txtExit.visibility = View.GONE


            //متد شیرپریفرنس رو باهاش میخواهیم کار کنیم
            var editor = preference.edit()
            editor.putString("username", "")
            editor.apply()
        }


        preference = requireContext().getSharedPreferences("account", Context.MODE_PRIVATE)
        checkSignIn()

        return view
    }

    fun signUp() {


        //اطلاعات ورود به خساب کاربری رو میفرستیم سمت سرور که چک کنه
        var apiClient = ApiClient()
        var call: retrofit2.Call<myResult> = apiClient.getClient().create(ApiService::class.java)
            .checkSignup(edtUserName.text.toString(), edtPass.text.toString())
        call.enqueue(object : retrofit2.Callback<myResult> {
            override fun onResponse(
                call: retrofit2.Call<myResult>?,
                response: Response<myResult>?
            ) {

                var myResponse = response!!.body()


                //چک می کنه که اگه اشتباه این پیام رو بده اگه نبود اون پیغام رو بده
                if (myResponse!!.result.equals("not exist")) {

                    Toast.makeText(
                        context,
                        "نام کاربری یا کلمه ی عبور اشتباه وارد شده",
                        Toast.LENGTH_LONG
                    ).show()

                } else {
                    btnEnter.setText("با موفقیت وارد شدید")


                    //وقتی که وارد شد با موفقیت این حرکتها انجام بشه
                    txtExit.visibility = View.VISIBLE
                    edtUserName.visibility = View.GONE
                    edtPass.visibility = View.GONE


                    //دوباره از شزیت پریفرنس استفاده می کنیم
                    var editor = preference.edit()
                    editor.putString("username", edtUserName.text.toString())
                    editor.apply()
                }
            }

            override fun onFailure(call: retrofit2.Call<myResult>?, t: Throwable?) {

                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show()

            }
        })
    }

    fun checkSignIn() {


        //متدی برای ثبت نام اولیه در جساب کاربری
        var username = preference.getString("username", "")

        if (!username.equals("")) {
            btnEnter.setText("با موفقیت وارد شدید")

            txtExit.visibility = View.VISIBLE
            edtUserName.visibility = View.GONE
            edtPass.visibility = View.GONE

        }

    }


}