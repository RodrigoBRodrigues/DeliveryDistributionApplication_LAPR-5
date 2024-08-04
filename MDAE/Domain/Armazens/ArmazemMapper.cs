namespace DDDSample1.Domain.Armazens
{
    public class ArmazemMapper
    {
        public static Armazem creatingToArmazemDomain(ArmazemDto dto)
        {
            return new Armazem(dto.Id,
                              dto.Designacao,
                              dto.Rua,dto.CodigoPostal,
                              dto.Latitude,dto.Longitude,dto.Altitude, dto.Ativo);
        }
        
        public static ArmazemDto toArmazemDto(Armazem armazem)
        {
            return new ArmazemDto
            {
                Id = armazem.Id.AsString(),
                Designacao = armazem.Designacao != null ? armazem.Designacao.Nome.ToString() : "sem_designação_associada",
                Rua = armazem.Endereco != null ? armazem.Endereco.Rua.ToString () : "sem_rua_associada",
                CodigoPostal = armazem.Endereco.CodigoPostal != null ? armazem.Endereco.CodigoPostal.ToString() : "sem_código_postal_associado",
                Latitude = armazem.Localizacao != null ? armazem.Localizacao.Latitude : 0.0,
                Longitude = armazem.Localizacao!= null ? armazem.Localizacao.Longitude : 0.0,
                Altitude = armazem.Localizacao != null ? armazem.Localizacao.Altitude : 0.0,
                Ativo = armazem.Ativo != null ? armazem.Ativo.ativo : true
            };

        }
    }
}