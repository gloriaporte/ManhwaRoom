package br.edu.ifsp.aluno.gloriaporte.manhwaroom.data

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Manhwa (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var titulo: String,
    var genero: String,
    var status: String
)