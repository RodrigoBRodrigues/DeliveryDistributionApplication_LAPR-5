using System.Threading.Tasks;
using System.Collections.Generic;
using DDDSample1.Domain.Shared;
using DDDSample1.Domain.Armazens;
using System;

namespace DDDSample1.Domain.Entregas
{
    public class EntregaService : IEntregaService
    {
        private readonly IUnitOfWork _unitOfWork;
        private readonly IEntregaRepository _repo;

        private readonly IArmazemRepository _repoArmazem;


        public EntregaService(IUnitOfWork unitOfWork, IEntregaRepository repo, IArmazemRepository repoArmazem)
        {
            this._unitOfWork = unitOfWork;
            this._repo = repo;
            this._repoArmazem = repoArmazem;
        }
        
        protected EntregaService()
        {
            // Empty Constructor for Proxy use only.
        }

        public async Task<List<EntregaDto>> GetAllAsync()
        {
            var list = await this._repo.GetAllAsync();

            foreach (Entrega ent in list)
            {
                
            }

            List<EntregaDto> listDto = list.ConvertAll<EntregaDto>(ent => EntregaMapper.toEntregaDto(ent));

            return listDto;
        }

        public async Task<EntregaDto> GetByIdAsync(EntregaId id)
        {
            var entrega = await this._repo.GetByIdAsync(id);

            if (entrega == null)
                return null;

            return EntregaMapper.toEntregaDto(entrega);
        }
        

        
        public async Task<List<EntregaDto>> GetEntregasByArmazem(string id)
        {
            var entregas = await this._repo.GetEntregasByArmazem(new ArmazemId(id));

            List<EntregaDto> dtos = entregas.ConvertAll<EntregaDto>(ent => EntregaMapper.toEntregaDto(ent));
            
            if (entregas == null)
                throw new BusinessRuleValidationException("Não existem entregas associados ao armazem." + id);

            return dtos;
        }

        public async Task<List<EntregaDto>> GetBetweenDates(string startDate, string finishDate)
        {
            var entregasEntreAsDatas = await this._repo.GetEntregasBetweenDates(startDate, finishDate);

            List<EntregaDto> dtos = entregasEntreAsDatas.ConvertAll<EntregaDto>(ent => EntregaMapper.toEntregaDto(ent));

            return dtos;
        }

        public async Task<EntregaDto> AddAsync(EntregaDto dto)
        {
            var armazem = await GetArmazemAsync(dto.armazemDesignacao);

            dto.Id = _repo.GetCountEntregas().ToString();
            
            var entrega = EntregaMapper.CreatingToEntegaDomainWithArmazem(dto, armazem);
            
            
            await this._repo.AddAsync(entrega);
            await this._unitOfWork.CommitAsync();

            return EntregaMapper.toEntregaDto(entrega);
        }

        public async Task<EntregaDto> UpdateAsync(EntregaDto dto)
        {
            
            var armazem = await GetArmazemAsync(dto.armazemDesignacao);

            var entrega = await this._repo.GetByIdAsync(new EntregaId(dto.Id));
            //var armazem = await GetArmazemAsync(dto.armazemDesignacao);

            if (entrega == null)
                return null;

            // change all field
            entrega.ChangeData(dto.data);
            entrega.ChangeMassa(Convert.ToDouble(dto.massa));
            entrega.AssociateArmazem(armazem);
            entrega.ChangeTempoColocar(Convert.ToDouble(dto.tempoColocar));
            entrega.ChangeTempoRetirar(Convert.ToDouble(dto.tempoRetirar));


            await this._unitOfWork.CommitAsync();

            return EntregaMapper.toEntregaDto(entrega);
        }

        public async Task<EntregaDto> DeleteAsync(EntregaId id)
        {
            var entrega = await this._repo.GetByIdAsync(id);

            if (entrega == null)
                return null;

            this._repo.Remove(entrega);
            await this._unitOfWork.CommitAsync();

            return EntregaMapper.toEntregaDto(entrega);

        }


        public async Task<Armazem> GetArmazemAsync(string id)
        {
            var armazem = await this._repoArmazem.GetFromArmazem(new ArmazemId(id));

            if (armazem == null)
                throw new BusinessRuleValidationException("Não é possível associar uma entrega a um armazém inexistente.");

            return armazem;
        }

         public async Task<List<EntregaDto>> GetByDate(string date){
             var entregasEntreAsDatas = await this._repo.GetByDate(date);

            List<EntregaDto> dtos = entregasEntreAsDatas.ConvertAll<EntregaDto>(ent => EntregaMapper.toEntregaDto(ent));

            return dtos;
         }

    }
}