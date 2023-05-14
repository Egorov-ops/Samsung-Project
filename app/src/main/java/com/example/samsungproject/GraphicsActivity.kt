package com.example.samsungproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.samsungproject.databinding.ActivityGraphicsBinding
import com.example.samsungproject.databinding.ActivityMainBinding.inflate
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry


class GraphicsActivity : AppCompatActivity() {

    public lateinit var binding: ActivityGraphicsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataSet = BarDataSet(getFatPercentage(), "Fat percentage")
        val arrayListOfLabels = arrayListOf<String>("IMT", "NAVY", "YMCA")
        val chart = BarChart(this)
        val data = BarData(dataSet)
        chart.data = data
        setContentView(chart)

    }


    private fun getFatPercentage(): ArrayList<BarEntry> {

        var entries: ArrayList<BarEntry> = ArrayList()

        val imt = intent.getFloatExtra("imt", 0.0F)
        val ymca = intent.getFloatExtra("ymca", 0.0F)
        val navy = intent.getFloatExtra("navy", 0F)

        entries.add(BarEntry(2f, imt))
        entries.add(BarEntry(4f, ymca))
        entries.add(BarEntry(6f, navy))
        return entries
    }
}
