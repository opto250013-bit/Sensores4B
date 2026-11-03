package com.example.sensores4b

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var tvValores : TextView
    private lateinit var tvEstado : TextView
    private lateinit var sensorManager: SensorManager

    // Crear objeto para ocupar el sensor de luz
    private var sensorLuz : Sensor? =
        null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        tvEstado = findViewById(R.id.tvEstado)
        tvValores = findViewById(R.id.tvValores)

        // Permitir el uso de los servicios de Hardware del dispositivo
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        // Seleccionar el sensor de luz
        sensorLuz = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        // Validar si el sensor se encuentra en el dispositivo
        if(sensorLuz == null){
            tvEstado.text =
                "No hay sensor de luz :("
        }
    }

    // MÉTODOS OBLIGATORIOS PARA EL USO DE SENSORES
    //MÉTODO PARA MEDIOR LOS CAMBIOS EN LOS VALORES DEL SENSOR
    override fun onSensorChanged(event: SensorEvent?) {

        // Verificar el uso correcto del sensor
        if(event?.sensor?.type == Sensor.TYPE_LIGHT){


            // Variable que guarde la información del sensor
            val luz = event.values[0]
            tvValores.text = "Luz: $luz lx"

            if(luz < 20){
                tvEstado.text =
                    "Ambiente oscuro :o"
            }else if(luz < 1000){
                tvEstado.text =
                    "Ambiente iluminado :)"
            }else{
                tvEstado.text =
                    "Demasiado luz :("
            }
        }
    }


    //MÉTODO PARA MEDIR LOS CAMBIOS EN LOS VALORES DEL SENSOR
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {


    }

    //METODO PARA DETENER EL USO DEL SENSOR
    override fun onResume() {
        super.onResume()

        sensorLuz?.let {

            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

}