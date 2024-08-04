using DDDSample1.Domain.Authz;
using DDDSample1.Domain.Shared;
using System.Collections.Generic;
using System.Threading.Tasks;
using System.Linq;
using System;
using DDDSample1.Infrastructure.Shared;
using Microsoft.EntityFrameworkCore;

namespace DDDSample1.Infrastructure.Authz
{
   public class AuthRepository : BaseRepository <Auth, Utilizador>, IAuthRepository
    {
        private readonly DbSet<Auth> _objs;

        public AuthRepository(DDDSample1DbContext context) : base(context.Authz)
        {
            _objs = context.Authz;
        }
    }
}