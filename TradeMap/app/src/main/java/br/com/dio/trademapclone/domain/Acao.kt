package br.com.dio.trademapclone.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Acao(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var loginUsuario: String = "",
    var codigo: String = "",
    var horario: String = "",
    var valor: Double = 0.0
) : Parcelable {

    @Ignore
    var status: String? = null

    companion object {
        const val UP = "UP"
        const val DOWN = "DOWN"
    }
}