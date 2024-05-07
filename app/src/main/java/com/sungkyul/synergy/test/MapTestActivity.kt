package com.sungkyul.synergy.test

import android.graphics.BitmapFactory
import android.graphics.Rect
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sungkyul.synergy.R
import com.sungkyul.synergy.databinding.ActivityMapTestBinding
import com.sungkyul.synergy.utils.MapPiece

class MapTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataSet = ArrayList<MapPiece>()
        for(y in 0..<10) {
            for(x in 0..<10) {
                dataSet.add(
                    MapPiece(
                        if((x+y)%2 == 0) BitmapFactory.decodeResource(resources, R.drawable.card) else BitmapFactory.decodeResource(resources, R.drawable.charge),
                        Rect(x*400, y*400, (x+1)*400, (y+1)*400)
                    )
                )
            }
        }

        binding.mapView.mapPieces = dataSet
    }
}
