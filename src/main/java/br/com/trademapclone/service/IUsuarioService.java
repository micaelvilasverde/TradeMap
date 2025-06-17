package br.com.trademapclone.service;

import br.com.trademapclone.dto.UsuarioDTO;
import br.com.trademapclone.modelo.Usuario;

public interface IUsuarioService {

	UsuarioDTO registrar(UsuarioDTO usuarioDTO);
	
	UsuarioDTO autenticar(UsuarioDTO usuarioDTO);
	
	UsuarioDTO consultar(String login);
	
	Usuario consultarEntidade(String login);

}
