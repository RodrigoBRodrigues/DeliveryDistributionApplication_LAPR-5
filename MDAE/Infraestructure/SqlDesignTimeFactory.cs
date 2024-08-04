using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Design;
using Microsoft.Extensions.Configuration;
using System.IO;

namespace DDDSample1.Infrastructure
{
    public class SqlDesignTimeFactory : IDesignTimeDbContextFactory<DDDSample1DbContext>
    {

        public IConfiguration Configuration { get; }
        public DDDSample1DbContext CreateDbContext(string[] args)
        {
            IConfiguration configs = new ConfigurationBuilder().SetBasePath(Directory.GetCurrentDirectory())
        .AddJsonFile("appsettings.json").Build();

            var builder = new DbContextOptionsBuilder()
            .UseSqlServer(configs.GetConnectionString("AzureDbConnection"));

            return new DDDSample1DbContext(builder.Options);
        }
    }
}