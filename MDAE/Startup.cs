using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using DDDSample1.Infrastructure;
using DDDSample1.Infrastructure.Shared;
using DDDSample1.Infrastructure.Armazens;
using DDDSample1.Infrastructure.Entregas;
using DDDSample1.Infrastructure.Authz;
using DDDSample1.Domain.Shared;
using DDDSample1.Domain.Armazens;
using DDDSample1.Domain.Entregas;
using DDDSample1.Domain.EntregasInfo;
using DDDSample1.Domain.Authz;
using Microsoft.EntityFrameworkCore.Proxies;


namespace DDDSample1
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {

            services.AddCors(o => o.AddPolicy("MyPolicy", builder =>{
                builder.AllowAnyOrigin().AllowAnyMethod().AllowAnyHeader();
            }));
            
            /* services.AddDbContext<DDDSample1DbContext>(opt =>
                 opt.UseLazyLoadingProxies().UseInMemoryDatabase("DDDSample1DB")
                 .ReplaceService<IValueConverterSelector, StronglyEntityIdValueConverterSelector>());*/
            services.AddDbContext<DDDSample1DbContext>(opt =>
                  opt.UseLazyLoadingProxies().UseSqlServer(Configuration.GetConnectionString("AzureDbConnection"))
                  .ReplaceService<IValueConverterSelector, StronglyEntityIdValueConverterSelector>());
            services.BuildServiceProvider().GetService<DDDSample1DbContext>().Database.Migrate();



            ConfigureMyServices(services);


            services.AddControllers().AddNewtonsoftJson();
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }
            else
            {
                // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
                app.UseHsts();
            }

                app.UseCors("MyPolicy");

            app.UseHttpsRedirection();

            app.UseRouting();

            app.UseAuthorization();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllers();
            });
        }

        public void ConfigureMyServices(IServiceCollection services)
        {
            services.AddTransient<IUnitOfWork, UnitOfWork>();


            services.AddTransient<IArmazemRepository, ArmazemRepository>();
            services.AddTransient<ArmazemService>();

            services.AddTransient<IEntregaRepository, EntregaRepository>();
            services.AddTransient<IEntregaService, EntregaService>();
            
            services.AddTransient<IEntregaInfoService, EntregaInfoService>();
            services.AddTransient<IEntregaInfoRepository, EntregaInfoRepository>();

            services.AddTransient<AuthService>();
            services.AddTransient<IAuthRepository, AuthRepository>();




        }
    }
}
