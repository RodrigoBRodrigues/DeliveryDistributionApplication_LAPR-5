using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using DDDSample1.Domain.Entregas;
using DDDSample1.Domain.Armazens;

namespace DDDSample1.Infrastructure.Entregas
{
    internal class EntregaEntityTypeConfiguration : IEntityTypeConfiguration<Entrega>
    {
        public void Configure(EntityTypeBuilder<Entrega> builder)
        {
            builder.ToTable("Entregas", SchemaNames.DDDSample1);
            builder.HasKey(b => b.Id);
            builder.Property(b => b.Id).HasConversion(i => i.AsString(),
                i => new EntregaId(i));
            builder.OwnsOne(b => b.massa);
            builder.HasOne<Armazem>(b => b.armazem);
            builder.OwnsOne(b => b.tempoColocar);
            builder.OwnsOne(b => b.tempoRetirar);

            //builder.Property<bool>("_active").HasColumnName("Active");
        }
    }
}