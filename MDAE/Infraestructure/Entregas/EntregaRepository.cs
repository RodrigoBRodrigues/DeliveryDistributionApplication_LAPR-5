using System;
using DDDSample1.Domain.Entregas;
using DDDSample1.Infrastructure.Shared;
using Microsoft.EntityFrameworkCore;
using System.Threading.Tasks;
using System.Linq;
using System.Collections.Generic;
using DDDSample1.Domain.Armazens;

namespace DDDSample1.Infrastructure.Entregas
{
    public class EntregaRepository : BaseRepository<Entrega, EntregaId>, IEntregaRepository
    {
        private readonly DbSet<Entrega> _objs;

        public EntregaRepository(DDDSample1DbContext context) : base(context.Entregas)
        {
            this._objs = context.Entregas;
        }

        public async Task<List<Entrega>> GetEntregasBetweenDates(string startDate, string finishDate)
        {
            return await this._objs.Where(r => r.data >= DateTime.Parse(startDate) && r.data < DateTime.Parse(finishDate))
            .ToListAsync();
        }

        public async Task<List<Entrega>> GetEntregasByArmazem(ArmazemId armazem)
        {
            return await this._objs.Where(r => r.armazem.Id == armazem)
                .ToListAsync();
        }
        
        public int GetCountEntregas()
        {
            string stmt = "SELECT * FROM dbo.entregas";
            return this._objs.FromSqlRaw(stmt).Count();
        }
        
        public async Task<List<Entrega>> GetByDate(string date)
        {
            return await this._objs.Where(r => r.data == DateTime.Parse(date))
                .ToListAsync();
        }
        
    }
}