using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using DDDSample1.Domain.EntregasInfo;
using DDDSample1.Domain.Armazens;

namespace DDDSample1.Infrastructure.EntregasInfo
{
    internal class EntregaInfoEntityTypeConfiguration : IEntityTypeConfiguration<EntregaInfo>
    {
        public void Configure(EntityTypeBuilder<EntregaInfo> builder)
        {
            builder.ToTable("EntregasInfo", SchemaNames.DDDSample1);
            builder.HasKey(b => b.Id);
            builder.Property(b => b.Id).HasConversion(i => i.AsString(),
                i => new EntregaInfoId(i));
            builder.OwnsOne(b => b.urlArmazem);
            builder.HasOne(b => b.armazem);
            builder.HasOne<Armazem>(i => i.armazemAdjacente);

            //builder.Property<bool>("_active").HasColumnName("Active");
        }
    }
}