using DDDSample1.Domain.Shared;
using System.Threading.Tasks;
using System.Collections.Generic;

namespace DDDSample1.Domain.Armazens
{
    public interface IArmazemRepository: IRepository<Armazem, ArmazemId>
    {
        public Task<Armazem> GetFromArmazem(ArmazemId designacao);

        public Task<Armazem> GetByDesignacao(Designacao designacao);

        public Task<List<Armazem>> GetAtivos(bool ativo);
    }
}