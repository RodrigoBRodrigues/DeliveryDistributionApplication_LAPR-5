using DDDSample1.Domain.Shared;
using System.Threading.Tasks;
using System.Collections.Generic;
using DDDSample1.Domain.Armazens;


namespace DDDSample1.Domain.Entregas
{
    public interface IEntregaRepository : IRepository<Entrega, EntregaId>
    {
        public Task<List<Entrega>> GetEntregasBetweenDates(string startDate, string finishDate);
        public Task<List<Entrega>> GetEntregasByArmazem(ArmazemId armazem);
        
        public int GetCountEntregas();

        public Task<List<Entrega>> GetByDate(string date);

    }


}