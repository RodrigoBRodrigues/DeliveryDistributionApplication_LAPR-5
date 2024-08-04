using DDDSample1.Domain.Shared;
using System.Threading.Tasks;
using System.Collections.Generic;
using DDDSample1.Domain.Armazens;


namespace DDDSample1.Domain.EntregasInfo
{
    public interface IEntregaInfoRepository : IRepository<EntregaInfo, EntregaInfoId>
    {
        
        public int GetCountEntregas();

        public Task<List<EntregaInfo>> GetEntregasByArmazem(EntregaInfoId armazem);

    }


}