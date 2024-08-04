using System.Threading.Tasks;
using System.Collections.Generic;
using DDDSample1.Domain.Shared;

namespace DDDSample1.Domain.Authz
{
    public class AuthService
    {
        private readonly IUnitOfWork _unitOfWork;
        private readonly IAuthRepository _repo;

        public AuthService(IUnitOfWork unitOfWork, IAuthRepository repo)
        {
            this._unitOfWork = unitOfWork;
            this._repo = repo;
        }

        public async Task<List<AuthDto>> GetAllAsync()
        {
            var list = await this._repo.GetAllAsync();

            List<AuthDto> listDto = list.ConvertAll<AuthDto>
            (arm => AuthMapper.toAuthDto(arm));
            return listDto;
        }

        public async Task<AuthDto> GetByIdAsync(Utilizador id)
        {
            var arm = await this._repo.GetByIdAsync(id);

            if (arm == null)
                return null;

            return AuthMapper.toAuthDto(arm);
        }

        public async Task<AuthDto> AddAsync(AuthDto dto)
        {

            var arm = new Auth(dto.Utilizador, dto.Nome, dto.Role, dto.Telemovel, dto.Email, dto.Ativo);

            await this._repo.AddAsync(arm);

            await this._unitOfWork.CommitAsync();

            return AuthMapper.toAuthDto(arm);
        }

        public async Task<AuthDto> Cancel(AuthDto dto) {
            Auth utilizador = null;
            var utilizadoresData = await this._repo.GetAllAsync();
            for(int i = 0; i<utilizadoresData.Count; i++){
                if(utilizadoresData[i].Utilizador.Equals(dto.Utilizador)){
                    utilizador = utilizadoresData[i];
                    break;
                }
            }

            if (utilizador.ToString().Equals(null)){
                return null;
            }

            utilizador.Anon();
            await this._unitOfWork.CommitAsync();

            return AuthMapper.toAuthDto(utilizador);
        }

        public async Task<AuthDto> AnonimizarConta(AuthDto dto, Ativo ativo)
        {
            var utilizador = await this._repo.GetAllAsync();

            var userEncontrado = utilizador.FindLast(c => c.Utilizador.AsString().Equals(dto.Utilizador));

            if (userEncontrado == null)
                return null;

            // change all field
            userEncontrado.ChangeAtivo(ativo.ativo);
            userEncontrado.ChangeEmail("000000000");
            userEncontrado.ChangeTelemovel("000000000");
            userEncontrado.ChangeNome("000000000");

            await this._unitOfWork.CommitAsync();

            return AuthMapper.toAuthDto(userEncontrado);
        }

    }
}