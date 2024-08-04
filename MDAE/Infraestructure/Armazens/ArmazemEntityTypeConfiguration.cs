using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using DDDSample1.Domain.Armazens;
using DDDSample1.Domain.Entregas;

namespace DDDSample1.Infrastructure.Armazens
{
    internal class ArmazemEntityTypeConfiguration : IEntityTypeConfiguration<Armazem>
    {
        public void Configure(EntityTypeBuilder<Armazem> builder)
        {
            builder.ToTable("Armazens", SchemaNames.DDDSample1);
            builder.HasKey(b => b.Id);
            builder.Property(b => b.Id).HasConversion(i => i.AsString(),
                i => new ArmazemId(i));
            builder.OwnsOne(b => b.Designacao);
            builder.OwnsOne(b => b.Endereco);
            builder.OwnsOne(b => b.Localizacao);
            builder.HasMany<Entrega>(i => i.Entregas);
            builder.OwnsOne(b => b.Ativo);

        }
    }
}


