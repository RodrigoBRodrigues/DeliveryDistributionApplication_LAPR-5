using DDDSample1.Domain.Armazens;
using DDDSample1.Domain.Shared;
using DDDSample1.Infrastructure.Shared;
using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;
using System.Threading.Tasks;
using System.Linq;
using System;

namespace DDDSample1.Infrastructure.Armazens
{
    public class ArmazemRepository : BaseRepository<Armazem, ArmazemId>, IArmazemRepository
    {
        private readonly DbSet<Armazem> _objs;
        public ArmazemRepository(DDDSample1DbContext context) : base(context.Armazens)
        {
            _objs = context.Armazens;
        }

        public async Task<Armazem> GetFromArmazem(ArmazemId id)
        {
            var armazens = await this._objs.Where(r => r.Id == id).ToListAsync();

            if (armazens == null)
                throw new BusinessRuleValidationException("Id inválido!");
            return armazens[0];
        }

        public async Task<Armazem> GetByDesignacao(Designacao designacao) {
            return await this._objs.Where(r => r.Designacao.Nome == designacao.Nome).FirstOrDefaultAsync();
        }

        public async Task<List<Armazem>> GetAtivos(bool ativo) {
            return await this._objs.Where(r => r.Ativo.ativo == ativo).ToListAsync();
        }

        // public System.Threading.Tasks.Task<System.Collections.Generic.List<DDDSample1.Domain.Armazens.Armazem>> GetCountEntregas()
        // {
        //     string stmt = "SELECT * FROM dbo.ArmazemData";
        //     return this._objs.FromSqlRaw(stmt).ToListAsync<Armazem>();
        // }
    }
}