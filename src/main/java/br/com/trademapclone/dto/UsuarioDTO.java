package br.com.trademapclone.dto;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class UsuarioDTO {

	@NotBlank(message = "O login é obrigatório")
	@Size(min = 3, max = 50, message = "O login deve ter entre 3 e 50 caracteres")
	private String login;

	@NotBlank(message = "A senha é obrigatória")
	@Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
	private String senha;

	@NotBlank(message = "O email é obrigatório")
	@Email(message = "Email inválido")
	private String email;

	@NotBlank(message = "O nome é obrigatório")
	@Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
	private String nome;

	private List<AcaoFavoritaDTO> acoesFavoritas;
	private Boolean ativo;

}
