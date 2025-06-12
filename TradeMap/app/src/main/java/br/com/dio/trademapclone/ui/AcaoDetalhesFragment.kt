package br.com.dio.trademapclone.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.dio.trademapclone.R
import br.com.dio.trademapclone.databinding.FragmentAcaoDetalheBinding
import br.com.dio.trademapclone.domain.Acao
import br.com.dio.trademapclone.extension.formatarMoeda
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import org.koin.androidx.viewmodel.ext.android.viewModel

class AcaoDetalhesFragment : Fragment() {

    private var _binding: FragmentAcaoDetalheBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AcaoViewModel by viewModel()
    private val arguments by navArgs<AcaoDetalhesFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAcaoDetalheBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val acao = arguments.acao
        viewModel.getUltimo(acao.codigo).observe(viewLifecycleOwner) {
            binding.textViewCodigo.text = it.codigo
            binding.textViewValor.text = it.valor.formatarMoeda()
        }
        observaAcoes(acao)
    }

    private fun observaAcoes(acao: Acao) {
        viewModel.getTodos(acao.codigo).observe(viewLifecycleOwner) { acoes ->
            val pontos = mutableListOf<Entry>()
            acoes?.forEachIndexed { index, acao ->
                pontos.add(Entry(index.toFloat(), acao.valor.toFloat()))
            }
            criarGrafico(pontos)
        }
    }

    private fun criarGrafico(pontos: MutableList<Entry>) {
        val lineDataSet = LineDataSet(pontos, "Ações").apply {
            lineWidth = 1.8f
            valueTextColor = ContextCompat.getColor(requireContext(), R.color.white)
            color = ContextCompat.getColor(requireContext(), R.color.colorAccent)
            fillColor = ContextCompat.getColor(requireContext(), R.color.colorAccent)
            circleColors =
                mutableListOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            fillAlpha = 170
            setDrawFilled(true)
            setDrawCircles(true)
        }
        binding.lineChart.apply {
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
            }
            axisLeft.apply {
                setDrawGridLines(false)
            }
            axisRight.isEnabled = false
            axisLeft.textColor = ContextCompat.getColor(requireContext(), android.R.color.white)
            xAxis.textColor = ContextCompat.getColor(requireContext(), android.R.color.white)
            description.isEnabled = false
            legend.isEnabled = false
            animateY(1000)
            data = LineData(lineDataSet)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}