using System.Collections.Generic;
using DDDSample1.Domain.Shared;
using System;
using DDDSample1.Domain.Armazens;


namespace DDDSample1.Domain.EntregasInfo
{
    public class EntregaInfoIdMapper
    {
   
        
        public static EntregaInfo CreatingToEntegaDomainWithArmazem(EntregaInfoDto dto, Armazem armazem, Armazem armazensAdjacentes){
            EntregaInfo info = new EntregaInfo();
            info.AssociateId(dto.Id);
            info.AssociateUrl(dto.urlArmazem);
            info.AssociateArmazem(armazem);
            info.AssociateArmazensAdjacentes(armazensAdjacentes);
            return info;
        }
        
        public static EntregaInfoDto toEntregaDto(EntregaInfo entrega)
        {
            return new EntregaInfoDto
            {
                Id = entrega.Id.AsString(),
                armazem = entrega.armazem.Id.AsString(),
                armazemAdjacente = entrega.armazemAdjacente.Id.AsString(),
                urlArmazem = entrega.urlArmazem.url,
            };

        }
    }
}