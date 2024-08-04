using System.Collections.Generic;
using DDDSample1.Domain.Shared;
using System;
using DDDSample1.Domain.Armazens;


namespace DDDSample1.Domain.Entregas
{
    public class EntregaMapper
    {
        public static Entrega CreatingToEntregaDomain(EntregaDto dto)
        {
            return new Entrega(dto.Id, dto.data, dto.massa, dto.tempoColocar, dto.tempoRetirar);
        }
        
        public static Entrega CreatingToEntegaDomainWithArmazem(EntregaDto dto, Armazem armazem){
            return new Entrega(dto.Id, dto.data, dto.massa, armazem, dto.tempoColocar, dto.tempoRetirar);
        }
        
        public static EntregaDto toEntregaDto(Entrega entrega)
        {
            return new EntregaDto
            {
                Id = entrega.Id.AsString(),
                data = entrega.data,
                armazemDesignacao = entrega.armazem != null ? entrega.armazem.Id.AsString() : "sem_armazem_associado",
                massa = entrega.massa != null ? entrega.massa.massa : 0.0,
                tempoColocar = entrega.tempoColocar != null ? entrega.tempoColocar.tempo : 0.0,
                tempoRetirar = entrega.tempoRetirar != null ? entrega.tempoRetirar.tempo : 0.0
            };

        }
    }
}