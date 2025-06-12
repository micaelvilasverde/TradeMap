package br.com.trademapclone.service;

import java.util.List;

import br.com.trademapclone.dto.AcaoFavoritaDTO;
import br.com.trademapclone.modelo.AcaoFavorita;
import br.com.trademapclone.modelo.Usuario;

public interface IAcaoFavoritaService {

	AcaoFavorita salvar(AcaoFavorita acaoFavorita);

	AcaoFavoritaDTO salvar(AcaoFavoritaDTO acaoFavoritaDTO);

	List<AcaoFavorita> listar(Usuario usuario);

	List<AcaoFavorita> listarSemDuplicidade();
}
