using Microsoft.EntityFrameworkCore;
using DDDSample1.Domain.Entregas;
using DDDSample1.Domain.Armazens;
using DDDSample1.Domain.EntregasInfo;
using DDDSample1.Domain.Authz;
using DDDSample1.Infrastructure.Entregas;
using DDDSample1.Infrastructure.Authz;
using DDDSample1.Infrastructure.Armazens;
using DDDSample1.Infrastructure.EntregasInfo;


namespace DDDSample1.Infrastructure
{
    public class DDDSample1DbContext : DbContext
    {


        public DbSet<Entrega> Entregas { get; set; }

        public DbSet<Armazem> Armazens { get; set; }
        public DbSet<EntregaInfo> EntregaInfos { get; set; }

        public DbSet<Auth> Authz { get; set; }

         
        public DDDSample1DbContext(DbContextOptions options) : base(options)
        {

        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.ApplyConfiguration(new EntregaEntityTypeConfiguration());

            modelBuilder.ApplyConfiguration(new ArmazemEntityTypeConfiguration());
            modelBuilder.ApplyConfiguration(new EntregaInfoEntityTypeConfiguration());
            modelBuilder.ApplyConfiguration(new EntregaEntityTypeConfiguration());
            modelBuilder.ApplyConfiguration(new AuthEntityTypeConfiguration());


            modelBuilder.Entity<Armazem>()
                .HasKey(mto => new { mto.Id });

            modelBuilder.Entity<Entrega>()
                .HasKey(mto => new { mto.Id });
            
            modelBuilder.Entity<EntregaInfo>()
                .HasKey(mto => new { mto.Id });

                modelBuilder.Entity<Auth>()
                .HasKey(mto => new { mto.Utilizador});

            modelBuilder.Entity<Entrega>().HasOne(x => x.armazem).WithMany(y => y.Entregas);

        }
    }
}