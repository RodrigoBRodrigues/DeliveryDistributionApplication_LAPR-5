using System.Threading.Tasks;
using System.Collections.Generic;
using System;
using DDDSample1.Domain.Armazens;
using DDDSample1.Domain.Entregas;


namespace DDDSample1.Domain.EntregasInfo
{
    public interface IEntregaInfoService
    {
        Task<List<EntregaInfoDto>> GetAllAsync();
        Task<EntregaInfoDto> GetByIdAsync(EntregaInfoId id);
        Task<List<EntregaInfoDto>> GetEntregasByArmazem(string id);
        Task<EntregaInfoDto> AddAsync(EntregaInfoDto dto);
    }
}