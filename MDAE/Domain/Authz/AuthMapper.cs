namespace DDDSample1.Domain.Authz
{
    public class AuthMapper
    {
        public static Auth creatingToAuthDomain(AuthDto dto)
        {
            return new Auth(dto.Utilizador, dto.Nome, dto.Role, dto.Telemovel, dto.Email, dto.Ativo);
        }

        public static AuthDto toAuthDto(Auth auth)
        {
            return new AuthDto
            {
                Utilizador = auth.Utilizador.AsString(),
                Nome = auth.Nome != null ? auth.Nome.N.ToString() : "sem Nome",
                Role = auth.Role != null ? auth.Role.Nome.ToString() : "sem Funcao",
                Telemovel = auth.Telemovel != null ? auth.Telemovel.Nome.ToString() : "sem Telemovel",
                Email = auth.Email != null ? auth.Email.Nome.ToString() : "sem Email",
                Ativo = auth.Ativo != null ? auth.Ativo.ativo : true
            };
        }
    }
}