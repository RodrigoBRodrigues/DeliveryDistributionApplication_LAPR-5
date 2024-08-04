using System.Threading.Tasks;
using System.Collections.Generic;
using DDDSample1.Domain.Shared;

namespace DDDSample1.Domain.Armazens
{
    public class ArmazemService
    {
        private readonly IUnitOfWork _unitOfWork;
        private readonly IArmazemRepository _repo;

        public ArmazemService(IUnitOfWork unitOfWork, IArmazemRepository repo)
        {
            this._unitOfWork = unitOfWork;
            this._repo = repo;
        }

        public async Task<List<ArmazemDto>> GetAllAsync()
        {
            var list = await this._repo.GetAllAsync();
            
            List<ArmazemDto> listDto = list.ConvertAll<ArmazemDto>
            (arm => ArmazemMapper.toArmazemDto(arm));
            return listDto;
        }

        public async Task<ArmazemDto> GetByIdAsync(ArmazemId id)
        {
            var arm = await this._repo.GetByIdAsync(id);
            
            if(arm == null)
                return null;

            return ArmazemMapper.toArmazemDto(arm);
        }

        public async Task<ArmazemDto> AddAsync(ArmazemDto dto)
        {

            var arm = new Armazem (dto.Id, dto.Designacao, dto.Rua,dto.CodigoPostal, dto.Latitude, dto.Longitude, dto.Altitude, dto.Ativo);

            await this._repo.AddAsync(arm);

            await this._unitOfWork.CommitAsync();

            return ArmazemMapper.toArmazemDto(arm);
        }

       public async Task<ArmazemDto> UpdateAsync(ArmazemDto dto)
        {
            var arm = await this._repo.GetByIdAsync(new ArmazemId(dto.Id));

            if (arm == null)
                return null;

            // change all field
            arm.ChangeDesignacao(dto.Designacao);
            arm.ChangeEndereco(dto.Rua,dto.CodigoPostal);
            arm.ChangeLocalizacao(dto.Longitude,dto.Latitude,dto.Altitude);
            arm.ChangeAtivo(dto.Ativo);
            await this._unitOfWork.CommitAsync();

            return ArmazemMapper.toArmazemDto(arm);
        }

        public async Task<ArmazemDto> PatchAtivo(ArmazemDto dto, Ativo ativo)
        {
            var arm = await this._repo.GetByIdAsync(new ArmazemId(dto.Id));

            if (arm == null)
                return null;

            // change all field
            arm.ChangeAtivo(ativo.ativo);
            await this._unitOfWork.CommitAsync();

            return ArmazemMapper.toArmazemDto(arm);
        }


         public async Task<ArmazemDto> DeleteAsync(ArmazemId id)
        {
            var arm = await this._repo.GetByIdAsync(id); 

            if (arm == null)
                return null;   
            
            this._repo.Remove(arm);
            await this._unitOfWork.CommitAsync();

            return ArmazemMapper.toArmazemDto(arm);
        }

        public async Task<ArmazemDto> GetByDesignacao(string designacao)
        {
            var arm = await this._repo.GetByDesignacao(new Designacao(designacao));

            if (arm == null)
                return null;

            return ArmazemMapper.toArmazemDto(arm);
        }

        public async Task<List<ArmazemDto>> GetAtivos(bool ativo)
        {
            var list = await this._repo.GetAtivos(ativo);
            
            List<ArmazemDto> listDto = list.ConvertAll<ArmazemDto>
            (arm => ArmazemMapper.toArmazemDto(arm));
            return listDto;
        }

    }
}