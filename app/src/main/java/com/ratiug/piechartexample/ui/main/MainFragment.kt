package com.ratiug.piechartexample.ui.main

import android.content.Context
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.ratiug.piechartexample.R
import com.ratiug.piechartexample.databinding.MainFragmentBinding
import java.text.DecimalFormat
import java.util.ArrayList

class MainFragment : Fragment(R.layout.main_fragment) {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MainFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setupPieChartView()
        setupPieChartData()
        animateChart()
    }

    private fun setupPieChartView() {
        val pieChart = binding.pieChart
        pieChart.setHoleColor(Color.TRANSPARENT)
        pieChart.apply {
            this.description.isEnabled = false
            this.legend.isWordWrapEnabled = true
            this.legend.textSize = 14f
            this.legend.textColor = ContextCompat.getColor(requireContext(), R.color.textColor)
            this.setDrawRoundedSlices(true)
            this.setUsePercentValues(true)
            this.dragDecelerationFrictionCoef = 0.95f
            this.isDragDecelerationEnabled = true
            this.setExtraOffsets(20f, 30f, 20f, 20f)
        }
    }

    private fun setupPieChartData() {
        val pieChart = binding.pieChart

        val pieEntries = ArrayList<PieEntry>()
        val typeAmountMap: MutableMap<String, Int> = initializingData()
        val colors = initializingColorsForEntries()

        //input data and fit data into pie chart entry
        for (type in typeAmountMap.keys) {
            pieEntries.add(PieEntry(typeAmountMap[type]!!.toFloat(), type))
        }

        //collecting the entries with label name
        val pieDataSet = PieDataSet(pieEntries, "")
        setupPieDataSet(pieDataSet, colors)

        val pieData = PieData(pieDataSet)
        pieData.setDrawValues(true)

        pieChart.data = pieData
        pieChart.invalidate()
    }

    private fun initializingData(): MutableMap<String, Int> {
        val typeAmountMap: MutableMap<String, Int> = HashMap()
        typeAmountMap["Kotlin"] = 300
        typeAmountMap["JavaScript"] = 181
        typeAmountMap["Java"] = 140
        typeAmountMap["C#"] = 130
        typeAmountMap["Python"] = 120
        typeAmountMap["PHP"] = 100
        return typeAmountMap
    }

    private fun initializingColorsForEntries(): ArrayList<Int> {
        val colors = ArrayList<Int>()
        colors.add(Color.parseColor("#304567"))
        colors.add(Color.parseColor("#309967"))
        colors.add(Color.parseColor("#476567"))
        colors.add(Color.parseColor("#890567"))
        colors.add(Color.parseColor("#a35567"))
        colors.add(Color.parseColor("#ff5f67"))
        colors.add(Color.parseColor("#3ca567"))
        return colors
    }

    private fun setupPieDataSet(
        pieDataSet: PieDataSet,
        colors: ArrayList<Int>
    ) {
        //setting size of the value
        pieDataSet.valueTextSize = 10f
        pieDataSet.valueLinePart1OffsetPercentage = 90.0f
        pieDataSet.valueLinePart1Length = 1f
        pieDataSet.valueLinePart2Length = .2f

        //setting position of the value
        pieDataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        pieDataSet.setUseValueColorForLine(true)

        //providing color list for coloring different entries
        pieDataSet.colors = colors

        //setting value format and colors
        pieDataSet.valueFormatter = PercentFormatter(DecimalFormat("00.0"))
        pieDataSet.valueTextColor = ContextCompat.getColor(requireContext(), R.color.textColor)
        pieDataSet.valueTextColor = ContextCompat.getColor(requireContext(), R.color.textColor)
    }

    private fun animateChart() {
        binding.pieChart.animateXY(2000, 600)
    }

}