using System.Threading.Tasks;
using System.Collections.Generic;
using System;
using DDDSample1.Domain.Armazens;
using DDDSample1.Domain.Entregas;


namespace DDDSample1.Domain.Entregas
{
    public interface IEntregaService
    {
        Task<List<EntregaDto>> GetAllAsync();
        Task<EntregaDto> GetByIdAsync(EntregaId id);
        Task<List<EntregaDto>> GetEntregasByArmazem(string id);

         Task<List<EntregaDto>> GetByDate(string date);
        Task<List<EntregaDto>> GetBetweenDates(string startDate, string finishDate);
        Task<EntregaDto> AddAsync(EntregaDto dto);
        Task<EntregaDto> UpdateAsync(EntregaDto dto);
        Task<EntregaDto> DeleteAsync(EntregaId id);
        Task<Armazem> GetArmazemAsync(string id);
    }
}