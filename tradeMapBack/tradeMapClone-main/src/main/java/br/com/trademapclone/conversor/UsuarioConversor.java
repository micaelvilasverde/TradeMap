package br.com.trademapclone.conversor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Lazy;

import br.com.trademapclone.dto.UsuarioDTO;
import br.com.trademapclone.modelo.AcaoFavorita;
import br.com.trademapclone.modelo.Usuario;

@Component
public class UsuarioConversor extends ConversorBase<Usuario, UsuarioDTO> {

	@Autowired
	@Lazy
	private AcaoFavoritaConversor acaoFavoritaConversor;
	
	@Override
	public UsuarioDTO converterEntidadeParaDto(Usuario entidade) {
		if (entidade == null) {
			return null;
		}
		
		return UsuarioDTO.builder()
		.login(entidade.getLogin())
		.email(entidade.getEmail())
		.nome(entidade.getNome())
		.ativo(entidade.getAtivo())
		.build();
	}
	
	public UsuarioDTO converterEntidadeParaDto(Usuario usuario, List<AcaoFavorita> acoes) {
		return UsuarioDTO.builder()
		.nome(usuario.getNome())
		.login(usuario.getLogin())
		.senha(usuario.getSenha())
		.email(usuario.getEmail())
		.ativo(usuario.getAtivo())
		.acoesFavoritas(acaoFavoritaConversor.converterEntidadesParaDtos(acoes))
		.build();
	}

	@Override
	public Usuario converterDtoParaEntidade(UsuarioDTO dto) {
		if (dto == null) {
			return null;
		}
		
		return Usuario.builder()
		.login(dto.getLogin())
		.senha(dto.getSenha())
		.email(dto.getEmail())
		.nome(dto.getNome())
		.ativo(dto.getAtivo())
		.build();
	}

}
