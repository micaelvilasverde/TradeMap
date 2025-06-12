package br.com.dio.trademapclone.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.dio.trademapclone.MainActivity
import br.com.dio.trademapclone.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observaUsuario()
        configuraBotaoLogin()
    }

    private fun configuraBotaoLogin() {
        binding.button.setOnClickListener {
            val usuario = binding.textInputLayout.editText?.text.toString()
            viewModel.login(usuario)
        }
    }

    private fun observaUsuario() {
        viewModel.usuario.observe(viewLifecycleOwner) {
            (activity as MainActivity).supportActionBar?.show()
            val direcao = LoginFragmentDirections.actionLoginFragmentToFirstFragment()
            findNavController().navigate(direcao)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}