package com.sungkyul.synergy.edu_space.delivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.SearchView
import com.sungkyul.synergy.R
import com.sungkyul.synergy.edu_space.delivery.shopActivity

class DlvMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dlv_main)


        //검색창에 목적지 입력시 가게목록으로 넘어감(검색어 intent로 전달)
        val searchView: SearchView = findViewById(R.id.searchView2)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    val intent = Intent(this@DlvMainActivity, shopActivity::class.java)
                    intent.putExtra("search_query", query)
                    startActivity(intent)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // 검색어가 변경될 때 실행하고 싶은 동작을 여기에 추가하세요.
                return true
            }
        })

    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.taxi_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        return super.onOptionsItemSelected(item)
    }
}