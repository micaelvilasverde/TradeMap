package br.com.trademapclone.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.trademapclone.dto.UsuarioDTO;
import br.com.trademapclone.service.IUsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource extends ResourceBase<UsuarioDTO> {

	@Autowired
	private IUsuarioService usuarioService;

	@PostMapping("/registrar")
	public ResponseEntity<UsuarioDTO> registrar(@RequestBody @Valid UsuarioDTO usuarioDTO) {
		UsuarioDTO usuario = usuarioService.registrar(usuarioDTO);
		return responderItemCriado(usuario);
	}

	@PostMapping("/autenticar")
	public ResponseEntity<UsuarioDTO> autenticar(@RequestBody @Valid UsuarioDTO usuarioDTO) {
		UsuarioDTO usuario = usuarioService.autenticar(usuarioDTO);
		if (usuario == null) {
			return responderItemNaoEncontrado();
		}
		return responderSucessoComItem(usuario);
	}

	@GetMapping("/{login}")
	public ResponseEntity<UsuarioDTO> consultar(@PathVariable String login) {
		UsuarioDTO usuario = usuarioService.consultar(login);
		if (usuario == null) {
			return responderItemNaoEncontrado();
		}
		return responderSucessoComItem(usuario);
	}

}