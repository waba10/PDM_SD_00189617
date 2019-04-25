package com.ayala.almacenamiento_sd

import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import android.Manifest
import android.util.Log

class MainActivity : AppCompatActivity() {
    lateinit var file:File
    val externalFolderPath = Environment.getExternalStorageDirectory().absoluteFile
    val fileName = "sd.txt"

    init {
        file = File(externalFolderPath,fileName)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        boton1.setOnClickListener {

            try{
                val writeExternalStoragePermission = ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                if(writeExternalStoragePermission!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),1234)
                    Toast.makeText(this,"No se pudo grabar",Toast.LENGTH_SHORT).show()
                }else{
                    val outputStreamWriter = OutputStreamWriter(FileOutputStream(file)).apply{
                        write(et1.text.toString())
                        flush()
                        close()
                    }
                    Toast.makeText (this, "Los datos fueron grabados correctamente", Toast.LENGTH_SHORT) .show ()
                }
            }catch (e:IOException){
                Log.e("ERROR",e.toString())
            }
        }

        boton2.setOnClickListener {

            try{
                val fIn = FileInputStream(file)
                val archivo = InputStreamReader(fIn)
                val br = BufferedReader(archivo)
                var linea = br.readLine()
                val contenido = StringBuilder()
                while (linea!=null){
                    contenido.append(linea)
                    linea = br.readLine()
                }
                br.close()
                archivo.close()
                et2.setText(contenido)

            }
            catch (e:IOException){
                Log.e("ERROR",e.toString())

            }

        }

    }
}
