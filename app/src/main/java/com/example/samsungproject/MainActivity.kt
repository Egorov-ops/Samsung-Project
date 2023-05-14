package com.example.samsungproject

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.samsungproject.databinding.ActivityMainBinding
import com.example.samsungproject.databinding.ActivityMainBinding.inflate
import com.google.android.material.snackbar.Snackbar
import kotlin.math.log10


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        binding.GenderMaleButton.setOnClickListener { onClickDeleteView(view) }
        binding.GenderFemaleButton.setOnClickListener { onClickDeleteView(view) }
        var allField = false
        binding.apply {
            allField = areFields(
                editTextWeight,
                editTextHeight,
                editTextAge,
                editTextNeckSm,
                editTextWaistSm,
                editTextWristSm,
                editTextHipsSm,
                editTextHipSm,
                editTextForearmSm,
                editTextShinSm
            )

        }
        binding.ButtonCalculate.setOnClickListener {

            if (!allField) {
                val imt = calculateImt()
                val navy = calculateNavyImt()
                val ymca =  calculateYmcaImt()
                val intent = Intent(this,GraphicsActivity::class.java)
                intent.putExtra("imt",imt)
                intent.putExtra("navy",navy)
                intent.putExtra("ymca",ymca)
                startActivity(intent)
            } else  {
                binding.apply {
                setErrors(editTextWeight,
                    editTextHeight,
                    editTextAge,
                    editTextNeckSm,
                    editTextHipSm,
                    editTextWaistSm,
                    editTextWristSm,
                    editTextHipsSm,
                    editTextForearmSm,
                    editTextShinSm)}
                Snackbar.make(binding.Her,"Поля не заполненны",Snackbar.LENGTH_LONG).show()
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

    private fun calculateImt(): Float {
        var imt: Float = 0.0F
        if (binding.editTextWeight.text.isEmpty() && binding.editTextHeight.text.isEmpty()) {
            Toast.makeText(this, "Введите вес и рост", Toast.LENGTH_LONG).show()
        } else {

            val weight: Float= binding.editTextWeight.text.toString().toFloat()
            val height: Float = binding.editTextHeight.text.toString().toFloat()/ 100.0F

            Log.v(TAG, "height $height")
            imt = weight / (height * height)
        }
        return imt


    }

    private fun calculateNavyImt(): Float {
        var imt: Float = 0.0F
        val id: Int = binding.GenderRadioGroup.checkedRadioButtonId
        if (id == binding.GenderMaleButton.id) {
            val height: Float = binding.editTextHeight.text.toString().toFloat()
            val neck: Float = binding.editTextNeckSm.text.toString().toFloat()
            val waist: Float = binding.editTextWaistSm.text.toString().toFloat()
            imt = 495.0F / (1.0324F - 0.19077F * log10(waist - neck) + 0.15456F * log10(
                height ?: 0.0F
            )) - 450F

        } else if (id == binding.GenderFemaleButton.id && binding.editTextHipSm.text.isNotEmpty()) {
            val height: Float = binding.editTextHeight.text.toString().toFloat()
            val neck: Float = binding.editTextNeckSm.text.toString().toFloat()
            val waist: Float = binding.editTextWaistSm.text.toString().toFloat()
            val hip: Float = binding.editTextHipSm.text.toString().toFloat()
            imt =
                495.0F / (1.29579F - 0.35004F * log10(waist + hip - neck) + 0.22100F * log10(height)) - 450.0F
        }



        return imt
    }

    private fun calculateYmcaImt(): Float {
        var imt: Float = 0.0F
        val id: Int = binding.GenderRadioGroup.checkedRadioButtonId
        if (id == binding.GenderMaleButton.id) {
            val weight: Float = binding.editTextWeight.text.toString().toFloat()
            val waist: Float = binding.editTextWaistSm.text.toString().toFloat()
            imt =
                (-98.42F + (4.15F * waist / 2.54F) - (0.082F * (weight / 0.454F))) / (weight / 0.454F) * 100.0F

        } else if (id == binding.GenderFemaleButton.id && binding.editTextHipSm.text.isNotEmpty()) {
            val weight: Float = binding.editTextWeight.text.toString().toFloat()
            val waist: Float = binding.editTextWaistSm.text.toString().toFloat()
            imt =
                (-76.76F + (4.15F * waist / 2.54F) - (0.082F * (weight / 0.454F))) / (weight / 0.454F) * 100.0F

        }


        return imt
    }

    private fun areFields(vararg fields: EditText): Boolean =
        fields.none { it.text.isNullOrEmpty() }

    private fun setErrors(vararg fields: EditText) =
        fields.forEach{ it.error= "Поле обязательно" }


}


