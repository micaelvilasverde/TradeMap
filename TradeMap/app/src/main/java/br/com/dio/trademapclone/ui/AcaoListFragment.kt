package br.com.dio.trademapclone.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.dio.trademapclone.R
import br.com.dio.trademapclone.databinding.DialogAcaoBinding
import br.com.dio.trademapclone.databinding.FragmentAcaoListBinding
import br.com.dio.trademapclone.domain.UsuarioLogado
import org.koin.androidx.viewmodel.ext.android.viewModel

class AcaoListFragment : Fragment() {

    private var _binding: FragmentAcaoListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AcaoViewModel by viewModel()
    private val adapter: AcaoAdapter by lazy {
        AcaoAdapter(UsuarioLogado.usuario.acoesFavoritas.toMutableList()) {
            val direcao = AcaoListFragmentDirections.actionFirstFragmentToAcaoDetalhesFragment(it)
            findNavController().navigate(direcao)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAcaoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuraRecyclerView()
        observaUltimaAcao()
        configuraBotaoAdicionarAcao()
        observaAcaoAdicionada()
    }

    private fun observaAcaoAdicionada() {
        viewModel.acaoAdicionada.observe(viewLifecycleOwner) {
            adapter.adicionar(it)
        }
    }

    private fun configuraBotaoAdicionarAcao() {
        binding.floatingActionButton.setOnClickListener {
            val dialogBinding = DialogAcaoBinding.inflate(layoutInflater)
            val dialog = AlertDialog.Builder(context)
                .setTitle("Adicionar")
                .setView(dialogBinding.root)
                .create()
            dialog.show()
            dialogBinding.buttonAdicionarAcao.setOnClickListener {
                val codigo = dialogBinding.input.text.toString()
                viewModel.adicionarAcao(codigo)
                dialog.dismiss()
            }
        }
    }

    private fun observaUltimaAcao() {
        viewModel.getUltimo().observe(viewLifecycleOwner) {
            adapter.atualizar(it)
        }
    }

    private fun configuraRecyclerView() {
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}