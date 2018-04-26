package com.training.training

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://dl-auth-api-dev.mybluemix.net/v1/")
                .addConverterFactory(GsonConverterFactory.create()).build()
        val authenticationService: AuthenticationService = retrofit.create(AuthenticationService::class.java)

        btn_login.setOnClickListener { _ ->
            val user: User = User(et_username.text.toString(), et_password.text.toString())
            val call: Call<Token> =  authenticationService.login(user)
            pb.visibility = View.VISIBLE
            btn_login.visibility = View.GONE

            call.enqueue(object : Callback<Token> {
                override fun onFailure(call: Call<Token>?, t: Throwable?) {
                    Toast.makeText(this@MainActivity, t.toString(), Toast.LENGTH_SHORT).show()
                    pb.visibility = View.GONE
                    btn_login.visibility = View.VISIBLE
                }

                override fun onResponse(call: Call<Token>?, response: Response<Token>?) {
                    if(response?.body()?.statusCode.equals("200")) {
                        Log.d("Token", response?.body()?.data)
                        Toast.makeText(this@MainActivity, "Login Sukses", Toast.LENGTH_SHORT).show()
                        val i:Intent = Intent(this@MainActivity, HomeActivity::class.java)
                        i.putExtra(TOKEN_EXTRA, response?.body()?.data)
                        startActivity(i)
                    }
                    else {
                        Toast.makeText(this@MainActivity, "Invalid username or password", Toast.LENGTH_SHORT).show()
                    }
                    pb.visibility = View.GONE
                    btn_login.visibility = View.VISIBLE
                }
            })
        }
    }

    companion object {
        val TOKEN_EXTRA = "TOKEN"
    }
}
