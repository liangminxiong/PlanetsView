package com.lmx.costumview_java

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lmx.costumview_java.adapter.TestAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        soulPlanetView.setAdapter(TestAdapter())

        initGridView()

    }

    private fun initGridView(){
        var isAdd = false
        hearView.setOnClickListener {
            var mVGridCount = gridView.mVGridCount
            if (isAdd) {
                mVGridCount += 1
            } else {
                mVGridCount -= 1
            }
            if (mVGridCount >= 10) {
                isAdd = false
            } else {
                if (mVGridCount == 1) {
                    isAdd = true
                }
            }

            gridView.vGridCount = mVGridCount
            gridView.setData()
            val viewHeight = gridView.viewHeight
            initGridHeight(viewHeight)
        }
    }

    private fun initGridHeight(height: Int) {
        val layoutParams = gridView.layoutParams
        layoutParams.height = height
        gridView.layoutParams = layoutParams
    }
}