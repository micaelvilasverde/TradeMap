package br.com.trademapclone.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.trademapclone.conversor.UsuarioConversor;
import br.com.trademapclone.dto.UsuarioDTO;
import br.com.trademapclone.exception.NegocioException;
import br.com.trademapclone.modelo.AcaoFavorita;
import br.com.trademapclone.modelo.Usuario;
import br.com.trademapclone.repository.UsuarioRepository;
import br.com.trademapclone.service.IAcaoFavoritaService;
import br.com.trademapclone.service.IUsuarioService;

@Service
public class UsuarioService implements IUsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private IAcaoFavoritaService acaoFavoritaService;

	@Autowired
	private UsuarioConversor usuarioConversor;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UsuarioDTO registrar(UsuarioDTO usuarioDTO) {
		validarRegistro(usuarioDTO);
		Usuario usuario = usuarioConversor.converterDtoParaEntidade(usuarioDTO);
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuario.setAtivo(true);
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		return usuarioConversor.converterEntidadeParaDto(usuarioSalvo);
	}

	@Override
	public UsuarioDTO autenticar(UsuarioDTO usuarioDTO) {
		Usuario usuario = usuarioRepository.findByLogin(usuarioDTO.getLogin());
		if (usuario != null && passwordEncoder.matches(usuarioDTO.getSenha(), usuario.getSenha())) {
			return usuarioConversor.converterEntidadeParaDto(usuario);
		}
		return null;
	}

	@Override
	public UsuarioDTO consultar(String login) {
		Usuario usuario = usuarioRepository.findByLogin(login);
		if (usuario != null) {
			List<AcaoFavorita> acoes = acaoFavoritaService.listar(usuario);
			return usuarioConversor.converterEntidadeParaDto(usuario, acoes);
		}
		return null;
	}

	@Override
	public Usuario consultarEntidade(String login) {
		return usuarioRepository.findByLogin(login);
	}

	private void validarRegistro(UsuarioDTO usuarioDTO) {
		if (usuarioRepository.findByLogin(usuarioDTO.getLogin()) != null) {
			throw new NegocioException("Login já cadastrado");
		}
		if (usuarioRepository.findByEmail(usuarioDTO.getEmail()) != null) {
			throw new NegocioException("Email já cadastrado");
		}
	}
}
