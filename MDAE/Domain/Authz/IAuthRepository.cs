using DDDSample1.Domain.Shared;
using System.Threading.Tasks;
using System.Collections.Generic;

namespace DDDSample1.Domain.Authz
{
    public interface IAuthRepository: IRepository<Auth, Utilizador>
    {
       
    }
}