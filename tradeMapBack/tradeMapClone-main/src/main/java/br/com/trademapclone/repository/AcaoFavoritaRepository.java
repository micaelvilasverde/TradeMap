package br.com.trademapclone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.trademapclone.modelo.AcaoFavorita;
import br.com.trademapclone.modelo.Usuario;

public interface AcaoFavoritaRepository extends JpaRepository<AcaoFavorita, Long> {

	List<AcaoFavorita> findByUsuario(Usuario usuario);

	AcaoFavorita findByCodigoAndUsuario(String codigo, Usuario usuario);

}
