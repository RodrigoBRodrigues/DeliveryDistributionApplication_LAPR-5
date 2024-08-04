using DDDSample1.Infrastructure.Shared;
using Microsoft.EntityFrameworkCore;
using DDDSample1.Domain.EntregasInfo;
using System.Threading.Tasks;
using System.Linq;
using System.Collections.Generic;
using System;


namespace DDDSample1.Infrastructure.Entregas
{
    public class EntregaInfoRepository : BaseRepository<EntregaInfo, EntregaInfoId>, IEntregaInfoRepository
    {
        private readonly DbSet<EntregaInfo> _objs;

        public EntregaInfoRepository(DDDSample1DbContext context) : base(context.EntregaInfos)
        {
            this._objs = context.EntregaInfos;
        }

        public async Task<List<EntregaInfo>> GetEntregasByArmazem(EntregaInfoId armazem)
        {
            return await this._objs.Where(r => r.armazem.Id == armazem)
                .ToListAsync();
        }
        
        public int GetCountEntregas()
        {
            string stmt = "SELECT * FROM dbo.entregas";
            return this._objs.FromSqlRaw(stmt).Count();
        }
      
    }
}