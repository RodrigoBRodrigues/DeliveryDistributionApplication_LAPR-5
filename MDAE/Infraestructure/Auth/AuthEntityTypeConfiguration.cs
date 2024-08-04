using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using DDDSample1.Domain.Authz;
using System.Collections.Generic;
using System.Threading.Tasks;
using System.Linq;
using System;

namespace DDDSample1.Infrastructure.Authz
{
    internal class AuthEntityTypeConfiguration : IEntityTypeConfiguration<Auth>
    {
        public void Configure(EntityTypeBuilder<Auth> builder)
        {
            builder.ToTable("Utilizador", SchemaNames.DDDSample1);
            builder.HasKey(b => b.Utilizador);
            builder.OwnsOne(b => b.Nome);
            builder.OwnsOne(b => b.Role);
            builder.OwnsOne(b => b.Telemovel);
            builder.OwnsOne(b => b.Email);
            builder.OwnsOne(b => b.Ativo);
        }
    }
}
