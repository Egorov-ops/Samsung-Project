package com.example.samsungproject

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.samsungproject.databinding.ActivityMainBinding
import com.example.samsungproject.databinding.ActivityMainBinding.inflate
import kotlin.math.log10


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        binding.GenderMaleButton.setOnClickListener { onClickDeleteView(view) }
        binding.GenderFemaleButton.setOnClickListener { onClickDeleteView(view) }
        binding.ButtonCalculate.setOnClickListener {
            calculateImt()
            if (binding.editTextWeight.text.isNotEmpty() && binding.editTextHeight.text.isNotEmpty()
                && binding.editTextNeckSm.text.isNotEmpty() && binding.editTextWaistSm.text.isNotEmpty()
            ) {
                calculateNavyImt()
            }else if (binding.editTextWeight.text.isNotEmpty()&&binding.editTextWaistSm.text.isNotEmpty()){
                calculateYmcaImt()
            }
            Log.v(TAG, "IMT" + calculateImt())
            Log.v(TAG, "IMTNavy" + calculateNavyImt())
            Log.v(TAG, "IMTYmca" + calculateYmcaImt())


        }

    }

    private fun onClickDeleteView(view: View) {
        val id: Int = binding.GenderRadioGroup.checkedRadioButtonId
        if (id == binding.GenderMaleButton.id) {
            binding.TextViewHipGirth.visibility = View.GONE
            binding.TextViewHipSm.visibility = View.GONE
            binding.editTextHipSm.visibility = View.GONE
            binding.TextViewShinGirth.visibility = View.GONE
            binding.TextViewShinSm.visibility = View.GONE
            binding.editTextShinSm.visibility = View.GONE


        } else if (id == binding.GenderFemaleButton.id) {
            binding.TextViewHipGirth.visibility = View.VISIBLE
            binding.TextViewHipSm.visibility = View.VISIBLE
            binding.editTextHipSm.visibility = View.VISIBLE
            binding.TextViewShinGirth.visibility = View.VISIBLE
            binding.TextViewShinSm.visibility = View.VISIBLE
            binding.editTextShinSm.visibility = View.VISIBLE

        }

    }

    private fun calculateImt(): Double {
        var imt: Double = 0.0
        if (binding.editTextWeight.text.isEmpty() && binding.editTextHeight.text.isEmpty()) {
            Toast.makeText(this, "Введите вес и рост", Toast.LENGTH_LONG).show()
        } else {

            val weight: Double = binding.editTextWeight.text.toString().toDouble()
            val height: Double = binding.editTextHeight.text.toString().toDouble() / 100

            Log.v(TAG, "height $height")
            imt = weight / (height * height)
        }
        return imt


    }

    private fun calculateNavyImt(): Double {
        var imt: Double = 0.0
        val id: Int = binding.GenderRadioGroup.checkedRadioButtonId
        if (id == binding.GenderMaleButton.id) {
            val height: Double = binding.editTextHeight.text.toString().toDouble()
            val neck: Double = binding.editTextNeckSm.text.toString().toDouble()
            val waist: Double = binding.editTextWaistSm.text.toString().toDouble()
            imt = 495.0 / (1.0324 - 0.19077 * log10(waist - neck) + 0.15456 * log10(height)) - 450

        } else if (id == binding.GenderFemaleButton.id && binding.editTextHipSm.text.isNotEmpty()) {
            val height: Double = binding.editTextHeight.text.toString().toDouble()
            val neck: Double = binding.editTextNeckSm.text.toString().toDouble()
            val waist: Double = binding.editTextWaistSm.text.toString().toDouble()
            val hip: Double = binding.editTextHipSm.text.toString().toDouble()
            imt =
                495.0 / (1.29579 - 0.35004 * log10(waist + hip - neck) + 0.22100 * log10(height)) - 450
        }



        return imt
    }

    private fun calculateYmcaImt(): Double {
        var imt: Double = 0.0
        val id: Int = binding.GenderRadioGroup.checkedRadioButtonId
        if (id == binding.GenderMaleButton.id) {
            val weight: Double = binding.editTextWeight.text.toString().toDouble()
            val waist: Double = binding.editTextWaistSm.text.toString().toDouble()
            imt =
                (-98.42 + (4.15 * waist / 2.54) - (0.082 * (weight / 0.454))) / (weight / 0.454) * 100

        } else if (id == binding.GenderFemaleButton.id && binding.editTextHipSm.text.isNotEmpty()) {
            val weight: Double = binding.editTextWeight.text.toString().toDouble()
            val waist: Double = binding.editTextWaistSm.text.toString().toDouble()
            imt =
                (-76.76 + (4.15 * waist / 2.54) - (0.082 * (weight / 0.454))) / (weight / 0.454) * 100

        }


        return imt
    }
}


