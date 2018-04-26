package com.training.training

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_buyer_detail.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_buyer_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BuyerDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_buyer_detail)

        val token = intent.getStringExtra(HomeActivity.TOKEN_EXTRA);
        val id = intent.getStringExtra(HomeActivity.ID_EXTRA);
        val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://dl-core-api-dev.mybluemix.net/v1/")
                .addConverterFactory(GsonConverterFactory.create()).build()

        val buyerService: BuyerService= retrofit.create(BuyerService::class.java)

        val call: Call<BuyerDetailResponse> =  buyerService.getById(id, "Bearer $token")
        call.enqueue(object : Callback<BuyerDetailResponse> {
            override fun onFailure(call: Call<BuyerDetailResponse>?, t: Throwable?) {
                Toast.makeText(this@BuyerDetailActivity, t.toString(), Toast.LENGTH_LONG).show()
//                pbBuyer.visibility = View.GONE
            }

            override fun onResponse(call: Call<BuyerDetailResponse>?, response: Response<BuyerDetailResponse>?) {
                if(response?.body()?.statusCode.equals("200")) {
//                    Log.d("Token", response?.body()?.data)
                    Toast.makeText(this@BuyerDetailActivity, "Buyer Sukses", Toast.LENGTH_SHORT).show()
                    val b:Buyer? = response?.body()?.data
                    tv_name.text = b?.name
                    tv_address.text = b?.address
                    tv_city.text = b?.city
                    tv_contact.text = b?.contact
                    tv_code.text = b?.code
                }
                else {
                    Toast.makeText(this@BuyerDetailActivity, "Failed get data from server", Toast.LENGTH_SHORT).show()
                }
//                pbBuyer.visibility = View.GONE
//                lv_buyers.visibility = View.VISIBLE
            }
        })
    }

}
