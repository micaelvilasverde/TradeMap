package br.com.dio.trademapclone.ui

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.dio.trademapclone.R
import br.com.dio.trademapclone.databinding.ItemAcaoBinding
import br.com.dio.trademapclone.domain.Acao
import br.com.dio.trademapclone.extension.formatarMoeda

class AcaoAdapter(
    private val acoes: MutableList<Acao> = mutableListOf(),
    private val onClick: (Acao) -> Unit
) : RecyclerView.Adapter<AcaoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAcaoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val acao = acoes[position]
        holder.bind(acao)
    }

    override fun getItemCount(): Int = acoes.size

    fun adicionar(acao: Acao) {
        val contemAcao = acoes.any { it.codigo == acao.codigo }
        if (!contemAcao) {
            acoes.add(acao)
            notifyDataSetChanged()
        }
    }

    fun atualizar(acao: Acao?) {
        if (acao == null) return
        acoes.find { it.codigo == acao.codigo }?.let {
            val status = if (acao.valor >= it.valor) {
                Acao.UP
            } else {
                Acao.DOWN
            }
            it.valor = acao.valor
            it.status = status
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(private val binding: ItemAcaoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(acao: Acao) {
            binding.textViewCodigo.text = acao.codigo
            binding.textViewValor.text = acao.valor.formatarMoeda()
            if (acao.status == Acao.UP) {
                animacaoBackground(R.color.green)
                binding.imageViewSeta.setImageDrawable(
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.ic_baseline_arrow_drop_up_green
                    )
                )
            } else if (acao.status == Acao.DOWN) {
                animacaoBackground(R.color.red)
                binding.imageViewSeta.setImageDrawable(
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.ic_baseline_arrow_drop_down_red
                    )
                )
            }
            binding.root.setOnClickListener { onClick(acao) }
        }

        private fun animacaoBackground(color: Int) {
            binding.constraintLayout.setBackgroundColor(ContextCompat.getColor(itemView.context, color))
            Handler(Looper.getMainLooper()).postDelayed({
                binding.constraintLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.gray
                    )
                )
            }, 1000)
        }
    }
}