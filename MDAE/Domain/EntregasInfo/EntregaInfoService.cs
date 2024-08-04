using System.Threading.Tasks;
using System.Collections.Generic;
using DDDSample1.Domain.Shared;
using DDDSample1.Domain.Armazens;
using System;

namespace DDDSample1.Domain.EntregasInfo
{
    public class EntregaInfoService : IEntregaInfoService
    {
        private readonly IUnitOfWork _unitOfWork;
        private readonly IEntregaInfoRepository _repo;

        private readonly IArmazemRepository _repoArmazem;


        public EntregaInfoService(IUnitOfWork unitOfWork, IEntregaInfoRepository repo, IArmazemRepository repoArmazem)
        {
            this._unitOfWork = unitOfWork;
            this._repo = repo;
            this._repoArmazem = repoArmazem;
        }
        
        protected EntregaInfoService()
        {
            // Empty Constructor for Proxy use only.
        }

        public async Task<List<EntregaInfoDto>> GetAllAsync()
        {
            var list = await this._repo.GetAllAsync();

            foreach (EntregaInfo ent in list)
            {
                
            }

            List<EntregaInfoDto> listDto = list.ConvertAll<EntregaInfoDto>(ent => EntregaInfoIdMapper.toEntregaDto(ent));

            return listDto;
        }

        public async Task<EntregaInfoDto> GetByIdAsync(EntregaInfoId id)
        {
            var entrega = await this._repo.GetByIdAsync(id);

            if (entrega == null)
                return null;

            return EntregaInfoIdMapper.toEntregaDto(entrega);
        }
        

        

        public async Task<EntregaInfoDto> AddAsync(EntregaInfoDto dto)
        {
            var armazem = await GetArmazemAsync(dto.armazem);
            var armazemAdjacente = await GetArmazemAsync(dto.armazemAdjacente);
           
            var entrega = EntregaInfoIdMapper.CreatingToEntegaDomainWithArmazem(dto, armazem, armazemAdjacente);
            
            
            await this._repo.AddAsync(entrega);
            await this._unitOfWork.CommitAsync();

            return EntregaInfoIdMapper.toEntregaDto(entrega);
        }


        public async Task<Armazem> GetArmazemAsync(string id)
        {
            var armazem = await this._repoArmazem.GetFromArmazem(new ArmazemId(id));

            if (armazem == null)
                throw new BusinessRuleValidationException("Não é possível associar uma entrega a um armazém inexistente.");

            return armazem;
        }
        
        public async Task<List<EntregaInfoDto>> GetEntregasByArmazem(string id)
        {
            var entregas = await this._repo.GetEntregasByArmazem(new EntregaInfoId(id));

            List<EntregaInfoDto> dtos = entregas.ConvertAll<EntregaInfoDto>(ent => EntregaInfoIdMapper.toEntregaDto(ent));
            
            if (entregas == null)
                throw new BusinessRuleValidationException("Não existem entregas associados ao armazem." + id);

            return dtos;
        }

    }
}