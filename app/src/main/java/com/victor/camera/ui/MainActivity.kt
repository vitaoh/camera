package com.victor.camera.ui

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.victor.camera.databinding.ActivityMainBinding
import com.victor.camera.factory.FiltroFactory
import com.victor.camera.factory.TipoFiltro
import com.victor.camera.helper.CameraHelper

class MainActivity : AppCompatActivity(), CameraHelper.Callback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var cameraHelper: CameraHelper
    private lateinit var originalBitmap: Bitmap
    private val REQUEST_CAMERA_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        cameraHelper = CameraHelper(this, this)

        // Botão para tirar foto
        binding.btnCapture.setOnClickListener {
            verificarPermissaoETirarFoto()
        }

        // --- CONFIGURAÇÃO DO NOVO DROPDOWN (SUBSTITUIU O SPINNER) ---
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            TipoFiltro.entries.toTypedArray()
        )

        binding.autoCompleteFiltros.setAdapter(adapter)

        // Define "Sem Filtro" como texto inicial padrão
        binding.autoCompleteFiltros.setText(TipoFiltro.SEM_FILTRO.toString(), false)

        binding.autoCompleteFiltros.setOnItemClickListener { _, _, position, _ ->
            val tipoFiltro = TipoFiltro.entries[position]
            aplicarFiltro(tipoFiltro)
        }
    }

    private fun verificarPermissaoETirarFoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED) {
            cameraHelper.tirarFoto()
        } else {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_CODE)
        }
    }

    private fun aplicarFiltro(tipo: TipoFiltro?) {
        if (!::originalBitmap.isInitialized) return

        if (tipo == null || tipo == TipoFiltro.SEM_FILTRO) {
            binding.imageView.setImageBitmap(originalBitmap)
        } else {
            val bitmapFiltrado = FiltroFactory.criar(tipo).aplicar(originalBitmap)
            binding.imageView.setImageBitmap(bitmapFiltrado)
        }
    }

    override fun onFotoRecebida(bitmap: Bitmap) {
        // 1. Armazena a foto original
        originalBitmap = bitmap

        // 2. Tenta pegar o filtro pelo texto atual do seletor
        val textoAtual = binding.autoCompleteFiltros.text.toString()
        val filtroSelecionado = TipoFiltro.entries.find { it.toString() == textoAtual }

        // 3. Se achou um filtro, aplica. Se não, mostra a original.
        if (filtroSelecionado != null) {
            aplicarFiltro(filtroSelecionado)
        } else {
            binding.imageView.setImageBitmap(bitmap)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_CODE && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            cameraHelper.tirarFoto()
        } else {
            Toast.makeText(this, "Permissão negada", Toast.LENGTH_SHORT).show()
        }
    }
}