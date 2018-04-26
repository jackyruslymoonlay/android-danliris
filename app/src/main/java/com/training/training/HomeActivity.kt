package com.training.training

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

public class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val token = intent.getStringExtra(MainActivity.TOKEN_EXTRA);

        val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://dl-core-api-dev.mybluemix.net/v1/")
                .addConverterFactory(GsonConverterFactory.create()).build()

        val buyerService: BuyerService= retrofit.create(BuyerService::class.java)

        val data : List<Buyer> = arrayListOf<Buyer>()
        val adapter = BuyerAdapter(this@HomeActivity, data)
        lv_buyers.adapter = adapter

        lv_buyers.setOnItemClickListener { parent, view, position, id ->
            val id = data[position]._id
            val intent: Intent = Intent(this@HomeActivity, BuyerDetailActivity::class.java)
            intent.putExtra(TOKEN_EXTRA, token)
            intent.putExtra(ID_EXTRA, id)
            startActivity(intent)
        }

        val call: Call<BuyerResponse> =  buyerService.get("Bearer $token")
        call.enqueue(object : Callback<BuyerResponse> {
            override fun onFailure(call: Call<BuyerResponse>?, t: Throwable?) {
                Toast.makeText(this@HomeActivity, t.toString(), Toast.LENGTH_LONG).show()
                pbBuyer.visibility = View.GONE
            }

            override fun onResponse(call: Call<BuyerResponse>?, response: Response<BuyerResponse>?) {
                if(response?.body()?.statusCode.equals("200")) {
//                    Log.d("Token", response?.body()?.data)
                    Toast.makeText(this@HomeActivity, "Buyer Sukses", Toast.LENGTH_SHORT).show()
                    adapter.addAll(response?.body()?.data)
                    adapter.notifyDataSetChanged()
                }
                else {
                    Toast.makeText(this@HomeActivity, "Failed get data from server", Toast.LENGTH_SHORT).show()
                }
                pbBuyer.visibility = View.GONE
                lv_buyers.visibility = View.VISIBLE
            }
        })
    }

    companion object {
        val TOKEN_EXTRA = "TOKEN"
        val ID_EXTRA = "ID"
    }
}
